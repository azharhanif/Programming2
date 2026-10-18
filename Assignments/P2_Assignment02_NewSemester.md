# P2 — Assignment 2: College Employee Management System

**Due Date:** To be announced for the new semester.  
**Submission:** Submit all `.java` source files and a `.png` or `.jpg` image of your complete UML class diagram.  
**Full Score:** 100 marks, **Submitted code (40 marks)**, **Post Submission Challenge (60 marks)**.

## Knowledge Points

This assignment continues Assignment 1 (`Employee`, `Manager`, constructors, `ArrayList`, and inheritance) and extends that design with the next lecture concepts.
```
| Assignment part                               | Main lecture concept                                                  |
|-----------------------------------------------|-----------------------------------------------------------------------|
| Part 1 — Abstract hierarchy                   | **Lecture 8:** Abstract classes and abstract methods                  |
| Part 2 — Constructors and object methods      | Assignment 1 review; supports Lectures 7–8                            |
| Part 3 — Polymorphic employee system          | **Lecture 7:** Polymorphism and overriding                            |
| Part 4 — Optional capability interface        | **Lectures 8 and 7:** Interface contract and interface polymorphism   |
| Part 5 — Natural ordering                     | **Lecture 9:** `Comparable<T>` and `compareTo()`                      |
| Part 6 — Alternative sorting                  | **Lecture 9:** `Comparator<T>`                                        |
| Part 7 — UML diagram                          | UML review; relationships used throughout Lectures 7–9                |
| Part 8 — Testing                              | **Lectures 7–9:** Verify polymorphism, interfaces, and sorting        |
```
---

## Task 1 — Design and implement the system

### Scenario

A college wants a **College Employee Management System** to store employees, display their information, calculate their pay, and sort them for different administrative purposes.

In Assignment 1, you created an `Employee` class and a `Manager` subclass. Continue that work rather than starting from an unrelated project:

- Make `Employee` an abstract superclass.
- Keep shared employee information and behavior in the superclass.
- Add different employee categories and specialized employee types.
- Use polymorphism to process different types through one `ArrayList<Employee>`.
- Add an interface for an optional capability.
- Support both natural ordering and an alternative ordering.

### Part 1 — Three-layer class hierarchy
**Concept source: Lecture 8 — Abstract Class and Interface**  
**Marks: 15**

Use this minimum structure:

```text
                         Employee (abstract)
                         /                 \
             Manager (abstract)       FacultyMember (abstract)
                /          \              /             \
   DepartmentManager  ProjectManager  FullTimeFaculty  PartTimeFaculty
```

Requirements:

1. `Employee` must be abstract and cannot be instantiated directly.
2. It must contain shared fields such as `employeeId`, `name`, and `baseSalary`.
3. It must declare `public abstract double calculatePay();`.
4. `Manager` and `FacultyMember` must be abstract subclasses of `Employee`.
5. `DepartmentManager` and `ProjectManager` extend `Manager`.
6. `FullTimeFaculty` and `PartTimeFaculty` extend `FacultyMember`.
7. Every concrete class implements `calculatePay()` using a simple, documented role-appropriate formula. Do not add tax or legal calculations.
8. Each concrete class has at least one role-specific field, such as department, project name, teaching allowance, or session allowance.
9. Use suitable access modifiers and encapsulation. Do not duplicate `employeeId`, `name`, or `baseSalary` in subclasses.

**Connection to Assignment 1:** You may reorganize the old `Employee` and `Manager` classes to fit this design. 

### Part 2 — Constructors and standard object methods
**Concept source: Assignment 1 review; supports Lectures 7–8**  
**Marks: 10**

Each class in the hierarchy must provide, where appropriate:

1. A default constructor.
2. A parameterized constructor.
3. A copy constructor.
4. Getters and setters for fields that should be accessible.
5. An informative `toString()`.
6. An overridden `equals(Object obj)` with a documented identity rule. Use employee ID for identity and keep the rule consistent across subclasses.

