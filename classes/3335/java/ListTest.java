import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListTest {

  public static void main(String args[]) {

    List<Integer> list = new ArrayList<>();

    // Load list
    long start = System.currentTimeMillis();
    for (int i = 0; i < 10000; i++) {
      list.add(0, i);
    }
    long stop = System.currentTimeMillis();
    System.out.println("Add: " + (stop - start));

    // Random get experiment
    Random r = new Random();
    start = System.currentTimeMillis();
    for (int i = 0; i < 10000; i++) {
      int value = list.get(Math.abs(r.nextInt()) % list.size());
    }
    stop = System.currentTimeMillis();
    System.out.println("Random get: " + (stop - start));
  }
}
