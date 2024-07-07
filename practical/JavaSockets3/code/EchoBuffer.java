import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class EchoBuffer extends SelectorStrategy {

  private int bufSize;

  public EchoBuffer(int bufSize) {
    this.bufSize = bufSize;
  }
  
  @Override
  public SocketChannel handleAccept(SelectionKey key) throws IOException {
    SocketChannel clntChan = super.handleAccept(key);
    // Register channel with selector and attach byte buffer
    clntChan.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
    
    return clntChan;
  }

  public long handleRead(SelectionKey key) throws IOException {
    SocketChannel clntChan = (SocketChannel) key.channel();
    ByteBuffer buf = (ByteBuffer) key.attachment();
    long bytesRead = clntChan.read(buf);
    if (bytesRead == -1) { // Did the other end close?
      clntChan.close();
    } else if (bytesRead > 0) {
      // Register both read and write with the selector
      key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }
    
    return bytesRead;
  }

  public long handleWrite(SelectionKey key) throws IOException {
    ByteBuffer buf = (ByteBuffer) key.attachment();
    buf.flip(); // Prepare buffer for writing
    SocketChannel clntChan = (SocketChannel) key.channel();
    long bytesWritten = clntChan.write(buf);
    if (!buf.hasRemaining()) { // Buffer completely written?
      // No longer interested in writes
      key.interestOps(SelectionKey.OP_READ);
    }
    // Yuck! This copies bytes to head of buffer
    buf.compact(); // Prepare buffer for reading
    
    return bytesWritten;
  }
}
