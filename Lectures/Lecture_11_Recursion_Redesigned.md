# Lecture 11 — Recursion

## Learning goals

By the end of this lecture, you should be able to:

1. Explain recursion and identify a method that calls itself.
2. Identify and write a correct **base case** and **recursive case**.
3. Ensure that each recursive call makes progress toward the base case.
4. Trace recursive calls and returns using the call stack, including statements placed before and after a recursive call.
5. Compare a recursive solution with an equivalent loop-based solution.
6. Write recursive methods that process an `ArrayList` one element at a time.
7. Apply recursion to familiar Programming 2 objects, including `Employee`/`Manager` objects and `Shape` subclasses.
8. Explain how recursion works with polymorphic references: the declared reference type controls which members are accessible, while overridden instance methods are selected using the actual object type.
9. Use recursion to traverse a collection whose objects implement `Comparable`, without confusing traversal with sorting.
10. Explain how `equals()` and `hashCode()` relate to object identity/equality, and why recursion does not replace their contracts.
11. Test recursive methods with base-case, ordinary, boundary, and invalid inputs.

**Connection to earlier lectures:** This lecture reuses the `Employee`/`Manager` polymorphism example from Lecture 7, the `Shape` abstract class and its `calcArea()`/`calcPerimeter()` methods from Lecture 8, and the object-comparison and collection ideas from Lectures 9 and 10. The goal is to apply recursion to Java objects you have already studied—not to introduce a new object-oriented programming unit.

---

## 1. What is recursion?

### 1.1 The basic idea

Recursion is a way to solve a problem by having a method call itself.

A recursive method does not solve every part of the problem in one step. Instead, it solves a small part and asks another call of the same method to solve a smaller version of the problem.

**Input assumptions in this lecture:** Unless an exercise says otherwise, examples such as `printInfo`, `countdown`, `sum`, and `powerOfTwo` are demonstrated with non-negative integer arguments. Their simple base cases are designed for that input range; they do not automatically validate negative arguments.

Think about a set of boxes placed inside one another. You open one box and find a smaller box inside it. You keep opening boxes until you reach the smallest box. Then you work your way back out.

That is the basic idea of recursion:

- A method calls itself.
- Each call works on a smaller or simpler problem.
- Eventually, a call reaches a condition where it already knows the answer.
- The method calls then return their results.

### 1.2 A method that calls itself

Consider this method:

```java
public static void printInfo() {
    System.out.println("Hello");
    printInfo();  // The method calls itself
}
```

The method prints `Hello`, then calls itself. The new call prints `Hello`, then calls itself again. This continues without stopping.

If we call it from `main`:

```java
public static void main(String[] args) {
    printInfo();
}
```

The output begins like this:

```text
Hello
Hello
Hello
Hello
Hello
...
```

This method has no stopping condition. It will eventually cause a `StackOverflowError` because Java keeps creating method calls that never finish.

### 1.3 The same behaviour using a loop

The same endless printing can be written using a loop:

```java
public static void printInfo() {
    while (true) {
        System.out.println("Hello");
    }
}
```

Both versions continue printing forever:

- The recursive version repeatedly calls the same method.
- The loop version repeatedly executes the loop body.

This shows that recursion and loops can sometimes solve the same repeated task. However, recursion can be easier to understand for problems that naturally break into smaller versions of themselves.

### 1.4 A recursive method that stops

Now consider a method that prints `Hello` a specific number of times:

```java
public static void printInfo(int num) {
    if (num == 0) {
        return;
    }

    System.out.println("Hello");
    printInfo(num - 1);
}
```

Call it with:

```java
public static void main(String[] args) {
    printInfo(3);
}
```

Output:

```text
Hello
Hello
Hello
```

Why does it stop?

1. The first call receives `3`. It prints `Hello` and calls `printInfo(2)`.
2. The second call receives `2`. It prints `Hello` and calls `printInfo(1)`.
3. The third call receives `1`. It prints `Hello` and calls `printInfo(0)`.
4. The fourth call receives `0`. It reaches `return` and stops.

The important difference is that the parameter becomes smaller each time. It moves toward `0`, where the method knows it can stop.

### 1.5 Comparing the loop and recursive versions

A loop can print the same number of messages:

```java
public static void printInfoLoop(int num) {
    for (int i = 0; i < num; i++) {
        System.out.println("Hello");
    }
}
```

The recursive version:

```java
public static void printInfoRecursion(int num) {
    if (num == 0) {
        return;
    }

    System.out.println("Hello");
    printInfoRecursion(num - 1);
}
```

Both methods print `Hello` three times when called with `3`.

The loop uses a counter (`i`) to control repetition. The recursive method uses a parameter (`num`) that gets closer to its base case.

---

## 2. How to write a recursive method

A recursive method needs two important parts:

1. **Base case:** the situation where the method already knows the answer and can stop.
2. **General pattern:** what the method does for other cases, usually including a call to itself with a smaller or simpler problem.

