# Lecture 12 — Exception Handling

## Learning goals

By the end of this lecture, you should be able to:

1. Explain what an exception is and why Java uses exception handling.
2. Distinguish between `Error`, `Exception`, checked exceptions, and unchecked exceptions.
3. Explain what happens when an exception is not handled by the programmer.
4. Use `try-catch` to handle specific exceptions.
5. Use multiple `catch` blocks and put them in the correct order.
6. Use the exception object (`e`) to obtain information about a problem.
7. Explain the difference between `throw` and `throws`.
8. Pass exception handling responsibility from one method to its caller.
9. Create and throw a user-defined exception.
10. Choose between handling an exception inside a method and declaring it for the caller to handle.
11. Apply exception handling to familiar Programming 2 classes: `Employee`/`Manager`, interfaces and polymorphic collections, `Student` sorting, `Author` equality, `HashSet`, `HashMap`, and recursive methods.
12. Distinguish an exception from an `Error`, including why a `StackOverflowError` is not normally handled as an ordinary input exception.

**Connections to earlier lectures:** This lecture reuses the `Employee`/`Manager` inheritance and polymorphism example from Lecture 7; the interface and polymorphic collection examples from Lecture 8 (`Flyable`, `Bird`, `Drone`, and `Airplane`); the `Student` natural ordering and scholarship `Comparator` examples from Lecture 9; the `Author`, `equals()`, `hashCode()`, `HashSet`, and `HashMap` examples from Lecture 10; and the recursive `ArrayList` traversal, search, and call-stack examples from Lecture 11. The aim is to apply exception handling to familiar code—not to introduce another unrelated set of classes.

---

## 1. What is exception handling?

### 1.1 What is an exception?

When a program runs, something unexpected may happen.

For example:

- A program tries to divide an integer by zero.
- A program tries to access an array position that does not exist.
- A program tries to open a file that cannot be found.
- A program receives input that it cannot process.

These situations may interrupt the normal execution of a program. Java represents many such situations using an **exception object**.

An exception is not simply another word for a programming mistake. It is an object that describes an unusual condition that has occurred while a program is running.

Consider this code:

```java
public static void main(String[] args) {
    int result = 10 / 0;
    System.out.println(result);
}
```

Java cannot perform integer division by zero. It throws an `ArithmeticException`.

The statement after the division is not executed because the exception interrupts the normal flow of the method.

### 1.2 Error versus Exception

Java organizes serious problems and exceptions in a class hierarchy. Both `Error` and `Exception` are subclasses of `Throwable`.

A simplified view is:

```text
                Object
  └── Throwable
        ├── Error
        └── Exception
              └── RuntimeException
```

- **Error:** Represents a serious problem that applications normally are not expected to recover from. Examples include `OutOfMemoryError` and `StackOverflowError`.
- **Exception:** Represents a condition that an application may be able to handle, such as a missing file or invalid input.
- **RuntimeException:** A category of unchecked exceptions, including `ArithmeticException`, `NullPointerException`, and `ArrayIndexOutOfBoundsException`.

The distinction is about the kind of problem and how Java expects a program to deal with it. It is not a guarantee that every `Error` is caused by a hardware problem or that every `Exception` is easy to fix.

### 1.3 A simple example

```java
public static void main(String[] args) {
    int[] numbers = {10, 20, 30};

    System.out.println(numbers[1]);
    System.out.println(numbers[5]);
    System.out.println("Program finished");
}
```

The first array access is valid and prints `20`.

The second array access is invalid because the array has positions `0`, `1`, and `2`. Java throws an `ArrayIndexOutOfBoundsException`.

The last line is not executed.

Output before the program stops:

```text
20
```

The exact exception message and stack trace depend on the Java runtime, but the exception type is:

```text
ArrayIndexOutOfBoundsException
```
**Important:** The exception type will be ArrayIndexOutOfBoundsException because the program attempted to access an array element using an invalid index.

The exact error message and stack trace may vary depending on the Java version and execution environment. 

However, the exception type identifies the problem consistently.
```
There are three important concepts:
Term	                Meaning
Exception type	        The category of error, such as ArrayIndexOutOfBoundsException.
Exception message	A descriptive explanation of what went wrong.
Stack trace	        Information showing where the exception occurred and the sequence of method calls leading to it.
```
### 1.4 What is exception handling?

**Exception handling** is the process of responding to an exception so that the program can deal with the problem in a controlled way.

Instead of allowing an exception to terminate the program unexpectedly, we can write an exception handler using a `catch` block. The handler contains instructions describing what the program should do when a particular exception occurs.

A `try` block contains code that might cause an exception, while a `catch` block contains the handler that responds to that exception.


In Java, an exception handler is usually the code inside a `catch` block. It is not necessarily a separate function or method.


For example, if a user enters an invalid array position, our program might display a useful message and ask for another position.

Exception handling does not mean that we should hide every problem. A good handler should respond meaningfully to the situation.

---

## 2. Checked versus unchecked exceptions

Java divides exceptions into two important categories: **checked** and **unchecked**.

### 2.1 Checked exceptions

A checked exception is checked by the Java compiler.

If a method may produce a checked exception, the calling code must deal with that possibility in one of two ways:

1. Handle it using `try-catch`.
2. Declare that it may pass the exception to its caller using `throws`.

If neither is done, the code will not compile.

For example, opening a file with `FileReader` may produce a `FileNotFoundException`.

```java
import java.io.FileReader;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        FileReader reader = new FileReader("students.txt");
    }
}
```

This code does not compile because `FileReader` may throw the checked exception `FileNotFoundException`, and the method has not handled or declared it.

We will learn how to handle or declare exceptions in later sections.

### 2.2 Unchecked exceptions

An unchecked exception is not required to be caught or declared by the compiler.

Most unchecked exceptions are subclasses of `RuntimeException`.

For example:

```java
public static void main(String[] args) {
    int result = 10 / 0;
    System.out.println(result);
}
```

Java allows this code to compile. However, when it runs, it throws an `ArithmeticException`.

Another example:

```java
int[] numbers = {10, 20, 30};
System.out.println(numbers[10]);
```

This compiles, but causes an `ArrayIndexOutOfBoundsException` at runtime.

### 2.3 Summary of checked and unchecked exceptions

| Category | Compiler requires handling or declaration? | Example |
|---|---|---|
| Checked exception | Yes | `FileNotFoundException` |
| Unchecked exception | No | `ArithmeticException` |
| `RuntimeException` | No; it is unchecked | `NullPointerException` |
| `Error` | No; it is unchecked | `StackOverflowError` |

Remember:

- Checked exceptions must be caught or declared.
- Unchecked exceptions do not have that compiler requirement.
- A program can still choose to catch an unchecked exception.
- Not every unchecked exception should be caught. Often the better solution is to correct the code or validate the input.

---

## 3. Java's default exception handler

### 3.1 What happens when we do not handle an exception?

When an exception is thrown and no matching handler is found in the current method, Java looks for a handler in the method that called it.

This process continues up the chain of method calls.

If no suitable handler is found, the exception reaches the thread's uncaught-exception handler. Java's default handler normally:

1. Prints the exception type and message.
2. Prints a stack trace showing where the exception occurred and how the program reached that point.
3. Ends the affected thread. If it is the only application thread, the program terminates.

Consider:

```java
public class Main {
    public static void main(String[] args) {
        int a = 10;
        int b = 0;

        int result = a / b;

        System.out.println(result);
        System.out.println("Program finished");
    }
}
```

The division throws an `ArithmeticException`.

A typical console message begins like this:

```text
Exception in thread "main" java.lang.ArithmeticException: / by zero
    at Main.main(Main.java:6)
```

The line number depends on the actual file.

Neither `System.out.println(result)` nor `System.out.println("Program finished")` is executed.

### 3.2 What is a stack trace?

A **stack trace** is a list of method calls that shows how Java arrived at the line where an exception occurred.

For example, suppose:

```java
public static void main(String[] args) {
    calculate();
}

public static void calculate() {
    divide();
}

public static void divide() {
    int result = 10 / 0;
}
```

The exception occurs inside `divide()`, but `divide()` was called by `calculate()`, and `calculate()` was called by `main()`.

The stack trace helps us follow that path.

A stack trace is useful when debugging because it helps identify:

- The line where the exception occurred.
- The method in which it occurred.
- The methods that called that method.

---

## 4. User-defined exception handling with `try-catch`

There are two main approaches covered in this lecture:

- `try-catch`: a method provides a handler for an exception.
- `throws`: a method declares that an exception may be passed to its caller.

We will begin with `try-catch`.

### 4.1 Basic syntax of try-catch

A basic `try-catch` structure contains a `try` block and at least one `catch` block.

```java
try {
    // Code that may throw an exception
}
catch (ExceptionType e) {
    // Code that handles this type of exception
}
```

