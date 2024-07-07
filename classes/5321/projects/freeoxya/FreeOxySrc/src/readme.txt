//*****************************************
// Readme for FreeOxy
//
// authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
// Nov. 24
//*****************************************
1.Usage:
	java FreeOxy <port> <ssl port> <maxthreads> [<TTL>]
  <port> :the listen port for FreeOxy server (>1024)
  <ssl port> :the SSL listen port for FreeOxy server (>1024 && !=<port>)
  <maxthreads> :the connections can be handled at the same time (recommend >10)
  <TTL> :the max hops a request can be passed among the FreeOxy servers
	 when TTL=0, the server will forward every request to WWW server directly
         (default=0)

2.Config:
  Common.DEBUG :set true to see debug message
  Common.SSL :set true to turn on SSL connection manually
  Common.SLEEP :set a proper value for better performance and connectivity



<The End>

