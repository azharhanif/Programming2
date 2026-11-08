# P2 — Assignment 3: College Employee Registry, Recursive Reports, and Exception Handling

**Submission:** Submit all required `.java` source files and an updated `.png` or `.jpg` UML class diagram.  
**Total:** 100 marks — **Submitted project: 40 marks; Post-submission challenge: 60 marks.**

## 1. Purpose and continuation from Assignment 2

This assignment is a direct continuation of **Assignment 2 — College Employee Management System**. Do not create a new unrelated project. Start with your Assignment 2 solution and extend it.

Your existing system already has an employee hierarchy, polymorphic payroll calculation, bonus eligibility, and employee sorting. Assignment 3 adds:

- `equals()` and `hashCode()` so employee identity is consistent.
- `HashSet<Employee>` to prevent duplicate employee records.
- `HashMap<String, Employee>` to look up an employee by ID.
- Recursive methods to display employees, calculate total payroll, and search employees.
- Exception handling to reject invalid employee data and report registration errors clearly.

## 2. Lecture alignment
```
| Assignment component                     | Lecture connection |
|---|---|
| Employee identity                        | Lecture 10 — `equals()` and `hashCode()` |
| Duplicate prevention                     | Lecture 10 — `Set` and `HashSet` |
| Employee directory                       | Lecture 10 — `HashMap` |
| Recursive reports and search             | Lecture 11 — base case, recursive case, `ArrayList` traversal |
| Validation and error reporting           | Lecture 12 — `throw`, `try-catch`, custom exceptions |
| Preserve the existing design             | Lectures 8–12 — interfaces, polymorphism, sorting, collections,                                                   recursion, and exception handling |
```
## 3. Existing classes to keep

Continue using the Assignment 2 classes:

- `Employee` — abstract superclass.
- `Manager` and `FacultyMember` — abstract subclasses.
- `DepartmentManager`, `ProjectManager`, `FullTimeFaculty`, and `PartTimeFaculty` — concrete employee classes.
- `BonusEligible` — interface.
- `CollegeEmployeeSystem` — manages employee records.
- Your existing test-driver class, such as `Main`.

**Do not remove or break** the Assignment 2 features: `calculatePay()`, `BonusEligible`, `Comparable<Employee>`, the pay `Comparator`, and polymorphic use of `ArrayList<Employee>`.

---

## Task 1 — Extend the College Employee Management System

**Submitted project: 40 marks**

### Part 1 — Define employee identity using `equals()` and `hashCode()` (5 marks)

**Lecture 10: object equality and the hashCode contract**

1. In `Employee.java`, override `equals(Object obj)`.
2. Define two employees as equal when their employee IDs are equal.
3. Apply the same identity rule across all employee subclasses. An employee must not become unequal merely because the other object belongs to a different employee subclass.
4. Override `hashCode()` in `Employee.java`.
5. Calculate the hash code from the same identity field used by `equals()`: `employeeId`.
6. Use `Objects.equals(...)` and `Objects.hash(...)`, or provide another correct implementation.
7. Do not compare the contents of employee ID strings using `==`.

Expected identity behaviour:

```text
Employee A has ID E101.
Employee B has ID E101.
A.equals(B) must return true.
A.hashCode() and B.hashCode() must be equal.
```

Different employees may have the same hash code. A collision is allowed; it does not mean the employees are equal.

**Important ID rule:** Do not change an employee ID while the employee is stored in a `HashSet` or used as a `HashMap` key. For this assignment, treat the ID as fixed after construction. If your existing design has a setter, do not use it to change a registered employee's ID.

### Part 2 — Prevent duplicates with `HashSet<Employee>` (5 marks)

**Lecture 10: Set and HashSet**

1. In `CollegeEmployeeSystem`, declare:

```java
private HashSet<Employee> uniqueEmployees;
```

2. Initialize the set in the `CollegeEmployeeSystem` constructor.
3. Use the set to determine whether an employee with the same identity is already registered.
4. Reject a duplicate employee ID.
5. If an employee is rejected as a duplicate, do not add it to any of the system's collections.
6. Demonstrate that two separately created employee objects with the same ID cannot both be registered.
7. Demonstrate that employees with different IDs can both be registered.

Implement an operation with this signature, unless your approved design uses a clearly documented equivalent:

```java
public boolean addEmployee(Employee employee)
```

Return `true` only when the employee is successfully registered. Return `false` when an employee with the same ID is already registered. Invalid input will be handled using the exception requirements in Part 7.

**Do not use `hashCode()` alone to decide whether two employees are equal.** `HashSet` uses hash codes to locate a candidate area and equality checks to distinguish elements.

### Part 3 — Create an employee directory with `HashMap` (4 marks)

**Lecture 10: HashMap**

1. In `CollegeEmployeeSystem`, declare:

```java
private HashMap<String, Employee> employeesById;
```

2. Initialize the map in the constructor.
3. Use the employee ID as the key and the employee object as the value.
4. Implement:

```java
public Employee getEmployeeById(String employeeId)
```