If either part is missing or incorrect, the recursion may not work.

### 2.1 The base case

The **base case** is the simplest situation for which we know the answer directly.

Imagine a method called `sum(int x)`. It calculates the sum of all integers in the range `[0, x)`. For the implementation below, assume `x >= 0`; negative input is outside this method’s stated domain.

The notation `[0, x)` means:

- Start at `0`.
- Include `0`.
- Stop before `x`.

For example:

```text
sum(4) = 0 + 1 + 2 + 3 = 6
sum(3) = 0 + 1 + 2 = 3
sum(2) = 0 + 1 = 1
sum(1) = 0
sum(0) = 0
```

If someone asks for `sum(4)`, we need to calculate. The same is true for `sum(3)`, `sum(2)`, and `sum(1)`.

But if someone asks for `sum(0)`, we immediately know the answer: there are no integers in the range from `0` up to, but not including, `0`. The sum is `0`.

Therefore:

```java
if (x == 0) {
    return 0;
}
```

This is the base case.

**Remember:** The base case is not just a condition that stops the method. It must also return the correct answer for that simplest situation.

### 2.2 The general pattern

The **general pattern** explains how to solve a larger problem using a smaller version of the same problem.

Let's examine a few values:

```text
sum(1) = 0

sum(2) = 0 + 1
       = sum(1) + 1

sum(3) = 0 + 1 + 2
       = sum(2) + 2

sum(4) = 0 + 1 + 2 + 3
       = sum(3) + 3
```

A pattern appears:

```text
sum(4) = sum(3) + 3
sum(3) = sum(2) + 2
sum(2) = sum(1) + 1
```

For a general value `x`:

```text
sum(x) = sum(x - 1) + (x - 1)
```

This equation is the general pattern.

In Java, we can write:

```java
return sum(x - 1) + (x - 1);
```

Notice two things:

1. The method calls itself: `sum(x - 1)`.
2. The argument becomes smaller: `x - 1`.

That smaller argument moves the method toward the base case `x == 0`.

### 2.3 Combine the base case and general pattern

Now combine the two parts:

```java
public static int sum(int x) {
    // Base case
    if (x == 0) {
        return 0;
    }

    // General pattern
    return sum(x - 1) + (x - 1);
}
```

Test it:

```java
public static void main(String[] args) {
    System.out.println(sum(4));
    System.out.println(sum(5));
    System.out.println(sum(0));
}
```

Output:

```text
6
10
0
```

Why is `sum(4)` equal to `6`?

```text
sum(4)
= sum(3) + 3
= (sum(2) + 2) + 3
= ((sum(1) + 1) + 2) + 3
= (((sum(0) + 0) + 1) + 2) + 3
= (((0 + 0) + 1) + 2) + 3
= 6
```

The method keeps reducing the problem until it reaches `sum(0)`. Then it can calculate the answer while returning through the earlier calls.

### 2.4 A second example: countdown

A countdown is another simple example. We want to print the numbers from `num` down to `1`.

```java
public static void countdown(int num) {
    // Base case
    if (num == 0) {
        return;
    }

    // General pattern
    System.out.println(num);
    countdown(num - 1);
}
```

Call:

```java
countdown(4);
```

Output:

```text
4
3
2
1
```

The base case is `num == 0`. The general pattern prints the current number and calls the method with `num - 1`.

### 2.5 A third example: factorial

The factorial of a positive integer is the product of all positive integers up to that number.

For example:

```text
4! = 4 × 3 × 2 × 1 = 24
3! = 3 × 2 × 1 = 6
2! = 2 × 1 = 2
1! = 1
0! = 1
```

The important pattern is:

```text
4! = 4 × 3!
3! = 3 × 2!
2! = 2 × 1!
1! = 1 × 0!
```

For factorial, the base case is `0! = 1`.

```java
public static int factorial(int num) {
    // Base case
    if (num == 0 || num == 1) {
        return 1;
    }

    // General pattern
    return num * factorial(num - 1);
}
```

Test:

```java
System.out.println(factorial(4));
System.out.println(factorial(5));
```

Output:

```text
24
120
```

For this example, assume the input is a non-negative integer (0 or greater). The method uses Java's `int` data type, which can store factorial results correctly only up to `12!`. Starting at `13!`, the result becomes too large for int, causing integer overflow. We will use small numbers in this introductory recursion example.

---

## 3. Pros and cons of recursion

Recursion is useful, but it is not automatically better than a loop. We should understand when it helps and what it costs.

### 3.1 Advantages

**Advantage 1: The solution can be easier to express**

Some problems naturally contain smaller versions of the same problem. Recursion lets us describe that relationship directly.

For example:

```java
public static int factorial(int num) {
    if (num == 0) {
        return 1;
    }

    return num * factorial(num - 1);
}
```

The code closely matches the mathematical definition:

```text
factorial(num) = num × factorial(num - 1)
```

