class Emp {
  private String salary;
  Emp() {salary = lookupSalary();}
  String lookupSalary() {return "$50,000";}
  String getSalary() {return salary;}
}

class ProgEmp extends Emp {
  private String progSalary;
  ProgEmp() {progSalary = "$70,000";}
  String lookupSalary() {return progSalary;}

  public static void main(String args[]) {
    ProgEmp bob = new ProgEmp();
    System.out.println(bob.getSalary());
  }
}