import java.util.Map;
import java.util.HashMap;

public class MapTest {

  public static void main(String args[]) {

    Map<Integer, Employee> map = new HashMap<>();

    for (int id = 10; id > 0; id--) {
      map.put(new Integer(id), new Employee(id, "Bob-" + id));
    }

    System.out.println("Employee 5 is " + map.get(new Integer(5)));
    map.remove(new Integer(6));

    for (Employee e : map.values()) {
      System.out.println(e);
    }
  }
}

class Employee {
  public int ID;
  public String name;

  public Employee(int ID, String name) {
    this.ID = ID;
    this.name = name;
  }

  public String toString() {
    return ID + ": " + name;
  }
}