**Advantage 2: The code can be shorter**

A recursive solution may require fewer lines than a loop-based solution, especially when the problem has a natural recursive structure.

**Advantage 3: It is useful for structures that contain smaller structures**

Examples include:

- A folder that contains other folders.
- A family tree with branches.
- A tree data structure.
- Problems that repeatedly divide into smaller problems.

### 3.2 Disadvantages

Recursion has two important disadvantages.

**Disadvantage 1: Memory usage**

Every time a method calls itself, the current call is not finished yet. Java must remember its local variables, parameters, and where it should continue after the new call returns.

For example:

```java
public static int sum(int x) {
    if (x == 0) {
        return 0;
    }

    return sum(x - 1) + (x - 1);
}
```

When `sum(4)` calls `sum(3)`, the `sum(4)` call must wait. When `sum(3)` calls `sum(2)`, both earlier calls must wait.

Each waiting call uses space on the **call stack**.

A loop normally reuses the same method call instead of creating a new method call for every repetition. Therefore, a simple loop often uses less memory.

**Disadvantage 2: Method-call overhead and speed**

A recursive call involves setting up another method call, passing arguments, and returning a result. This can make recursion slower than a loop for some tasks.

For a large number of simple repetitions, a loop is often more efficient.

**Important:** Recursion is not always slower or worse. It can make a complicated problem much easier to express. Choose the approach that makes the solution clear while considering performance and memory.

### 3.3 Recursion versus loops

| Feature | Loop | Recursion |
|---|---|---|
| Repetition | Uses `for` or `while` | A method calls itself |
| Stopping condition | Loop condition | Base case |
| Progress | Counter or variable changes | Argument or problem becomes smaller |
| Memory | Usually less extra memory | Uses call-stack space for waiting calls |
| Common risk | Infinite loop | Infinite recursion / `StackOverflowError` |
| Useful for | Straightforward repetition | Problems naturally defined in smaller versions |

---

## 4. How recursion works: the call stack

A loop and recursion do not execute in exactly the same way.

A loop normally completes one iteration and then moves to the next iteration.

Recursion follows a different process:

1. A method starts.
2. It calls itself.
3. The earlier call waits for the new call to finish.
4. This continues until a call reaches the base case.
5. The calls return one at a time, in reverse order.

This is similar to a stack data structure: **last in, first out (LIFO)**.

### 4.1 Review the loop version

```java
public static int sumLoop(int num) {
    int total = 0;

    for (int i = 0; i < num; i++) {
        total += i;
    }

    return total;
}
```

For `sumLoop(5)`:

| Iteration | `i` | `total` after addition |
|---:|---:|---:|
| Start | — | 0 |
| 1 | 0 | 0 |
| 2 | 1 | 1 |
| 3 | 2 | 3 |
| 4 | 3 | 6 |
| 5 | 4 | 10 |

The loop finishes each iteration before moving to the next one.

### 4.2 Review the recursive version

```java
public static int sumRecursion(int num) {
    if (num == 0) {
        return 0;
    }

    return sumRecursion(num - 1) + (num - 1);
}
```

Call:

```java
System.out.println(sumRecursion(5));
```

Output:

```text
10
```

The recursive calls happen first:

```text
sumRecursion(5)
    calls sumRecursion(4)
        calls sumRecursion(3)
            calls sumRecursion(2)
                calls sumRecursion(1)
                    calls sumRecursion(0)
```

At `sumRecursion(0)`, the base case returns `0`.

Only then can the earlier calls finish.

### 4.3 Trace the return process

Let's trace `sumRecursion(5)`.

The method is:

```java
public static int sumRecursion(int num) {
    if (num == 0) {
        return 0;
    }

    return sumRecursion(num - 1) + (num - 1);
}
```

**Step 1 — Calls move toward the base case**

| Call | What it must calculate | What happens |
|---|---|---|
| `sumRecursion(5)` | `sumRecursion(4) + 4` | Waits |
| `sumRecursion(4)` | `sumRecursion(3) + 3` | Waits |
| `sumRecursion(3)` | `sumRecursion(2) + 2` | Waits |
| `sumRecursion(2)` | `sumRecursion(1) + 1` | Waits |
| `sumRecursion(1)` | `sumRecursion(0) + 0` | Waits |
| `sumRecursion(0)` | `0` | Base case reached |

**Step 2 — Results return in reverse order**

| Returning call | Calculation | Result |
|---|---|---:|
| `sumRecursion(0)` | Base case | 0 |
| `sumRecursion(1)` | `0 + 0` | 0 |
| `sumRecursion(2)` | `0 + 1` | 1 |
| `sumRecursion(3)` | `1 + 2` | 3 |
| `sumRecursion(4)` | `3 + 3` | 6 |
| `sumRecursion(5)` | `6 + 4` | 10 |

The final answer is `10`.

### 4.4 Visualize the stack