Think of the `try` block as a protected area.

- Java executes the statements inside `try` normally.
- If no exception is thrown, the `catch` block is skipped.
- If an exception is thrown, Java stops executing the remaining statements in that `try` block.
- Java looks for a matching `catch` block.
- If a matching handler is found, its code runs.

A `try` statement must be followed by at least one `catch` block, a `finally` block, or both. This lecture focuses mainly on `try-catch`; a short `finally` example appears in Section 15.6.

### 4.2 A simple try-catch example

Let's protect a division operation.

```java
public class Main {
    public static void main(String[] args) {
        int num1 = 10;
        int num2 = 0;

        try {
            int result = num1 / num2;
            System.out.println("Result: " + result);
        }
        catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero.");
        }

        System.out.println("Program continues.");
    }
}
```

Output:

```text
Cannot divide by zero.
Program continues.
```

What happened?

1. Java enters the `try` block.
2. It attempts `10 / 0`.
3. Java throws an `ArithmeticException`.
4. Java skips the remaining statements in the `try` block.
5. The `catch (ArithmeticException e)` block matches the exception.
6. The catch block prints the message.
7. Execution continues after the `try-catch` structure.

Notice that `Result: ...` is not printed because the division did not complete.

### 4.3 What if no exception occurs?

Change the divisor:

```java
int num2 = 2;
```

Now the division succeeds.

Output:

```text
Result: 5
Program continues.
```

The `catch` block is skipped because no exception was thrown.

A `try-catch` does not automatically make the code inside `try` safer or change its normal behaviour. The catch block runs only when a matching exception is thrown.

### 4.4 Put only the relevant statements inside try

It is usually better to place the statements that may throw the exception inside the `try` block, rather than putting an entire program inside it.

For example:

```java
int[] numbers = {10, 20, 30};
int index = 5;

try {
    System.out.println(numbers[index]);
}
catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("That array position does not exist.");
}
```

The array access is the operation that may throw the exception, so it is the statement that needs protection.

---

## 5. Multiple catch blocks and specific handlers

A `try` block can have more than one `catch` block. Each catch block can handle a different type of exception.

### 5.1 Why use more than one catch?

Consider an array:

```java
int[] nums = {1, 2, 0, 3};
```

Suppose we divide `5` by an array element selected using a random index.

Two different problems may occur:

1. The index may be outside the array's valid range.
2. The selected array value may be `0`, causing division by zero.

These are different exceptions:

- `ArrayIndexOutOfBoundsException`
- `ArithmeticException`

We can provide a different handler for each one.

### 5.2 Example with two specific handlers

```java
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int num = 5;
        int result = 0;
        int[] nums = {1, 2, 0, 3};

        Random rand = new Random();

        try {
            for (int i = 0; i < 10; i++) {
                int index = rand.nextInt(10);
                result = num / nums[index];
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            result = -1;
        }
        catch (ArithmeticException e) {
            result = 0;
        }

        System.out.println(result);
    }
}
```

The program's result depends on the random indexes generated. It is not guaranteed to print the same value every time.

### 5.3 Understand the two possible exceptions

**Case 1: An invalid array index**

The array contains four elements:

```text
Index:  0  1  2  3
Value:  1  2  0  3
```

If the random index is `7`, this statement fails:

```java
nums[index]
```

Java throws an `ArrayIndexOutOfBoundsException`.

The first catch block matches:

```java
catch (ArrayIndexOutOfBoundsException e) {
    result = -1;
}
```

The result becomes `-1`.

**Case 2: Division by zero**

If the random index is `2`, the selected array value is `0`.

The calculation becomes:

```java
5 / 0
```

Java throws an `ArithmeticException`.

The first catch does not match. Java checks the next catch:

```java
catch (ArithmeticException e) {
    result = 0;
}
```

This catch handles the exception, and the result becomes `0`.

### 5.4 Important detail: execution leaves the try block

Once an exception is thrown, Java does not return to the next iteration of the loop in the `try` block.

It transfers control to the matching catch block. After the catch block finishes, execution continues after the complete `try-catch` structure.

This is why the loop does not simply continue after an exception.

### 5.5 Make random examples easier to test

Random examples are useful for demonstrating different situations, but their output is unpredictable.

When learning exception handling, you can test each case separately by using a fixed index.

For an invalid index:

```java
int index = 7;
```

For division by zero:

```java
int index = 2;
```

For a valid calculation:

```java
int index = 0;
```

This makes it easier to confirm which catch block is activated.

---

## 6. General catch blocks

### 6.1 What if we do not know every possible exception?

Suppose our `try` block may throw several different exceptions.

We could write a separate catch block for every possible type. However, this may become difficult if many exception types are possible.

Java allows us to use a more general catch block.

For example:

```java
catch (Exception e) {
    System.out.println("Something went wrong.");
}
```

`Exception` is a superclass of many exception types. This catch can handle many different exceptions that inherit from `Exception`.

We can also catch a category such as `RuntimeException`:

```java
catch (RuntimeException e) {
    System.out.println("A runtime exception occurred.");
}
```

### 6.2 Specific catches must come before general catches

Java checks catch blocks in order.

Consider:

```java
try {
    int[] numbers = {10, 20, 30};
    System.out.println(numbers[5]);
}
catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Invalid array index.");
}
catch (Exception e) {
    System.out.println("A different exception occurred.");
}
```

The first catch handles the specific array-index exception. The second catch handles other exceptions that are subclasses of `Exception`.

The order matters.

**Incorrect order:**

```java
try {
    int[] numbers = {10, 20, 30};
    System.out.println(numbers[5]);
}
catch (Exception e) {
    System.out.println("Something went wrong.");
}
catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Invalid array index.");
}
```

This does not compile. The first catch already handles `ArrayIndexOutOfBoundsException`, so the later, more specific catch can never be reached.

### 6.3 A general handler after specific handlers

```java
public static void main(String[] args) {
    int[] nums = {1, 2, 0, 3};
    int result = 0;

    try {
        result = 5 / nums[2];
    }
    catch (ArrayIndexOutOfBoundsException e) {
        result = -1;
    }
    catch (ArithmeticException e) {
        result = 0;
    }
    catch (Exception e) {
        result = -2;
    }

    System.out.println(result);
}
```

Output:

```text
0
```

The array access is valid, but the value at index `2` is `0`. Therefore, the `ArithmeticException` catch handles the problem.

The final `catch (Exception e)` is a general fallback for other exception types.

### 6.4 Should we always catch Exception?

No. A general catch can prevent a program from stopping, but it may also hide the real problem.

Prefer specific handlers when you know how to respond to a particular exception.

Use a general handler only when you have a meaningful reason to handle a wider category of exceptions.

---

## 7. Meaningful handlers: using the exception object

### 7.1 What is `e`?

In a catch block:

```java
catch (ArithmeticException e) {
    // ...
}
```

The variable `e` refers to the exception object that was thrown.

It contains information about the exception. We can use methods from the exception class and its parent classes to inspect that information.

Three useful methods are:

| Method | What it provides |
|---|---|
| `e.getClass()` | The exact runtime class of the exception |
| `e.getMessage()` | The detail message, if one was supplied |
| `e.getStackTrace()` | An array of stack-trace elements describing the call path |

### 7.2 Display the exception type and message

```java
public static void main(String[] args) {
    try {
        int result = 10 / 0;
    }
    catch (ArithmeticException e) {
        System.out.println("Exception type: " + e.getClass());
        System.out.println("Message: " + e.getMessage());
    }
}
```

Typical output:

```text
Exception type: class java.lang.ArithmeticException
Message: / by zero
```

The exact message can vary by Java implementation.

### 7.3 Display the stack trace

`getStackTrace()` returns an array of `StackTraceElement` objects.

We can use an enhanced `for` loop to print each element:

```java
public static void main(String[] args) {
    try {
        int result = 10 / 0;
    }
    catch (ArithmeticException e) {
        StackTraceElement[] trace = e.getStackTrace();

        for (StackTraceElement element : trace) {
            System.out.println(element);
        }
    }
}
```

A stack-trace element can show information such as the class, method, source file, and line number.

### 7.4 A more meaningful handler

Instead of simply changing a result to an arbitrary number, a handler should explain the problem or take an appropriate recovery action.

```java
public static void main(String[] args) {
    int numerator = 10;
    int denominator = 0;

    try {
        int result = numerator / denominator;
        System.out.println("Result: " + result);
    }
    catch (ArithmeticException e) {
        System.out.println("The calculation could not be completed.");
        System.out.println("Reason: " + e.getMessage());
    }
}
```

Output:

```text
The calculation could not be completed.
Reason: / by zero
```

The handler now gives useful information to the person running the program.

### 7.5 Connection to object methods: avoid accidental NullPointerException

In Lectures 7–10, you worked with objects and reference variables. A reference can be `null`, which means it does not currently refer to an object.

