# Programming 2 — Assignment 1
## ArrayList and Inheritance

**Full Score:** 100  
**Work:** Individual or group of 2 students  

**UML:** Not required

---

# Grading

| Component | Marks |
|---|---:|
| Submitted project code | 60 |
| Post-submission challenge | 40 |
| **Total** | **100** |

## What is the post-submission challenge?

After final code submission, there will be **in-class extension challenges** built directly on the submitted project.

The challenge is designed to fit a **30-minute coding task** and will test **high-level understanding, not memorization**.

Each challenge:

- plugs into the existing submitted solution;
- targets a different skill from the assignment;
- is specific enough to grade consistently.

---

# Knowledge Points of This Assignment

1. **ArrayList**
2. **Inheritance**

This assignment contains **two separate problems**:

- **Problem 1 — ArrayList:** 50 marks
- **Problem 2 — Inheritance:** 50 marks

You are not required to combine the two problems.

---

# Individual or Group of Two

You may work:

- individually; or
- with **one partner**.

Maximum group size: **2 students**.

At the top of each main Java file, include:

```java
// Student 1: Full Name — Student ID
// Student 2: Full Name — Student ID
```

For an individual submission:

```java
// Student 1: Full Name — Student ID
// Student 2: N/A
```

Both students in a group are responsible for understanding the submitted code. Each student may be asked to explain orwrite or modify the program during the post-submission challenge.

---

# Problem 1 — Student Name List
## ArrayList — 50 marks

A college wants a simple program to keep track of student names.

Create a class:

```java
StudentList
```

that uses:

```java
ArrayList<String>
```

to store student names.

This problem is intentionally based on the basic `ArrayList` operations practiced in Lecture 4.

---

## Part 1.1 — Create the ArrayList
### 5 marks

Create an instance variable:

```java
ArrayList<String> names;
```

Initialize it in the constructor.

---

## Part 1.2 — Add Names
### 10 marks

Create:

```java
public void addName(String name)
```

The method should add the name to the list.

In your test program, add at least **6 names**.

Example:

```java
names.add("Ali");
names.add("Sara");
names.add("John");
```

---

## Part 1.3 — Display Names
### 10 marks

Create:

```java
public void displayNames()
```

Use an enhanced `for` loop to display all names.

Example:

```java
for (String name : names) {
    System.out.println(name);
}
```

---

## Part 1.4 — Find a Name
### 10 marks

Create:

```java
public boolean containsName(String name)
```

Return `true` if the name exists in the list.

Return `false` otherwise.

You may use the `contains()` method discussed in Lecture 4.

Test both cases:

- a name that exists;
- a name that does not exist.

---

## Part 1.5 — Remove a Name
### 10 marks

Create:

```java
public boolean removeName(String name)
```

Remove the name from the list if it exists.

Return:

- `true` if a name was removed;
- `false` if the name was not found.

Test both cases.

Do not use an unsafe enhanced-for loop to remove an element.

---

## Part 1.6 — Size
### 5 marks

Create:

```java
public int getNumberOfNames()
```

Return the number of names using:

```java
names.size()
```

Display the size before and after removing a name.

---

## Required Test Program

Create:

```java
ArrayListProblem
```

with a `main` method.

Your program must demonstrate:

1. creating the `StudentList`;
2. adding at least 6 names;
3. displaying the names;
4. checking for an existing name;
5. checking for a name that does not exist;
6. removing an existing name;
7. attempting to remove a name that does not exist;
8. displaying the number of names.

Keep the output simple and clear.

---

# Problem 2 — Employees
## Inheritance — 50 marks

A small company has employees.

Every employee has:

- a name;
- a salary.

A manager is an employee with one additional piece of information:

- department.

Create the following two classes:

```text
Employee
   ↑
Manager
```

The important Lecture 5 question is:

> Is a Manager an Employee?

Yes. Therefore:

```java
class Manager extends Employee
```

is appropriate.

---

## Part 2.1 — Employee
### 15 marks

Create:

```java
Employee
```

with:

```java
name
salary
```

Use appropriate access modifiers.

Create a constructor:

```java
public Employee(String name, double salary)
```

Create getters for the fields.

Create:

```java
public String toString()
```

that displays the employee's information.

---

## Part 2.2 — Manager
### 15 marks

Create:

```java
Manager extends Employee
```

Add:

