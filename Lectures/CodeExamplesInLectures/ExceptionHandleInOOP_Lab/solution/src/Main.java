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
        if (amount <= 0) {
            throw new NegativeAmountException("Deposit amount must be positive.");
        }
        balance += amount;
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
        if (amount <= 0) {
            throw new NegativeAmountException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Savings account does not have enough funds.");
        }
        balance -= amount;
    }
}

class ChequingAccount extends Account {
    public ChequingAccount(int accountNumber, String ownerName, double balance) {
        super(accountNumber, ownerName, balance);
    }

    @Override
    public void withdraw(double amount)
            throws NegativeAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new NegativeAmountException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Chequing account does not have enough funds.");
        }
        balance -= amount;
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
        if (from.getAccountNumber() == to.getAccountNumber()) {
            throw new SameAccountException("Source and target account cannot be the same.");
        }
        from.withdraw(amount);
        to.deposit(amount);
    }

    public static Account findByIndex(Account[] accounts, int index) {
        return accounts[index];
    }
}

abstract class Animal {
    public abstract void makeSound();
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Dog barks");
    }

    public void wagTail() {
        System.out.println("Dog wagging tail");
    }
}

class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Cat meows");
    }
}

public class Main {
    public static void main(String[] args) {
        Account[] accounts = {
            new SavingsAccount(101, "Ava", 1200),
            new ChequingAccount(102, "Noah", 300),
            new SavingsAccount(103, "Mia", 80)
        };

        Animal a = new Cat();
        try {
            Dog d = (Dog) a;
            d.wagTail();
        } catch (ClassCastException e) {
            System.out.println("Unsafe cast: object is not a Dog.");
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }

        if (a instanceof Dog) {
            Dog d = (Dog) a;
            d.wagTail();
        } else {
            System.out.println("Not a Dog. Safe branch used.");
        }

        try {
            Account chosen = BankService.findByIndex(accounts, 5);
            System.out.println(chosen.getOwnerName());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid account index.");
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }

        try {
            BankService.transfer(accounts[0], accounts[1], 200);
            System.out.println("Transfer 1 complete.");

            BankService.transfer(accounts[2], accounts[2], 50);
            System.out.println("Transfer 2 complete.");
        } catch (SameAccountException e) {
            System.out.println("Same-account transfer blocked.");
            System.out.println(e.getMessage());
        } catch (NegativeAmountException e) {
            System.out.println("Negative or zero amount blocked.");
            System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("Insufficient funds.");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected problem.");
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }

        try {
            BankService.transfer(accounts[2], accounts[1], 500);
        } catch (SameAccountException e) {
            System.out.println(e.getMessage());
        } catch (NegativeAmountException e) {
            System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }

        try {
            accounts[1].deposit(-20);
        } catch (NegativeAmountException e) {
            System.out.println("Deposit failed: " + e.getMessage());
        }

        System.out.println();
        System.out.println("Final balances:");
        for (Account account : accounts) {
            System.out.println(account.getOwnerName() + " -> " + account.getBalance());
        }
    }
}
