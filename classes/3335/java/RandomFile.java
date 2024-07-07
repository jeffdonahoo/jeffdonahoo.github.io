import java.io.*;

public class RandomFile {
  public static void main(String args[]) throws IOException {
    RandomAccessFile r = new RandomAccessFile("random", "rw");

    r.writeInt(5);
    r.writeChars("Bob Smith");
    long nameEnd = r.getFilePointer();
    r.writeDouble(43567.89);

    r.seek(0);
    System.out.println("EID: " + r.readInt());
    System.out.print("Name: ");
    while (r.getFilePointer() <= nameEnd) {
      System.out.print(r.readChar());
    }
    System.out.println();
    System.out.println("Salary: $" + r.readDouble());
  }
}