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

A **call stack** is the part of Java's memory that keeps track of which methods are currently running, what information each method needs, and where execution should return when a method finishes.

Think of it as a stack of plates: every time a method calls another method, Java places a new plate on top. When that method finishes, Java removes the top plate and returns to the method underneath.

This is particularly important in recursion because a recursive method calls itself several times before the first call can finish.
#### A simple mental picture
Imagine this recursive method:
```
public static void countdown(int n) {
    if (n == 0) {
        System.out.println("Done!");
        return;
    }

    System.out.println(n);
    countdown(n - 1);
}
```
We call it from main:
```
public static void main(String[] args) {
    countdown(3);
}
```
When `countdown(3)` calls `countdown(2)`, the first call does not disappear. It waits for the second call to finish.

Then `countdown(2)` calls `countdown(1)`, which calls `countdown(0)`.

When the base case executes, then Java starts removing method calls from the top of the stack. This is called **unwinding the call stack**.

Notice something important: in this particular example, the method prints each number before making the recursive call. 

Therefore, the numbers appear in descending order even though the calls return in ascending order.

Program output:
```
3
2
1
Done!
```
#### One more important concept: each call has its own variables
When Java calls `countdown(3)`, `countdown(2)`, and `countdown(1)`, these are separate method executions.

Each has its own value of `n`:
```
Method call     Its own local variable
countdown(3)	    n = 3
countdown(2)	    n = 2
countdown(1)	    n = 1
countdown(0)	    n = 0
```
Java stores this information in a separate **stack frame** for each method call.

If recursion continues too deeply, the call stack can run out of space, causing a `StackOverflowError`.

On the other hand, a **loop** normally reuses the same method call instead of creating a new method call for every repetition. Therefore, a simple loop often uses less memory.

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
## 8. Applying recursion to Java concepts from Lectures 8, 9, and 10

The earlier sections used numbers to explain recursion. We will now apply the same pattern to Java objects and collections from the previous lectures.

The important idea is that recursion does not replace inheritance, interfaces, polymorphism, sorting, or object equality. It provides another way to visit elements or break a task into smaller tasks.

The code fragments below assume the relevant classes from Lectures 8–10 are available in the project. Add `java.util.ArrayList`, `java.util.Collections`, `java.util.Comparator`, and other required imports to the driver class when needed.

### 8.1 Recursively visit objects that implement `Flyable` — Lecture 8

In Lecture 8, we used the `Flyable` interface to describe a capability:

```java
public interface Flyable {
    void fly();
}
```

Different classes can implement the same interface. The lecture uses examples such as `Bird`, `Drone`, and `Airplane`. An `ArrayList<Flyable>` can store objects of these different classes:

```java
ArrayList<Flyable> flyers = new ArrayList<>();

flyers.add(new Bird());
flyers.add(new Drone());
flyers.add(new Airplane());
```

We can recursively call `fly()` on each object:

```java
public static void testFlights(ArrayList<Flyable> flyers, int index) {
    // Base case: every object has been visited
    if (index == flyers.size()) {
        return;
    }

    // Process the current object
    flyers.get(index).fly();

    // Process the remaining objects
    testFlights(flyers, index + 1);
}
```

Call the method from `main`:

```java
testFlights(flyers, 0);
```

**How does this connect to Lecture 8?**

- `Flyable` is the interface reference type.
- `Bird`, `Drone`, and `Airplane` are concrete object types that implement the interface.
- The recursive method only needs to know that each object is `Flyable`.
- When `fly()` is called, Java executes the implementation belonging to the actual object.

Recursion controls **which object is visited next**. Polymorphism controls **which implementation of `fly()` runs**.

The exact output depends on the `fly()` implementations in the classes. For example, the lecture's bird and airplane examples use different messages.

**Trace the index:**

| Call | Index | Action |
|---|---:|---|
| `testFlights(flyers, 0)` | 0 | Calls `fly()` on the first object; calls index 1 |
| `testFlights(flyers, 1)` | 1 | Calls `fly()` on the second object; calls index 2 |
| `testFlights(flyers, 2)` | 2 | Calls `fly()` on the third object; calls index 3 |
| `testFlights(flyers, 3)` | 3 | Reaches `index == flyers.size()` and returns |

**Check your understanding:** If the list is empty and the method is called with index `0`, does it try to access `flyers.get(0)`?

No. The base case is immediately true because `0 == flyers.size()`. The method returns before accessing an element.

### 8.2 Recursively traverse `Student` objects — Lecture 9

In Lecture 9, the `Student` class implements `Comparable<Student>` and defines its natural ordering by student ID in ascending order:

```java
@Override
public int compareTo(Student other) {
    return this.id.compareTo(other.id);
}
```

The lecture also defines an alternative scholarship ordering with a `Comparator<Student>`:

