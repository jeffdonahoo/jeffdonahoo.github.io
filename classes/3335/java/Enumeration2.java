public class Enumeration2 {
  public static void main(String[] args) {
    ShirtSize bobShirt = ShirtSize.SMALL;
    ShirtSize janeShirt = ShirtSize.LARGE;
    ShirtSize earlShirt = ShirtSize.SMALL;

    System.out.println(bobShirt < janeShirt);
    System.out.println(bobShirt == janeShirt);
    System.out.println(bobShirt == earlShirt);

    janeShirt = 18;              // Type safe
    janeShirt = new ShirtSize(); // Range safe

    f(janeShirt);
    f(18);
  }

  public static void f(ShirtSize s) {
  }
}

final class ShirtSize {
  public static final ShirtSize SMALL = new ShirtSize();
  public static final ShirtSize MEDIUM = new ShirtSize();
  public static final ShirtSize LARGE = new ShirtSize();

  private ShirtSize() { // Disallows creation of new ShirtSize instances
  }
}
