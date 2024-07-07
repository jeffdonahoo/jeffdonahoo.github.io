public class Factorial {

  private static boolean isBaseCase(int n) {
    return (n == 1);
  }

  public static int computeFactorial(int n) {
    if (isBaseCase(n))
      return 1;
    else
      return n * computeFactorial(n - 1);
  }

  public static void main(String[] args) {

    // Factorial. prefix not technically required here within the class
    System.out.println("5! = " + Factorial.computeFactorial(5));
  }
}