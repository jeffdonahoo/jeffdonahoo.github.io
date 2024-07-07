import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SetTest {

  public static void main(String args[]) {

    Set<Person> set = new HashSet<>();

    if (!set.add(new Person("Bob"))) System.out.println("Add 1 failed");
    if (!set.add(new Person("Jane"))) System.out.println("Add 2 failed");
    if (!set.add(new Person("Bob"))) System.out.println("Add 3 failed");

    for (Person p : set) {
      System.out.println(p);
    }
  }
}
