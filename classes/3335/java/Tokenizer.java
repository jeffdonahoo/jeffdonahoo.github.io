import java.io.*;
import java.util.*;

public class Test {
  public static void main(String args[]) throws IOException {
    BufferedReader r = new BufferedReader(new FileReader("letter.txt"));

    int sentenceNo = 1;
    String line;
    while ((line=r.readLine()) != null) {
      StringTokenizer t = new StringTokenizer(line, ".");
      while (t.hasMoreTokens()) {
        System.out.println(sentenceNo + ": " + t.nextToken());
        sentenceNo++;
      }
    }
    r.close();
  }
}