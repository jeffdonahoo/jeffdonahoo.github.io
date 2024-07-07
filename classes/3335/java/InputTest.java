public class InputTest {
  public static void main(String[] args) {

    if (args.length != 1) {
      throw new IllegalArgumentException("Parameters:  <input>");
    }

    String inFile = args[0];

    FileInputStream in = new FileInputStream(inFile);

    byte[] buffer = new byte[20];
    int bytesRead;
    while ((bytesRead = in.read(buffer)) != 1) {
      System.out.println(new String(buffer, 0, bytesRead, "US-ASCII"));
    }
  }
}