```java
Comparator<Student> scholarshipOrder =
    Comparator.comparingDouble(Student::getGpa).reversed()
              .thenComparing(Student::getName)
              .thenComparing(Student::getId);
```

After sorting, we can use recursion to visit and display the students:

```java
public static void printStudents(ArrayList<Student> students, int index) {
    if (index == students.size()) {
        return;
    }

    System.out.println(students.get(index));
    printStudents(students, index + 1);
}
```

**Natural ordering — ID ascending:**

```java
Collections.sort(students);
printStudents(students, 0);
```

**Alternative ordering — scholarship order:**

```java
students.sort(scholarshipOrder);
printStudents(students, 0);
```

The responsibilities are different:

| Java feature | Responsibility |
|---|---|
| `Comparable<Student>` | Defines the natural ordering through `compareTo()` |
| `Comparator<Student>` | Defines an alternative ordering rule |
| `Collections.sort(students)` | Sorts using the natural ordering |
| `students.sort(scholarshipOrder)` | Sorts using the supplied comparator |
| `printStudents(students, 0)` | Visits and prints the elements in their current order |

**Important:** The recursive method does not sort the list. It prints the list in whatever order the list currently has. Sort first if a particular order is required.

#### A comparison tie does not necessarily mean two students are equal

Lecture 9 explains that `compareTo()` returning `0` means the two objects tie under that comparison rule. It does not automatically mean the objects are equal according to `equals()`.

For example, if `compareTo()` compares only GPA, two students with the same GPA compare as tied even if their names or IDs differ.

A recursive traversal still visits both objects. It does not remove one just because the comparison result is zero.

### 8.3 Recursively search for an `Author` using `equals()` — Lecture 10

Lecture 10 uses an `Author` class whose equality rule is based on both `name` and `country`.

For example:

```java
Author a1 = new Author("Alice Chen", "Canada");
Author a2 = new Author("Alice Chen", "Canada");
```

These are two different objects, but the lecture's `equals()` method considers them logically equal because their names and countries match.

We can use that existing equality rule in a recursive linear search:

```java
public static int findAuthor(
        ArrayList<Author> authors, Author target, int index) {

    // Base case: no more authors to examine
    if (index == authors.size()) {
        return -1;
    }

    // Is the current author logically equal to the target?
    if (authors.get(index).equals(target)) {
        return index;
    }

    // Search the remaining elements
    return findAuthor(authors, target, index + 1);
}
```

Example call:

```java
Author target = new Author("Alice Chen", "Canada");
int position = findAuthor(authors, target, 0);

System.out.println(position);
```

The method returns the index of the first author that is equal to `target`. It returns `-1` if no match is found.

**What does each part do?**

- `equals()` decides whether the current author and target represent the same logical author.
- `index + 1` moves the search to the next list element.
- The recursive call continues the search.
- `-1` is the method's chosen result when the target is not found.

#### Why does this linear search not need `hashCode()`?

This method examines the list one element at a time and calls `equals()` on each candidate. It does not use a hash table to locate the object.

Lecture 10 teaches that `HashSet` and `HashMap` use both `hashCode()` and `equals()` for hash-based lookup. That is a different search mechanism.

| Search approach | How the candidate is located |
|---|---|
| Recursive linear search using `equals()` | Examines elements one by one. It does not need `hashCode()` |
| `HashMap.get(key)` | Uses the key's hash code to identify the bucket where the key should be stored, then uses equality checks as needed to find the matching key. |

Do not add `hashCode()` to the recursive `ArrayList` search just because the `Author` class overrides it. The search does not need it.

**Takeaway:** A `HashMap` does not normally search every key one by one. It uses `hashCode()` to narrow down where to look and `equals()`to identify the correct key.

**One technical distinction:** a bucket can contain multiple entries because of collisions. Therefore, the bucket is a candidate location, not a guarantee that the requested key is already there.

### 8.4 Recursion does not replace `HashSet` or `HashMap` — Lecture 10

Lecture 10 introduced:

```java
Set<Author> authors = new HashSet<>();
```

This is useful when the program needs a collection of unique authors.

It also introduced:

```java
Map<Author, Integer> bookCounts = new HashMap<>();
```

This is useful when the program needs to associate each author with a book count.

A recursive method can traverse an `ArrayList`, but recursion is not a replacement for these collection types:

- Use `HashSet` when you need to store unique elements according to `equals()` and `hashCode()`.
- Use `HashMap` when you need key–value mappings.
- Use a recursive `ArrayList` method when the learning task is to visit or search the list one element at a time.

Remember the Lecture 10 contract: if two objects are equal according to `equals()`, they must have the same hash code. A hash code is not a unique identifier, and a collision does not prove equality.

Also remember the warning about mutable keys: do not change fields used by `equals()` and `hashCode()` while an object is being used as a key in a `HashMap` or as an element in a `HashSet`. Changing those fields can make the object difficult to find.