Use `super(...)` to initialize inherited state. Copy constructors must copy source data, not unrelated defaults. Reuse parent information in `toString()` where appropriate. Do not use `==` to compare `String` contents.

### Part 3 — Demonstrate polymorphism
**Concept source: Lecture 7 — Polymorphism**  
**Marks: 10**

Create `CollegeEmployeeSystem` containing:

```java
private ArrayList<Employee> employees;
```

It must:

1. Initialize the collection.
2. Add an employee.
3. Display all employees.
4. Calculate and return total payroll using `calculatePay()`.
5. Process employees through an `Employee` reference in a loop.
6. Avoid a long `if/else if` chain to calculate pay for each subclass.

Your test program must store all four concrete types in the same collection. Demonstrate that a call to `calculatePay()` through an `Employee` reference invokes the implementation belonging to the actual object type.

### Part 4 — User-defined interface
**Concept source: Lecture 8 — Interface contract; Lecture 7 — Interface polymorphism**  
**Marks: 5**

Create this interface or an equivalent approved interface:

```java
public interface BonusEligible {
    double calculateBonus();
}
```

Requirements:

1. Implement `BonusEligible` in at least two concrete employee classes, but not necessarily every class.
2. Each implementing class provides its own meaningful `calculateBonus()`.
3. Demonstrate the interface through an interface reference or a collection of `BonusEligible` objects.
4. Do not add bonus-specific methods to `Employee` merely to avoid using the interface. This interface represents an optional capability.

The bonus formula is your choice. Keep it simple and document it.

### Part 5 — Natural ordering with `Comparable`
**Concept source: Lecture 9 — Comparable and `compareTo()`**  
**Marks: 5**

Make `Employee` implement `Comparable<Employee>`. The natural ordering must be **employee ID ascending**.

Requirements:

1. Implement `compareTo(Employee other)`.
2. Compare IDs safely: use `String.compareTo()` for string IDs or the appropriate comparison method for numeric IDs. Do not use subtraction for numeric comparisons.
3. Document the ordering rule.
4. Demonstrate natural sorting with `Collections.sort(employees)` or another suitable natural-order sort.

Natural ordering must not depend on a subclass-specific field.

### Part 6 — Alternative ordering with `Comparator`
**Concept source: Lecture 9 — Comparator and multiple sorting rules**  
**Marks: 5**

Create at least one `Comparator<Employee>` that sorts by **calculated pay, highest first**. If two employees have the same calculated pay, sort them by name ascending.

Requirements:

1. Use `Comparator<Employee>`.
2. Demonstrate the comparator in the test program.
3. Keep it separate from `Employee.compareTo()`.
4. Do not combine comparison results with arbitrary numeric weights.

You may use a named comparator class, an anonymous class, or comparator utility methods covered in lecture.

### Part 7 — UML class diagram
**Concept source: UML review; class relationships in Lectures 7–9**  
**Marks: 5**

Draw a UML diagram of the complete project. It must show:

- All classes and the `BonusEligible` interface.
- The three-layer inheritance hierarchy.
- Which classes are abstract.
- Important fields and methods, including `calculatePay()`, `compareTo()`, and `calculateBonus()`.
- Which classes implement `BonusEligible`.
- The association between `CollegeEmployeeSystem` and its employee collection.
- Inheritance and interface-realization relationships using appropriate UML notation.

Submit the diagram as a `.png` or `.jpg`. A screenshot is acceptable if readable. The diagram must match the submitted Java code.

### Part 8 — Test program and evidence
**Concept source: Lectures 7, 8, and 9**  
**Marks: 5**

Create a `Main` or test-driver class that:

1. Creates at least one object of each concrete employee type.
2. Adds all objects to one `CollegeEmployeeSystem`.
3. Displays employee details and total payroll.
4. Demonstrates polymorphic calls to `calculatePay()`.
5. Demonstrates `BonusEligible` for at least two eligible employees.
6. Sorts by natural order (employee ID).
7. Sorts by comparator order (pay descending, then name ascending).
8. Demonstrates copy constructors and `equals()` with at least one meaningful test.
9. Includes a tie in calculated pay so the name tie-breaker can be checked.

