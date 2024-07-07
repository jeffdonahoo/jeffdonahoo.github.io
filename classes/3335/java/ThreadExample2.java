class Count {
  transient int count = 0;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}

class BirdCounter extends Thread {

  private Count birdCtr;
  private int myCt = 0;

  public static final int BIRDBND = 5000;

  public BirdCounter(Count birdCtr) {
    this.birdCtr = birdCtr;
  }

  public synchronized boolean recordSighting() {
    int currentCt = birdCtr.getCount();
    if (currentCt < BIRDBND) {
      int newBirds = (int) (Math.random() * 100);  // Between 0 and 100
      System.out.println(getName() + " adding " + newBirds + " to " + currentCt);
      currentCt += newBirds;
      myCt += newBirds;
      birdCtr.setCount(currentCt);
    } else {
      System.out.println(getName() + " saw " + myCt + " birds!");
      return true;
    }
    return false;
  }

  public void run() {
    while (!recordSighting());
  }
}

public class ThreadExample extends Thread {

  public static void main(String[] args) {
    Count Counter = new Count();
    for (int i = 0; i < 4; i++) {
      new BirdCounter(Counter).start();
    }
  }
}