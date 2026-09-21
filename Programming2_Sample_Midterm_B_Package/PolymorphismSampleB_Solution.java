import java.util.ArrayList;

public class PolymorphismSampleB {
    public static void main(String[] args) {

        ArrayList<Employee> employees = new ArrayList<>();

        employees.add(new Employee("Ali"));
        employees.add(new Manager("Sara"));
        employees.add(new Programmer("Mina"));

        for (Employee e : employees) {
            e.work();
        }

        for (Employee e : employees) {
            if (e instanceof Manager) {
                Manager m = (Manager) e;
                m.holdMeeting();
            }
        }
    }
}

class Employee {
    protected String name;

    public Employee(String name) {
        this.name = name;
    }

    public void work() {
        System.out.println(name + " is working.");
    }
}

class Manager extends Employee {
    public Manager(String name) {
        super(name);
    }

    @Override
    public void work() {
        System.out.println(name + " is managing.");
    }

    public void holdMeeting() {
        System.out.println(name + " is holding a meeting.");
    }
}

class Programmer extends Employee {
    public Programmer(String name) {
        super(name);
    }

    @Override
    public void work() {
        System.out.println(name + " is programming.");
    }
}