```java
department
```

as a `String`.

Create a constructor:

```java
public Manager(String name,
               double salary,
               String department)
```

Use:

```java
super(...)
```

to initialize the Employee information.

Create a getter for `department`.

---

## Part 2.3 — Override toString
### 10 marks

Override:

```java
toString()
```

in `Manager`.

The Manager version should display:

- name;
- salary;
- department.

Use `@Override`.

---

## Part 2.4 — Simple Employee Method
### 10 marks

Add this method to `Employee`:

```java
public void giveRaise(double amount)
```

The method increases the employee's salary by the specified amount.

Example:

```java
Employee e = new Employee("Ali", 50000);
e.giveRaise(2000);
```

The salary should become:

```text
52000
```

Your Manager object should also be able to use this inherited method.

---

# Required Test Program

Create:

```java
InheritanceProblem
```

with a `main` method.

Your program must:

1. Create at least **2 Employee objects**.
2. Create at least **2 Manager objects**.
3. Display all objects using `toString()`.
4. Give a raise to at least one Employee.
5. Give a raise to at least one Manager.
6. Display the objects again so the salary change can be seen.

---

# Private vs Protected — Small Design Requirement

This assignment is intentionally simple.

For the Employee fields, choose either `private` or `protected` based on what was discussed in Lecture 5.

You must add **3–5 lines of comments** explaining your choice.

For example:

```java
// I chose private because Employee should control
// its own data. Manager can use the public methods
// without directly changing the fields.
private double salary;
```

There is no requirement to create a complicated design.

The important part is that you can explain your choice.

---

# Restrictions

Use only concepts covered in **Lecture 4 and Lecture 5**.

You may use:

- classes and objects;
- constructors;
- getters;
- simple methods;
- `ArrayList`;
- `add`;
- `get`;
- `set` if useful;
- `remove`;
- `size`;
- `contains`;
- enhanced `for` loop;
- `extends`;
- `super`;
- method overriding;
- `@Override`;
- `private`;
- `protected`;
- `public`.

Do **not** use concepts from later lectures.

In particular, do not use:

- polymorphism as an assignment requirement;
- abstract classes;
- abstract methods;
- interfaces;
- UML class diagrams;
- `Comparable`;
- `Comparator`;
- advanced collections;
- advanced exception handling;
- lambda expressions unless they were specifically demonstrated in your Lecture 4 material.

Keep the solution at the same level as the examples from Lectures 4 and 5.

---

# Submission

Submit the Java source files for both problems:

```text
Assignment1/
│
├── StudentList.java
├── ArrayListProblem.java
├── Employee.java
├── Manager.java
└── InheritanceProblem.java
```

If you are working as a pair, include both student IDs in the appropriate files.

---

# Post-Submission Challenge — 40 marks

The instructor will give extension challenges during class after the project submission.

The challenges will use your submitted code.

They are designed to be completed in approximately **30 minutes**.

You will not simply be asked to reproduce the original assignment. You will need to make a small change that shows you understand the code you submitted.

Possible challenge areas include:

### Challenge type A — ArrayList search
Modify the existing program to find and display a name at a specified position.

### Challenge type B — ArrayList modification
Modify the existing program to replace one name using `set()`.

### Challenge type C — Inheritance
Add one small method to `Manager` that uses information inherited from `Employee`.

### Challenge type D — Overriding
Modify the `Manager` output so that one additional piece of manager information is displayed.

The exact challenge will be given in class.

---

# Simple Marking Rubric

## Submitted Project — 60 marks

| Category | Marks |
|---|---:|
| Problem 1 — ArrayList | 30 |
| Problem 2 — Inheritance | 25 |
| Code organization / testing | 5 |
| **Total** | **60** |

## Post-Submission Challenge — 40 marks

| Category | Marks |
|---|---:|
| Correctly modifies submitted code | 20 |
| Uses the required Lecture 4/5 concept correctly | 10 |
| Explains the change / demonstrates understanding | 10 |
| **Total** | **40** |

---

# Final Reminder

You should be able to explain every important line of your submission.

The goal is not to create a sophisticated program.

The goal is to demonstrate that you understand:

```text
ArrayList
   ↓
add / remove / contains / size / loop

Inheritance
   ↓
extends / super / overriding
```

Keep your solution simple, readable, and at the level of Lectures 4 and 5.
