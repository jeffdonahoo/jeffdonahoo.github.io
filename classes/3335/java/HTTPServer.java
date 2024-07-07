import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream

public class HTTPServer {

  private static final int BUFSIZE = 32;   // Size of receive buffer

  public static void main(String[] args) throws IOException {

    if (args.length != 1)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int servPort = Integer.parseInt(args[0]);

    // Create a server socket to accept client connection requests
    ServerSocket servSock = new ServerSocket(servPort);

    int recvMsgSize;   // Size of received message
    byte[] byteBuffer = new byte[BUFSIZE];  // Receive buffer

    for (;;) { // Run forever, accepting and servicing connections
      Socket clntSock = servSock.accept();     // Get client connection

      System.out.println("Handling client at " +
        clntSock.getInetAddress().getHostAddress() + " on port " +
             clntSock.getPort());

      BufferedReader in = new BufferedReader(
        new InputStreamReader(clntSock.getInputStream()));
      OutputStream out = clntSock.getOutputStream();

      // Request of the form "GET <file> HTTP/1.X\r\n"
      String request = in.readLine();
      System.out.println(request);

      String filename = request.substring(5); // Kill "GET /"
      filename = filename.substring(0, filename.indexOf(' '));
      System.out.println("Fetching file " + filename);

      // Open file and copy to socket
      FileInputStream fin = new FileInputStream(filename);
      int nxtChar;
      while ((nxtChar = fin.read()) != -1) {
        out.write(nxtChar);
      }

      clntSock.close();  // Close the socket.  We are done with this client!
    }
    /* NOT REACHED */
  }
}
