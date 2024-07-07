import java.io.IOException;
import java.nio.*;
import java.nio.channels.*;
import java.util.Iterator;
import java.net.InetSocketAddress;

/**
 * TCP server that handles connections on multiple ports. The
 * implementation uses a single threaded and nonblocking I/O.
 */
public class TCPEchoServerSelector {

  public static final int BUFSIZE = 256;
  public static final int TIMEOUTMILLIS = 3000;

  public static void main(String args[]) throws IOException {

    if (args.length != 1) { // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");
    }

    // Create a selector to multiplex client connections.
    Selector selector = Selector.open();

    // Create a listening socket channel, bind to the given port
    ServerSocketChannel listnChannel = ServerSocketChannel.open();
    listnChannel.socket().bind(
            new InetSocketAddress(Integer.parseInt(args[0])));

    // Register the listening channel with the selector for accepting
    // Note well: it's done via *Channel* method.
    // We can ignore the returned key.
    listnChannel.register(selector,SelectionKey.OP_ACCEPT);

    while (true) {
      // Wait for some I/O or until timeout expires
      if (selector.select(TIMEOUTMILLIS) == 0) {
        System.out.print(".");
        continue;
      }
      /* At least one channel is ready for I/O */

      // Get iterator on set of keys with I/O to process
      Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
      while (keyIter.hasNext()) {
        SelectionKey key = keyIter.next(); // key indicates channel and ops

        if (key.isAcceptable()) { // ServerSocket has a pending connection
	  SocketChannel clntChan =
	               ((ServerSocketChannel)key.channel()).accept();
	  // register the new connection so we know when it's ready to read
	  clntChan.register(selector, SelectionKey.OP_READ,
		      ByteBuffer.allocate(BUFSIZE));
        } else { // same key will never be acceptable and read/writable.
	  if (key.isReadable()) {
	    // Client socket channel has pending data?
	    SocketChannel clntChan = (SocketChannel) key.channel();
	    ByteBuffer buf = (ByteBuffer) key.attachment();
	    long bytesRead = clntChan.read(buf);
	    if (bytesRead == -1) { // Did the other end close?
	      clntChan.close(); // XXX do we need to unregister it?
	    } else if (bytesRead > 0) {
	      // Indicate via key that reading/writing are both of interest
	      key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	    }
	  }
	  if (key.isValid() && key.isWritable()) {
	    /* Channel is available for writing, and
	       key is valid (i.e., client channel not closed). */
	    // retrieve data read earlier
	    ByteBuffer buf = (ByteBuffer) key.attachment();
	    buf.flip(); // Prepare buffer for writing
	    SocketChannel clntChan = (SocketChannel) key.channel();
	    long bytesWritten = clntChan.write(buf);
	    if (!buf.hasRemaining()) { // Buffer completely written?
	      // nothing left, so no longer interested in writes
	      key.interestOps(SelectionKey.OP_READ);
	    }
	    buf.compact(); // make room for more data to be read in
	  }
	} // else (not acceptable)
        keyIter.remove(); // remove key from set
      }
    }
  }
}