For example:

```java
Student student = null;
System.out.println(student.getGpa());
```

This compiles, but it throws a `NullPointerException` at runtime because Java cannot call `getGpa()` on a `null` reference.

A better design is often to validate the reference before using it:

```java
if (student == null) {
    System.out.println("No student object was supplied.");
}
else {
    System.out.println(student.getGpa());
}
```

Do not automatically catch every `NullPointerException`. First ask why the reference is null and whether the program should reject it earlier, for example in a constructor or method parameter check.

Lecture 10 also showed a safer way to compare possibly-null object fields:

```java
Objects.equals(name, other.name)
```

This avoids a `NullPointerException` that could occur with:

```java
name.equals(other.name)
```

when `name` is `null`. The `Objects.equals()` method does not eliminate every possible null problem, but it is useful when comparing nullable fields.

### 7.6 About `System.exit(1)`

You may sometimes see:

```java
System.exit(1);
```

This asks the Java Virtual Machine to terminate the application with a non-zero exit status.

A common convention is:

- `System.exit(0)`: normal termination.
- A non-zero status such as `System.exit(1)`: termination indicating a problem.

Do not use `System.exit()` automatically in every catch block. Often the program should display an explanation, recover, or let a higher-level method decide what to do.

---

## 8. The `throws` keyword: pass responsibility to the caller

### 8.1 What does `throws` mean?

Sometimes a method should not decide how to handle an exception.

Instead, it can declare that an exception may occur and allow the calling method to handle it.

The keyword `throws` is written in the method header.

```java
public static void m1() throws ArithmeticException {
    int result = 10 / 0;
}
```

This declaration tells callers that `m1()` may throw an `ArithmeticException`.

`throws` does not itself catch or fix the exception. It declares the possibility and allows the exception to propagate to the caller.

### 8.2 A simple example

```java
public class Main {

    public static void divide() throws ArithmeticException {
        int result = 10 / 0;
    }

    public static void main(String[] args) {
        try {
            divide();
        }
        catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero.");
        }
    }
}
```

Output:

```text
Cannot divide by zero.
```

What happens?

1. `main()` calls `divide()`.
2. `divide()` attempts to divide by zero.
3. Java throws an `ArithmeticException`.
4. The exception propagates out of `divide()`.
5. The `try-catch` in `main()` handles it.

The method `divide()` does not provide its own handler. The caller provides the handler.

### 8.3 Passing responsibility through multiple methods

The exception can pass through more than one method.

```java
public class Main {

    public static void m2() throws ArithmeticException {
        int result = 10 / 0;
    }

    public static void m1() throws ArithmeticException {
        m2();
    }

    public static void main(String[] args) {
        try {
            m1();
        }
        catch (ArithmeticException e) {
            System.out.println("The calculation failed.");
        }
    }
}
```

Output:

```text
The calculation failed.
```

The flow is:

```text
main()
  |
  v
m1()
  |
  v
m2()
  |
  v
ArithmeticException is thrown
  |
  v
m2() does not handle it
  |
  v
m1() does not handle it
  |
  v
main() catches it
```

Both `m2()` and `m1()` declare `throws ArithmeticException`, so the exception can propagate through them to `main()`.

### 8.4 Declaring more than one exception

A method can declare more than one exception type, separated by commas.

```java
public static void m1()
        throws ArithmeticException, ArrayIndexOutOfBoundsException {

    int[] numbers = {10, 20, 30};

    int result = 10 / numbers[5];
}
```

The method declares both possibilities.

A caller can provide separate catch blocks:

```java
public static void main(String[] args) {
    try {
        m1();
    }
    catch (ArithmeticException e) {
        System.out.println("Cannot divide by zero.");
    }
    catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Invalid array index.");
    }
}
```

In this particular example, the array index is invalid, so the second catch handles the exception.

### 8.5 What if main uses throws?

Even `main()` can declare `throws`.

```java
public static void main(String[] args) throws ArithmeticException {
    int result = 10 / 0;
}
```

The code compiles because `ArithmeticException` is unchecked, and the declaration is allowed.

However, there is no application method above `main()` to provide a handler. If the exception remains uncaught, it reaches the thread's uncaught-exception handler.

For a checked exception, declaring `throws` in `main()` also satisfies the compiler, but the exception can still remain uncaught at runtime.

---

## 9. `throw` versus `throws`

These two keywords look similar, but they have different jobs.

### 9.1 `throws` — declaration in a method header

`throws` declares that a method may pass an exception to its caller.

```java
public static void calculate() throws ArithmeticException {
    int result = 10 / 0;
}
```

Think of `throws` as an announcement.

### 9.2 `throw` — actually throw an exception object

`throw` is used inside a method body to throw an exception.

```java
public static void checkNumber(int num) {
    if (num < 0) {
        throw new IllegalArgumentException("Number cannot be negative.");
    }

    System.out.println("Number accepted: " + num);
}
```

Call:

```java
checkNumber(-5);
```

The method creates an `IllegalArgumentException` object and throws it.

If there is no matching handler in this method, the exception propagates to the caller.

### 9.3 Compare the two keywords

| Keyword | Where it appears | Purpose |
|---|---|---|
| `throw` | Inside a method body | Throws one exception object |
| `throws` | In a method declaration | Declares exception types that may propagate to the caller |

A quick way to remember:

- `throw` = perform the action.
- `throws` = declare the possibility.

### 9.4 A method can use both

```java
public static void checkAge(int age)
        throws IllegalArgumentException {

    if (age < 0) {
        throw new IllegalArgumentException("Age cannot be negative.");
    }

    System.out.println("Age accepted.");
}
```

The method declares the exception type with `throws`, and actually throws an exception object with `throw`.

Because `IllegalArgumentException` is unchecked, Java does not require the `throws` declaration. It is included here to make the relationship between the two keywords visible.

---

## 10. User-defined exceptions

### 10.1 Why create our own exception?

Java already provides many exception classes, such as:

- `ArithmeticException`
- `NullPointerException`
- `ArrayIndexOutOfBoundsException`
- `IllegalArgumentException`

Sometimes, however, an application has a special rule that is not represented clearly by an existing exception.

For example, imagine a method that adds two numbers, but the application does not allow the two numbers to be equal.

We could create a custom exception called:

```text
TwoNumberTheSameException
```

This makes the problem clear and gives the application its own exception type.

### 10.2 Create a custom unchecked exception

To create an unchecked custom exception, extend `RuntimeException`.

```java
public class TwoNumberTheSameException extends RuntimeException {

    public TwoNumberTheSameException() {
        super();
    }

    public TwoNumberTheSameException(String message) {
        super(message);
    }
}
```

The class has two constructors:

1. A default constructor.
2. A constructor that accepts a message.

The call:

```java
super(message);
```

passes the message to the parent exception class. The message can later be retrieved using:

```java
e.getMessage()
```

### 10.3 Create a custom checked exception

If we extend `Exception` directly, the custom exception is checked.

```java
public class TwoNumberTheSameException extends Exception {

    public TwoNumberTheSameException() {
        super();
    }

    public TwoNumberTheSameException(String message) {
        super(message);
    }
}
```

The main difference is:

| Declaration | Category |
|---|---|
| `extends RuntimeException` | Unchecked exception |
| `extends Exception` | Checked exception |

For a checked custom exception, callers must catch it or declare it using `throws`.

### 10.4 Why is `super(message)` important?

Consider this constructor:

```java
public TwoNumberTheSameException(String message) {
    super(message);
}
```

The parent exception class stores the message so that it can be retrieved later.

For example:

```java
try {
    throw new TwoNumberTheSameException("Numbers cannot be the same!");
}
catch (TwoNumberTheSameException e) {
    System.out.println(e.getMessage());
}
```

Output:

```text
Numbers cannot be the same!
```

If the constructor does not pass the message to the parent class, the inherited `getMessage()` method will normally return `null`, unless the custom exception class provides its own implementation.

### 10.5 Throw the custom exception manually

Once the class exists, we can create and throw an object of that class.

```java
public static int addDifferentNumbers(int a, int b) {
    if (a == b) {
        throw new TwoNumberTheSameException(
            "The two numbers cannot be the same."
        );
    }

    return a + b;
}
```

Test it:

```java
public static void main(String[] args) {
    try {
        int result = addDifferentNumbers(5, 5);
        System.out.println("Result: " + result);
    }
    catch (TwoNumberTheSameException e) {
        System.out.println("Exception: " + e.getMessage());
    }
}
```

Output:

```text
Exception: The two numbers cannot be the same.
```

The exception is thrown when the condition `a == b` is true. The catch block handles it.

If we call:

```java
addDifferentNumbers(5, 8);
```

The method returns `13` because the numbers are different.

---

## 11. Exception chaining: preserving the original cause

### 11.1 What is exception chaining?

