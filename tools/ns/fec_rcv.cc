/*
 * Author:  Jeff Donahoo
 * Date:    6.2.99
 *
 * FEC Receiver implementation
 *
 * When started, this receiver sends a request to the sender agent.  It then tracks to make 
 * sure it receives the necessary packets for decoding from the sender.
 */

#include <iostream.h>
#include "fec_rcv.h"

/* Class to bridge between TCL and C++ for FEC Receiver Agent */
static class FECRcvClass : public TclClass {
public:
	FECRcvClass() : TclClass("Agent/FEC/Rcv") {}
	TclObject* create(int, const char*const*) {
		return (new FECRcvAgent());
	}
} class_FECRcvAgent;

/* FEC Receiver Agent constructor function */
FECRcvAgent::FECRcvAgent() : done(false)
{
    bind_bool("debug_", &debug_);
}

/* Handles reception of packets from sender agent */
void FECRcvAgent::recv(Packet* pkt, Handler*)
{
    if (done)  // If already received necessary packets, toss packet
    {
	Packet::free(pkt);
	return;
    }
    
    // Retrieve packet and block information from packet
    hdr_fec *h = (hdr_fec*)pkt->access(off_fec_);

    int rblk = h->blk();
    int rpkt = h->pkt();

    if (debug_)
    {
	if (blkDone(rblk))  // If block is done, discard packet
	{
	    cout << "Discarded (Pkt: " << h->pkt() << ", Blk:" << h->blk() << ")" << endl;
	}
	else  // If block is not done, receive packet
	{
	    cout << "Received (Pkt: " << h->pkt() << ", Blk:" << h->blk() << ")" << endl;
	}
    }

    // Check if particular packet has already been received
    if (blkStatus[rblk].pktStatus[rpkt]++ == 0)
    {
	blkStatus[rblk].pktCount++;
    }
    
    // Test if receiver has received all necessary packets for all blocks to decode
    if (rcvDone())
    {
	done = true;
	finish();
    }
    
    Packet::free(pkt);
}

/* Function to send request for file to sender agent */
void FECRcvAgent::SendRequest()
{
    Packet* p = allocpkt();
    send(p, 0);
}

/* Function executed when receiver agent receives last necessary packet for decoding */
void FECRcvAgent::finish()
{
    if (debug_)
    {
	cout << "Receiver Done\n";
    }
}

/* Returns true if each block has k unique packets */
bool FECRcvAgent::rcvDone()
{
    for (int blk = 1; blk <= B; blk++)
    {
	if (!blkDone(blk))
	{
	    return false;
	}
    }
    return true;
}

/* True if block blk has k unique packets */
bool FECRcvAgent::blkDone(int blk)
{
    if (blkStatus[blk].pktCount < k)
    {
	return false;
    }
    
    return true;
}

/* Function to start receiver by initializing block status structure and sending request */
void FECRcvAgent::start()
{
    // Make block status vector for all blocks (numbered from 1)
    blkStatus = new BlkStatus [B + 1];
    
    // For each block, allocate pkt array.
    for (int blk = 0; blk <= B; blk++)
    {
	blkStatus[blk].pktStatus = new int [n + 1];
	blkStatus[blk].pktCount = 0;
	for (int pkt = 0; pkt <= n; pkt++)
	{
	    blkStatus[blk].pktStatus[pkt] = 0;
	}
    }
    
    SendRequest();   // Send request to sender agent
}

/* Function to process commands from TCL script */
int FECRcvAgent::command(int argc, const char*const* argv)
{
    if (argc == 5) {
	if (strcmp(argv[1], "start") == 0) {
	    B = atoi(argv[2]);  // Parameters to start
	    n = atoi(argv[3]);
	    k = atoi(argv[4]);
	    start();
	    return (TCL_OK);
	}
    }
    return (Agent::command(argc, argv));
}
