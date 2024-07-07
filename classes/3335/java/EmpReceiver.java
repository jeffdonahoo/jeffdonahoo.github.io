import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream

public class EmpReceiver {

  public static void main(String[] args) throws IOException {

    if (args.length != 1)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int servPort = Integer.parseInt(args[0]);

    // Create a server socket to accept client connection requests
    ServerSocket servSock = new ServerSocket(servPort);

    Socket clntSock = servSock.accept();     // Get client connection

    System.out.println("Handling client at " +
      clntSock.getInetAddress().getHostAddress() + " on port " +
        clntSock.getPort());

    DataInputStream in = new DataInputStream(clntSock.getInputStream());

    Employee e = Employee.read(in);

    System.out.println(e);
  }
}