Sometimes one exception occurs while we are handling another exception.

We may want to throw a new, more meaningful exception while preserving the original exception that caused the problem.

This is called **exception chaining**.

For example:

- The program encounters an `ArithmeticException`.
- We want to report a more application-specific exception.
- We wrap the original exception inside the new exception as its cause.

### 11.2 Add a constructor that accepts a cause

```java
public class TwoNumberTheSameException extends Exception {

    public TwoNumberTheSameException(String message) {
        super(message);
    }

    public TwoNumberTheSameException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

The second constructor accepts:

- A message describing the new exception.
- A `Throwable` representing the original cause.

The call to `super(message, cause)` stores both pieces of information in the parent exception object.

### 11.3 Wrap an existing exception

```java
public static int divide(int a, int b)
        throws TwoNumberTheSameException {

    try {
        if (a == b) {
            throw new ArithmeticException(
                "Same numbers detected internally"
            );
        }

        return a / b;
    }
    catch (ArithmeticException e) {
        throw new TwoNumberTheSameException(
            "Invalid division attempt", e
        );
    }
}
```

Call it:

```java
public static void main(String[] args) {
    try {
        divide(5, 5);
    }
    catch (TwoNumberTheSameException e) {
        System.out.println("Message: " + e.getMessage());
        System.out.println("Cause: " + e.getCause());
    }
}
```

Output:

```text
Message: Invalid division attempt
Cause: java.lang.ArithmeticException: Same numbers detected internally
```

The new exception gives the caller a message that makes sense for the application. The original cause is not lost.

**Important:** This example deliberately throws an `ArithmeticException` when the two values are equal to demonstrate exception chaining. In a real application, choose an exception type that accurately describes the condition.

---

## 12. Two approaches to handling a user-defined exception

The previous example can be written in two ways, depending on which method should handle the exception.

### 12.1 Approach A — Handle the exception inside the method

In this approach, the method that detects the problem also handles it.

```java
public class Calculator {

    public static int divide(int a, int b) {
        try {
            if (a == b) {
                throw new TwoNumberTheSameException(
                    "Numbers cannot be the same!"
                );
            }

            return a / b;
        }
        catch (TwoNumberTheSameException e) {
            System.out.println("Exception caught: " + e.getMessage());
            return 0;
        }
    }
}
```

Main:

```java
public class Main {
    public static void main(String[] args) {
        int result = Calculator.divide(5, 5);
        System.out.println("Result = " + result);
    }
}
```

Output:

```text
Exception caught: Numbers cannot be the same!
Result = 0
```

Execution flow:

1. `main()` calls `Calculator.divide(5, 5)`.
2. The condition `a == b` is true.
3. The method throws `TwoNumberTheSameException`.
4. The catch block inside `divide()` handles it.
5. The method returns the fallback value `0`.
6. `main()` receives `0` and prints it.

The method itself decides what to do when the exception occurs.

### 12.2 Approach B — Let the caller handle the exception

In this approach, the method declares the exception using `throws` and does not catch it.

```java
public class Calculator {

    public static int divide(int a, int b)
            throws TwoNumberTheSameException {

        if (a != b) {
            return a / b;
        }

        throw new TwoNumberTheSameException(
            "Numbers cannot be the same!"
        );
    }
}
```

Main handles it:

```java
public class Main {
    public static void main(String[] args) {
        try {
            int result = Calculator.divide(5, 5);
            System.out.println("Result = " + result);
        }
        catch (TwoNumberTheSameException e) {
            System.out.println(
                "Handled in main: " + e.getMessage()
            );
        }
    }
}
```

Output:

```text
Handled in main: Numbers cannot be the same!
```

Execution flow:

1. `main()` calls `Calculator.divide(5, 5)`.
2. The condition `a != b` is false.
3. The method throws `TwoNumberTheSameException`.
4. The exception leaves `divide()` because it is not caught there.
5. The `try-catch` in `main()` receives and handles it.

### 12.3 Compare the two approaches

| Question | Approach A: `try-catch` inside method | Approach B: `throws` to caller |
|---|---|---|
| Who handles the exception? | The method itself | The caller |
| Does the method catch it? | Yes | No |
| Does the exception propagate? | Not beyond that catch | Yes, until a handler is found |
| Can the method return a fallback value? | Yes | The caller decides what to do |
| Useful when | The method knows how to recover | The caller should decide the response |

Neither approach is always better. Choose based on which part of the program has enough information to make the right decision.

---

## 13. Common mistakes

### Mistake 1: Catching the wrong exception type

Incorrect:

```java
try {
    int result = 10 / 0;
}
catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Invalid index.");
}
```

The code throws `ArithmeticException`, not `ArrayIndexOutOfBoundsException`. The catch block does not match.

Correction:

```java
catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero.");
}
```

### Mistake 2: Putting a general catch before a specific catch

Incorrect:

```java
try {
    int[] values = {1, 2, 3};
    System.out.println(values[5]);
}
catch (Exception e) {
    System.out.println("General handler.");
}
catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Specific handler.");
}
```

The second catch is unreachable because the first catch already handles that exception type.

Correction: Put specific catches first and general catches last.

### Mistake 3: Thinking `throws` handles the exception

Incorrect understanding:

```java
public static void readFile() throws FileNotFoundException {
    // ...
}
```

The method has not handled the exception. It has declared that the exception may propagate to the caller.

The caller must catch the checked exception or declare it too.

### Mistake 4: Confusing `throw` and `throws`

Incorrect:

```java
throw ArithmeticException;
```

`throw` requires an exception object.

Correct:

```java
throw new ArithmeticException("Invalid calculation.");
```

A method declaration uses `throws`:

```java
public static void calculate() throws ArithmeticException {
    // ...
}
```

### Mistake 5: Creating a custom exception without passing its message

Incorrect:

```java
public TwoNumberTheSameException(String message) {
    // message is ignored
}
```

Correction:

```java
public TwoNumberTheSameException(String message) {
    super(message);
}
```

### Mistake 6: Catching exceptions without a meaningful response

Weak:

```java
catch (Exception e) {
    // Do nothing
}
```

This hides the problem and makes debugging difficult.

Better:

```java
catch (Exception e) {
    System.out.println("The operation failed: " + e.getMessage());
}
```

The exact response should depend on the application. Sometimes the correct action is to report the problem, retry, use a fallback, or let the exception propagate.

### Mistake 7: Using exceptions for ordinary decisions

Do not use exceptions as a replacement for simple conditions.

For example, before dividing, you can check:

```java
if (denominator == 0) {
    System.out.println("Cannot divide by zero.");
}
else {
    System.out.println(numerator / denominator);
}
```

Exception handling is useful for exceptional situations, but ordinary input validation can often be handled with regular `if` statements.

---

## 14. Practice — predict, run, explain

For each question, predict what happens before running the code. Identify the exception type, the matching handler, and which statements execute.

### Practice 1 — Identify the exception

```java
int[] numbers = {10, 20, 30};

System.out.println(numbers[1]);
System.out.println(numbers[5]);
System.out.println("Finished");
```

What happens?

**Answer:**

- `numbers[1]` prints `20`.
- `numbers[5]` throws `ArrayIndexOutOfBoundsException`.
- `"Finished"` is not printed because there is no handler.

### Practice 2 — Which catch block runs?

```java
try {
    int result = 10 / 0;
}
catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Index problem.");
}
catch (ArithmeticException e) {
    System.out.println("Math problem.");
}
catch (Exception e) {
    System.out.println("Other problem.");
}
```

**Answer:**

```text
Math problem.
```

The exception is an `ArithmeticException`, so the second catch block handles it.

### Practice 3 — Catch order

Why is this code invalid?

```java
try {
    int[] values = {1, 2, 3};
    System.out.println(values[5]);
}
catch (Exception e) {
    System.out.println("General exception.");
}
catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Invalid index.");
}
```

**Answer:** The general `Exception` catch appears before the specific catch. It can already handle `ArrayIndexOutOfBoundsException`, making the later catch unreachable. Put the specific catch first.

### Practice 4 — `throw` or `throws`?

Fill in the blanks:

```java
public static void check(int num) ______ IllegalArgumentException {
    if (num < 0) {
        ______ new IllegalArgumentException("Negative number");
    }
}
```

**Answer:**

```java
public static void check(int num) throws IllegalArgumentException {
    if (num < 0) {
        throw new IllegalArgumentException("Negative number");
    }
}
```

`throws` appears in the method declaration. `throw` throws the exception object.

### Practice 5 — Checked exception

Why does this code require exception handling or a `throws` declaration?

```java
import java.io.FileReader;

