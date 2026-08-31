abstract class Account {
    protected int accountNumber;
    protected String ownerName;
    protected double balance;

    public Account(int accountNumber, String ownerName, double balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) throws NegativeAmountException {
        // TODO
    }

    public abstract void withdraw(double amount)
            throws NegativeAmountException, InsufficientFundsException;
}

class SavingsAccount extends Account {
    public SavingsAccount(int accountNumber, String ownerName, double balance) {
        super(accountNumber, ownerName, balance);
    }

    @Override
    public void withdraw(double amount)
            throws NegativeAmountException, InsufficientFundsException {
        // TODO
    }
}

class ChequingAccount extends Account {
    public ChequingAccount(int accountNumber, String ownerName, double balance) {
        super(accountNumber, ownerName, balance);
    }

    @Override
    public void withdraw(double amount)
            throws NegativeAmountException, InsufficientFundsException {
        // TODO
    }
}

class NegativeAmountException extends Exception {
    public NegativeAmountException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class SameAccountException extends Exception {
    public SameAccountException(String message) {
        super(message);
    }
}

class BankService {
    public static void transfer(Account from, Account to, double amount)
            throws NegativeAmountException, InsufficientFundsException, SameAccountException {
        // TODO
    }

    public static Account findByIndex(Account[] accounts, int index) {
        // TODO
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        Account[] accounts = {
            new SavingsAccount(101, "Ava", 1200),
            new ChequingAccount(102, "Noah", 300),
            new SavingsAccount(103, "Mia", 80)
        };

        // Warm-up idea from polymorphism lecture:
        // Animal a = new Cat();
        // Dog d = (Dog) a;
        // d.wagTail();

        // Task 1: handle risky array access
        // Account chosen = BankService.findByIndex(accounts, 5);

        // Task 2: handle checked exceptions from transfer and deposit
        // BankService.transfer(accounts[0], accounts[1], 200);
        // BankService.transfer(accounts[2], accounts[2], 50);
        // BankService.transfer(accounts[2], accounts[1], 500);
        // accounts[1].deposit(-20);
    }
}
