public class MyNumber {
  private int number;

  public MyNumber(int number) {
    this.number = number;
  }

  public Incrementable getIncrementor() {
    return new Incrementable() {
      public void increment() {number++;}
    };
  }

  public String toString() {
    return new Integer(number).toString();
  }

  public static void main(String args[]) {
    MyNumber num = new MyNumber(3);
    num.getIncrementor().increment();
    System.out.println(num);
  }
}