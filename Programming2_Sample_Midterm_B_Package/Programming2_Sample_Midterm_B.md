# Programming 2 — Sample Midterm Exam B
## Review through Polymorphism

**Total: 250 marks**  
**Recommended time: 90 minutes**  
**Purpose:** Sample/practice exam. Solutions are shown immediately after each question.


### Topics
Programming fundamentals and OOP review, arrays/ArrayList, classes and objects, constructors, encapsulation, inheritance, overriding, reference type vs. actual object type, upcasting, downcasting, `instanceof`, and polymorphic collections.


---

# Part A — Theory and Concept Check
**90 marks**

## Q1 — Quick Concepts [18 marks]

Answer briefly.

**a)** What is the difference between an instance variable and a local variable? [6]

**b)** What does `super(...)` do in a subclass constructor? [6]

**c)** What is the difference between the reference type and the actual object type in:

```java
Employee e = new Manager("Sara", 70000, "Science");
```

[6]

### Solution

**a)** An instance variable belongs to an object and normally stores that object's state. A local variable is declared inside a method/block and exists for that method/block's execution.

**b)** `super(...)` calls a constructor of the superclass as part of constructing the subclass object.

**c)** The reference type is `Employee`; the actual object type is `Manager`. The reference type controls which members can be requested through `e`, while the actual object type matters when an overridden method is selected at runtime.

---

## Q2 — ArrayList Tracing [20 marks]

Consider:

```java
ArrayList<String> names = new ArrayList<>();

names.add("Ali");
names.add("Sara");
names.add("Mina");
names.add(1, "Omar");
names.remove("Sara");
```

**a)** What is the final list? [8]

**b)** What does `names.size()` return? [4]

**c)** What does `names.get(1)` return? [4]

**d)** Explain why `names.remove(1)` and `names.remove("Mina")` are different operations. [0.4]

### Solution

**a)** `[Ali, Omar, Mina]`

**b)** `3`

**c)** `"Omar"`

**d)** `remove(1)` uses the integer index and removes the element at index 1. `remove("Mina")` removes the matching object/value.

---

## Q3 — Inheritance and Encapsulation [20 marks]

Given:

```java
class Employee {
    private String name;
    protected double salary;

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
}

class Manager extends Employee {
    private String department;

    public Manager(String name, double salary, String department) {
        super(name, salary);
        this.department = department;
    }
}
```

Answer:

**a)** Why can `Manager` directly access `salary` but not `name`? [7]

**b)** Why is `super(name, salary)` important? [7]

**c)** Is `department` inherited from `Employee`? Explain. [6]

### Solution

**a)** `salary` is `protected`, so a subclass can access it. `name` is `private`, so it can only be accessed directly inside `Employee`. `Manager` can use `getName()`.

**b)** It calls the `Employee` constructor so the Employee-defined part of the Manager object's state is initialized correctly.

**c)** No. `department` is declared in `Manager`, so it is Manager-specific state.

---

## Q4 — Overriding and Runtime Method Selection [16 marks]

```java
class Animal {
    public void speak() {
        System.out.println("Animal");
    }
}

class Dog extends Animal {
    @Override
    public void speak() {
        System.out.println("Dog");
    }
}

Animal a = new Dog();
a.speak();
```

**a)** What is printed? [6]

**b)** Why does `Dog.speak()` execute even though the reference type is `Animal`? [6]

**c)** Would this compile?

```java
a.fetch();
```

Assume `fetch()` exists only in `Dog`. Explain. [4]

### Solution

**a)** `Dog`

**b)** The actual object is a `Dog`. Since `speak()` is overridden, Java selects the overridden implementation at runtime.

**c)** No. The reference type is `Animal`, and `Animal` does not declare `fetch()`. The compiler checks whether the requested member is available through the reference type.

---

## Q5 — Upcasting, `instanceof`, and Downcasting [16 marks]

```java
Employee e = new Manager("Nadia", 72000, "IT");
```

**a)** Is this an example of upcasting? [4]

**b)** Write one line using `instanceof` to check whether `e` refers to a `Manager`. [4]

**c)** Write the Java statement that safely creates a `Manager` reference from `e`, assuming the `instanceof` test was true. [4]

**d)** Does the cast create a new Manager object? Explain. [4]

### Solution

**a)** Yes. A `Manager` object is being viewed through an `Employee` reference.

**b)**
```java
if (e instanceof Manager)
```

**c)**
```java
Manager m = (Manager) e;
```

**d)** No. The cast does not create or transform an object. It changes how the existing object is accessed through the reference.

---

# Part B — Coding
**90 marks**

## Q6 — Count Digits [18 marks]

Write:

```java
public static int countDigits(String text)
```

