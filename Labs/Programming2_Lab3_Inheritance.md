# Programming 2 — Lab 3: Inheritance and Encapsulation

**Topic:** Inheritance, `extends`, `super`, constructor chaining, overriding, `private` vs `protected`, and design choices  
**Estimated time:** 75–90 minutes  
**Submission:** Java project + JUnit tests + short design explanations

---

## 1. Purpose

Lecture 5 introduced inheritance as an **is-a relationship**:

```text
Dog is an Animal
Manager is an Employee
ElectricCar is a Car
```

The important Programming 2 question is no longer only:

> "Can I make one class extend another?"

It is:

> **"What should the subclass be allowed to access from the superclass, and why?"**

This lab focuses especially on the design choice between:

```java
private
```

and:

```java
protected
```

You will practice inheritance while deciding whether superclass data should remain private or be exposed to subclasses.

---

# 2. Part A — Warm-Up: Is It Really Inheritance?

For each relationship, decide whether inheritance is appropriate.

### A

```text
Manager
Employee
```

Question:

> Is a Manager an Employee?

Answer:

```text
Yes
```

Inheritance is reasonable:

```java
class Manager extends Employee {
}
```

---

### B

```text
Library
Book
```

Question:

> Is a Library a Book?

No.

A library **has books**.

This is composition/containment:

```java
class Library {
    private ArrayList<Book> books;
}
```

Do **not** write:

```java
class Library extends Book {
}
```

---

### C

```text
Car
Engine
```

Question:

> Is a Car an Engine?

No.

A car **has an engine**.

Use composition:

```java
class Car {
    private Engine engine;
}
```

---

### D

```text
Manager
Employee
```

**Can every Manager be treated as an Employee?**

Yes.

This is a stronger reason for inheritance than simply saying that the words are related.

---

# 3. Part B — Basic Inheritance

Create the following classes.

## `Employee.java`

```java
public class Employee {

    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public double calculateBonus() {
        return salary * 0.05;
    }
}
```

## `Manager.java`

Create:

```java
public class Manager extends Employee
```

A Manager should have:

```java
private double managementAllowance;
```

Its constructor should receive:

```text
name
salary
management allowance
```

Use constructor chaining:

```java
super(name, salary);
```

Add:

```java
public double getManagementAllowance()
```

Then override:

```java
calculateBonus()
```

For a Manager, calculate the bonus as:

```text
10% of salary + management allowance
```

Use `@Override`.

---

## Questions

Before running your program, predict:

```java
Manager m = new Manager("John", 80000, 5000);
```
## Questions
What should these produce?

```java
m.getName()
m.getSalary()
m.getManagementAllowance()
m.calculateBonus()
```

Then run your program and check your predictions.

---

# 4. Part C — Constructor Chaining

Add print statements temporarily to the constructors.

For example:

```java
public Employee(String name, double salary) {
    System.out.println("Employee constructor");
    this.name = name;
    this.salary = salary;
}
```

and:

```java
public Manager(String name, double salary, double managementAllowance) {
    super(name, salary);
    System.out.println("Manager constructor");
    this.managementAllowance = managementAllowance;
}
```

Create:

```java
Manager m = new Manager("Amanda", 80000, 5000);
```

### Question

What prints first?

```text
Employee constructor
Manager constructor
```

or:

```text
Manager constructor
Employee constructor
```

Explain why.

---

# 5. Part D — The Encapsulation Decision

Now consider this design:

```java
class Employee {

    protected double salary;

}
```

and:

```java
class Manager extends Employee {

    public void giveRaise() {
        salary += 1000;
    }
}
```

This works.
## Questions
> **Should `salary` really be protected? Why?**

### Experiment

Create this version:

```java
class Employee {

    protected double salary;

    public Employee(double salary) {
        this.salary = salary;
    }
}

class Manager extends Employee {

    public Manager(double salary) {
        super(salary);
    }

    public void giveRaise() {
        salary += 1000;
    }

    public void breakSalary() {
        salary = -500000;
    }
}
```

Run in a Driver/Main class:

```java
Manager manager = new Manager(80000);

manager.giveRaise();

System.out.println(manager.salary);
```

Then run:

```java
manager.breakSalary();

System.out.println(manager.salary);
```

---

## Questions

1. Can the subclass modify `salary` directly?
2. Can the subclass accidentally put the object into an invalid state?
3. Is the `salary` field protected giving us strong encapsulation?
4. Would `private` be safer?

Explain your answer.

---

# 6. Part E — Private Field, Controlled Access

Now redesign `Employee`.

Use:

```java
private double salary;
```

The subclass should **not** directly access `salary`.

Instead, give the superclass control over salary changes.

Implement:

```java
protected void increaseSalary(double amount)
```

The method must reject non-positive amounts.

For example:

```java
protected void increaseSalary(double amount) {
    if (amount > 0) {
        salary += amount;
    }
}
```

Then `Manager` should use:

```java
increaseSalary(1000);
```

instead of:

```java
salary += 1000;
```

---

## Required Design

Your `Employee` should now follow this general structure:

```java
public class Employee {

    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    protected void increaseSalary(double amount) {
        // validate the amount
        // modify salary only if valid
    }
}
```

Your `Manager` should not directly access:

```java
salary
```

Instead, it should call:

```java
increaseSalary(...)
```

---

## Important Design Question

Why is this design stronger than:

```java
protected double salary;
```

Think about who controls the rule.

### Design 1

```java
protected double salary;
```

The subclass controls the data directly.

### Design 2

```java
private double salary;

protected void increaseSalary(double amount) {
    // validation
}
```

The superclass controls how the data can change.

