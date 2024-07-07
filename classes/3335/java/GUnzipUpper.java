import java.io.*;
import java.util.zip.GZIPInputStream;

public class GUnzipUpper {
  public static void main(String[] args) throws IOException {

    if (args.length != 3) {
      throw new IllegalArgumentException("Parameters:  <input> <output> <encoding>");
    }

    String inFile = args[0];
    String outFile = args[1];
    String encoding = args[2];

    InputStream in = new GZIPInputStream(new FileInputStream(inFile));
    OutputStream out = new FileOutputStream(outFile);

    byte[] buffer = new byte[3];
    int bytesRead;
    while ((bytesRead = in.read(buffer)) != -1) {
      String s = new String(buffer, 0, bytesRead, encoding).toUpperCase();
      out.write(s.getBytes(encoding));
    }
    in.close();
    out.close();
  }
}
