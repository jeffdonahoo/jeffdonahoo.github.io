public class T {

  public static void go() {
    for (;;)
      System.out.println("3335 Rules!");
  }

  public static void main(String args[]) {
    new Thread() {
      public void run() {
        go();
      }
    }.start();

    /*new Thread(new Runnable() {
      public void run() {
        go();
      }
    }
    ).start(); */
  }
}