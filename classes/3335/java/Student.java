public class Student {

  private String studentId;
  private String name;
  private int age;

  public Student(String studentId, String name, int age) {
    this.studentId = studentId;
    this.name = name;
    this.age = age;
  }

  // Parameterless constructor required to make Student a Java Bean
  public Student() {
  }

  // A Java Bean must have an accessor and mutator method for each member variable as follows:

  // Accessor method for studentId
  public String getStudentId() {
    return studentId;
  }

  // Mutator method for studentId
  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  // Accessor method for name
  public String getName() {
    return name;
  }

  // Mutator method for name
  public void setName(String name) {
    this.name = name;
  }

  // Accessor method for age
  public int getAge() {
    return age;
  }

  // Mutator method for age
  public void setAge(int age) {
    this.age = age;
  }

  public String toString() {
    return studentId + ": " + name + " - " + age;
  }
}