The method returns the number of digit characters (`0`–`9`) in `text`.

Example:

```java
countDigits("A12B7")   // 3
countDigits("Hello")   // 0
```

### Solution

```java
public static int countDigits(String text) {
    int count = 0;

    for (int i = 0; i < text.length(); i++) {
        char c = text.charAt(i);

        if (Character.isDigit(c)) {
            count++;
        }
    }

    return count;
}
```

---

## Q7 — Modify an ArrayList [20 marks]

Write:

```java
public static boolean replaceFirst(
        ArrayList<String> names,
        String oldName,
        String newName)
```

The method replaces only the **first occurrence** of `oldName`.

Return `true` if a replacement was made and `false` otherwise.

Example:

```text
Before: [Ali, Sara, Ali, Mina]
replaceFirst(names, "Ali", "Omar")
After:  [Omar, Sara, Ali, Mina]
```

### Solution

```java
public static boolean replaceFirst(
        ArrayList<String> names,
        String oldName,
        String newName) {

    for (int i = 0; i < names.size(); i++) {
        if (names.get(i).equals(oldName)) {
            names.set(i, newName);
            return true;
        }
    }

    return false;
}
```

---

## Q8 — Create an Inheritance Pair [26 marks]

Write the following classes.

### Employee

Fields:
- `private String name`
- `protected double salary`

Constructor:

```java
Employee(String name, double salary)
```

Methods:
- `getName()`
- `getSalary()`
- `giveRaise(double amount)`
- `getDescription()` returning `"Employee: " + name`

### Manager

`Manager` extends `Employee`.

Additional field:
- `private String department`

Constructor:

```java
Manager(String name, double salary, String department)
```

Requirements:
- use `super(...)`
- provide `getDepartment()`
- override `getDescription()` to return:

```text
Manager: <name>, Department: <department>
```

### Solution

```java
class Employee {
    private String name;
    protected double salary;

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

    public void giveRaise(double amount) {
        salary += amount;
    }

    public String getDescription() {
        return "Employee: " + name;
    }
}
```

```java
class Manager extends Employee {
    private String department;

    public Manager(String name, double salary, String department) {
        super(name, salary);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String getDescription() {
        return "Manager: " + getName()
                + ", Department: " + department;
    }
}
```

---

## Q9 — Polymorphic ArrayList [26 marks]

Assume the `Employee` and `Manager` classes from Q8.

Write code that:

1. Creates an `ArrayList<Employee>`.
2. Adds one `Employee` and two `Manager` objects.
3. Uses one loop to print `getDescription()` for every object.
4. Uses a second loop to find Managers with `instanceof`.
5. For a Manager, downcast safely and print its department.

### Solution

```java
ArrayList<Employee> employees = new ArrayList<>();

employees.add(new Employee("Ali", 50000));
employees.add(new Manager("Sara", 70000, "Science"));
employees.add(new Manager("Mina", 75000, "IT"));

for (Employee e : employees) {
    System.out.println(e.getDescription());
}

for (Employee e : employees) {
    if (e instanceof Manager) {
        Manager m = (Manager) e;
        System.out.println("Department: " + m.getDepartment());
    }
}
```

The collection type is `Employee`, but the actual objects can be different subclasses. The first loop demonstrates polymorphic method calls; the second demonstrates safe downcasting.

---

# Programming Test

**Recommended time: 20–25 minutes.**

## P1 — Same Object, Two References [30 marks]

Starter file: `SameObjectSampleB.java`

Complete the program so that:
- `employee1` and `employee2` refer to the same Employee object.
- A raise of $5,000 is made through `employee2`.
- Both references are printed afterward.
- Both show the updated salary.

### Solution
See Attached file.
---

## P2 — Polymorphic Employee List [40 marks]

Starter file: `PolymorphismSampleB.java`

Complete the program so that:
- The list is `ArrayList<Employee>`.
- It contains one Employee, one Manager, and one Programmer.
- A single loop prints each object's `work()` result.
- `instanceof` finds Managers.
- A safe downcast calls `holdMeeting()`.
- No separate Manager list is created.

### Solution
See Attached file

---

# Preparation Hints

You should be able to:
- trace an `ArrayList` after `add`, `set`, and `remove`
- distinguish `remove(index)` from `remove(value)`
- use `size()` correctly
- write simple loops and methods
- distinguish instance variables from local variables
- write constructors
- use `private`, `protected`, and `public`
- explain `super(...)`
- distinguish overriding from overloading
- identify an is-a relationship
- distinguish reference type from actual object type
- explain upcasting
- use `instanceof`
- perform safe downcasting
- explain that casting does not create a new object
- use `ArrayList<Superclass>` with subclass objects
- explain runtime selection of overridden methods
