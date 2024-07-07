import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

record EmployeeRecord(String name, int age, List<String> emailList) {}

public class EmpMainRec {
  public static void main(String[] args) {
    // Generated Constructor
    EmployeeRecord emps = new EmployeeRecord("Bob", 35, List.of());
    // Generated Accessors
    System.out.println("Employee: %s %d %s".formatted(emps.name(), emps.age(), emps.emailList().stream().collect(Collectors.joining(", "))));
    // Generated toString
    System.out.println(emps);
    // Generated hashCode
    System.out.println("Set size " + Set.of(new EmployeeRecord("A", 21, List.of()), new EmployeeRecord("A", 20, List.of()), new EmployeeRecord("A", 20, List.of())).size());
  }
}
