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
                 Throwable
                /         \
             Error      Exception
                           |
                    RuntimeException
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

### 1.4 What is exception handling?

**Exception handling** is the process of responding to an exception so that the program can deal with the problem in a controlled way.

Instead of allowing an exception to stop the program immediately, we can provide a handler.

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

A `try` statement must have at least one `catch` block or a `finally` block. This lecture focuses mainly on `try-catch`; `finally` is introduced later in this section.

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

### 7.5 About `System.exit(1)`

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

### Teacher solution guide

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

## 16. Final summary

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

## Further reading

- [Java `Throwable` API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Throwable.html)
- [Java `Exception` API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Exception.html)
- [Java `RuntimeException` API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/RuntimeException.html)
- [Java `try` statement — Java Language Specification](https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html#jls-14.20)
- [Java `throw` statement — Java Language Specification](https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html#jls-14.18)
