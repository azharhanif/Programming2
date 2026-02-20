//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Customer c = new Customer(101, "John", 15);
        Invoice inv = new Invoice(9001, c, 500.0);

        System.out.println(inv);
        System.out.println("Amount after discount: " + inv.getAmountAfterDiscount());
    }
}