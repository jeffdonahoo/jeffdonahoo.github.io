import java.util.List;

public class EmployeeUtility {
  public static void emailAllEmployees(List<Employee> emps) {
    for (Employee e : emps) {
      sendEmail(e.getEmail().get(0));
    }
  }

  public static void emailRetiringEmployees(List<Employee> emps) {
    for (Employee e : emps) {
      if (e.getAge() >= 60) {
        sendEmail(e.getEmail().get(0));
      }
    }
  }
  
  public static void emailCompanyEmployee (List<Employee> emps) {
    for (Employee e : emps) {
      if (e.getEmail().get(0).endsWith("@company.com")) {
        sendEmail(e.getEmail().get(0));
      }
    }
  }

  public static void sendEmail(String email) {
    System.out.println("Sending email to " + email);
  }

  public static void main(String[] args) {
    List<Employee> emps = List.of(new Employee("Phil", 17).addEmail("phil@company.com"),
        new Employee("Bob", 61).addEmail("bob@bob.com"));
    
    emailRetiringEmployees(emps);
  }
}
