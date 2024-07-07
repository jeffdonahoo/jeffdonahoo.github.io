/*
 *   A UDP echo server implemented using the MFC CSocket libaray
 *   Copyright (C) 2001 
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

#include <iostream>     // For cout, cerr
#include <afxsock.h>    // For CSocket 

using namespace std;

const int ECHOMAX = 255;               // Longest string to echo
void DieWithError(char* errorMessage); // External error handling function

int main(int argc, char* argv[]){

  // Initialize MFC and print an error on failure
  if (!AfxWinInit(::GetModuleHandle(NULL), NULL, ::GetCommandLine(), 0)) {
    DieWithError("Fatal Error: MFC initialization failed");
  }
  // Initialize the AfxSocket
  AfxSocketInit(NULL);
 
  // Test for correct number of arguments
  if (argc != 2) {              
    cerr << "Usage: " << argv[0] << " <UDP server port>" << endl;
    exit(1);
  }

  UINT echoServPort = atoi(argv[1]);  // First arg: local port
  CSocket echoServer;                 // Socket

  // Create socket for sending/receiving datagrams
  if (echoServer.Create(echoServPort, SOCK_DGRAM, NULL)== 0) {
    DieWithError("Create() failed");
  }

  cout << "Echo server running ..." << endl;

  for(;;) { // Run forever

    // Client address
    SOCKADDR_IN echoClntAddr; 

    // Set the size of the in-out parameter
    int clntAddrLen = sizeof(echoClntAddr);

    // Buffer for echo string
    char echoBuffer[ECHOMAX]; 
 
    // Block until receive message from a client
    int recvMsgSize = echoServer.ReceiveFrom(echoBuffer, 
	  ECHOMAX, (SOCKADDR*)&echoClntAddr, &clntAddrLen, 0);
    if (recvMsgSize < 0) {
      DieWithError("RecvFrom() failed");
    }

    cout << "Handling client " << inet_ntoa(echoClntAddr.sin_addr) << endl;

    // Send received datagram back to the client
    if (echoServer.SendTo(echoBuffer, recvMsgSize, (SOCKADDR*)&echoClntAddr,
        clntAddrLen, 0) != recvMsgSize) {
      DieWithError("SendTo() failed.");
    }
    // NOT REACHED
  }

  return 0;
}
