/*
 * Author:  Jeff Donahoo
 * Date:    6.2.99
 *
 * FEC Sender implementation
 *
 * Waits for request from receiver agent for packets (barks if
 * timeout).  After request reception, k+extraPkts packets are sent for
 * every block
 */

#include <iostream.h>
#include "fec_snd.h"

#define REQUESTTIMEOUT 1000   /* Maximum amount of time for sender to wait on receiver to send request */

/* Class to bridge between TCL and C++ for FEC Sender Agent */
static class FECSndClass : public TclClass {
public:
	FECSndClass() : TclClass("Agent/FEC/Snd") {}
	TclObject* create(int, const char*const*) {
		return (new FECSndAgent());
	}
} class_FECSndAgent;

/* Class to bridge between TCL and C++ for FEC header */
static class FECHeaderClass : public PacketHeaderClass {
public:
        FECHeaderClass() : PacketHeaderClass("PacketHeader/FEC",
					     sizeof(hdr_fec)) {}
} class_fechdr;

/* FEC Sender Agent constructor */
FECSndAgent::FECSndAgent() : fecsnd_timer_(this), nextPkt(1), nextBlk(1), requestReceived(false)
{
    bind_bw("rate_", &rate_);     // in bits per seconds
    bind("packetSize_", &size_);  // in bytes
    interval_ = (double)(size_ << 3)/(double)rate_; // Change size to bits per sec
    bind("extraPkts_", &extraPkts_); // send k+extraPkts_ packets per block
    bind_bool("debug_", &debug_);  // True if want sender debug messages
}

/* Function to start sender listening for receiver request */
void FECSndAgent::start()
{
    if (debug_)
    {
	cout << "Sender started\n";
    }
    fecsnd_timer_.resched(REQUESTTIMEOUT);  // Set timeout of sender waiting on request
}

/* Function to handle sender send completion */
void FECSndAgent::stop()
{
    /* Cancel timer if one is active */
    if (fecsnd_timer_.status() == TIMER_PENDING) 
    {
	fecsnd_timer_.cancel();
    }

    if (debug_)
    {
	cout << "Sender stopped\n";
    }
}

/* Function to handle simulation timeouts */
void FECSndAgent::timeout(int)
{
    /* If simulation timeout to send packet, then send packet */
    if (requestReceived)
    {
	sendpkt();
    }
    else /* If receiver request timeout, signal error */
    {
	cerr << "Sender timeout on receiver request\n";
	exit(1);
    }
}

/* Function to send a packet and schedule next packet send */
void FECSndAgent::sendpkt()
{

    Packet* p = allocpkt();   // Build new packet and get offset
    hdr_fec* h = (hdr_fec*)p->access(off_fec_);
    if (!getNextPkt(h->pkt(), h->blk()))  // New packet to send?
    {
	stop();  // If not, stop
    }
    else
    {
	send(p, 0);
	if (debug_)
	{
	    cout << "(Pkt: " << h->pkt() << ", Blk: " << h->blk() << ") sent\n";
	}
	fecsnd_timer_.resched(interval_);  // Schedule simulation time for next packet
    }
}

// Return next packet in pkt and blk.  Return false if no more packets to send
bool FECSndAgent::getNextPkt(int& pkt, int& blk)
{
    static bool morePkts = true;  // True if there are more packets to send

    if (!morePkts)  // Test if more packets to send
    {
	return false;
    }

    pkt = nextPkt;
    blk = nextBlk;
    if (nextPkt == (k + extraPkts_))   // Sent last packet in block?
    {
	if (nextBlk == B)   // Sent last packet in last block?
	{
	    morePkts = false;    // Done sending
	}
	nextPkt = 1;       // Send first packet in next block
	nextBlk++;
    }
    else  // Not sent last packet in block
    {
	nextPkt++;           // Send next packet in current block
    }

    return true;
}

/* Function to handle receiver request reception */
void FECSndAgent::recv(Packet* pkt, Handler*)
{
    Packet::free(pkt);  
    if (requestReceived)  // If already received request
    {
        return;
    }

    requestReceived = true;    // Note that receiver request has been received
    if (debug_)
    {
	cout << "Request received by sender\n";
    }

    sendpkt();         // Send first packet
}

/* Function to process commands from TCL script */
int FECSndAgent::command(int argc, const char*const* argv)
{
    if (argc == 5) 
    {
	if (strcmp(argv[1], "start") == 0) {
	    B = atoi(argv[2]);  // Parameters to start
	    n = atoi(argv[3]);
	    k = atoi(argv[4]);
	    start();
	    return (TCL_OK);
	}
    }
    else if (argc == 2)
    {
	if (strcmp(argv[1], "stop") == 0) {
	    stop();
	    return (TCL_OK);
	}
    }
    return (Agent::command(argc, argv));
}

/* Function to signal simulation timeout for agent */
void FECSndTimer::expire(Event *e) {
  a_->timeout(0);
}
