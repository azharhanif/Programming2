# Programming 2 Lab — Exception Handling with Familiar OOP Designs

## Source connection to class lectures
This lab is built from two class ideas:

- The exception handling lecture explains the default handler, `try-catch`, `throws`, checked vs unchecked exceptions, meaningful handlers using `e.getMessage()` / `e.getClass()` / `e.getStackTrace()`, and user-defined exceptions. citeturn424200view0turn444764view0
- The polymorphism lecture uses familiar designs such as `Animal`, `Dog`, and `Cat`, explains runtime method selection, shows that unsafe downcasting can crash with `ClassCastException`, and also emphasizes contract-based polymorphism with interfaces. citeturn444764view0turn424200view0

---

## Time
- Part 0 warm-up: 10 minutes
- Main lab: 30 minutes
- Exercise 1: 10 minutes
- Exercise 2: 10 minutes

---

## Learning goals
By the end of this lab, you should be able to:

1. explain the difference between Java's default handler and a user-defined handler,
2. write focused `try-catch` blocks instead of putting too much code inside `try`,
3. use `throws` to pass exception-handling responsibility to the caller,
4. create and use user-defined exceptions in an OOP design,
5. connect exception handling with familiar polymorphism examples.

---

# Part 0 — Warm-up from the polymorphism lecture

The polymorphism lecture shows that an unsafe downcast can crash at runtime with `ClassCastException`. citeturn444764view0

## Starter code

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

        // This compiles, but may crash at runtime.
        Dog d = (Dog) a;
        d.wagTail();
    }
}
```

## Task A
Run the idea mentally. What exception will happen?

## Task B
Wrap the risky lines in a `try-catch` block and print a meaningful message.

## Task C
Write a safer version using `instanceof` so the exception does not happen in the first place.

### Questions
1. Why does the code compile even though it crashes later?
2. Is `ClassCastException` checked or unchecked?
3. When this crash is not handled by the programmer, what does Java's default handler do?

---

# Main Lab — Bank Transfer System with Custom Exceptions

## Scenario
You are building a small bank system using familiar OOP ideas.

- `Account` is the parent abstraction.
- `SavingsAccount` and `ChequingAccount` are child classes.
- Transfers and withdrawals may fail.
- Some failures should be represented with **user-defined exceptions**.

This lab follows the lecture idea that `try-catch` gives a local handler, while `throws` passes responsibility to the caller. citeturn424200view0

---

## Starter code

### `Account.java`

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
        // TODO
    }

    public abstract void withdraw(double amount)
            throws NegativeAmountException, InsufficientFundsException;
}
```

### `SavingsAccount.java`

```java
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
```

### `ChequingAccount.java`

```java
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
```

### User-defined exceptions

```java
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
```

### `BankService.java`

```java
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
```

### `Main.java`

```java
public class Main {
    public static void main(String[] args) {
        Account[] accounts = {
            new SavingsAccount(101, "Ava", 1200),
            new ChequingAccount(102, "Noah", 300),
            new SavingsAccount(103, "Mia", 80)
        };

        // Task 1: handle risky array access here with try-catch
        // Account chosen = BankService.findByIndex(accounts, 5);

        // Task 2: handle checked exceptions here when transferring money
        // BankService.transfer(accounts[0], accounts[1], 200);
        // BankService.transfer(accounts[2], accounts[2], 50);
        // BankService.transfer(accounts[2], accounts[1], 500);
        // accounts[1].deposit(-20);
    }
}
```

---

## Main Task 1 — deposit with `throws`
Complete `deposit(double amount)`.

### Rules
- If `amount <= 0`, throw `NegativeAmountException`.
- Otherwise add to balance.

### Design focus
Do not handle this exception inside `deposit`. Use `throws` and let the caller decide how to handle it.

---

## Main Task 2 — withdraw in both subclasses
Complete `withdraw(double amount)` in both subclasses.

### Rules
- If `amount <= 0`, throw `NegativeAmountException`.
- If `amount > balance`, throw `InsufficientFundsException`.
- Otherwise subtract from balance.

### Reflection
Even if the logic is similar in both subclasses, why is polymorphism still useful here?

---

## Main Task 3 — transfer using a custom exception
Complete `BankService.transfer(...)`.

### Rules
- If the two account numbers are the same, throw `SameAccountException`.
- Otherwise call `from.withdraw(amount)` and then `to.deposit(amount)`.
- Do not use `try-catch` inside `transfer`.
- Use `throws` in the method header.

### Design question
Why is it better here to let `transfer(...)` throw the checked exceptions instead of swallowing them internally?

---

## Main Task 4 — unchecked exception with a local handler
Complete `findByIndex(Account[] accounts, int index)`.

### Rules
- Return `accounts[index]`.
- Do not catch inside this method.
- In `main`, call it with an invalid index and use `try-catch` to handle the problem.

### Hint
The lecture explains that Java's default handler prints exception information, stack trace, and stops execution when the programmer does not provide a handler. citeturn424200view0

---

## Main Task 5 — meaningful catch blocks
In `main`, write handlers for:

- `ArrayIndexOutOfBoundsException`
- `SameAccountException`
- `NegativeAmountException`
- `InsufficientFundsException`
- a general `Exception` catch at the end

Inside at least one catch block, print:

```java
System.out.println(e.getClass());
System.out.println(e.getMessage());
```

Do not place the general `catch (Exception e)` before the specific catches.

---

# Exercise 1 — Vehicle Rental

## Scenario
A vehicle rental program uses polymorphism:

- `Vehicle` is the parent class.
- `Car` and `Truck` are child classes.

Complete the code so that invalid rental days throw a custom checked exception.

### Starter

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
        // TODO
        return 0;
    }
}

class Truck extends Vehicle {
    public Truck(String plate) {
        super(plate);
    }

    @Override
    public double rent(int days) throws InvalidRentalDaysException {
        // TODO
        return 0;
    }
}
```

### Rules
- If `days <= 0`, throw `InvalidRentalDaysException`.
- `Car` rate = `45 * days`
- `Truck` rate = `80 * days`

---

# Exercise 2 — Student Grades

## Scenario
A student system stores a test score.

Create and use a custom checked exception called `InvalidGradeException`.

### Rule
A valid grade must be from `0` to `100` inclusive.

### Starter

```java
class Student {
    private String name;
    private int grade;

    public Student(String name) {
        this.name = name;
    }

    public void setGrade(int grade) throws InvalidGradeException {
        // TODO
    }

    public int getGrade() {
        return grade;
    }
}
```

### In `main`
- Create a `Student`
- Try to set an invalid grade such as `120`
- Handle the exception with `try-catch`
- Print a meaningful message

---

# Submission checklist
- [ ] Warm-up completed
- [ ] `deposit` completed with `throws`
- [ ] both `withdraw` methods completed
- [ ] `transfer` completed with `throws`
- [ ] `main` contains meaningful handlers
- [ ] Vehicle exercise completed
- [ ] Student exercise completed

---

# Concept summary

## `try-catch`
Use when you want to handle the problem **here**.

## `throws`
Use when you want to say: **the caller must handle this**.

## User-defined exception
Use when built-in exception types do not describe the problem clearly enough.

## Polymorphism connection
Even in exception-handling labs, familiar parent/child designs such as `Animal`, `Vehicle`, and `Account` make the code easier to read and test because the same method call can behave differently depending on the object type. citeturn444764view0
