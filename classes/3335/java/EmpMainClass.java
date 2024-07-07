import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

class EmployeeClass {
  private final String name;
  private final int age;
  private final List<String> emailList;
}

public class EmpMainClass {
  public static void main(String[] args) {
    EmployeeClass emps = new EmployeeClass("Bob", 35, List.of());
    System.out.println("Employee: %s %d %s".formatted(emps.getName(), emps.getAge(), emps.getEmailList().stream().collect(Collectors.joining(", "))));
    System.out.println(emps);
    System.out.println("Set size " + Set.of(new EmployeeClass("A", 21, List.of()), new EmployeeClass("A", 20, List.of()), new EmployeeClass("A", 20, List.of())).size());
  }
}