### Explain

Which design would you choose for a real payroll system? Give a real life example scenario for your choice.

---

# 7. Part F — Protected Field vs Protected Method

Consider these two designs.

## Design A

```java
class Employee {

    protected double salary;

}
```

A subclass can do:

```java
salary = -100000;
```

---

## Design B

```java
class Employee {

    private double salary;

    protected void increaseSalary(double amount) {
        if (amount > 0) {
            salary += amount;
        }
    }
}
```

The subclass can request a salary increase, but the superclass controls the state change.

---

## Question

 > **Why can a protected method sometimes be a better inheritance design than a protected field?**

---

# 8. Part G — Private Fields and Inheritance

Create:

```java
class Animal {

    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

Then:

```java
class Dog extends Animal {

    public Dog(String name) {
        super(name);
    }

    public void printName() {
        System.out.println(name);
    }
}
```

Try to compile it.

You should get a compiler error because:

```java
name
```

is private to `Animal`.

---

## Fix the Design

Change:

```java
System.out.println(name);
```

to:

```java
System.out.println(getName());
```

Now the subclass accesses the information through a public method provided by the superclass.

---

## Question

Why is:

```java
private String name;
```

plus:

```java
public String getName()
```

often preferable to:

```java
protected String name;
```

?

---

# 9. Part H — The Tricky `protected` Rule

Java's `protected` has an additional rule.

A protected member can be accessed:

1. inside the superclass;
2. by subclasses;
3. by classes in the same package.

The third rule is easy to forget.

---

## Experiment

Create this package structure:

```text
college/
    hr/
        Employee.java

    management/
        Manager.java
        TestManager.java
```

### `Employee.java`

```java
package college.hr;

public class Employee {

    protected double salary;

    public Employee(double salary) {
        this.salary = salary;
    }
}
```

### `Manager.java`

```java
package college.management;

import college.hr.Employee;

public class Manager extends Employee {

    public Manager(double salary) {
        super(salary);
    }

    public void changeMySalary() {
        salary = 100000;
    }
}
```

The following is allowed:

```java
salary = 100000;
```

because `Manager` is a subclass of `Employee`.

---

## Now create `TestManager.java`

```java
package college.management;

import college.hr.Employee;

public class TestManager extends Employee {

    public TestManager(double salary) {
        super(salary);
    }

    public void changeMySalary() {
        salary = 100000;
    }

    public void changeAnotherEmployee(Employee employee) {
        employee.salary = 100000;
    }
}
```

The first use of `salary` is allowed:

```java
salary = 100000;
```

The second use is **not** allowed:

```java
employee.salary = 100000;
```

because `employee` is an arbitrary `Employee` object.

---

## Question

Explain the difference between:

```java
salary = 100000;
```

and:

```java
employee.salary = 100000;
```

in this example.

Your answer should mention:

- inheritance;
- the object being accessed;
- the fact that the classes are in different packages.

---

# 10. Part I — Same Package Experiment

Create `Payroll` class:

```java
package college.hr;

public class Payroll {

    public void changeSalary(Employee employee) {
        employee.salary = 100000;
    }
}
```

This time `Payroll` is **not** a subclass.

But it is in the same package:

```text
college.hr
```

as `Employee`.

### Question

Does this compile?

Explain why.

Then answer:

> What does this tell you about the meaning of `protected` in Java?

---

# 11. Part J — Design Challenge: Employee Hierarchy

Extend your employee system.

Create:

```text
Employee
   ├── Manager
   ├── Developer
   └── Intern
```

### Employee

Common information:

```text
name
salary
```

Common behavior:

```text
calculateBonus()
```

Use appropriate encapsulation.

---

### Manager

Additional information:

```text
managementAllowance
```

Bonus:

```text
10% of salary + management allowance
```

---

### Developer

Additional information:

```text
programmingLanguage
```

Bonus:

```text
8% of salary
```

---

### Intern

Additional information:

```text
school
```

Bonus:

```text
0
```

---

## Design Requirement

Do **not** make every field `protected`.

For every field, decide:

```text
private
or
protected
```

You must justify each choice.

A strong design will probably keep most state private and expose behavior through methods.

---
# 12. Part M — JUnit Testing

Create JUnit tests for your employee hierarchy.

At minimum, test:

### Employee

- constructor stores the correct name;
- constructor stores the correct salary;
- employee bonus calculation.

### Manager

- constructor correctly initializes inherited state;
- management allowance is stored;
- Manager bonus is calculated correctly.

### Developer

- programming language is stored;
- Developer bonus is calculated correctly.

### Intern

- Intern bonus is zero.

---

## Required Design Test

Write a test showing that a Manager can change its salary using the superclass's controlled method.

For example, if the Manager has:

```java
public void giveRaise(double amount)
```

test:

```text
initial salary
        ↓
giveRaise(1000)
        ↓
new salary
```

Also test an invalid amount:

```text
giveRaise(-1000)
```

and verify that your chosen validation rule is respected.

---
---

# 13. Submission Checklist

- [ ] `Employee` superclass implemented
- [ ] `Manager` subclass implemented
- [ ] `Developer` subclass implemented
- [ ] `Intern` subclass implemented
- [ ] constructor chaining demonstrated
- [ ] `super(...)` used correctly
- [ ] at least one method overridden with `@Override`
- [ ] private/protected design choices explained
- [ ] protected field vs protected method analyzed
- [ ] private field with controlled subclass access implemented
- [ ] cross-package protected-access experiment completed
- [ ] same-package protected-access experiment completed
- [ ] inheritance vs composition questions completed
- [ ] JUnit tests written


---