public static void openFile() {
    FileReader reader = new FileReader("data.txt");
}
```

**Answer:** `FileReader` may throw the checked exception `FileNotFoundException`. The method must catch it or declare it using `throws FileNotFoundException`.

### Practice 6 — Write a custom exception

Create a custom unchecked exception called `InvalidAgeException` with a constructor that accepts a message.

**Answer:**

```java
public class InvalidAgeException extends RuntimeException {

    public InvalidAgeException(String message) {
        super(message);
    }
}
```

### Practice 7 — Where is the exception handled?

```java
public static void methodB() throws ArithmeticException {
    int result = 10 / 0;
}

public static void methodA() throws ArithmeticException {
    methodB();
}

public static void main(String[] args) {
    try {
        methodA();
    }
    catch (ArithmeticException e) {
        System.out.println("Handled in main.");
    }
}
```

**Answer:**

```text
Handled in main.
```

`methodB()` throws the exception. `methodA()` allows it to propagate. `main()` catches it.

### Practice 8 — What does getMessage() return?

```java
try {
    throw new IllegalArgumentException("Invalid student ID");
}
catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());
}
```

**Answer:**

```text
Invalid student ID
```

The message was supplied when the exception object was created.

### Practice 9 — Choose the right approach

A method is used by several different parts of a program. Each caller should decide what message to display when the operation fails.

Should the method handle the exception internally or declare it with `throws`?

**Answer:** Declaring the exception with `throws` is often more appropriate. It allows each caller to decide how to respond.

---

## 15. AI-assisted practice — use AI as a debugging partner

The goal is to practise exception types, catch-block ordering, and the difference between `throw` and `throws`. Ask AI to guide your reasoning rather than immediately supplying the answer.

### Activity: Repair a broken calculator

A student writes:

```java
public class Calculator {

    public static int divide(int a, int b) {
        if (b == 0) {
            throw ArithmeticException("Cannot divide by zero");
        }

        return a / b;
    }

    public static void main(String[] args) {
        try {
            int result = divide(10, 0);
            System.out.println(result);
        }
        catch (Exception e) {
            System.out.println("General problem.");
        }
        catch (ArithmeticException e) {
            System.out.println("Math problem: " + e.getMessage());
        }
    }
}
```

The student expects the program to print:

```text
Math problem: Cannot divide by zero
```

But the code does not compile.

**Your tasks:**

1. Find the problem with the `throw` statement.
2. Explain why the catch-block order is invalid.
3. Correct the code.
4. Predict the output.
5. Explain why the `ArithmeticException` catch runs instead of the general catch.

### AI prompt for students

You may use the following prompt:

> I am learning Java exception handling. Do not immediately rewrite my code. First ask me to identify the two compile-time problems. Give me one hint at a time about the throw statement and catch-block order. After I attempt the correction, explain the exception flow in simple terms.

### Solution guide

There are two compile-time problems.

**Problem 1 — The throw statement needs an exception object**

Incorrect:

```java
throw ArithmeticException("Cannot divide by zero");
```

Correct:

```java
throw new ArithmeticException("Cannot divide by zero");
```

The `new` keyword creates the exception object.

**Problem 2 — The catch blocks are in the wrong order**

Incorrect:

```java
catch (Exception e) {
    System.out.println("General problem.");
}
catch (ArithmeticException e) {
    System.out.println("Math problem: " + e.getMessage());
}
```

`ArithmeticException` is a subclass of `RuntimeException`, which is a subclass of `Exception`. Therefore, the general catch can already handle it. The specific catch must come first.

Corrected program:

```java
public class Calculator {

    public static int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException(
                "Cannot divide by zero"
            );
        }

        return a / b;
    }

    public static void main(String[] args) {
        try {
            int result = divide(10, 0);
            System.out.println(result);
        }
        catch (ArithmeticException e) {
            System.out.println(
                "Math problem: " + e.getMessage()
            );
        }
        catch (Exception e) {
            System.out.println("General problem.");
        }
    }
}
```

Expected output:

```text
Math problem: Cannot divide by zero
```

Execution flow:

1. `main()` calls `divide(10, 0)`.
2. The condition `b == 0` is true.
3. `divide()` creates and throws an `ArithmeticException`.
4. Control returns to the `try-catch` structure in `main()`.
5. Java checks the first catch block.
6. `ArithmeticException` matches, so that catch block runs.
7. The general `Exception` catch is skipped.

**Teaching point:** A specific exception handler should appear before a general handler. Also, `throw` must be followed by an exception object.

---

## 16. Summary of core exception-handling concepts

| Term | Plain-language meaning |
|---|---|
| `Throwable` | The parent class for Java errors and exceptions |
| `Error` | A serious problem applications normally do not handle |
| `Exception` | A condition an application may be able to handle |
| Checked exception | An exception that must be caught or declared |
| Unchecked exception | An exception that the compiler does not require us to catch or declare |
| Default handler | The handler used when an exception reaches the uncaught-exception mechanism |
| `try` | The block containing code that may throw an exception |
| `catch` | The block that handles a matching exception |
| Exception object (`e`) | The object containing information about the thrown exception |
| `getMessage()` | Retrieves the exception's detail message |
| `getStackTrace()` | Retrieves stack-trace elements |
| `throw` | Throws an exception object |
| `throws` | Declares exception types that may propagate from a method |
| User-defined exception | An exception class created for an application's own rules |
| Exception chaining | Wrapping an original cause inside a new exception |

Remember these six rules:

1. **Know the exception type.** Different problems produce different exception classes.
2. **Understand checked and unchecked exceptions.** Checked exceptions must be caught or declared.
3. **Use specific catch blocks first.** Put general handlers after specific handlers.
4. **Use the exception object.** `getMessage()` and `getStackTrace()` can help explain and debug a problem.
5. **Know the difference between `throw` and `throws`.** One throws an object; the other declares possible propagation.
6. **Create custom exceptions when they make application rules clearer.** Pass messages to the parent constructor with `super(message)`.

## Further reading — core exception handling

- [Java `Throwable` API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Throwable.html)
- [Java `Exception` API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Exception.html)
- [Java `RuntimeException` API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/RuntimeException.html)
- [Java `try` statement — Java Language Specification](https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html#jls-14.20)
- [Java `throw` statement — Java Language Specification](https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html#jls-14.18)


---

## 17. Applying exception handling to Lecture 7 — `Employee` and `Manager`

In Lecture 7, you learned inheritance and polymorphism using an `Employee` superclass and a `Manager` subclass.

Exception handling becomes useful when objects are created with invalid data or when a method receives an invalid argument.

### 15.1 Validate employee data in the constructor

Imagine that an employee's salary must not be negative.

A negative salary is not a problem that should be silently accepted. We can reject it using `IllegalArgumentException`.

```java
public class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                "Employee name cannot be empty."
            );
        }

        if (salary < 0) {
            throw new IllegalArgumentException(
                "Salary cannot be negative."
            );
        }

        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void displayDetails() {
        System.out.println(name + " earns $" + salary);
    }
}
```

What does `throw new IllegalArgumentException(...)` do?

1. It creates an exception object.
2. It throws that object immediately.
3. The constructor stops at that point.
4. The `Employee` object is not successfully created.
5. The exception propagates to the code that called the constructor, unless it is handled earlier.

### 15.2 Test the constructor from `main`

```java
public class Main {
    public static void main(String[] args) {
        try {
            Employee e1 = new Employee("Mina", 65000);
            Employee e2 = new Employee("Ali", -5000);

            e1.displayDetails();
            e2.displayDetails();
        }
        catch (IllegalArgumentException e) {
            System.out.println("Employee could not be created.");
            System.out.println("Reason: " + e.getMessage());
        }

        System.out.println("Program continues.");
    }
}
```

Output:

```text
Employee could not be created.
Reason: Salary cannot be negative.
Program continues.
```

Notice that `e1` is created, but the second constructor call throws an exception. The remaining statements inside the `try` block are skipped. Therefore, `e1.displayDetails()` is also skipped because it appears after the failing constructor call.

If you want to handle each employee independently, put each creation in its own `try-catch` block or move the creation into a helper method that handles one employee at a time.

### 15.3 What about a `Manager` object?

A `Manager` is an `Employee`, so it can use the validation in the superclass constructor by calling `super(...)`.

```java
public class Manager extends Employee {
    private String department;

    public Manager(String name, double salary, String department) {
        super(name, salary);

        if (department == null || department.isBlank()) {
            throw new IllegalArgumentException(
                "Manager department cannot be empty."
            );
        }

        this.department = department;
    }

