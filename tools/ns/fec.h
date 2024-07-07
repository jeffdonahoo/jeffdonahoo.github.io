/*
 * Author:  Jeff Donahoo
 * Date:    6.2.99
 *
 * Contains common functionality to FEC Sender and Receiver agent
 */

#ifndef mftp_h
#define mftp_h

#include "agent.h"

/* FEC header */
struct hdr_fec {
    int blk_;    /* Datagram block */
    int pkt_;    /* Datagram packet */
    
    int &blk() { return(blk_); }  /* Accessor Functions */
    int &pkt() { return(pkt_); }
};

/* Common data/methods for FEC agents */
class FECAgent : public Agent {
  protected:
    FECAgent();    /* Constructor function */
    int off_fec_;  /* Offset into FEC header */
    int B;         /* Number of file blocks */
    int k;         /* k of (k,n) FEC encoding */
    int n;         /* n of (k,n) FEC encoding */
};

#endif
