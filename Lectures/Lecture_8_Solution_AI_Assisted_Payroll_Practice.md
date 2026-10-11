# Lecture 8 —  AI-Assisted Payroll Practice

## Practice prompt

> Design a payroll system using an abstract `Employee` class and a `Taxable` interface.

Then critique the design:

- Is the abstract class really necessary?
- What belongs in the interface?
- Which members should be private?
- Can an employee be both taxable and something else?
- Can the design support a new employee type without changing existing code?

## Learning goals

This practice is not just about writing a payroll program. Students should critique the design and explain how abstract classes and interfaces support reuse, encapsulation, polymorphism, and extensibility.

---

## 1. Example Java solution

The following is one reasonable design. It uses an abstract `Employee` class for common employee data and behavior, and a `Taxable` interface for the capability to calculate tax. It also demonstrates polymorphism and allows new employee types to be added without rewriting the payroll processor.

```java
interface Taxable {
    double calculateTax();
}

abstract class Employee {
    private String name;
    private double grossSalary;

    public Employee(String name, double grossSalary) {
        this.name = name;
        setGrossSalary(grossSalary);
    }

    public String getName() {
        return name;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        if (grossSalary < 0) {
            throw new IllegalArgumentException(
                "Gross salary cannot be negative."
            );
        }
        this.grossSalary = grossSalary;
    }

    public abstract double calculatePay();
}

class SalariedEmployee extends Employee implements Taxable {
    public SalariedEmployee(String name, double annualSalary) {
        super(name, annualSalary);
    }

    @Override
    public double calculatePay() {
        return getGrossSalary();
    }

    @Override
    public double calculateTax() {
        return calculatePay() * 0.20;
    }
}

class Contractor extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public Contractor(String name, int hoursWorked, double hourlyRate) {
        super(name, 0);

        if (hoursWorked < 0 || hourlyRate < 0) {
            throw new IllegalArgumentException(
                "Hours and hourly rate cannot be negative."
            );
        }

        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculatePay() {
        return hoursWorked * hourlyRate;
    }
}

class Payroll {
    public static void printPayroll(Employee employee) {
        double gross = employee.calculatePay();
        double tax = 0.0;

        if (employee instanceof Taxable) {
            Taxable taxable = (Taxable) employee;
            tax = taxable.calculateTax();
        }

        double net = gross - tax;

        System.out.printf(
            "%s: Gross pay = $%.2f, Tax = $%.2f, Net pay = $%.2f%n",
            employee.getName(), gross, tax, net
        );
    }
}

public class PayrollDemo {
    public static void main(String[] args) {
        Employee employee1 =
            new SalariedEmployee("Amina", 60000);

        Employee employee2 =
            new Contractor("Ravi", 100, 45);

        Payroll.printPayroll(employee1);
        Payroll.printPayroll(employee2);
    }
}
```

### Example output

```text
Amina: Gross pay = $60000.00, Tax = $12000.00, Net pay = $48000.00
Ravi: Gross pay = $4500.00, Tax = $0.00, Net pay = $4500.00
```

**Important:** The 20% tax rate is illustrative only, not a real tax calculation. This is a deliberately simplified  example, not production payroll software.

---

## 2. Critique the design

### Question 1: Is the abstract class really necessary?

**Answer:** In this design, the abstract class is useful, although an abstract class is not automatically necessary in every payroll system.

`Employee` provides a common parent type for employees and holds shared data such as the employee's name and salary amount. It also defines a method contract:

```java
public abstract double calculatePay();
```

A `SalariedEmployee` and a `Contractor` calculate pay differently, so each subclass implements the method in its own way.

The abstract class supports polymorphism:

```java
Employee employee = new SalariedEmployee("Amina", 60000);
System.out.println(employee.calculatePay());
```

The reference type is `Employee`, but Java executes the `SalariedEmployee` implementation of `calculatePay()`.

If there were no shared employee data, common behavior, or useful superclass contract, an abstract class might add unnecessary complexity.

### Question 2: What belongs in the interface?

**Answer:** The interface should represent a capability or contract, rather than a category of employee.

```java
interface Taxable {
    double calculateTax();
}
```

