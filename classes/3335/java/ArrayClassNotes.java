import java.awt.Point;

public class ArrayClassNotes {

  public static void main(String args[]) {

    // For arrays of nonprimitive types...
    Point pt[] = new Point[2];// Allocated both array of references
    pt[0] = new Point(0, 0);   // and each instance

    System.out.println(pt[0]);
    System.out.println(pt[1]);// Throws exception? No (prints "null")
    pt[1].move(3, 3);         // Throws exception? Yes

    pt[1] = new Point(1, 1);

    Point newPt[] = new Point[2];  // What gets copied?  References!  Need to allocate
    System.arraycopy(pt, 0, newPt, 0, newPt.length);
    // What if newPt shorter than pt and use pt.length?  Exception!

    // Initialization
    String s[] = {"Ok", "Cancel"};
    Point[][] x = {{new Point(1, 1)}, {new Point(2, 2), new Point(3, 3)}};
    printMD(x);

    Point[][][] y = new Point[2][2][2];  // Yes you can do arbitrary dimensions
  }

  // Note that we do not need to specify any array dimensions
  public static void printMD(Object p[][]) {
    System.out.println("2D array:");
    for (int i = 0; i < p.length; i++) {
      for (int j = 0; j < p[i].length; j++) {
        System.out.print(p[i][j] + "\t");
      }
      System.out.println();
    }
  }

}
