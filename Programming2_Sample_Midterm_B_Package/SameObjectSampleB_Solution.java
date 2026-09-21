public class SameObjectSampleB {
    public static void main(String[] args) {
        Employee employee1 = new Employee("Alex", 50000);
        Employee employee2 = employee1;

        employee2.giveRaise(5000);

        System.out.println(employee1);
        System.out.println(employee2);
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
