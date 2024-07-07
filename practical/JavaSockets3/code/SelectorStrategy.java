import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public abstract class SelectorStrategy {
  public SelectionKey createServerChannel(Selector selector, int port) throws IOException {
    // Create nonblocking server socket channel bound to the specified port
    ServerSocketChannel servChan = ServerSocketChannel.open();
    servChan.configureBlocking(false);
    servChan.socket().bind(new InetSocketAddress(port));

    // Set selector to handle new client connections
    return servChan.register(selector, SelectionKey.OP_ACCEPT);
  }

  public SocketChannel handleAccept(SelectionKey key) throws IOException {
    // Get server channel
    ServerSocketChannel servChan = (ServerSocketChannel) key.channel();
    // Accept and set new client socket channel to nonblocking
    SocketChannel clntChan = servChan.accept();
    // Cannot register blocking channel
    clntChan.configureBlocking(false);

    return clntChan;
  }

  public abstract long handleRead(SelectionKey key) throws IOException;

  public abstract long handleWrite(SelectionKey key) throws IOException;
}
