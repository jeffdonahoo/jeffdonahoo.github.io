import java.util.Date;

public class ExceptionQ4b {

  public static void importantFunction() throws Exception {
    return;
  }

  public static void main(String[] args) {
    long startTime = (new Date()).getTime();
    for (int i = 0; i < ExceptionQ4.NOTESTS; i++)
      try {
        importantFunction();
      } catch (Exception e) {}
    System.out.println("Time: " + (new Date().getTime() - startTime));
  }
}
