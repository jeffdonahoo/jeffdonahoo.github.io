public class BirdWatcher2 implements Runnable {

  private BirdCounter ctr;

  public BirdWatcher2(BirdCounter ctr) {
    this.ctr = ctr;
  }

  public static void main(String[] args) {
    BirdCounter ctr = new BirdCounter();

    new Thread(new BirdWatcher2(ctr), "Bob").start();
    new Thread(new BirdWatcher2(ctr), "Jane").start();
    new Thread(new BirdWatcher2(ctr), "Jack").start();
  }

  public void run() {
    int myCt = 0;
    int ct;
    do {
      ct = ctr.recordSighting((int)(Math.random() * 100) + 1);
      myCt += ct;
    } while (ct > 0);
    System.out.println(Thread.currentThread().getName() + " saw " + myCt + " birds!");
  }
}