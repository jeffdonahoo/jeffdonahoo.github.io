import java.util.*;

public class Enumeration3 {
  public static void main(String[] args) {
    ShirtSize bobShirt = ShirtSize.SMALL;
    ShirtSize janeShirt = ShirtSize.LARGE;
    ShirtSize earlShirt = ShirtSize.SMALL;

    System.out.println(bobShirt.compareTo(janeShirt) < 0);
    System.out.println(bobShirt == janeShirt);
    System.out.println(bobShirt == earlShirt);

    f(janeShirt);

    ShirtSize list[] = new ShirtSize[] {bobShirt, janeShirt, earlShirt};
    Arrays.sort(list);
    for (int i = 0; i < list.length; i++) {      
      System.out.print(list[i] + " ");
    }
    System.out.println();
  }

  public static void f(ShirtSize s) {
  }
}

final class ShirtSize implements Comparable {
  public static final ShirtSize SMALL = new ShirtSize(0);
  public static final ShirtSize MEDIUM = new ShirtSize(1);
  public static final ShirtSize LARGE = new ShirtSize(2);

  private int ordinal;

  private ShirtSize(int ordinal) {
    this.ordinal = ordinal;
  }

  public int compareTo(Object size) {
    int cmpSize = ((ShirtSize) size).ordinal;

    if (ordinal == cmpSize) {
      return 0;
    } else if (ordinal < cmpSize) {
      return -1;
    } else {
      return 1;
    }
  }

  public String toString() {
    return new String [] {"Small", "Medium", "Large"}[ordinal];
  }
}
