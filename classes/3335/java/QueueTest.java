import java.util.Queue;
import java.util.LinkedList;

public class QueueTest {

  public static void main(String args[]) {

    Queue<Person> q = new LinkedList<>();

    if (!q.offer(new Person("Bob Smith"))) System.out.println("Add 1 failed");
    if (!q.offer(new Child("Jane Doe", 5))) System.out.println("Add 2 failed");
    if (!q.offer(new Person("Bob Jones"))) System.out.println("Add 3 failed");

    while (!q.isEmpty()) {
      System.out.println(q.remove());
    }
  }
}
