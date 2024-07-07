import java.awt.Point;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadObject {

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    ObjectInputStream in = new ObjectInputStream(new FileInputStream("data"));
    Point p = (Point) in.readObject();
    System.out.println(p);
    p = (Point) in.readObject();
    System.out.println(p);
    in.close();
  }
}
