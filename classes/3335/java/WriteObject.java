import java.awt.Point;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

public class WriteObject {

  public static void main(String[] args) throws IOException {
    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data"));
    Point p = new Point(1, 1);
    out.writeObject(p);
    // out.reset();
    p.setLocation(2, 2);
    out.writeObject(p);
    out.close();
  }
}
