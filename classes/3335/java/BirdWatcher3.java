public class BirdWatcher3 {

  public static void main(String[] args) {
    final BirdCounter ctr = new BirdCounter();

    new Thread(new Runnable() {
      public void run() {go(ctr);}
    }, "Jack").start();
    new Thread(newWatcher(ctr), "Bob").start();
    new Thread(newWatcher(ctr), "Jane").start();
  }

  public static Runnable newWatcher(final BirdCounter ctr) {
    return new Runnable() {
      public void run() {
        go(ctr);
      }
    };
  }

  public static void go(BirdCounter ctr) {
    int myCt = 0;
    int ct;
    do {
      ct = ctr.recordSighting((int)(Math.random() * 100) + 1);
      myCt += ct;
    } while (ct > 0);
    System.out.println(Thread.currentThread().getName() + " saw " 
      + myCt + " birds!");
  }
}