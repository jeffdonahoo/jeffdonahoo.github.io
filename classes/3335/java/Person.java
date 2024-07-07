public class Person {

  private final static int MAXNAME = 25;
  private String name;

  public Person(final String name) {
    if (name.length() > MAXNAME) {
      throw new IllegalArgumentException("Bad name");
    }
    this.name = name;
  }

  public String getName() {
    return name;   // Safe?  Yes cuz immutable!
  }

  public void sayHello() {
    System.out.println("Hi!  My name is " + name);
  }

  public static void sayType() {
    System.out.println("I am a person!");
  }

  @Override
  public final String toString() {
    return name;
  }

  public static void main(String[] args) {
    Person p = new Person("Bob");

    p.sayHello();
    p.sayType();
  }
}