5. Use `employeesById.get(employeeId)` to retrieve the employee.
6. Return `null` when the ID is not registered.
7. Demonstrate one successful lookup and one lookup for a missing ID.

Example:

```java
Employee found = system.getEmployeeById("E102");

if (found != null) {
    System.out.println(found);
} else {
    System.out.println("Employee not found.");
}
```

**Important:** The key in this map is a `String`. Java uses `String.hashCode()` and `String.equals()` for the key. `Employee.hashCode()` is used by `HashSet<Employee>`, not by this map's string key.

### Part 4 — Keep the three collections synchronized (5 marks)

**Lecture 10: collection consistency**

Your system must keep these collections consistent:

```java
private ArrayList<Employee> employees;
private HashSet<Employee> uniqueEmployees;
private HashMap<String, Employee> employeesById;
```

1. Update `addEmployee(Employee employee)` so a successful registration adds the employee to all three collections.
2. If registration is rejected, none of the collections may change.
3. Implement:

```java
public boolean removeEmployee(String employeeId)
```

4. When an employee is removed, remove the same employee record from all three collections.
5. Return `true` when an employee is removed.
6. Return `false` when the employee ID is not registered.
7. Test that a removed employee cannot be found in the list, set, or map.

Use this safe registration sequence:

1. Validate the employee reference and employee ID as described in Part 7.
2. Check whether the ID is already registered.
3. If it is already registered, return `false`.
4. Add the employee to `uniqueEmployees`.
5. Add the employee to `employees`.
6. Add the employee to `employeesById`, using the employee ID as the key.
7. Return `true`.

Do not update only one or two collections. The operation is successful only when all three represent the same registered employee records.

### Part 5 — Recursive employee reports (5 marks)

**Lecture 11: recursion and ArrayList traversal**

Do not use a `for` loop or `while` loop inside either recursive helper method.

#### A. Recursive employee display

Implement:

```java
public void displayEmployeesRecursive()
```

Use a private helper method:

```java
private void displayEmployeesRecursive(int index)
```

Follow these instructions:

1. Start the helper with index `0`.
2. Use `index >= employees.size()` as the base case.
3. In the base case, return without displaying anything.
4. Otherwise, display the employee at the current index.
5. Make the recursive call using `index + 1`.
6. Ensure each employee is displayed exactly once.
7. Test the method with an empty list and a list containing several employees.

#### B. Recursive total payroll

Implement:

```java
public double calculateTotalPayrollRecursive()
```

Use a private helper method with an index parameter.

1. Start the helper with index `0`.
2. Return `0.0` when `index >= employees.size()`.
3. Otherwise, calculate the current employee's pay by calling `calculatePay()`.
4. Add that pay to the value returned by the next recursive call.
5. Make the next call using `index + 1`.
6. Do not write separate pay formulas for managers and faculty. Use polymorphism through `calculatePay()`.
7. Test the result with an empty list and with a list containing different employee subclasses.

The recursive relationship is:

```text
totalPay(index) =
    employees[index].calculatePay()
    + totalPay(index + 1)
```

### Part 6 — Recursive employee search using `equals()` (4 marks)

**Lectures 10 and 11: equality and recursive linear search**

Implement:

```java
public Employee findEmployeeRecursive(Employee target)
```

Use a private helper method with an index parameter.

1. Start the helper at index `0`.
2. If the target is `null`, return `null`.
3. If the index reaches `employees.size()`, return `null`.
4. Compare the current employee with the target by calling `equals()`.
5. If they are equal, return the employee from the list.
6. Otherwise, recursively search from index `+ 1`.
7. Do not use a loop in the recursive helper method.
8. Demonstrate a successful search using a separately created employee object with the same ID as a registered employee.
9. Demonstrate a search for an employee that is not registered.

This is a recursive **linear search** because it examines the list one element at a time. It uses `equals()` for comparison. It does not need `hashCode()` because it is not searching a hash-based collection.

### Part 7 — Validate input and handle registration errors (8 marks)

**Lecture 12: validation, `throw`, `try-catch`, and custom exceptions**

Create a custom unchecked exception named:

```java
InvalidEmployeeException
```

1. Create a new file named `InvalidEmployeeException.java`.
2. Make the class extend `RuntimeException`.
3. Add a constructor that accepts a message string.
4. Pass the message to the superclass constructor using `super(message)`.

Use this template:

```java
public class InvalidEmployeeException extends RuntimeException {
    public InvalidEmployeeException(String message) {
        super(message);
    }
}
```

#### A. Validate employee registration

At the beginning of `CollegeEmployeeSystem.addEmployee(Employee employee)`, perform these checks in this order:

1. If `employee == null`, throw `new InvalidEmployeeException("Employee cannot be null.")`.
2. If `employee.getEmployeeId() == null`, throw `new InvalidEmployeeException("Employee ID cannot be null.")`.
3. If `employee.getEmployeeId().isBlank()`, throw `new InvalidEmployeeException("Employee ID cannot be blank.")`.
4. Only after these checks, test whether the ID is already registered.
5. A duplicate ID is an ordinary registration outcome, not an exception. Return `false` for a duplicate.
6. Add a valid, non-duplicate employee to all three collections and return `true`.

