public class Enumeration1 {
  public static void main(String[] args) {
    int bobShirt = ShirtSize.SMALL;
    int janeShirt = ShirtSize.LARGE;
    int earlShirt = ShirtSize.LARGE;

    System.out.println(bobShirt < janeShirt);
    System.out.println(bobShirt == janeShirt);
    System.out.println(janeShirt == earlShirt);

    janeShirt = 18;  // No type safety

    f(18);           // Again, no type safety
  }

  public static void f(int x) {
  }
}

interface ShirtSize {
    int SMALL = 0;
    int MEDIUM = 1;
    int LARGE = 2;
    int XLARGE = 3;
}
