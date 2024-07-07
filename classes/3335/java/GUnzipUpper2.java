import java.io.*;
import java.util.zip.GZIPInputStream;

public class GUnzipUpper2 {
  public static void main(String[] args) throws IOException {

    if (args.length != 3) {
      throw new IllegalArgumentException("Parameters:  <input> <output> <encoding>");
    }

    String inFile = args[0];
    String outFile = args[1];
    String encoding = args[2];

    Reader in = new InputStreamReader(new GZIPInputStream(
      new FileInputStream(inFile)), encoding);
    Writer out = new OutputStreamWriter(
      new FileOutputStream(outFile), encoding);

    char[] buffer = new char[3];
    int charsRead;
    while ((charsRead = in.read(buffer)) != -1) {
      String s = new String(buffer, 0, charsRead).toUpperCase();
      out.write(s);
    }
    in.close();
    out.close();
  }
}