```text
CALLING DOWN                         RETURNING UP

sumRecursion(5)                      sumRecursion(5) = 10
    |                                    ^
    v                                    |
sumRecursion(4)                      sumRecursion(4) = 6
    |                                    ^
    v                                    |
sumRecursion(3)                      sumRecursion(3) = 3
    |                                    ^
    v                                    |
sumRecursion(2)                      sumRecursion(2) = 1
    |                                    ^
    v                                    |
sumRecursion(1)                      sumRecursion(1) = 0
    |                                    ^
    v                                    |
sumRecursion(0)  ---------------->   returns 0
    BASE CASE
```

The first call is the last one to finish. The last call is the first one to return.

### 4.5 Print statements before and after the recursive call

The location of a print statement matters.

```java
public static void count(int num) {
    if (num == 0) {
        return;
    }

    System.out.println("Before: " + num);
    count(num - 1);
    System.out.println("After: " + num);
}
```

Call:

```java
count(3);
```

Output:

```text
Before: 3
Before: 2
Before: 1
After: 1
After: 2
After: 3
```

Why?

- Statements before the recursive call execute while the method calls are moving toward the base case.
- Statements after the recursive call execute while the calls are returning from the base case.

This is one of the most important ideas in understanding recursion.

---

## 5. Common recursion mistakes

### Mistake 1: No base case

Incorrect:

```java
public static void printInfo() {
    System.out.println("Hello");
    printInfo();
}
```

The method never stops. It eventually causes a `StackOverflowError`.

Correction:

```java
public static void printInfo(int num) {
    if (num == 0) {
        return;
    }

    System.out.println("Hello");
    printInfo(num - 1);
}
```

### Mistake 2: The recursive call does not move toward the base case

Incorrect:

```java
public static void countdown(int num) {
    if (num == 0) {
        return;
    }

    System.out.println(num);
    countdown(num);  // num never changes
}
```

If `num` starts at `3`, every call receives `3`. The method never reaches `0`.

Correction:

```java
countdown(num - 1);
```

The argument must move toward the base case.

### Mistake 3: Incorrect base-case answer

Incorrect factorial method:

```java
public static int factorial(int num) {
    if (num == 0) {
        return 0;
    }

    return num * factorial(num - 1);
}
```

This returns `0` for every non-negative input because every calculation eventually multiplies by the base-case result `0`.

Correction:

```java
if (num == 0) {
    return 1;
}
```

The correct base case is `0! = 1`.

### Mistake 4: Forgetting to return the recursive result

Incorrect:

```java
public static int sum(int num) {
    if (num == 0) {
        return 0;
    }

    sum(num - 1);
    return num - 1;
}
```

The method calls itself, but it ignores the answer returned by that call.

Correction:

```java
return sum(num - 1) + (num - 1);
```

The recursive result is part of the answer.

### Mistake 5: Confusing `num` with `num - 1`

For the range `[0, num)`, the last number included is `num - 1`.

Therefore:

```java
return sum(num - 1) + (num - 1);
```

is correct for this definition.

If the problem instead asks for the sum from `1` through `num`, the formula would be different:

```java
return sum(num - 1) + num;
```

Always identify exactly which numbers the problem includes before writing the recursive formula.

### Mistake 6: Testing only one input

A recursive method should be tested with:

- The base case.
- A small value such as `1`.
- A value greater than `1`.
- Any boundary or invalid inputs that the method is expected to handle.

For example:

```java
System.out.println(sum(0));
System.out.println(sum(1));
System.out.println(sum(4));
```

Expected output:

```text
0
0
6
```

---

## 6. A reliable method for solving recursion problems

When you face a new recursion problem, do not immediately start writing Java code. Work through these steps.

### Step 1: Understand what the method must return or do

For example:

`sum(x)` returns the sum of all integers in the range `[0, x)`.

### Step 2: Find the simplest input

Ask: What is the smallest input for which I know the answer immediately?

For this example:

```text
sum(0) = 0
```

That gives the base case.

### Step 3: Work out two or three small examples

```text
sum(1) = 0
sum(2) = 0 + 1
sum(3) = 0 + 1 + 2
```

### Step 4: Find a relationship between the current problem and a smaller problem

```text
sum(3) = sum(2) + 2
sum(4) = sum(3) + 3
```

Therefore:

```text
sum(x) = sum(x - 1) + (x - 1)
```

### Step 5: Write the recursive method

```java
public static int sum(int x) {
    if (x == 0) {
        return 0;
    }

    return sum(x - 1) + (x - 1);
}
```

### Step 6: Check that the method reaches the base case

Ask:

- Does every recursive call make the problem smaller?
- Can the input eventually reach the base case?
- Does the base case return the correct answer?
- Does the general pattern use the recursive result correctly?

If the answer to any of these questions is no, revise the method.


---

## 8. Applying recursion to the Java concepts from earlier lectures

The examples so far use integers. That is useful for learning the mechanics of recursion, but Programming 2 is an object-oriented Java course. We should also be able to recognize where recursion can help when working with objects and collections.

