public class ProgEx1 {

  public static void main(String args[]) {

    int N = 10;
    if (args.length == 1) {
      N = Integer.parseInt(args[0]);
    } else if (args.length > 1) {
      System.out.println("Parameter(s): [N]");
      System.exit(1);
    }

    int MIDPOINT = N/2;

    for (int i = 0; i <= N; i++) {
      int mid;
      if (i < MIDPOINT) {
        mid = MIDPOINT - i;
      } else {
        mid = i - MIDPOINT;
      }
      String output = "i = " + i + ":\t" + (i * i) + "\t" + mid + "\t" + Math.sqrt(i);
      System.out.println(output);
    }
  }
}