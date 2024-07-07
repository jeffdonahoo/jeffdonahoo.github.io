import java.io.*;

public class ChecksumOutputStream extends FilterOutputStream {

  private byte checksum = 0;

  public ChecksumOutputStream(OutputStream out) {
    super(out);
  }

  public void write(int b) throws IOException {
    super.write(b);
    checksum ^= (byte) b;
  }

  public void write(byte[] b) throws IOException {
    write(b, 0, b.length);
  }

  public void write(byte[] b, int off, int len) throws IOException {
    for (int i=off; i < len; i++) {
      write(b[i]);
    }
  }

  public void close() throws IOException {
    super.write(checksum);
    super.close();
  }
}