In this section, we reuse familiar ideas from earlier lectures:

- Lecture 7: `Employee`, `Manager`, superclass references, and polymorphism.
- Lecture 8: the abstract `Shape` class and subclasses that implement `calcArea()` and `calcPerimeter()`.
- Lecture 9: objects that can be compared with `Comparable` or a `Comparator`.
- Lecture 10: `equals()` and `hashCode()` and the rules that make them work correctly.

The key idea is that recursion controls **how we visit elements**. The existing object-oriented methods still do their own jobs.

### 8.1 Recursively visit an `ArrayList` of employees

In earlier lectures, we used an `Employee` superclass and a `Manager` subclass. A collection declared as `ArrayList<Employee>` can hold both ordinary `Employee` objects and `Manager` objects.

Assume the `Employee` class provides `getName()` and that `Manager` extends `Employee`. The following method visits the list by index:

```java
import java.util.ArrayList;

public static void printEmployees(ArrayList<Employee> employees, int index) {
    // Base case: every element has been visited
    if (index == employees.size()) {
        return;
    }

    // Process the current element
    System.out.println(employees.get(index).getName());

    // Visit the next element
    printEmployees(employees, index + 1);
}
```

Example call:

```java
ArrayList<Employee> staff = new ArrayList<>();

staff.add(new Employee("Guy", 50000));
staff.add(new Manager("Sarah", 70000, "Science"));
staff.add(new Employee("Mina", 52000));

printEmployees(staff, 0);
```

Possible output:

```text
Guy
Sarah
Mina
```

Trace the parameter:

| Call | Current index | Action |
|---|---:|---|
| `printEmployees(staff, 0)` | 0 | Prints Guy; calls index 1 |
| `printEmployees(staff, 1)` | 1 | Prints Sarah; calls index 2 |
| `printEmployees(staff, 2)` | 2 | Prints Mina; calls index 3 |
| `printEmployees(staff, 3)` | 3 | `index == employees.size()`; returns |

**What is the base case?**

```java
if (index == employees.size()) {
    return;
}
```

**What makes progress?**

```java
printEmployees(employees, index + 1);
```

The index increases by one until it reaches the list size.

**Why does polymorphism still work?**

The list is declared as `ArrayList<Employee>`, so each element is accessed through an `Employee` reference. A `Manager` object can still be stored in the list because a `Manager` is an `Employee`. If the recursive method calls an overridden instance method on an element, Java uses the actual object's implementation through dynamic method dispatch, just as it did in Lecture 7.

This example only prints the name, so it does not demonstrate an overridden method call. The next example does.

### 8.2 Recursion and dynamic method dispatch

Suppose `Employee` has a method named `displayDetails()` and `Manager` overrides it:

```java
class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public void displayDetails() {
        System.out.println("Employee: " + name);
    }
}

class Manager extends Employee {
    private String department;

    public Manager(String name, double salary, String department) {
        super(name, salary);
        this.department = department;
    }

    @Override
    public void displayDetails() {
        System.out.println("Manager of " + department);
    }
}
```

The recursive traversal can call the method without checking the object's class:

```java
public static void displayAll(ArrayList<Employee> employees, int index) {
    if (index >= employees.size()) {
        return;
    }

    employees.get(index).displayDetails();
    displayAll(employees, index + 1);
}
```

For an ordinary `Employee`, the `Employee` version runs. For a `Manager`, the overridden `Manager` version runs.

**Important distinction:** Recursion does not cause polymorphism. The recursive method simply visits each reference. Polymorphism determines which overridden method executes for the actual object.

### 8.3 Recursively calculate the total area of `Shape` objects

In Lecture 8, we used an abstract `Shape` class with abstract methods such as `calcArea()` and `calcPerimeter()`. Concrete subclasses provide the implementation.

Assume the course's `Shape` hierarchy has a working `calcArea()` method in each concrete subclass. We can recursively calculate the total area of a list:

```java
import java.util.ArrayList;

public static double totalArea(ArrayList<Shape> shapes, int index) {
    // Base case: no shapes remain
    if (index == shapes.size()) {
        return 0.0;
    }

    // Current shape's area + total area of the remaining shapes
    return shapes.get(index).calcArea()
            + totalArea(shapes, index + 1);
}
```

Example:

```java
double result = totalArea(shapes, 0);
System.out.println("Total area: " + result);
```

The recursive relationship is:

```text
totalArea(shapes, index)
    = current shape's area
      + totalArea(shapes, index + 1)
```

At the end of the list, the total of the remaining shapes is `0.0`.

**Where does polymorphism appear?** `shapes` is an `ArrayList<Shape>`, but it can contain objects such as `Circle` and `Rectangle` if they extend `Shape`. The call to `calcArea()` uses the implementation belonging to each actual shape object.

This is a useful combination of three ideas:

