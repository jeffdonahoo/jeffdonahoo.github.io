import java.net.*;
import java.io.*;

/**
 *  Messenger is a class that can send/receive messages to/from all other
 *  messengers.
 */
public class Messenger extends MulticastSocket {

  private static final int PORT = 5000;
  private static final InetAddress ADDRESS = getAddress();
  /**
   * Maximum number of characters in a message
   */
  public static final int MAXMESSAGE = 30;

  private static InetAddress getAddress() {
    try {
      return InetAddress.getByName("224.5.12.14");
    } catch(Exception e) {
      System.err.println("Unable to create broadcast address");
      System.exit(1);
    }
    return null;
  }

  /**
   *  Sets up a messenger instance for communication
   */
  public Messenger() throws IOException {
    super(PORT);
    joinGroup(ADDRESS);
    setTimeToLive(1);
  }

  /**
   *  Sends the message to all other messenger instances.  Message size is
   *  bounded by MAXMESSAGE constant.
   *
   *@param  message message to send
   *@exception IOException if send fails or message too long
   */
  public void send(String message) throws IOException {
    if (message.length() > MAXMESSAGE) {
      throw new IOException("Message too long");
    }
    byte[] bytesToSend = message.getBytes();
    DatagramPacket sendPacket = new DatagramPacket(bytesToSend,
      bytesToSend.length, ADDRESS, PORT);
    send(sendPacket);
  }

  /**
   *  Receives a message from other messenger instances.  recv() <b>blocks</b>
   *  until it receives a message.  Calling close() while a messenger instance
   *  is blocked on recv() will cause an exception.
   *
   *@return  message from other messenger
   *@exception IOException if receive fails
   */
  public String recv() throws IOException {
    DatagramPacket receivePacket =
    new DatagramPacket(new byte[MAXMESSAGE], MAXMESSAGE);
    receive(receivePacket);
    return new String(receivePacket.getData(), 0, receivePacket.getLength());
  }

  /**
   *  Closes this datagram socket.
   */
  public void close() {
    try {
      leaveGroup(ADDRESS);
    } catch(IOException e) {}
    super.close();
  }

  /**
   *  Example main that sends "Hi" and receives response.  You can run two
   *  instances of this program and they will say "Hi" to each other (hence,
   *  the 3 second sleep to make sure both are started).
   */
  public static void main(String args[]) throws Exception {
    Messenger m = new Messenger();

    Thread.sleep(3000);  // 3 second sleep

    m.send("Hi");
    System.out.println(m.recv());

    m.close();
  }
}