### 8.5 Choosing the right technique

| Task | Suitable technique |
|---|---|
| Visit every object in an `ArrayList<Flyable>` | Loop or recursive traversal |
| Display students in ID order | Sort with `Comparable`, then traverse |
| Display students in scholarship order | Sort with `Comparator`, then traverse |
| Find an equal `Author` in an `ArrayList` using the recursion pattern | Recursive linear search with `equals()` |
| Store unique authors | `HashSet<Author>` |
| Retrieve an author's book count | `HashMap<Author, Integer>` |

For a simple `ArrayList`, a loop is usually shorter and avoids creating multiple recursive stack frames. The recursive versions here are examples: they help you practise recursion while reusing familiar Java classes and interfaces.

---

## 9. Integrated practice — recursion with earlier Java topics

Try each question before reading its answer. For code-writing questions, identify the base case first.

### Practice 9.1 — Traverse a list of `Flyable` objects

Lecture 8 introduced `Flyable` and showed that a collection can hold different objects that implement the interface.

Complete the recursive method:

```java
public static void testFlights(ArrayList<Flyable> flyers, int index) {
    if (____________________________) {
        return;
    }

    ________________________________;

    ________________________________;
}
```

**Answer:**

```java
public static void testFlights(ArrayList<Flyable> flyers, int index) {
    if (index == flyers.size()) {
        return;
    }

    flyers.get(index).fly();

    testFlights(flyers, index + 1);
}
```

The base case stops when all elements have been visited. The recursive call advances the index.

### Practice 9.2 — What does recursion do after sorting?

Assume `Student` implements `Comparable<Student>` with natural ordering by ID ascending.

A student writes:

```java
printStudents(students, 0);
```

and claims that this method automatically sorts the students by ID.

Is the claim correct?

**Answer:** No. The recursive method only visits the list in its current order. To use the natural ordering, call:

```java
Collections.sort(students);
printStudents(students, 0);
```

### Practice 9.3 — Comparator order

The scholarship comparator from Lecture 9 uses:

1. GPA descending.
2. Name ascending when GPA ties.
3. ID ascending when both GPA and name tie.

Should the recursive printing method contain these three comparison rules?

**Answer:** No. The comparator defines the ordering. The sorting method rearranges the list. The recursive method simply prints each element in the order it receives.

### Practice 9.4 — Search for an `Author`

Assume `Author.equals()` compares both `name` and `country`, as in Lecture 10.

What should the recursive search return when it reaches the end of the list without finding an equal author?

**Answer:** `-1`, if the method's documented convention is that `-1` means “not found.”

### Practice 9.5 — Does a comparison tie mean duplicate authors?

A student says:

> If `compareTo()` returns `0`, the two objects are duplicates and one should be removed.

Is this always true?

**Answer:** No. `compareTo()` returning `0` means the objects tie under the ordering rule. It does not automatically mean `equals()` returns `true`. Sorting and equality are separate concepts.

### Practice 9.6 — Which collection should you use?

Choose `ArrayList`, `HashSet`, or `HashMap` for each task:

1. Keep a list of authors in the order they were entered.
2. Store each author only once according to the `Author.equals()` rule.
3. Associate each author with the number of books they have written.
4. Recursively visit every author in a list.

**Answer:**

1. `ArrayList<Author>`
2. `HashSet<Author>`
3. `HashMap<Author, Integer>`
4. `ArrayList<Author>` with a recursive traversal method

### Practice 9.7 — Trace the recursive author search

Suppose the list contains:

```text
Index 0: Alice Chen (Canada)
Index 1: Omar Ali (Canada)
Index 2: Maya Singh (India)
```

The target is:

```java
new Author("Maya Singh", "India")
```

What index does `findAuthor(authors, target, 0)` return?

**Answer:** `2`.

The method checks index 0, then index 1, then index 2. At index 2, `equals()` returns `true`, so the method returns `2` without searching further.

---

## 10. AI-assisted practice — explain, implement, and verify

Use AI as a programming tutor, not as a replacement for your own reasoning. The goal is to demonstrate that you understand both recursion and the Java concepts from the previous lectures.

### Activity A — Recursively test `Flyable` objects

**Task:** Use the `Flyable` interface and the `Bird`, `Drone`, and `Airplane` examples from Lecture 8. Create an `ArrayList<Flyable>` and write a recursive method that calls `fly()` on every element.

Ask AI:

> I am learning Java recursion. I have an ArrayList<Flyable> containing objects that implement the Flyable interface. Do not write the complete solution immediately. Help me identify the base case, the recursive case, and how the index should change.

Then ask:

> Trace my method for a list containing three objects. Show each call, the index value, and when each call returns.

**Student submission:**