    @Override
    public void displayDetails() {
        System.out.println(
            "Manager: " + getName()
            + ", department: " + department
            + ", salary: $" + getSalary()
        );
    }
}
```

The superclass constructor validates the employee name and salary. The subclass constructor validates the department.

Try:

```java
try {
    Manager manager = new Manager("Sara", 85000, "");
}
catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());
}
```

Output:

```text
Manager department cannot be empty.
```

**Important:** The exception does not change inheritance or polymorphism. It controls what happens when invalid data is supplied during object creation.

### 15.4 When should the constructor throw and when should `main` catch?

The constructor knows what makes an `Employee` invalid. It should reject invalid data.

The caller knows what the program should do next. For example, `main` might display an error, ask for another value, or skip that employee.

This is a useful division of responsibility:

- Constructor: enforce the class's rules.
- Calling method: decide how to respond to the failed creation.

---

## 18. Applying exception handling to Lecture 8 — interfaces and polymorphic collections

Lecture 8 used interfaces and polymorphism. Examples included `Flyable`, with classes such as `Bird`, `Drone`, and `Airplane` implementing the interface.

Exception handling can be used when a polymorphic method receives invalid data or when a collection contains a null reference.

### 16.1 A polymorphic collection

```java
import java.util.ArrayList;

ArrayList<Flyable> flyers = new ArrayList<>();

flyers.add(new Bird());
flyers.add(new Drone(80));
flyers.add(new Airplane());
```

The collection is declared using the interface type `Flyable`, but it can hold objects of different concrete classes that implement that interface.

Suppose we want to call `fly()` on every object:

```java
for (Flyable flyer : flyers) {
    flyer.fly();
}
```

This is polymorphism: the implementation belonging to the actual object is executed.

### 16.2 What if the collection contains null?

Consider:

```java
flyers.add(null);
```

The collection can contain a null reference. But this call cannot work:

```java
flyer.fly();
```

If `flyer` is null, Java throws a `NullPointerException`.

A defensive version is:

```java
for (Flyable flyer : flyers) {
    if (flyer == null) {
        System.out.println("Skipping an empty flyer reference.");
        continue;
    }

    flyer.fly();
}
```

This example uses an ordinary null check rather than using an exception for a predictable condition.

**Teaching point:** A variable declared as an interface type does not guarantee that it refers to a valid object. The declared type controls which methods can be called; the actual reference must still be non-null.

### 16.3 Handle an exception thrown by an implementation

An interface method can be implemented by different classes. A particular implementation may reject an operation by throwing an unchecked exception.

For example, suppose a drone's `fly()` implementation validates its battery level:

```java
public class Drone implements Flyable {
    private int batteryLevel;

    public Drone(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    @Override
    public void fly() {
        if (batteryLevel < 20) {
            throw new IllegalStateException(
                "Drone battery is too low to fly."
            );
        }

        System.out.println("Drone is flying.");
    }
}
```

The caller can handle that exception without needing to know the internal details of the drone:

```java
for (Flyable flyer : flyers) {
    try {
        flyer.fly();
    }
    catch (IllegalStateException e) {
        System.out.println("Flight skipped: " + e.getMessage());
    }
}
```

This is an example of polymorphism and exception handling working together:

- Polymorphism chooses the correct `fly()` implementation.
- The implementation may throw an exception when its own rule is violated.
- The calling code decides how to respond.

The `Bird` and `Airplane` implementations do not have to use the same validation rule.

### 16.4 Do not catch an exception just to hide it

This is weak:

```java
try {
    flyer.fly();
}
catch (Exception e) {
    // Ignore the problem
}
```

The program gives no indication that a flight failed. Prefer a specific catch and a meaningful response.

---

## 19. Applying exception handling to Lecture 9 — `Comparable`, `Comparator`, and Student ranking

Lecture 9 introduced `Student`, `Comparable<Student>`, `compareTo()`, and separate `Comparator<Student>` rules such as scholarship ranking.

Exception handling can help enforce valid student data and diagnose problems in comparison or sorting code.

### 17.1 Reject an invalid GPA in the Student constructor

Assume the GPA scale for this example is from `0.0` to `4.0`.

```java
public class Student implements Comparable<Student> {
    private String name;
    private double gpa;

    public Student(String name, double gpa) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                "Student name cannot be empty."
            );
        }

        if (Double.isNaN(gpa) || gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException(
                "GPA must be between 0.0 and 4.0."
            );
        }

        this.name = name;
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    @Override
    public int compareTo(Student other) {
        return Double.compare(this.gpa, other.gpa);
    }

    @Override
    public String toString() {
        return name + " (" + gpa + ")";
    }
}
```

Why check `Double.isNaN(gpa)`?

A `double` can contain a special value called `NaN` ("Not a Number"). Ordinary comparisons such as `gpa < 0.0` and `gpa > 4.0` are both false when `gpa` is `NaN`. Therefore, the explicit check rejects it.

### 17.2 Test valid and invalid students

```java
public static void main(String[] args) {
    try {
        Student a = new Student("Mina", 3.7);
        Student b = new Student("Ali", 4.5);

        System.out.println(a);
        System.out.println(b);
    }
    catch (IllegalArgumentException e) {
        System.out.println("Student data rejected: " + e.getMessage());
    }
}
```

Output:

```text
Student data rejected: GPA must be between 0.0 and 4.0.
```

The first student is created. The second constructor call throws an exception, so the later print statements inside the `try` block are skipped.

### 17.3 Does `compareTo()` need exception handling?

A comparison method should normally compare valid objects. The constructor validation helps ensure that student objects begin with valid data.

However, this method can still fail if `other` is null:

```java
@Override
public int compareTo(Student other) {
    return Double.compare(this.gpa, other.gpa);
}
```

Calling:

```java
a.compareTo(null);
```

causes a `NullPointerException` when the method tries to access `other.gpa`.

One possible defensive implementation is:

```java
@Override
public int compareTo(Student other) {
    if (other == null) {
        throw new NullPointerException(
            "Cannot compare a Student with null."
        );
    }

    return Double.compare(this.gpa, other.gpa);
}
```

The explicit throw gives a clearer message. It is still an unchecked exception.

For this introductory course, the more important rule is to avoid putting null references into a list that will be sorted.

### 17.4 Sorting and catching invalid student data

```java
import java.util.ArrayList;
import java.util.Collections;

public static void main(String[] args) {
    ArrayList<Student> students = new ArrayList<>();

    try {
        students.add(new Student("Mina", 3.7));
        students.add(new Student("Ali", 3.9));
        students.add(new Student("Sara", 3.5));

        Collections.sort(students);

        for (Student student : students) {
            System.out.println(student);
        }
    }
    catch (IllegalArgumentException e) {
        System.out.println("Could not build student list: "
                + e.getMessage());
    }
}
```

Output:

```text
Sara (3.5)
Mina (3.7)
Ali (3.9)
```

This example sorts using the natural ordering defined by `compareTo()`.

### 17.5 Comparator for scholarship ranking

A scholarship comparator may rank the highest GPA first:

```java
import java.util.Comparator;

Comparator<Student> byGpaDescending =
        (first, second) ->
                Double.compare(second.getGpa(), first.getGpa());
```

Then:

```java
students.sort(byGpaDescending);
```

Exception handling does not replace the comparator. The comparator defines the ordering. Validation protects the data used by that ordering.

**Key distinction:**

- `IllegalArgumentException` can reject invalid GPA values when a `Student` is created.
- `compareTo()` defines natural ordering.
- `Comparator<Student>` defines an alternative ordering.
- `sort()` performs the reordering.
- A catch block decides how to respond if an exception occurs.

---

## 20. Applying exception handling to Lecture 10 — `equals()`, `hashCode()`, `HashSet`, and `HashMap`

Lecture 10 introduced the `Author` class, logical equality, hashing, and hash-based collections.

These concepts have several useful connections to exceptions.

### 18.1 Null-safe equality prevents a common NullPointerException

Suppose an `Author` has `name` and `country` fields.

A risky implementation is:

```java
return name.equals(other.name)
        && country.equals(other.country);
```

This can throw a `NullPointerException` if `name`, `country`, or `other` is null.

The safer pattern from Lecture 10 is:

```java
import java.util.Objects;

@Override
public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
        return false;
    }

    Author other = (Author) obj;

    return Objects.equals(name, other.name)
            && Objects.equals(country, other.country);
}
```

`Objects.equals(a, b)` safely handles null references. It does not mean that null is always valid data; it simply prevents this comparison from throwing a null-related exception.

### 18.2 Constructor validation versus allowing null fields

There are two legitimate design choices.

**Choice A — Reject null or blank author information**

```java
public Author(String name, String country) {
    if (name == null || name.isBlank()) {
        throw new IllegalArgumentException(
            "Author name is required."
        );
    }

    if (country == null || country.isBlank()) {
        throw new IllegalArgumentException(
            "Author country is required."
        );
    }

    this.name = name;
    this.country = country;
}
```

**Choice B — Permit null fields**

If the design intentionally permits null values, use null-safe operations such as `Objects.equals()` and `Objects.hash()`.

The important point is consistency: decide whether null is allowed, then implement the class accordingly.

### 18.3 Does HashSet throw an exception for a duplicate?

No. A duplicate is an ordinary collection condition, not an exception.

Assume `Author` correctly overrides `equals()` and `hashCode()`:

```java
Set<Author> authors = new HashSet<>();

