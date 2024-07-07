public class ArrayPrimitive {

  public static void main(String[] args) {

    int[] a = new int[3];

    a[0] = 10;
    a[1] = 9;
    a[2] = 8;
    a[3] = 8;
    a++;

    int b[] = a;

    b[1] = 5;
    System.out.println("a[1]: " + a[1]);

    int[] c = new int[3];
    System.arraycopy(a, 0, c, 0, c.length);
    // or int[] c = java.util.Arrays.copyOf(a, a.length);
    a[0] = 11;
    System.out.println("c[0]: " + c[0]);

    int[] s = incrementAndSort(a);
    System.out.println("a[0]: " + a[0]);
    System.out.println("s[0]: " + s[0]);

    char[] ch = {'x', 'y', 'z'};

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
    printMD(new int[][] {{1},{1,2},{1,2,3}});
  }

  public static int[] incrementAndSort(int[] p) {
    for (int i = 0; i < p.length; i++) {
      p[i]++;
    }

    int[] sorted = new int[p.length];
    System.arraycopy(p, 0, sorted, 0, p.length);
    java.util.Arrays.sort(sorted);

    return sorted;
  }

  public static void printMD(int[][] p) {
    System.out.println("2D array:");
    for (int i = 0; i < p.length; i++) {
      for (int j = 0; j < p[i].length; j++) {
        System.out.print(p[i][j] + "\t");
      }
      System.out.println();
    }
  }
}