1. Abstract class: `Shape` defines the common contract.
2. Polymorphism: each concrete shape calculates its own area.
3. Recursion: the method visits the collection one element at a time and combines the results.

### 8.4 Recursion is not sorting: connection to `Comparable` and `Comparator`

In Lecture 9, we learned that `Comparable` defines a class's natural ordering through `compareTo()`, while a `Comparator` provides an ordering rule separately.

Recursion can visit a list before or after it has been sorted. However, simply visiting each element recursively does **not** sort the list.

For example, assume `Student` implements `Comparable<Student>` and its `compareTo()` method defines the natural order:

```java
import java.util.ArrayList;
import java.util.Collections;

public static void printStudents(ArrayList<Student> students, int index) {
    if (index == students.size()) {
        return;
    }

    System.out.println(students.get(index));
    printStudents(students, index + 1);
}
```

To print students in natural order, sort first and then traverse:

```java
Collections.sort(students);
printStudents(students, 0);
```

If a `Comparator<Student>` is required instead, sort with that comparator:

```java
students.sort(studentComparator);
printStudents(students, 0);
```

The responsibilities are separate:

| Operation | Responsibility |
|---|---|
| `compareTo()` | Defines natural ordering for the class |
| `Comparator` | Defines an alternative ordering rule |
| `Collections.sort()` / `List.sort()` | Reorders the list |
| Recursive `printStudents()` | Visits and prints the elements in their current order |

Do not claim that recursion has sorted the list. The sorting method performs the reordering.

### 8.5 Recursion and `equals()` / `hashCode()`

Lecture 10 introduced the relationship between `equals()` and `hashCode()`.

The essential contract is:

- If two objects are equal according to `equals()`, they must return the same `hashCode()`.
- Two objects that are not equal may still have the same hash code.
- A hash code is not a unique identifier.
- When overriding `equals()`, you should also override `hashCode()` consistently.

Recursion does not change these rules. A recursive method can use `equals()` to compare an item with a target while searching a collection, but it should not invent a new equality rule.

For example, assume `Student` has a correctly implemented `equals()` method:

```java
public static int findStudent(
        ArrayList<Student> students, Student target, int index) {

    // Base case: the target was not found
    if (index == students.size()) {
        return -1;
    }

    // Compare the current student using equals()
    if (students.get(index).equals(target)) {
        return index;
    }

    // Search the rest of the list
    return findStudent(students, target, index + 1);
}
```

Call:

```java
int position = findStudent(students, searchStudent, 0);
```

The method returns the index of the first equal student, or `-1` if no equal student is found.

This example demonstrates the difference between two jobs:

- `equals()` decides whether the current object is equal to the target.
- Recursion decides which list element to examine next.

`hashCode()` is not needed for this linear recursive search. Hash-based collections such as `HashSet` and `HashMap` use both `hashCode()` and `equals()` as part of their lookup process.

### 8.6 A practical rule: when should we use recursion with objects?

Recursion is useful when the work can be expressed as:

1. Process the current object or element.
2. Process the remaining objects or elements using the same method.
3. Stop when no elements remain.

For a simple `ArrayList`, a loop is often easier and uses less call-stack memory. Recursion is included here to help you understand the pattern and prepare for recursive structures such as trees, where each node can lead to smaller subtrees.

---

## 9. Integrated practice — connect Lectures 7–10 to recursion

### Practice 9.1 — Polymorphic employee list

Assume `Manager extends Employee`, and both classes have a working `displayDetails()` method. Write a recursive method that displays every employee in an `ArrayList<Employee>`.

**Requirements:**

- Use an index parameter.
- Stop when the index reaches the list size.
- Do not use a loop.
- Do not use `instanceof` to decide which `displayDetails()` method to call.

**Answer:**

```java
public static void displayAll(
        ArrayList<Employee> employees, int index) {

    if (index == employees.size()) {
        return;
    }

    employees.get(index).displayDetails();
    displayAll(employees, index + 1);
}
```

The method relies on dynamic method dispatch. It does not need to know whether the current object is an `Employee` or a `Manager`.

### Practice 9.2 — Total area of shapes

Assume `Shape` is abstract and every concrete shape implements `calcArea()`. Complete the recursive method:

```java
public static double totalArea(ArrayList<Shape> shapes, int index) {
    if (________________________) {
        return 0.0;
    }

    return ________________________________
            + ________________________________;
}
```

**Answer:**

```java
public static double totalArea(ArrayList<Shape> shapes, int index) {
    if (index == shapes.size()) {
        return 0.0;
    }

    return shapes.get(index).calcArea()
            + totalArea(shapes, index + 1);
}
```

### Practice 9.3 — Search using `equals()`

A recursive method searches an `ArrayList<Student>` for a student equal to `target`. What should it return if it reaches the end of the list without finding a match?

**Answer:** Return `-1`, provided the method's documented return value uses `-1` to mean “not found.” This is a method-design decision; it is not a special recursion rule.

