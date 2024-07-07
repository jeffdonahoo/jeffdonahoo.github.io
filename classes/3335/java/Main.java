import java.io.*;

public class Main {

  public static void main(String[] args) throws IOException {

    if (args.length != 2) {
      System.err.println("Parameters: <Filename> <Data>");
    }

    String filename = args[0];
    byte[] data = args[1].getBytes("US-ASCII");

    ChecksumOutputStream out = new ChecksumOutputStream(new FileOutputStream("data"));

    for (int i=0; i<data.length; i++) {
      out.write(data[i]);
    }
    out.close();
  }
}