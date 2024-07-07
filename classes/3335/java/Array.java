import java.awt.Point;

public class Array {

  public static void main(String args[]) {
  
    // Arrays are objects
    int a[] = new int[3];     // Reference to array of 2 integers

    a[0] = 2;
    a[1] = 4;
    a[2] = 6;
    // a[3] = 8;              // Throws exception at *runtime*

    int[] b = a;              // Can declare either way

    b[1] = 5;
    System.out.println(a[1]); // 5!  a and b are references to same array

    // You can make a separate copy either yourself or with...
    int c[] = new int[3];
    System.arraycopy(a, 0, c, 0, c.length);
    a[0] = 3;
    System.out.println(c[0]); // 2!  c is independent copy of a

    // For arrays of nonprimitive types...
    Point pt[] = new Point[3];// Allocated both array of references
    pt[0] = new Point(1,1);   // and each instance

    // An array variable is a reference
    increment(a);
    System.out.println(a[2]); // 7!  You can change referenced values

    // Initialization
    char ch[] = {'x', 'y', 'z'};  // Constructs a 3 element array
    String s[] = {"Ok", "Cancel"};

    // Java does not have multidimensional arrays, just arrays of arrays
    
  }

  public static void increment(int p[]) {
    for (int i = 0; i < p.length; i++)
      p[i]++;
  }
}