### Practice 9.4 — Sorting versus traversal

A student writes:

```java
printStudents(students, 0);
```

and claims that this prints the students in `Comparable` order.

Is the claim necessarily correct?

**Answer:** No. The recursive method prints the elements in the list's current order. The list must first be sorted using `Collections.sort(students)` or an appropriate `students.sort(comparator)` call if a particular ordering is required.

### Practice 9.5 — Identify each concept

For the statement below, identify the concepts involved:

```java
shapes.get(index).calcArea()
        + totalArea(shapes, index + 1);
```

**Answer:**

- `shapes.get(index)` accesses an object from an `ArrayList`.
- `calcArea()` uses the `Shape` abstraction and polymorphic method dispatch.
- `totalArea(...)` is the recursive call.
- `+` combines the current area's result with the result for the remaining elements.

---

## 10. AI-assisted practice — explain and verify a recursive object traversal

Use an AI assistant as a programming tutor. Do not ask it to complete the exercise immediately. Ask it to help you reason about the base case, recursive progress, and object-oriented behaviour.

**Task:** Create an `ArrayList<Employee>` containing at least one `Employee` and one `Manager`. Write a recursive method that displays the details of every object.

**Prompt 1 — Plan before coding**

> I am learning recursion in Java. I have an ArrayList<Employee> that can contain Employee and Manager objects. Do not write the complete solution yet. Help me identify the base case, the recursive case, and how the index should change.

**Prompt 2 — Trace the method**

> Trace my recursive method for a list of three objects. Show every method call, the index value, when each call waits, and the order in which calls return.

**Prompt 3 — Check polymorphism**

> Explain why calling displayDetails() through an Employee reference can execute the Manager override. Separate the role of polymorphism from the role of recursion.

**Prompt 4 — Test edge cases**

> Suggest test cases for an empty list, a one-element list, and a list containing both Employee and Manager objects. Explain the expected behaviour.

**Student submission:**

1. The `Employee` and `Manager` code used.
2. The recursive traversal method.
3. A trace for a list containing three objects.
4. Expected output and actual output.
5. A short explanation of how recursion and polymorphism work together.

### Teacher solution guide

A suitable solution has this structure:

```java
public static void displayAll(
        ArrayList<Employee> employees, int index) {

    if (index == employees.size()) {
        return;
    }

    employees.get(index).displayDetails();
    displayAll(employees, index + 1);
}
```

A strong explanation should state:

- The base case is reached when `index == employees.size()`.
- The recursive call uses `index + 1`, so each call moves toward the base case.
- Each call processes exactly one element before moving to the next.
- An empty list stops immediately when called with index `0`.
- The declared type `Employee` permits references to both `Employee` and `Manager` objects.
- Overridden `displayDetails()` calls are selected according to the actual object type.
- Recursion performs the traversal; polymorphism selects the overridden method implementation.

Common errors to check:

- Starting with an index other than `0` without explaining why.
- Using `index > employees.size()` as the only base case. `index == employees.size()` is sufficient when the method is always called with a valid starting index and increments by one.
- Forgetting `index + 1`.
- Calling the method with `index` unchanged.
- Using `employees.get(index + 1)` before checking the base case.
- Writing `Employee.displayDetails()` as if the method were static.
- Using `instanceof` unnecessarily instead of allowing polymorphism to select the overridden method.

---


---

## 7. Practice — predict, trace, run, explain

For each exercise, first predict the output. Then trace the method calls and returns. Finally, run the code to check your answer.

### Practice 1 — How many messages?

```java
public static void printInfo(int num) {
    if (num == 0) {
        return;
    }

    System.out.println("Hello");
    printInfo(num - 1);
}
```

What is printed by:

```java
printInfo(4);
```

**Answer:**

```text
Hello
Hello
Hello
Hello
```

There are four calls that print a message: `4`, `3`, `2`, and `1`. The call with `0` reaches the base case and prints nothing.

### Practice 2 — Calculate the sum

```java
public static int sum(int num) {
    if (num == 0) {
        return 0;
    }

    return sum(num - 1) + (num - 1);
}
```

What is the result of `sum(5)`?

**Answer:** `10`

```text
sum(5) = sum(4) + 4
       = sum(3) + 3 + 4
       = sum(2) + 2 + 3 + 4
       = sum(1) + 1 + 2 + 3 + 4
       = sum(0) + 0 + 1 + 2 + 3 + 4
       = 10
```

### Practice 3 — Trace the output

```java
public static void show(int num) {
    if (num == 0) {
        return;
    }

    System.out.println(num);
    show(num - 1);
    System.out.println(num);
}
```

What is printed by `show(3)`?

**Answer:**

```text
3
2
1
1
2
3
```

The first three lines are printed while calls move toward the base case. The final three lines are printed as the calls return.

### Practice 4 — Find the error

```java
public static void count(int num) {
    if (num == 0) {
        return;
    }

    System.out.println(num);
    count(num + 1);
}
```

