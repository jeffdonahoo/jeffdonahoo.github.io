/* CHANGES FROM UNIX VERSION                                                   */
/*                                                                             */
/* 1.  Changed header files.                                                   */

#include <stdio.h>      /* for fprintf() */
#include <winsock.h>    /* for getservbyname() and htons() */
#include <stdlib.h>     /* for atoi() */

unsigned short ResolveService(char service[], char protocol[])
{
    struct servent *serv;        /* Structure containing service information */
    unsigned short port;         /* Port to return */

    if ((port = atoi(service)) == 0)  /* Is port numeric?  */
    {
        /* Not numeric.  Try to find as name */
        if ((serv = getservbyname(service, protocol)) == NULL)  
        {
            fprintf(stderr, "getservbyname() failed");
            exit(1);
        }
        else 
            port = serv->s_port;   /* Found port (network byte order) by name */
    }
    else
        port = htons(port);  /* Convert port to network byte order */

    return port;
}
