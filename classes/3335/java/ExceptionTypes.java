public class ExceptionTypes {

  public static void main(String[] args) {

    int x[] = new int[2];
    ExceptionTypes y = null;
    try {
      int z = 5/0;
      y.go();
      int p = x[2];
    } catch(Exception e) {
      System.out.println("Some exception");
      e.printStackTrace();
    }
  }

  public void go() {}
}