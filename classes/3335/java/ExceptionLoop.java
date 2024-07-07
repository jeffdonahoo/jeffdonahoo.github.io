public class ExceptionLoop {

  public static void foo() throws Exception {
    if (Math.random() < 0.95) {
      System.out.println("Exception");
      throw new Exception();
    } else {
      System.out.println("No Exception");
    }
  }

  public static void main(String[] args) {

  /*  boolean stay = true;
    while (stay) {
      try {
        foo();
        stay = false;
      } catch (Exception e) {}
    }
   */
    for (;;) {
      try {
        foo();
        break;
      } catch (Exception e) {}
    }
  }
}