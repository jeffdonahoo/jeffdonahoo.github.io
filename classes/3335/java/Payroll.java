interface Employeex {
  public int salary();
}

class Manager implements Employeex {
  public int salary() {return 40000;}
}

class Programmerx implements Employeex {
  public int salary() {return 30000;}
}

public class Payroll {

  public static int computeCheck(Employeex emp) {
    if (emp instanceof Programmerx) {
      return emp.salary() + 500;           // Programmers get $500 bonus
    } else {
      return (int) (emp.salary() * 1.1) ;  // Managers get 10% salary bonus
    }
  }

  public static void main(String[] args) {
    System.out.println("Paycheck for Programmer is " +
      computeCheck(new Programmerx()));
  }
}