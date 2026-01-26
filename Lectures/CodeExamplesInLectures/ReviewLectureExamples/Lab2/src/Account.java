public class Account {

    // i) Instance variables
    private int accountNumber;
    private String name;
    private double amount;

    // ii) Static variable
    private static int numberOfAccounts = 0;

    // iii) Default constructor
    public Account() {
        numberOfAccounts++;
    }

    // iv) Parameterized constructor
    public Account(int accountNumber, String name, double amount) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.amount = amount;
        numberOfAccounts++;
    }

    // v) Getters and setters
    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public static int getNumberOfAccounts() {
        return numberOfAccounts;
    }

    // vi) Deposit method
    public void deposit(double value) {
        if (value > 0) {
            amount += value;
        }
    }

    // vii) Withdraw method
    public void withdraw(double value) {
        if (value > 0 && value <= amount) {
            amount -= value;
        }
    }

    // viii) Calculate interest (2%)
    public void calculateInterest() {
        amount += amount * 0.02;
    }

    // ix) toString method
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }

    // x) equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Account)) return false;

        Account other = (Account) obj;
        return this.accountNumber == other.accountNumber &&
                this.name.equals(other.name) &&
                Double.compare(this.amount, other.amount) == 0;
    }
}
