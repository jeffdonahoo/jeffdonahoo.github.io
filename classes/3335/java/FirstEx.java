public class FirstEx {

  public static void main(String[] args) {

    if (args.length != 1) {
      System.out.println("Parameter(s): radius (inches)");
      System.exit(1);
    }

    final double PI = 3.14;

    double radius = Double.parseDouble(args[0]);

    String out = "The area of the circle is " + PI * radius * radius;
    System.out.println(out + " square inches");
  }
}