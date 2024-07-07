public class BirdCounter {

  private int birdCt = 0;
  public static final int BIRDBND = 10000;

  public int recordSighting(int noBirds) {
    int newBirds = Math.min(noBirds, BIRDBND - birdCt);
    if (newBirds > 0) {
      System.out.println(Thread.currentThread().getName() +
        " adding " + newBirds + " to " + birdCt);
      birdCt += newBirds;
    }
    return newBirds;
  }
}
