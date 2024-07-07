import java.awt.Point;

public class ReferenceEx {

  public static void main(String args[]) {

    // Primitive type variable location contains a value of the type
    int i = 5;
    double d = 5.0;
    System.out.println("i == d: " + (i == d));       // true!  5 == 5.0

    // String is a class, not a primitive type
    String s1 = "Yellow";             // This is just....
    String s2 = new String("Yellow"); // syntactical sugar for this (kinda)

    System.out.println(s1.substring(0, 4));  // Prints Yell

    // Nonprimitive type variables are actually references
    String s3;                      // Creates a *reference* to a String
    // s3.substring(0,4);           // ERROR: No instance reference
    s3 = s2;

    // false!  s1 and s2 refer to different String instances
    System.out.println("s1 == s2: " + (s1 == s2));
    // true!  s1 holds the same characters as s2
    System.out.println("s1.equals(s2): " + s1.equals(s2));
    // true!  s2 and s3 refer to the same String instance
    System.out.println("s2 == s3: " + (s2 == s3));

    // Evil compiler
    String s4 = "Yellow";
    System.out.println("s1 == s4: " + (s1 == s4));

    // Strings are immutable.  No methods to change String
    // Concatenation creates a *new* String
    s2 += " Dog";
    // false!  s2 and s3 now refer to different strings
    System.out.println("s2 == s3: " + (s2 == s3));

    // Garbage collection
    s3 = null;      // Now nothing references original s2 instance
                    // s3 = s1 would also allow garbage collection
                    // of object that s3 did point to

    // Pass by value
    Point p = new Point(3, 3);  // 2D point
    increment(i, s1, p);
    System.out.println("i: " + i);      // 5!  i is pass-by-value
    System.out.println("s1: " + s1);     // Yellow!  Strings are immutable
    System.out.println("p: " + p);      // (4, 4).  Instance, not reference, changed
  }

  public static void increment(int i, String s, Point p) {
    i++;
    s = "Next";
    p.setLocation(p.getX()+1, p.getY()+1);
  }
}