authors.add(new Author("Alice Chen", "Canada"));
authors.add(new Author("Alice Chen", "Canada"));

System.out.println(authors.size());
```

Output:

```text
1
```

The second equal author is simply not added as a second element.

Do not write a `try-catch` block to handle ordinary duplicates. Use the return value of `add()` if you want to know whether a new element was added:

```java
boolean added = authors.add(
    new Author("Alice Chen", "Canada")
);

if (!added) {
    System.out.println("That author is already in the set.");
}
```

### 18.4 HashMap lookup and null values

A `HashMap` permits one null key and null values. Therefore, `map.get(key)` returning null does not always tell you whether the key is absent or whether the key is present and mapped to null.

Use `containsKey()` when you need to distinguish those cases:

```java
import java.util.HashMap;
import java.util.Map;

Map<String, String> status = new HashMap<>();

status.put("A1", "Active");
status.put("B2", null);

System.out.println(status.get("A1"));
System.out.println(status.get("B2"));
System.out.println(status.containsKey("B2"));
System.out.println(status.containsKey("C3"));
```

Output:

```text
Active
null
true
false
```

This is not an exception. It is normal `HashMap` behaviour.

### 18.5 A custom hashCode() or equals() can itself fail

Hash-based collections call the key's `hashCode()` and may call `equals()` when checking keys.

If your own implementation dereferences a null field unsafely, collection operations can trigger a `NullPointerException`.

For example, this is risky:

```java
@Override
public int hashCode() {
    return name.hashCode();
}
```

If `name` is null, this throws a `NullPointerException`.

A null-safe alternative is:

```java
@Override
public int hashCode() {
    return Objects.hash(name, country);
}
```

This is another reason to implement `equals()` and `hashCode()` carefully and consistently.

**Important:** A hash collision is not an exception. Two unequal objects may have the same hash code. The collection uses equality checks to distinguish them.

---

## 21. Applying exception handling to Lecture 11 — recursion and the call stack

Lecture 11 introduced recursive methods, base cases, recursive progress, and the call stack.

Exception handling is especially useful when recursive methods receive invalid input. It also helps us understand why `StackOverflowError` is different from an ordinary exception.

### 19.1 Validate input before starting recursion

Recall the recursive sum method from Lecture 11:

```java
public static int sum(int x) {
    if (x == 0) {
        return 0;
    }

    return sum(x - 1) + (x - 1);
}
```

This method assumes `x` is non-negative.

What happens if someone calls:

```java
sum(-3);
```

The base case `x == 0` is never reached because each recursive call makes the value smaller:

```text
-3, -4, -5, -6, ...
```

The method eventually causes a `StackOverflowError`.

A better design validates the input before recursion begins:

```java
public static int sum(int x) {
    if (x < 0) {
        throw new IllegalArgumentException(
            "x must be non-negative."
        );
    }

    return sumHelper(x);
}

private static int sumHelper(int x) {
    if (x == 0) {
        return 0;
    }

    return sumHelper(x - 1) + (x - 1);
}
```

The public method validates the input. The private helper performs the recursion after the input has passed validation.

Test it:

```java
public static void main(String[] args) {
    try {
        System.out.println(sum(4));
        System.out.println(sum(-3));
    }
    catch (IllegalArgumentException e) {
        System.out.println("Invalid input: " + e.getMessage());
    }
}
```

Output:

```text
6
Invalid input: x must be non-negative.
```

The negative input is rejected before it can create an unbounded sequence of recursive calls.

### 19.2 Recursive ArrayList search: validate the index

Lecture 11 used recursive traversal and search through an `ArrayList`.

For example:

```java
public static int findStudent(
        ArrayList<Student> students, Student target, int index) {

    if (index == students.size()) {
        return -1;
    }

    if (students.get(index).equals(target)) {
        return index;
    }

    return findStudent(students, target, index + 1);
}
```

This method expects a valid starting index, normally `0`.

If a caller passes `-1`, the method attempts:

```java
students.get(-1)
```

and Java throws an `IndexOutOfBoundsException` (specifically, an `IndexOutOfBoundsException` for a negative index).

We can validate the starting index:

```java
public static int findStudent(
        ArrayList<Student> students, Student target, int index) {

    if (students == null) {
        throw new IllegalArgumentException(
            "Student list cannot be null."
        );
    }

    if (index < 0 || index > students.size()) {
        throw new IndexOutOfBoundsException(
            "Starting index is outside the list."
        );
    }

    if (index == students.size()) {
        return -1;
    }

    if (students.get(index).equals(target)) {
        return index;
    }

    return findStudent(students, target, index + 1);
}
```

The method now distinguishes two situations:

- `index == students.size()` is a valid stopping point meaning no elements remain.
- `index < 0` or `index > students.size()` is an invalid starting index.

### 19.3 Catch the invalid starting index

```java
try {
    int position = findStudent(students, searchStudent, -1);
    System.out.println("Found at: " + position);
}
catch (IndexOutOfBoundsException e) {
    System.out.println("Search could not start: " + e.getMessage());
}
```

Output:

```text
Search could not start: Starting index is outside the list.
```

### 19.4 StackOverflowError is an Error, not an ordinary Exception

Consider the incorrect recursive method:

```java
public static void count(int num) {
    System.out.println(num);
    count(num + 1);
}
```

The recursive call moves away from a stopping condition. Eventually, the call stack can run out of space and Java throws a `StackOverflowError`.

Recall the simplified hierarchy:

```text
Throwable
├── Error
│   └── StackOverflowError
└── Exception
    └── RuntimeException
```

A catch block such as:

```java
catch (Exception e) {
    System.out.println("Something went wrong.");
}
```

does **not** catch `StackOverflowError`, because `StackOverflowError` is an `Error`, not an `Exception`.

You could technically catch `StackOverflowError` because it is a `Throwable`, but that is not a normal recovery strategy. The call stack may already be exhausted, and the correct solution is to repair the recursive logic:

- Add a correct base case.
- Make each recursive call move toward that base case.
- Validate input before starting recursion.

**Teaching point:** Exception handling does not replace correct recursive design.

### 19.5 Integer overflow is not automatically an exception

The factorial method from Lecture 11 used `int`:

```java
public static int factorial(int num) {
    if (num == 0) {
        return 1;
    }

    return num * factorial(num - 1);
}
```

For sufficiently large values, the result exceeds the range of `int`. Java integer arithmetic normally wraps around; it does not automatically throw an exception for integer overflow.

If overflow must be detected, use checked arithmetic:

```java
public static int factorialChecked(int num) {
    if (num < 0) {
        throw new IllegalArgumentException(
            "Factorial input must be non-negative."
        );
    }

    if (num == 0) {
        return 1;
    }

    return Math.multiplyExact(
        num,
        factorialChecked(num - 1)
    );
}
```

`Math.multiplyExact()` throws `ArithmeticException` if the multiplication overflows.

Test:

```java
try {
    System.out.println(factorialChecked(5));
    System.out.println(factorialChecked(13));
}
catch (IllegalArgumentException | ArithmeticException e) {
    System.out.println("Factorial could not be calculated: "
            + e.getMessage());
}
```

The first call returns `120`. The second call throws `ArithmeticException` because `13!` is larger than the maximum `int` value.

---

## 22. `finally` — code that runs after try/catch

The `finally` block is useful when some cleanup action should happen whether the `try` block succeeds or an exception is handled.

### 20.1 Basic example

```java
public static void main(String[] args) {
    try {
        int result = 10 / 0;
        System.out.println(result);
    }
    catch (ArithmeticException e) {
        System.out.println("Cannot divide by zero.");
    }
    finally {
        System.out.println("End of calculation.");
    }
}
```

Output:

```text
Cannot divide by zero.
End of calculation.
```

If the divisor is changed to `2`, the output is:

```text
5
End of calculation.
```

The `finally` block normally executes whether the `try` block completes normally or a matching exception is caught.

### 20.2 When is finally useful?

Common uses include:

- Closing resources.
- Releasing a lock.
- Restoring a state after an operation.
- Performing cleanup that should happen on both success and failure.

For files, database connections, and similar resources, Java's try-with-resources statement is often preferred because it closes supported resources automatically. That topic can be introduced separately.

---

## 23. Integrated practice — exceptions across Lectures 7–11

Try each task before reading the answer. Identify which earlier lecture concept is involved and what exception-handling decision is required.

### Practice 21.1 — Employee validation

A student writes:

```java
Employee e = new Employee("Mina", -5000);
System.out.println(e.getSalary());
```

Assume the `Employee` constructor rejects negative salaries by throwing `IllegalArgumentException`.

**Questions:**

1. Does the object get created successfully?
2. Which exception is thrown?
3. What should the caller do?

**Answer:**

1. No. The constructor does not complete successfully.
2. `IllegalArgumentException`.
3. The caller can catch it and display a useful message, request corrected input, or reject the employee record.

Example:

```java
try {
    Employee e = new Employee("Mina", -5000);
    System.out.println(e.getSalary());
}
catch (IllegalArgumentException e) {
    System.out.println("Invalid employee: " + e.getMessage());
}
```

### Practice 21.2 — Polymorphic Flyable list

A list contains `Bird`, `Drone`, and `Airplane` objects implementing `Flyable`. One element is null.

**Question:** Why can `flyer.fly()` throw `NullPointerException`, even though the variable is declared as `Flyable`?

**Answer:** `Flyable` is the reference type, but the reference can still hold null. The interface type does not guarantee that an object exists. Check for null before calling the method.

### Practice 21.3 — Student GPA validation

A `Student` constructor accepts only GPA values between `0.0` and `4.0`.

What exception should it throw for a GPA of `4.5`?

**Answer:** `IllegalArgumentException`, because the caller supplied an argument outside the method's accepted range.

### Practice 21.4 — HashSet duplicate

A student adds the same logical `Author` twice to a correctly implemented `HashSet<Author>`.

Should the student use `try-catch` to handle the duplicate?

**Answer:** No. A duplicate is normal `HashSet` behaviour, not an exception. Use the boolean return value of `add()` if the program needs to know whether the element was newly added.

### Practice 21.5 — Recursive search

A recursive search method is called with index `-1`.

What is the problem, and how should it be handled?

**Answer:** A negative index is invalid. Validate the index before calling `ArrayList.get(index)` and throw `IndexOutOfBoundsException` or `IllegalArgumentException` according to the method's documented contract.

### Practice 21.6 — Recursive method never reaches its base case

A recursive method keeps calling itself with `num + 1` and eventually causes `StackOverflowError`.

Will `catch (Exception e)` handle it?

**Answer:** No. `StackOverflowError` extends `Error`, not `Exception`. Repair the base case and recursive progress instead of relying on a catch block.

### Practice 21.7 — Sorting students

A `Comparator<Student>` receives a null student reference and tries to call `getGpa()`.

What may happen?

**Answer:** A `NullPointerException` may occur. Prevent null entries in the list or define an explicit null-ordering policy in the comparator if null entries are part of the intended design.

---

## 24. AI-assisted practice — debug a small student-management program

Use AI as a debugging partner. First identify the problems yourself; then ask AI for hints and verify the corrected program by running it.

### Activity: Repair the program

Assume the course already has `Employee`, `Manager`, `Student`, and `Author` classes based on the earlier lectures.

A student submits this code:

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        ArrayList<Student> students = new ArrayList<>();

        students.add(new Student("Mina", 3.7));
        students.add(new Student("Ali", 4.5));

        Collections.sort(students);

        Student first = students.get(5);
        System.out.println(first);

        Set<Author> authors = new HashSet<>();
        authors.add(new Author("Alice Chen", "Canada"));
        authors.add(new Author("Alice Chen", "Canada"));

        System.out.println("Authors: " + authors.size());
    }
}
```

