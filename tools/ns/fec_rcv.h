/*
 * Author:  Jeff Donahoo
 * Date:    6.2.99
 *
 * FEC Receiver implementation
 *
 * When started, this receiver sends a request to the sender agent.  It then tracks to make 
 * sure it receives the necessary packets for decoding from the sender.
 */

#ifndef fec_rcv_h
#define fec_rcv_h

#include "fec.h"

/* FEC Receiver Agent Class */
class FECRcvAgent : public FECAgent {
public:
    FECRcvAgent();          /* Constructor */
    void recv(Packet* pkt, Handler*); /* Handle datagram reception */
    int command(int argc, const char*const* argv); /* Handle TCL script commands */
protected:
    bool rcvDone();        /* True if receiver has received necessary packets for decode */
    void SendRequest();    /* Make receiver agent sent request to sender */
    void start();          /* Start receiver by sending request to sender */
    bool blkDone(int blk); /* True if block blk has sufficient pkts to decode */
    void finish();         /* Executed when receiver has enough packets to decode */

    struct BlkStatus       /* Structure to track the packets received for each block */
    {
	int *pktStatus;           // Vector of integers to count instances of pkt received
	int pktCount;             // Number of packets in blk
    } *blkStatus;
    
    bool done;            /* True if receiver agent has sufficient packets for each blk */
    int debug_;           /* True if want receiver trace messages */
};

#endif