This contract says that a class implementing `Taxable` must provide a tax-calculation method.

Employee-specific instance data—such as a name, salary, or hours worked—does not belong in this interface. That data belongs in the appropriate classes.

Different classes can implement the contract using different tax rules, without changing the meaning of the interface.

### Question 3: Which members should be private?

**Answer:** Fields should normally be private so that classes control how their internal state is accessed and changed.

For example:

```java
private String name;
private double grossSalary;
```

Private fields prevent other classes from assigning arbitrary values directly. Public methods such as getters and validated setters provide controlled access.

Subclass-specific data should also be private:

```java
private int hoursWorked;
private double hourlyRate;
```

The setter and constructor validate values before accepting them. This supports **encapsulation** and helps maintain valid object state.

### Question 4: Can an employee be both taxable and something else?

**Answer:** Yes. Java allows a class to implement multiple interfaces.

```java
interface Insurable {
    double calculateInsurancePremium();
}

class SalariedEmployee extends Employee
        implements Taxable, Insurable {
    // Implement the required methods.
}
```

The class extends one superclass, `Employee`, but implements two interfaces.

Interfaces allow capabilities to be combined without forcing every employee to support every capability.

### Question 5: Can the design support a new employee type without changing existing code?

**Answer:** Yes, provided the new class follows the existing contracts and the payroll processor is designed around those contracts.

For example, a `CommissionEmployee` could extend `Employee`, implement `calculatePay()`, and implement `Taxable` if tax applies to that employee.

The payroll processor accepts the new type through an `Employee` reference. If the new employee implements `Taxable`, the processor can calculate tax without needing a separate branch for every concrete employee class.

This illustrates the **Open/Closed Principle**: software components should be open to extension but closed to unnecessary modification.

---

## 3. Why keep payroll calculation in one place?

A possible alternative is to put a `calculateNetPay()` method in `Employee` and override it in every taxable subclass. That creates a risk: a developer might add a taxable employee but forget to override `calculateNetPay()`. The program could then display tax while failing to subtract it from net pay.

In this solution, `Payroll.printPayroll()` calculates gross pay, checks whether the employee implements `Taxable`, calculates tax if applicable, and calculates net pay.

```java
double gross = employee.calculatePay();
double tax = 0.0;

if (employee instanceof Taxable) {
    Taxable taxable = (Taxable) employee;
    tax = taxable.calculateTax();
}

double net = gross - tax;
```

This keeps the simplified deduction rule in one place. In a larger system, a payroll service and a more extensible tax-calculation design could reduce type checks and better separate responsibilities.

---

## 4. Further discussion: `private` versus `protected` fields

An abstract class is designed to be extended, but that does not mean all its fields should be accessible to subclasses.

Consider:

```java
abstract class Employee {
    private double salary;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException(
                "Salary cannot be negative."
            );
        }
        this.salary = salary;
    }
}
```

If `salary` were `protected`, a subclass could assign a negative value directly and bypass the validation method. With a private field, subclasses must use the available methods.
```
| Concern | `private` field | `protected` field |
|---|---|---|
| Direct subclass access | No | Yes |
| Can bypass setter validation | Not by direct field access | Yes |
| Freedom to change internal representation | Greater | More limited |
| Subclass convenience | Requires methods | More convenient |
| Encapsulation | Stronger | Weaker |
```
**Note:** Make fields private by default, including in abstract classes. Use public or protected methods to provide the access subclasses actually need. Choose protected fields only when direct subclass access is a deliberate design decision.

Protected fields can be reasonable in a deliberately inheritance-oriented design, but they make subclasses more dependent on the superclass's internal representation.

---
## 5. Optional extension questions

1. Add a `CommissionEmployee` whose pay is a base amount plus a commission.
2. Make `CommissionEmployee` implement `Taxable`.
3. Add an `Insurable` interface and implement it only for employee types eligible for insurance.
4. Explain why `Contractor` should not be forced to implement `Taxable` if the business rules do not require it.
5. Discuss how the example would need to change to support different tax rates, deductions, and jurisdiction-specific payroll rules.

These are design exercises; a real payroll system would require substantially more detailed business rules and validation.
