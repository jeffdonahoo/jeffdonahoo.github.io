import java.util.Date;

public class ExceptionQ4a {

  static final Exception exception = new Exception();

  public static void importantFunction() throws Exception {
    throw exception;
  }

  public static void main(String[] args) {
    long startTime = (new Date()).getTime();
    for (int i = 0; i < 100000; i++) {
      try {
        importantFunction();
      } catch (Exception e) {}
    }
    System.out.println("Time: " + (new Date().getTime() - startTime));
  }
}
