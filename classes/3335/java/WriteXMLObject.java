import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteXMLObject {

  public static void main(String[] args) throws IOException {
    XMLEncoder out = new XMLEncoder(new FileOutputStream("data"));
    Student bob = new Student("1234", "Bob", 22);
    Student jane = new Student("4321", "Jane", 21);
    out.writeObject(bob);
    out.writeObject(jane);
    out.close();
  }
}
