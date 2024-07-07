import java.net.MulticastSocket;
import java.net.DatagramPacket; 
import java.net.InetAddress;
import java.io.IOException;

public class SendUDPMulticast {

  public static void main(String args[]) throws Exception {

    if ((args.length < 2) || (args.length > 3)) // Test for correct # of args
      throw new IllegalArgumentException(
        "Parameter(s): <Multicast Addr> <Port> [<TTL>]");

    InetAddress destAddr = InetAddress.getByName(args[0]); // Destination
    if (!destAddr.isMulticastAddress())  // Test if multicast address
      throw new IllegalArgumentException("Not a multicast address");

    int destPort = Integer.parseInt(args[1]);  // Destination port
    int TTL;   // Time To Live for datagram
    if (args.length == 3)
      TTL = Integer.parseInt(args[2]);
    else
      TTL = 1;  // Default TTL

    byte[] msg = new VoteMsgText(true,true, 888, 1000001L).toWire();

    MulticastSocket sock = new MulticastSocket(); // Multicast socket
    sock.setTimeToLive(TTL);                      // Set TTL for all datagrams

    // Create and send a datagram
    DatagramPacket message = new DatagramPacket(msg, msg.length,
                                                destAddr, destPort);
    sock.send(message);

    sock.close();
  }
}
