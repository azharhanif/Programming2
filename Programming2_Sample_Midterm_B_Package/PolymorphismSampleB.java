import java.util.ArrayList;

public class PolymorphismSampleB {
    public static void main(String[] args) {

        // TODO: Create an ArrayList<Employee>.
        // TODO: Add one Employee, one Manager, and one Programmer.
        // TODO: Use one loop to call work() on every object.
        // TODO: Use instanceof to find Managers.
        // TODO: Downcast safely and call holdMeeting().
        // Do not create a separate Manager list.
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