For this exercise, assume:

- `Student` rejects a GPA outside `0.0` to `4.0` using `IllegalArgumentException`.
- `Student` implements `Comparable<Student>`.
- `Author` correctly overrides `equals()` and `hashCode()`.

**Your tasks:**

1. Identify the first exception that occurs.
2. Explain why the sorting and array access are not reached.
3. Add a `try-catch` around student creation and sorting.
4. Explain why `students.get(5)` would be invalid if execution reached it.
5. Explain why adding two equal authors does not throw an exception.
6. Rewrite the program so the author set is still tested even if student creation fails.

### AI prompt for students

> I am learning Java exception handling and have studied inheritance, interfaces, Comparable, Comparator, equals(), hashCode(), HashSet, HashMap, and recursion. Do not give me the complete corrected program immediately. Ask me to identify which line throws first, then guide me through the exception flow. Help me separate invalid input, invalid collection index, and ordinary duplicate behaviour. After I attempt a solution, check it and explain each correction.

### Solution guide

The first exception occurs here:

```java
students.add(new Student("Ali", 4.5));
```

The `Student` constructor rejects the GPA and throws `IllegalArgumentException`.

Therefore:

- `Collections.sort(students)` is not reached.
- `students.get(5)` is not reached.
- The author set is not created in the original program because execution has already left `main()` due to the uncaught exception.

A corrected version can isolate the student operation so that the author test still runs:

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        ArrayList<Student> students = new ArrayList<>();

        try {
            students.add(new Student("Mina", 3.7));
            students.add(new Student("Ali", 4.5));

            Collections.sort(students);

            System.out.println(students);
        }
        catch (IllegalArgumentException e) {
            System.out.println(
                "Student data error: " + e.getMessage()
            );
        }

        Set<Author> authors = new HashSet<>();

        authors.add(new Author("Alice Chen", "Canada"));
        boolean addedAgain =
                authors.add(new Author("Alice Chen", "Canada"));

        System.out.println("Second Alice added? " + addedAgain);
        System.out.println("Authors: " + authors.size());
    }
}
```

Expected output, assuming the constructor message shown in Section 17:

```text
Student data error: GPA must be between 0.0 and 4.0.
Second Alice added? false
Authors: 1
```

The invalid student data is handled, and execution continues after the catch block. The `HashSet` test then runs.

If the student also wants to demonstrate an invalid index, it should be a separate test:

```java
try {
    Student first = students.get(5);
    System.out.println(first);
}
catch (IndexOutOfBoundsException e) {
    System.out.println("Invalid student-list index.");
}
```

**Teaching points:**

- A constructor can throw `IllegalArgumentException` to enforce class rules.
- A `try-catch` handles the exception at the level where the program can decide what to do next.
- An invalid `ArrayList` index causes an index exception.
- `Comparable` defines ordering; it does not validate the index used by `get()`.
- `HashSet` uses `equals()` and `hashCode()` to recognize duplicates. A duplicate does not throw an exception.
- Exception handling should be narrow enough that unrelated operations can continue when appropriate.

---

## 25. Final summary — exception handling across the course

| Earlier lecture | Familiar concept | Exception-handling connection |
|---|---|---|
| Lecture 7 | `Employee`, `Manager`, inheritance, polymorphism | Validate constructor arguments; a subclass can also reject invalid subclass-specific data |
| Lecture 8 | `Flyable`, `Bird`, `Drone`, `Airplane`, polymorphic collections | A polymorphic method implementation may throw an exception; null references must be checked |
| Lecture 9 | `Student`, `Comparable`, `Comparator`, scholarship ranking | Validate GPA and reject invalid comparison data; sorting does not protect against null references |
| Lecture 10 | `Author`, `equals()`, `hashCode()`, `HashSet`, `HashMap` | Use null-safe equality and hashing; duplicates are normal collection behaviour, not exceptions |
| Lecture 11 | Recursion, `ArrayList` traversal/search, call stack | Validate input and indexes; understand why a missing base case may cause `StackOverflowError` |

Remember these eight rules:

1. **Validate data at the boundary.** Constructors and public methods should reject invalid arguments with a meaningful exception when appropriate.
2. **Use specific exceptions.** `IllegalArgumentException` is suitable for an invalid argument; index exceptions describe invalid collection positions.
3. **Catch exceptions where you can respond meaningfully.** The method that detects a problem does not always need to handle it.
4. **Do not use exceptions for ordinary conditions.** A duplicate in a `HashSet` is not an exception; use the collection's return value.
5. **Do not confuse polymorphism with exception handling.** Dynamic dispatch chooses the method implementation; exceptions describe failures during execution.
6. **Keep comparison and equality contracts intact.** `compareTo()`, `Comparator`, `equals()`, and `hashCode()` each have their own responsibility.
7. **Validate recursive input before starting recursion.** A bad base case or a call that moves away from it can cause `StackOverflowError`.
8. **Remember that `Error` and `Exception` are different.** `catch (Exception e)` does not catch `StackOverflowError`.

**Main takeaway:** Exception handling is not an isolated Java topic. It protects the object-oriented programs you have already built. Constructors validate objects, polymorphic methods can report failed operations, sorting depends on valid data, hash-based collections depend on correct equality methods, and recursive methods need input validation as well as a correct base case.

## Further reading

- [Java `Throwable` API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Throwable.html)
- [Java `Exception` API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Exception.html)
- [Java `RuntimeException` API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/RuntimeException.html)
- [Java `IllegalArgumentException` API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/IllegalArgumentException.html)
- [Java `Math.multiplyExact()` API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Math.html#multiplyExact(int,int))
- [Java `try` statement — Java Language Specification](https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html#jls-14.20)
- [Java `throw` statement — Java Language Specification](https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html#jls-14.18)