Do not use exceptions for ordinary duplicate detection. Use the boolean return value for that situation.

#### B. Handle registration errors in the test-driver

In your `Main` or test-driver class:

1. Put the call to `addEmployee(...)` inside a `try` block.
2. Catch `InvalidEmployeeException` in a matching `catch` block.
3. Print a clear message using `e.getMessage()`.
4. Continue testing other valid employees after handling the invalid registration.
5. Also demonstrate a duplicate registration and show that it returns `false` without throwing an exception.

Example:

```java
try {
    boolean added = system.addEmployee(employee);
    System.out.println("Employee added: " + added);
}
catch (InvalidEmployeeException e) {
    System.out.println("Registration rejected: " + e.getMessage());
}
```

#### C. Test the following cases

Your test-driver must demonstrate all four cases:

| Test | Expected behaviour |
|---|---|
| Register a valid employee with a new ID | Employee is added; method returns `true` |
| Register a second object with an already-used ID | No exception; method returns `false` |
| Register `null` as the employee | `InvalidEmployeeException` is thrown and caught |
| Register an employee with a null or blank ID | `InvalidEmployeeException` is thrown and caught |

You may test null and blank IDs separately. If your `Employee` constructor already rejects invalid IDs, catch the exception around object construction as well. Do not weaken existing constructor validation simply to move the check into `addEmployee()`.

**Design distinction:** `throw` creates and throws the exception object. `throws` declares that a method may pass an exception to its caller. For this custom unchecked exception, a `throws InvalidEmployeeException` declaration is optional; it is not required by the compiler.

### Part 8 — Integrated test program and updated UML (4 marks)

**Lectures 8–12: integration and testing**

Update your existing test-driver. Demonstrate each item:

1. Create at least one object of every concrete employee type from Assignment 2.
2. Register the employees using `addEmployee(...)`.
3. Demonstrate that a duplicate ID is rejected.
4. Demonstrate successful and unsuccessful `HashMap` lookups.
5. Demonstrate employee removal and confirm all three collections are updated.
6. Display employees using the recursive display method.
7. Calculate total payroll using the recursive payroll method.
8. Search recursively using an employee object with the same ID as a stored employee.
9. Demonstrate the `InvalidEmployeeException` for invalid registration input.
10. Demonstrate that the Assignment 2 natural ordering and pay comparator still work.
11. Demonstrate that `calculatePay()` and `BonusEligible` still work polymorphically.

Update the UML diagram to show:

- The Assignment 2 employee hierarchy.
- The `BonusEligible` interface and its implementing classes.
- `InvalidEmployeeException` extending `RuntimeException`.
- `CollegeEmployeeSystem`.
- Its `ArrayList<Employee>`, `HashSet<Employee>`, and `HashMap<String, Employee>` fields.
- The important new methods: `addEmployee`, `removeEmployee`, `getEmployeeById`, `displayEmployeesRecursive`, `calculateTotalPayrollRecursive`, and `findEmployeeRecursive`.
- Relevant associations and multiplicities.

The UML diagram must match your submitted Java code.

---

## Task 2 — Post-submission in-class challenge

**60 marks | 30 minutes**

After submitting the project worth 40 marks, you will complete **one** instructor-assigned challenge worth 60 marks. The instructor will provide the exact challenge during class.


## 4. Submission checklist

- [ ] All Java source files needed to compile and run the project.
- [ ] Updated UML diagram as a readable `.png` or `.jpg`.
- [ ] Assignment 2 employee hierarchy and features are preserved.
- [ ] `equals()` and `hashCode()` use the same employee identity field.
- [ ] `HashSet<Employee>` rejects duplicate employee identities.
- [ ] `HashMap<String, Employee>` supports lookup by employee ID.
- [ ] The `ArrayList`, `HashSet`, and `HashMap` remain synchronized after additions and removals.
- [ ] Recursive display works for empty and non-empty lists.
- [ ] Recursive payroll returns the correct total and uses polymorphism.
- [ ] Recursive search uses `equals()` and handles found/not-found cases.
- [ ] Invalid employee input throws `InvalidEmployeeException`.
- [ ] The test-driver catches invalid registration errors and continues.
- [ ] Duplicate registration returns `false` without throwing an exception.
- [ ] The existing `BonusEligible`, `Comparable`, and `Comparator` features still work.
- [ ] The project compiles and runs successfully.
- [ ] The UML diagram matches the code.

## 5. Marking rubric — post-submission challenge (60 marks)

| Criterion | Marks | Evidence |
|---|---:|---|
| Interpretation and design | 12 | Understands the main problem and both extensions; selects a suitable design |
| Main problem implementation | 20 | Correct implementation of the core requirement |
| Extension 1 | 10 | Correctly completes the first tricky extension |
| Extension 2 | 10 | Correctly completes the second tricky extension |
| Integration, tests, and explanation | 8 | Existing system remains correct; demonstrates relevant tests and explains the reasoning |
| **Total** | **60** | |

**Overall: 100 marks** — 40 marks for the submitted project and 60 marks for the 30-minute in-class challenge.
