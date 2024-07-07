public class Test {

  public static void main(String[] args) {

    System.out.println(0b1011);

    float f2 = 3.14F;
    double d1 = 3.14;

    byte b1 = 3;

    long l2 = 3000000000L;
    l2 = 30000 * 100000;
    double d3 = 3000000000L;

    int i1 = b1;
    int i2 = (int) l2;
    System.out.println(i2);

    double d2 = f2 + i1;

    i1 = 2000000000;
    System.out.println((float) i1 == i1 + 50);

    i2 = (int) 3.53;
    System.out.println(i2);
    i2 = Math.round(3.53F);
    System.out.println (i2);

    System.out.println("Answer: " + b1 + b1);
    System.out.println(b1 + b1 + " is the answer");

    d2 = 5.0/0;
    System.out.println(d2);
    i2 = 5/0;
  }
}
