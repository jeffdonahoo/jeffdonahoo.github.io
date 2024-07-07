import java.util.Date;

public class ExceptionQ4 {

  public final static double NOTESTS = 100000;

  public static void importantFunction() throws Exception {
    throw new Exception();
  }

  public static void main(String[] args) {
    long startTime = (new Date()).getTime();
    for (int i = 0; i <  ExceptionQ4.NOTESTS; i++) {
      try {
        importantFunction();
      } catch (Exception e) {}
    }
    System.out.println("Time: " + (new Date().getTime() - startTime));
  }
}
