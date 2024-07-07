/*
 * Author:  Jeff Donahoo
 * Date:    6.2.99
 *
 * Contains common functionality to FEC Sender and Receiver agent
 */

#include <iostream.h>
#include "fec.h"

/* Constructor for FEC agents */
FECAgent::FECAgent() : Agent(PT_FEC)
{
    bind("off_fec_", &off_fec_);
    B = k = n = -1;   /* B, k, and n initialized by start so set to bad values */
}
