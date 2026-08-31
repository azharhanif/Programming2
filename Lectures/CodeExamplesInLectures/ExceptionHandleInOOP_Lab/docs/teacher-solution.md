# Programming 2 Lab — Teacher Solution

This teacher version aligns the lab with two lecture threads:

- The exception lecture distinguishes Java's default handler from user-defined handlers, explains `try-catch`, `throws`, meaningful handlers, and custom exceptions. citeturn424200view0
- The polymorphism lecture uses `Animal`/`Dog`/`Cat`, explains runtime method selection, shows `ClassCastException` from unsafe downcasting, and also stresses contract-based polymorphism through interfaces. citeturn444764view0

---

# Part 0 — Warm-up solution

## Exception that occurs
`ClassCastException`

## Why it compiles
The compiler only checks whether `Dog` and `Animal` are related types. They are. The JVM later checks the real object and sees that the object is actually a `Cat`, not a `Dog`. That matches the lecture's distinction between reference type and runtime object type. citeturn444764view0

## try-catch version

```java
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
        Animal a = new Cat();

        try {
            Dog d = (Dog) a;
            d.wagTail();
        } catch (ClassCastException e) {
            System.out.println("Unsafe cast: object is not a Dog.");
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }
    }
}
```

## safer `instanceof` version

```java
public class Main {
    public static void main(String[] args) {
        Animal a = new Cat();

        if (a instanceof Dog) {
            Dog d = (Dog) a;
            d.wagTail();
        } else {
            System.out.println("Not a Dog. Safe branch used.");
        }
    }
}
```

## Teaching note
`ClassCastException` is unchecked because it is a `RuntimeException`, and the lecture states that runtime exceptions are unchecked. citeturn424200view0

---

# Main lab — full teacher solution

## Full code in one file form

```java
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
            throw new InsufficientFundsException(
                    "Savings account does not have enough funds.");
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
            throw new InsufficientFundsException(
                    "Chequing account does not have enough funds.");
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

public class Main {
    public static void main(String[] args) {
        Account[] accounts = {
            new SavingsAccount(101, "Ava", 1200),
            new ChequingAccount(102, "Noah", 300),
            new SavingsAccount(103, "Mia", 80)
        };

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
        for (Account a : accounts) {
            System.out.println(a.getOwnerName() + " -> " + a.getBalance());
        }
    }
}
```

---

## Expected behavior notes

### `deposit`
Uses `throws` because the method should not silently fix a bad amount. The lecture describes `throws` as passing the problem to the caller to handle. citeturn424200view0

### `withdraw`
Same idea as `deposit`, but with two checked exceptions.

### `transfer`
This is the strongest design point in the lab:
- `SameAccountException` is a business-rule problem,
- `NegativeAmountException` is an input-validation problem,
- `InsufficientFundsException` is an account-state problem.

Keeping them separate makes the handlers meaningful.

### `findByIndex`
This demonstrates an unchecked exception. If not handled locally, Java's default handler would print exception info, stack trace, and stop execution, exactly as described in the lecture. citeturn424200view0

---

# Exercise 1 — Vehicle solution

```java
class InvalidRentalDaysException extends Exception {
    public InvalidRentalDaysException(String message) {
        super(message);
    }
}

abstract class Vehicle {
    protected String plate;

    public Vehicle(String plate) {
        this.plate = plate;
    }

    public abstract double rent(int days) throws InvalidRentalDaysException;
}

class Car extends Vehicle {
    public Car(String plate) {
        super(plate);
    }

    @Override
    public double rent(int days) throws InvalidRentalDaysException {
        if (days <= 0) {
            throw new InvalidRentalDaysException("Car rental days must be positive.");
        }
        return 45 * days;
    }
}

class Truck extends Vehicle {
    public Truck(String plate) {
        super(plate);
    }

    @Override
    public double rent(int days) throws InvalidRentalDaysException {
        if (days <= 0) {
            throw new InvalidRentalDaysException("Truck rental days must be positive.");
        }
        return 80 * days;
    }
}
```

### quick usage example

```java
try {
    Vehicle v1 = new Car("CAR-100");
    Vehicle v2 = new Truck("TRK-900");
    System.out.println(v1.rent(3));
    System.out.println(v2.rent(0));
} catch (InvalidRentalDaysException e) {
    System.out.println(e.getMessage());
}
```

---

# Exercise 2 — Student solution

```java
class InvalidGradeException extends Exception {
    public InvalidGradeException(String message) {
        super(message);
    }
}

class Student {
    private String name;
    private int grade;

    public Student(String name) {
        this.name = name;
    }

    public void setGrade(int grade) throws InvalidGradeException {
        if (grade < 0 || grade > 100) {
            throw new InvalidGradeException("Grade must be between 0 and 100.");
        }
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }
}
```

### quick usage example

```java
try {
    Student s = new Student("Emma");
    s.setGrade(120);
    System.out.println(s.getGrade());
} catch (InvalidGradeException e) {
    System.out.println("Invalid grade entered.");
    System.out.println(e.getMessage());
}
```

---

# Suggested marking guide

## Part 0 — Warm-up: 3 marks
- identify `ClassCastException`: 1
- correct `try-catch`: 1
- correct `instanceof` prevention: 1

## Main lab: 10 marks
- `deposit`: 2
- both `withdraw` methods: 2
- `transfer`: 2
- `findByIndex` + local handler in `main`: 2
- meaningful catch ordering and messages: 2

## Exercise 1: 3 marks
- custom exception + two correct `rent` methods: 3

## Exercise 2: 3 marks
- custom exception + correct `setGrade`: 3

Total: 19 marks

---

# Compact instructor notes

1. Do not let students put all code inside one giant `try` block. The lecture explicitly warns against that style. citeturn424200view0
2. Emphasize why `catch (Exception e)` must come last. The lecture states that general catches cannot appear before more specific catches. citeturn424200view0
3. Reinforce the polymorphism link:
   - `Animal` warm-up shows runtime behavior plus runtime crash from bad cast,
   - `Account`, `Vehicle`, and `Student` examples keep the lab grounded in familiar OOP patterns.
