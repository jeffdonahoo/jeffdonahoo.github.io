import java.awt.Point;

public class ArrayClass {

  public static void main(String[] args) {

    Point[] pt = new Point[2];
    pt[0] = new Point(0, 0);

    System.out.println(pt[0]);
    System.out.println(pt[1]);
    pt[1].move(3, 3);

    pt[1] = new Point(1, 1);

    Point[] newPt = java.util.Arrays.copyOf(pt, pt.length);
    newPt[1].move(2,2);
    System.out.println(pt[1]);

    String[] s = {"Ok", "Cancel"};
    Point[][] x = {{new Point(1, 1)}, {new Point(2, 2), new Point(3, 3)}};
    printMD(x);

    Integer[] i = new Integer[3];
    i[0] = 5;
    i[1] = 6;
    int j = i[1];

    Point[][][] y = new Point[2][2][2];
  }

  public static void printMD(Object[][] p) {
    System.out.println("2D array:");
    for (int i = 0; i < p.length; i++) {
      for (int j = 0; j < p[i].length; j++) {
        System.out.print(p[i][j] + "\t");
      }
      System.out.println();
    }
  }
}
