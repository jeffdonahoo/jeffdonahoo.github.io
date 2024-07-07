import java.net.MulticastSocket;
import java.net.DatagramPacket; 
import java.net.InetAddress;
import java.io.IOException;

public class RecvUDPMulticast {

  public static final int MAX_WIRE_LENGTH = 2000;

  public static void main(String[] args) throws Exception {

    if (args.length != 2)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Multicast Addr> <Port>");

    InetAddress address = InetAddress.getByName(args[0]);  // Multicast address
    if (!address.isMulticastAddress())  // Test if multicast address
      throw new IllegalArgumentException("Not a multicast address");

    int port = Integer.parseInt(args[1]);  // Multicast port
    MulticastSocket sock = new MulticastSocket(port); // for receiving
    sock.joinGroup(address);                     // Join the multicast group

    // Create and receive a datagram
    DatagramPacket packet = new DatagramPacket(
      new byte[MAX_WIRE_LENGTH], MAX_WIRE_LENGTH);
    sock.receive(packet);

    VoteMsg vote = VoteMsgText.fromWire(packet.getData());
    System.out.println("Received vote info: " + vote);
    sock.close();
  }
}
