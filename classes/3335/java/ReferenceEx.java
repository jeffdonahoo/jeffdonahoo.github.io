import java.awt.Point;

public class ReferenceEx {

  public static void main(String[] args) {

    int i = 5;
    double d = 5.0;
    System.out.println("i == d: " + (i == d));

    String s1 = "Yellow";
    String s2 = new String("Yellow");

    System.out.println(s1.substring(0, 4));

    String s3 = "Yellow";
    s3.substring(0, 4);
    s3 = s2;

    System.out.println("s1 == s2: " + (s1 == s2));
    System.out.println("s1.equals(s2): " + s1.equals(s2));
    System.out.println("s2 == s3: " + (s2 == s3));

    String s4 = "Yellow";
    System.out.println("s1 == s4: " + (s1 == s4));

    s2 += " Dog";
    System.out.println("s2 == s3: " + (s2 == s3));

    s3 = null;

    Point p = new Point(3, 3);
    increment(i, s1, p);
    System.out.println("i: " + i);
    System.out.println("s1: " + s1);
    System.out.println("p: " + p);
  }

  public static void increment(int i, String s, Point p) {
    i++;
    if (s.equals("Next")) {
	s = "NextNext";
    } else {
	s = "Next";
    }
    p.setLocation(p.getX()+1, p.getY()+1);
  }
}
