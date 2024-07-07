public class ComparableInteger implements Comparable {
  private int number;

  public ComparableInteger(int number) {
    this.number = number;
  }

  public int getNumber() {
    return number;
  }

  public boolean greaterThan(Object x) {
    if (number > ((ComparableInteger) x).getNumber()) {
      return true;
    }
    return false;
  }

  public static void main(String args[]) {
    System.out.println(new ComparableInteger(5).greaterThan(new ComparableInteger(10)));
  }
}