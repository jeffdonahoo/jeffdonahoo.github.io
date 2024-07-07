public class FunNum {

  public static void main(String args[]) {

    int N = 10;
    if (args.length == 1) {
      N = Integer.parseInt(args[0]);
    } else if (args.length > 1) {
      System.out.println("Parameter(s): [N]");
      System.exit(1);
    }

    int midpoint = N/2;

    for (int i = 0; i <= N; i++) {
      int mid;
      if (i < midpoint) {
        mid = midpoint - i;
      } else {
        mid = i - midpoint;
      }
      String output = "i = " + i + ":\t" + (i * i) + "\t" + mid + "\t" +
                      Math.sqrt(i);
      System.out.println(output);
    }
  }
}