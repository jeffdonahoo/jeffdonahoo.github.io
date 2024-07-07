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

#ifndef fec_snd_h
#define fec_snd_h

#include "fec.h"

class FECSndAgent;

/* Timer for sender events such as sending */
class FECSndTimer : public TimerHandler {
  public:
    FECSndTimer(FECSndAgent *a) : TimerHandler() { a_ = a; }
  protected:
    virtual void expire(Event *e);
    FECSndAgent *a_;
};

/* FEC Sender Agent Class */
class FECSndAgent : public FECAgent {
  public:
    FECSndAgent();          /* Sender agent constructor function */
    /* Function to handle TCL commands */
    int command(int argc, const char*const* argv); 
    virtual void timeout(int);  /* Function to handle simulation timer expiration */
    void recv(Packet* pkt, Handler*);  /* Handle start request message from receiver agent */
  protected:
    void start();            /* Get Sender ready to receive receiver request */
    void stop();             /* Sender is finished sending */
    void sendpkt();          /* Sends next packet */
    bool getNextPkt(int&, int&); /* Return next packet to be sent */
    double interval_;        /* Time between packets (computed from rate_) */
    double rate_;            /* Send rate (bps) - given by TCL script */
    int debug_;              /* Debug messages for sender agent */
    int extraPkts_;          /* Number of packets beyond k to send for each block */
    FECSndTimer fecsnd_timer_;  /* Timer for FEC sender agent simulation events */
    int nextPkt;             /* Number of next packet */
    int nextBlk;             /* Number of next block */
    bool requestReceived;    /* True if request received from receiver */
};

#endif
