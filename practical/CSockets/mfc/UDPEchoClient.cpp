/*
 *   A UDP echo client implemented using the MFC CSocket libaray
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

const int ECHOMAX = 255; // Longest string to echo

void DieWithError(char* errorMessage); // External error handling function

int main(int argc, char* argv[]){
 
  // Initialize MFC and print an error on failure
  if (!AfxWinInit(::GetModuleHandle(NULL), NULL, ::GetCommandLine(), 0)) {
    DieWithError("Fatal Error: MFC initialization failed");
  }
  // Initialize the AfxSocket
  AfxSocketInit( NULL);
  
  // Test for correct number of arguments  
  if ((argc < 3 ) || (argc > 4)) {
    cerr << "Usage: " << argv[0] << " <echo server IP> <echo word> [<echo port>]" << endl;
    exit(1);
  }

  char* servIP = argv[1];      // First arg: server IP address (dotted quad) 
  char* echoString = argv[2];  // Second arg: string to echo

  // Take the length of the echo string
  int echoStringLen = strlen(echoString);        
  if (echoStringLen > ECHOMAX) { // Check input length
    DieWithError("Echo word too long");
  }

  // Use given port, if any; otherwise, use 7, the well-known port
  // for the echo service 
  UINT echoServPort = (argc == 4)? atoi(argv[3]) : 7; 

  CSocket echoClient;       // Socket descriptor

  // Create a datagram/UDP socket
  if (echoClient.Create(0,SOCK_DGRAM,NULL) == 0) {
    DieWithError("Create() failed");
  }

  // Send the echo string to the server
  if (echoClient.SendTo(echoString, echoStringLen, echoServPort,
      (LPCSTR)servIP, 0) != echoStringLen) {
    DieWithError("SendTo() sent a different number of bytes than expected");
  }

  // Source address of echo
  SOCKADDR_IN fromAddr;       

  // In-out address size for RecvFrom()
  int fromSize = sizeof(fromAddr); 

  // Declare a buffer for received echo string
  char echoBuffer[ECHOMAX+1]; 

  // Receive a response and get the response string length
  int respStringLen = echoClient.ReceiveFrom(echoBuffer, 
    ECHOMAX, (SOCKADDR*)&fromAddr, &fromSize, 0);
  if (respStringLen != echoStringLen) {
    DieWithError("ReceiveFrom() failed");
  }

  cout << "Received from " << inet_ntoa(fromAddr.sin_addr) << endl;

  // Null-terminate the received data
  echoBuffer[respStringLen] = '\0';
  cout << echoBuffer << endl; // Print the echoed arg

  echoClient.Close(); // Close the connection
  return 0;
}
