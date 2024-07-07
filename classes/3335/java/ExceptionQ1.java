public class ExceptionQ1 {
  public static void main(String[] args) {
    int x = 0;
    try {
      x += 2 + ExceptionEx1.string2Integer("3");
      x += 4 + ExceptionEx1.string2Integer("a");
    } catch (Exception e) {
      System.out.println("x = " + x);
    }
  }
}
