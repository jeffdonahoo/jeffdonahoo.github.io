******************************* CSocket TCP/UDP Echo Server/Client ****************************************
To run the program in VC++, you must first complete three steps:

1) Create a Win32 Console Application in a simple empty project and include in the project DieWithError.cpp
along with the server or client .cpp file for the application you wish
to run.  For the iterative and threaded TCP echo servers, you must
also include HandleTCPClient.cpp.

2) Change project settings:
     - Open the Project Settings window ("Alt+F7")
     - Select the General tab
     - Choose use MFC in a shared DDL in the Microsoft Foundation Classes box

3) Enter command line argument(s):
    - Select the Debug tab in the Project Settings window, and enter the program argument(s):
      *** for echo client *** 
      enter echo server IP or name, echo string and, optionally, echo server port (the system will select 7 as 
      the default echo port unless you specify your own port). 
      
     *** for echo server *** 
     enter echo port
