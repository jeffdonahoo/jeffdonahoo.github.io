public final class Child extends Person {

  private int grade;

  public Child(String name, int grade) {
    super(name);    // Must be first
    this.grade = grade;
  }

  @Override
  public void sayHello() {
    super.sayHello();  // Need not be first
    System.out.println("I'm in grade " + grade);
  }

  public static void sayType() {
    System.out.println("I am a child!");
  }

  public int getGrade() {
    return grade;
  }

  public static void main(String[] args) {
    Person p = new Child("Julie", 5);
    Person x = new Person("Bob");

    p.sayHello();

    p.sayType();

    System.out.println(x instanceof Child);
    // System.out.println(p.getGrade());
  }
}