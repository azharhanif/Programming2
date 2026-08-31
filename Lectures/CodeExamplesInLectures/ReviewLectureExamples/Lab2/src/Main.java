public class Main {

    public static void main(String[] args) {

        Account acc1 = new Account(101, "Alice", 1000.0);
        Account acc2 = new Account(102, "Bob", 500.0);

        acc1.deposit(200.0);
        acc2.withdraw(100.0);

        acc1.calculateInterest();
        acc2.calculateInterest();

        System.out.println(acc1);
        System.out.println(acc2);

        System.out.println("Total number of accounts created: " +
                Account.getNumberOfAccounts());
    }
}
