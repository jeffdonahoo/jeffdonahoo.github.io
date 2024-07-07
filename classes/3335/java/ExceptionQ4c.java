import java.util.Date;

public class ExceptionQ4c {

  public static void importantFunction() {
    return;
  }

  public static void main(String[] args) {
    long startTime = (new Date()).getTime();
    for (int i = 0; i < ExceptionQ4.NOTESTS; i++) {
      importantFunction();
    }
    System.out.println("Time: " + (new Date().getTime() - startTime));
  }
}