Why does this method not stop when called with `count(3)`?

**Answer:** The argument increases instead of decreasing. It moves away from the base case `num == 0`. The recursive call should be `count(num - 1)` for positive starting values.

### Practice 5 — Factorial

Write a recursive method that calculates the factorial of a non-negative integer.

**Answer:**

```java
public static int factorial(int num) {
    if (num == 0) {
        return 1;
    }

    return num * factorial(num - 1);
}
```

For `factorial(4)`, the result is `24`.

### Practice 6 — Recursion or loop?

Which approach would usually be simpler for printing `Hello` exactly 1,000 times: a loop or recursion? Explain why.

**Answer:** A loop is usually simpler and uses less call-stack memory for straightforward repetition. Recursion is useful when a problem naturally breaks into smaller versions of itself.

### Practice 7 — Identify the base case and general pattern

Assume `num` is non-negative; negative input is outside the domain of this introductory implementation.

Consider:

```java
public static int powerOfTwo(int num) {
    if (num == 0) {
        return 1;
    }

    return 2 * powerOfTwo(num - 1);
}
```

Identify:

1. The base case.
2. The general pattern.
3. The result of `powerOfTwo(3)`.

**Answer:**

1. Base case: `num == 0`, returning `1`.
2. General pattern: `2 * powerOfTwo(num - 1)`.
3. `powerOfTwo(3) = 8`. (This `int` implementation overflows for sufficiently large exponents; for example, `2^31` is outside the positive `int` range.)

---

## 8. AI-assisted practice — use AI as a debugging partner

The goal of this activity is to practise identifying the base case and general pattern. AI may help explain a trace, but you must be able to justify the answer yourself.

### Activity: Repair a broken recursive method

A student writes:

```java
public static int product(int num) {
    if (num == 1) {
        return 0;
    }

    return num * product(num - 1);
}
```

The student expects `product(4)` to return:

```text
24
```

But the method returns `0`.

**Your tasks:**

1. Identify the base-case error.
2. Explain why the current method always returns `0`.
3. Correct the method.
4. Trace the calls for `product(4)`.
5. Explain how the calls return in reverse order.

### AI prompt  

You may use the following prompt:

> I am learning Java recursion. Do not immediately give me the corrected code. First ask me what the base case should be. Then guide me to identify the general pattern and trace the method calls for product(4). After I attempt the solution, check my reasoning and explain any mistake in simple terms.


---

## 9. Final summary

| Term | Plain-language meaning |
|---|---|
| Recursion | A method calling itself |
| Recursive call | A call from a method to itself |
| Base case | The simplest situation where the answer is known and recursion stops |
| General pattern | The rule that solves a problem using a smaller version of the same problem |
| Call stack | The memory structure that keeps track of active method calls |
| Stack overflow | An error that can occur when too many method calls are waiting on the call stack |
| LIFO | Last in, first out; the last call made is the first one to return |

Remember these five rules:

1. **Every recursive method needs a base case.**
2. **The base case must return the correct answer.**
3. **Every recursive call must move toward the base case.**
4. **Understand that calls move down toward the base case and results return in reverse order.**
5. **Use recursion when it makes the problem easier to express; use a loop when straightforward repetition is clearer and more efficient.**

## Further reading

- [Java Language Specification — Methods](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html)
- [Java `StackOverflowError` API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/StackOverflowError.html)
- [Java Tutorials — Classes and Objects](https://docs.oracle.com/javase/tutorial/java/javaOO/)


## 11. Final summary — what recursion adds to Programming 2

Recursion is a method of solving a problem by solving a smaller version of the same problem.

Every correct recursive method needs:

1. A **base case** that returns or performs the correct result for the simplest case.
2. A **recursive case** that makes progress toward the base case.
3. A correct use of the result returned by the recursive call, when the method returns a value.

When applying recursion to earlier Programming 2 topics, keep each concept's responsibility clear:

| Earlier topic | What it contributes | What recursion contributes |
|---|---|---|
| Polymorphism (`Employee` / `Manager`) | The actual object's overridden method is executed | Visits each object in sequence |
| Abstract class (`Shape`) | Defines a common contract such as `calcArea()` | Combines results from each concrete shape |
| `Comparable` / `Comparator` | Defines an ordering rule | Traverses the list; it does not sort it |
| `equals()` / `hashCode()` | Defines consistent object equality and hash behaviour | Can visit objects and call `equals()` during a search |

Before submitting a recursive method, ask:

- What is the smallest valid input?
- What does the method return or do in that case?
- How does each recursive call make the problem smaller or simpler?
- Does the method eventually reach the base case?
- Does the recursive result contribute correctly to the final result?
- Have I tested an empty input, a base case, and a normal case?

**Main takeaway:** Recursion is not a replacement for object-oriented programming. It is another control technique that can work with the classes, objects, interfaces, collections, and methods you already know.
