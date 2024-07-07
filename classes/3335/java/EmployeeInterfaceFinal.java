import java.util.ArrayList;
import java.util.List;

public class EmployeeInterfaceFinal implements Listable, Soundable {

    private String name;
    private int age;
    private List<String> emailList;

    public EmployeeInterfaceFinal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void log() {
        System.out.println(name + " logged");
    }

    @Override
    public String getEntry() {
        return name;
    }

    @Override
    public void makeSound() {
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

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getEmail() {
        return emailList;
    }

    public void addEmail(String email) {
        this.emailList.add(email);
    }

    public static void main(String args[]) {

        EmployeeInterfaceFinal bob = new EmployeeInterfaceFinal("Bob", 25);
        EmployeeInterfaceFinal phil = new EmployeeInterfaceFinal("Phil", 36);

        List<Listable> emps = new ArrayList<>();
        emps.add(bob);
        emps.add(phil);

        for (Listable l : emps) {
            System.out.println(l.getEntry());
            l.log();
        }
    }
}