The project must compile and run. Make the output easy to verify.

---

## Task 2 — Post-submission in-class challenge

**Weight: 60 marks out of 100**  
**Time limit: 30 minutes**

The submitted project is worth **40 marks**. After submission, you will complete a separate, instructor-assigned challenge based on your own project. It tests understanding and the ability to extend your design, not memorization.

The exact challenge and mark allocation will be provided in class. Variants may differ between students.
```
 Challenge skill                                                                                                        
-------------------------------------------------------------------------------                                                                                         
Add a new employee type and demonstrate it through an `Employee` reference                  
Decide whether a behavior belongs in an abstract class or an interface; implement the contract                                                                        
Add or correct natural ordering or an alternative comparator, including a tie-breaker 
```
The challenge may ask you to modify existing classes, update the system class, and add a short test. Be ready to explain why your solution uses an abstract class, an interface, `Comparable`, or `Comparator`.

**Do not implement a guessed challenge in advance.** The exact requirements will be provided during the in-class challenge.

---

## Submission checklist

- [ ] All `.java` source files required to compile and run the project.
- [ ] Complete UML diagram as a `.png` or `.jpg`.
- [ ] Test-driver class demonstrating all requirements.
- [ ] Comments documenting pay and bonus formulas.
- [ ] Project compiles and runs without errors.
- [ ] `Employee` is abstract and the hierarchy contains all required layers.
- [ ] All concrete classes implement `calculatePay()`.
- [ ] One `ArrayList<Employee>` stores all concrete employee types.
- [ ] The system uses polymorphism rather than type-checking chains to calculate pay.
- [ ] At least two concrete classes implement `BonusEligible`.
- [ ] Natural sorting uses `Comparable<Employee>` and employee ID.
- [ ] Alternative sorting uses `Comparator<Employee>` and the required tie-breaker.
- [ ] Copy constructors, `equals()`, and `toString()` work correctly and consistently.
- [ ] UML matches the Java implementation.

## Marking rubric — submitted project (60 marks)

| Criterion | Marks | What is assessed |
|---|---:|---|
| Abstract hierarchy and required classes | 15 | Three layers, correct inheritance, abstract classes, role-specific fields, `calculatePay()` |
| Constructors and object methods | 10 | Default/parameterized/copy constructors, accessors, `equals()`, `toString()` |
| Polymorphism and system collection | 10 | One `ArrayList<Employee>`, system operations, polymorphic pay calculation, total payroll |
| User-defined interface | 5 | `BonusEligible`, at least two implementations, interface demonstration |
| `Comparable` natural ordering | 5 | Correct, consistent employee-ID ordering |
| `Comparator` alternative ordering | 5 | Pay descending, name ascending for ties, working demonstration |
| UML class diagram | 5 | Complete, readable, correct relationships, matches code |
| Test program and evidence | 5 | Demonstrates the required behavior, including a sorting tie, copying, and equality |
| **Total submitted project** | **60** | |

## Marking rubric — post-submission challenge (40 marks)

| Criterion | Marks | What is assessed |
|---|---:|---|
| Correct interpretation and design choice | 10 | Identifies the right class/interface and comparison behavior |
| Correct implementation | 20 | Meets requirements and integrates with the existing project |
| Testing and explanation | 10 | Demonstrates result, handles relevant edge cases, explains reasoning |
| **Total challenge** | **40** | |

**Overall score: 100 marks** — 60 for the submitted project and 40 for the 30-minute in-class challenge.

## Learning objective

The goal is not simply to create more classes. It is to design a system where:

- an abstract class defines shared state and required behavior;
- subclasses provide specialized implementations;
- polymorphism processes different employee types through one parent reference;
- an interface represents an optional capability;
- `Comparable` provides one natural ordering;
- `Comparator` provides alternative ordering without changing the natural ordering.

This assignment builds directly on Assignment 1 and applies Lectures 7, 8, and 9 in one coherent Java project.