1. The recursive method.
2. A trace showing all method calls.
3. The output produced by the actual `fly()` implementations.
4. An explanation of how recursion and interface polymorphism work together.

### Activity B — Sort, then recursively print students

**Task:** Use the `Student` class from Lecture 9.

1. Sort by natural ordering (ID ascending) and recursively print the list.
2. Sort by the scholarship comparator and recursively print the list again.
3. Explain which part sorts and which part traverses.

Ask AI:

> I have a Java Student class that implements Comparable<Student> by ID ascending. I also have a Comparator<Student> for scholarship order: GPA descending, name ascending, and ID ascending. Help me write a recursive method that prints an ArrayList<Student> by index. Do not put sorting logic inside the recursive method.

**Student submission:**

1. The recursive printing method.
2. The natural-order sorting call and output.
3. The comparator sorting call and output.
4. A short explanation of why recursion does not perform the sorting.

### Activity C — Search for an `Author` using `equals()`

**Task:** Use the `Author` equality rule from Lecture 10: two authors are equal when both their names and countries match.

Ask AI:

> Help me trace a recursive linear search through an ArrayList<Author>. The search must use the existing equals() method, return the first matching index, and return -1 if no match is found. First ask me to identify the base case.

**Student submission:**

1. The recursive search method.
2. A trace for a target that is found.
3. A trace for a target that is not found.
4. An explanation of why this ArrayList search uses `equals()` but does not need `hashCode()`.

### Solution guide

**Activity A — expected method structure:**

```java
public static void testFlights(ArrayList<Flyable> flyers, int index) {
    if (index == flyers.size()) {
        return;
    }

    flyers.get(index).fly();
    testFlights(flyers, index + 1);
}
```

A strong explanation identifies `index == flyers.size()` as the base case, `index + 1` as progress, and dynamic dispatch as the reason each object's own `fly()` implementation runs.

**Activity B — expected method structure:**

```java
public static void printStudents(ArrayList<Student> students, int index) {
    if (index == students.size()) {
        return;
    }

    System.out.println(students.get(index));
    printStudents(students, index + 1);
}
```

Sorting happens before the recursive call:

```java
Collections.sort(students);
printStudents(students, 0);

students.sort(scholarshipOrder);
printStudents(students, 0);
```

**Activity C — expected method structure:**

```java
public static int findAuthor(
        ArrayList<Author> authors, Author target, int index) {

    if (index == authors.size()) {
        return -1;
    }

    if (authors.get(index).equals(target)) {
        return index;
    }

    return findAuthor(authors, target, index + 1);
}
```

A strong explanation distinguishes the jobs: `equals()` checks logical equality; recursion advances through the list; `hashCode()` is used by hash-based collections, not by this sequential search.

**Common errors to check:**

- The base case is missing or checks the wrong index.
- The recursive call does not use `index + 1`.
- The method accesses `get(index)` before checking the base case.
- The student puts sorting comparisons inside the recursive printing method.
- The student assumes `compareTo() == 0` always means `equals()` is true.
- The student uses `hashCode()` as if it were a unique identifier.
- The student claims that recursive `ArrayList` search uses the same mechanism as `HashSet` or `HashMap`.

---

## 11. Final summary — recursion and the earlier Java lectures

Recursion solves a problem by solving a smaller version of the same problem. It can also be used to visit objects in a collection one element at a time.

Every correct recursive method needs:

1. A **base case** that stops the recursion and returns or performs the correct result.
2. A **recursive case** that makes progress toward the base case.
3. Correct handling of the result from the recursive call when the method returns a value.

### How recursion connects to earlier lectures

| Previous lecture | Java concept | What recursion adds |
|---|---|---|
| Lecture 8 | Abstract classes, interfaces, and polymorphism (`Flyable`, `Bird`, `Drone`, `Airplane`) | Visits each object and calls the appropriate implementation |
| Lecture 9 | `Comparable`, `compareTo()`, and `Comparator` | Traverses a list after it has been sorted; it does not perform the sorting |
| Lecture 10 | `equals()` and `hashCode()` | Can use `equals()` during a recursive linear search; hash-based collections use `hashCode()` and `equals()` for their own lookup process |

### Final checklist

Before submitting a recursive method, ask:

- What is the smallest valid input?
- What should happen in the base case?
- How does each recursive call make progress?
- Does the method eventually reach the base case?
- If the method returns a value, am I using the recursive result correctly?
- Have I tested an empty collection, a one-element collection, a match, and a no-match case where appropriate?
- Have I kept recursion separate from the responsibilities of interfaces, comparators, equality methods, and collections?

**Main takeaway:** Recursion is another way to control how a task is performed. It works alongside object-oriented concepts; it does not replace them. Use a loop when straightforward repetition is clearer, and use recursion when the problem naturally breaks into smaller versions of itself.

