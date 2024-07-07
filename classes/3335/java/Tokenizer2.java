import java.io.*;
import java.util.*;

public class Test2 {
  public static void main(String args[]) throws IOException {
    BufferedReader r = new BufferedReader(new FileReader("letter.txt"));

    int sentenceNo = 1;
    String line;
    String sentence = "";
    String token = "";
    while ((line=r.readLine()) != null) {
      StringTokenizer t = new StringTokenizer(line, ".", true);
      while (t.hasMoreTokens()) {
        token = t.nextToken();
        if (token.compareTo(".") == 0) {
          System.out.println(sentenceNo + ": " + sentence);
          sentence = "";
          sentenceNo++;
        } else {
          sentence += token;
        }
      }
/*      if (token.compareTo(".") != 0) {
        sentence += token;
      } */
    }
    r.close();
  }
}