public class BirdWatcher1 extends Thread {

  private BirdCounter ctr;

  public BirdWatcher1(String name, BirdCounter ctr) {
    super(name);
    this.ctr = ctr;
  }

  public static void main(String[] args) {
    BirdCounter ctr = new BirdCounter();

    new BirdWatcher1("Bob", ctr).start();
    new BirdWatcher1("Jane", ctr).start();
    new BirdWatcher1("Jack", ctr).start();
  }

  public void run() {
    int myCt = 0;
    int ct;
    do {
      ct = ctr.recordSighting((int)(Math.random() * 100) + 1);
      myCt += ct;
    } while (ct > 0);
    System.out.println(getName() + " saw " + myCt + " birds!");
  }
}
