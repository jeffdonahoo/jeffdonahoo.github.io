import java.net.*;  // for Socket
import java.io.*;   // for IOException and Input/OutputStream

public class EmpSender {

  public static void main(String[] args) throws IOException {

    if (args.length != 2)
      throw new IllegalArgumentException("Parameter(s): <Server> <Port>");

    String server = args[0];       // Server name or IP address

    int servPort = Integer.parseInt(args[1]);

    // Create socket that is connected to server on specified port
    Socket socket = new Socket(server, servPort);
    System.out.println("Connected to server...sending echo string");

    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

    Employee e = Employee.read();
    e.write(out);

    socket.close();  // Close the socket and its streams
  }
}