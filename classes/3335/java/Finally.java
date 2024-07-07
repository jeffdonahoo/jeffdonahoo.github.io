public class Finally {

  public static void main(String[] args) {
    try {
      int x = 5 / 0;
    } catch (Exception e) {
      System.out.println("Exception");
    } finally {
      System.out.println("Finally");
    }
  }
}