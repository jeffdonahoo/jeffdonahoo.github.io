import java.util.List;

public class Employee {

    private String name;
    private int age;
    private List<String> emailList;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public Employee setAge(int age) {
        this.age = age;
        return this;
    }

    public List<String> getEmail() {
        return emailList;
    }

    public Employee addEmail(String email) {
        this.emailList.add(email);
        return this;
    }
}
