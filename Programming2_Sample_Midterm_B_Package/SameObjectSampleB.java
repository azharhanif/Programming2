public class SameObjectSampleB {
    public static void main(String[] args) {
        // TODO: Create one Employee object.
        // TODO: Make employee2 refer to the same object.
        // TODO: Give the employee a $5,000 raise through employee2.
        // TODO: Print both references.
    }
}

class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public void giveRaise(double amount) {
        salary += amount;
    }

    @Override
    public String toString() {
        return name + " - $" + salary;
    }
}
