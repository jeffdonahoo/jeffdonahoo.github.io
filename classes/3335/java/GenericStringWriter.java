import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class GenericStringWriter {
  public static void main(String[] args) throws IOException {

    if (args.length != 3) {
      throw new IllegalArgumentException("Parameters:  <string> " +
        "<encoding (e.g. UnicodeLittleUnmarked)> <output>");
    }

    String string = args[0];
    String encoding = args[1];
    String outFile = args[2];

    OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outFile), encoding);
    out.write(string);

    out.close();
  }
}