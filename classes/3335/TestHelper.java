import java.util.*;

public class TestHelper {
  /**
   * Two arrays are equal if they have the same length and after sorting
   * all corresponding pairs of elements in the two lists are equal.
   */
  public static boolean ArraysEqual(char[] p1, char[] p2) {
    // Sort and test each element pair for equality
    if (p1 == null) {
      return p2 == null;
    }
    if (p2 == null) {
      return p1 == null;
    }
    char np1[] = new char[p1.length];
    System.arraycopy(p1, 0, np1, 0, p1.length);
    char np2[] = new char[p2.length];
    System.arraycopy(p2, 0, np2, 0, p2.length);
    Arrays.sort(np1);
    Arrays.sort(np2);
    return Arrays.equals(np1,np2);
  }

  /**
   * Two arrays are equal if they have the same length and after sorting
   * all corresponding pairs of elements in the two lists are equal.
   */
  public static boolean ArraysEqual(Comparable[] p1, Comparable[] p2) {
    // Sort and test each element pair for equality
    if (p1 == null) {
      return p2 == null;
    }
    if (p2 == null) {
      return p1 == null;
    }
    Comparable np1[] = new Comparable[p1.length];
    System.arraycopy(p1, 0, np1, 0, p1.length);
    Comparable np2[] = new Comparable[p2.length];
    System.arraycopy(p2, 0, np2, 0, p2.length);

    Arrays.sort(np1);
    Arrays.sort(np2);
    return Arrays.equals(np1,np2);
  }

  /**
   * An array contains a value if there exists some i such that
   * value.equals(list[i])
   */
  public static <T> boolean ArrayContains(T value, T[] list) {
    for (T x : list) {
      if (value.equals(x)) {
        return true;
      }
    }
    return false;
  }

  /** Two lists are equal if they have the same length and after sorting
   *  all corresponding pairs of elements in the two lists are equal.
   */
  public static boolean ListsEqual(List<Comparable> p1, List<Comparable> p2) {
    // Make Comparator to put nulls first
    Comparator<Comparable> c = new Comparator<Comparable>() {
      public int compare(Comparable o1, Comparable o2) {
        if (o1 == null) {
          if (o2 == null) {
            return 0;  // if both null then equal
          } else {
            return -1;  // if only o1 null, then o1 < o2
          }
        }
        if (o2 == null) {
          return 1;  // o1 cannot be null; therefore o2 < o1
        }

        return o1.compareTo(o2);  // neither o1 nor o2 are null
      }
    };
    List<Comparable> np1 = new LinkedList<Comparable>(p1);
    List<Comparable> np2 = new LinkedList<Comparable>(p2);
    // Sort and test each element pair for equality
    Collections.sort(np1, c);
    Collections.sort(np2, c);
    return np1.equals(np2);
  }
}
