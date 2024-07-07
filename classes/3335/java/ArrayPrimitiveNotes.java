public class ArrayPrimitiveNotes {

  public static void main(String args[]) {

    // Arrays are objects
    int[] a = new int[3];     // Reference to array of 3 integers
                              // You cannot change the size of an array

    a[0] = 2;
    a[1] = 4;
    a[2] = 6;
    // a[3] = 8;              // Throws exception at *runtime*

    // a++;                   // No pointer arithmetic

    int b[] = a;              // Can declare either way

    b[1] = 5;
    System.out.println("a[1]: " + a[1]); // 5!  a and b are references to same array

    // You can make a separate copy either yourself or with...
    int c[] = new int[3];
    System.arraycopy(a, 0, c, 0, c.length);
    a[0] = 3;
    System.out.println("c[0]: " + c[0]); // 2!  c is independent copy of a

    // An array variable is a reference
    increment(a);
    System.out.println("a[2]: " + a[2]); // 7!  You can change referenced values

    // Initialization
    char ch[] = {'x', 'y', 'z'};  // Constructs a 3 element array

    // Java does not have multidimensional arrays, just arrays of arrays
    int[][] d = new int[3][2];
    System.out.println("d[2][1]: " + d[2][1]);
    d[1][0] = 5;

    int[][] e = new int[2][];
    for (int i = 0; i < 2; i++) {
      e[i] = new int[i+2];
      for (int j = 0; j < (i+2); j++) {
        e[i][j] = i * j;
      }
    }

    printMD(e);
    System.out.println("-----------");
    printMD(new int[][] {{1},{1,2},{1,2,3}});  // Anonymous 2D array
  }

  public static void increment(int p[]) {
    for (int i = 0; i < p.length; i++)
      p[i]++;
  }

  // Note that we do not need to specify any array dimensions
  public static void printMD(int p[][]) {
    System.out.println("2D array:");
    for (int i = 0; i < p.length; i++) {
      for (int j = 0; j < p[i].length; j++) {
        System.out.print(p[i][j] + "\t");
      }
      System.out.println();
    }
  }
}
