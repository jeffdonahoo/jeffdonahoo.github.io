import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadXMLObject {

  public static void main(String[] args) throws IOException {
    XMLDecoder in = new XMLDecoder(new FileInputStream("data"));
    boolean done = false;
    while (!done) {
      try {
        System.out.println(in.readObject());
      } catch (ArrayIndexOutOfBoundsException e) {
        done = true;
      }
    }
    in.close();
  }
}
