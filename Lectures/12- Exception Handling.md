# Programming 2 — Lecture 12: Exception Handling

## Learning objectives

You should be able to:

- distinguish normal program logic from exceptional situations;
- identify common Java exceptions;
- use `try`, `catch`, and `finally`;
- catch specific exception types;
- explain checked versus unchecked exceptions;
- use `throw` to create an exception;
- use `throws` to declare a method's checked exception;
- understand exception propagation;
- design useful exception messages.

---

## 1. What is an exception?

An exception represents an unusual condition during program execution that interrupts the normal flow.

Examples:

```java
int x = 10 / 0;
```

can cause:

```text
ArithmeticException
```

and:

```java
String s = null;
s.length();
```

can cause:

```text
NullPointerException
```

---

## 2. try/catch

```java
try {
    int result = 10 / 0;
}
catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero.");
}
```

The exception is caught and handled.

---

## 3. Catch specific exceptions

Prefer:

```java
catch (ArithmeticException e)
```

over:

```java
catch (Exception e)
```

when you know the expected problem.

Specific catches communicate intent and reduce accidental handling of unrelated problems.

---

## 4. Multiple catches

```java
try {
    ...
}
catch (NumberFormatException e) {
    ...
}
catch (IOException e) {
    ...
}
```

More specific exception types should be caught before broader types.

This is invalid:

```java
catch (Exception e) { ... }
catch (IOException e) { ... } // unreachable
```

because `Exception` already catches IOException.

---

## 5. finally

`finally` is used for cleanup that should occur whether an exception happens or not.

```java
try {
    ...
}
catch (Exception e) {
    ...
}
finally {
    System.out.println("Finished.");
}
```

For file/resource handling, modern Java often uses try-with-resources instead.

---

## 6. Checked versus unchecked exceptions

A practical distinction:

### Checked

The compiler requires handling or declaration.

Examples include many `IOException` cases.

```java
public void readFile() throws IOException {
    ...
}
```

### Unchecked

Subclasses of `RuntimeException`.

Examples:

```text
NullPointerException
ArithmeticException
IllegalArgumentException
IndexOutOfBoundsException
```

The compiler does not require a catch or `throws` declaration.

---

## 7. `throw` versus `throws`

### `throw`

Actually creates/throws an exception:

```java
if (score < 0) {
    throw new IllegalArgumentException("Score cannot be negative.");
}
```

### `throws`

Declares that a method may pass an exception to its caller:

```java
public void readFile() throws IOException {
    ...
}
```

Think:

```text
throw  → do it now
throws → announce the possibility
```

---

## 8. Exception propagation

If a method does not catch an exception, the exception can move to its caller.

```text
method A
   ↓ calls
method B
   ↓ calls
method C
   ↓
exception
   ↑
propagates back
```

A caller may catch it.

---

## 9. Good exception messages

Bad:

```java
throw new IllegalArgumentException("error");
```

Better:

```java
throw new IllegalArgumentException(
    "Age must be between 0 and 120: " + age
);
```

The message should help diagnose the problem.

---

## 10. Don't use exceptions for ordinary control flow

This is poor design:

```java
try {
    int value = list.get(100);
}
catch (IndexOutOfBoundsException e) {
    // normal search logic
}
```

If you can check the condition normally, do so.

Exceptions should represent exceptional conditions, not routine branching.

---

# Practice

## Practice 1

What happens?

```java
try {
    int x = 10 / 0;
}
catch (ArithmeticException e) {
    System.out.println("Caught");
}
```

### Answer

It prints:

```text
Caught
```

---

## Practice 2

Which is more appropriate?

```java
catch (Exception e)
```

or:

```java
catch (NumberFormatException e)
```

when parsing an integer fails?

### Answer

`NumberFormatException` is more specific and communicates the intended problem.

---

## Practice 3

Explain:

```java
throw new IllegalArgumentException("Invalid age");
```

### Answer

The statement creates and throws an `IllegalArgumentException` with the supplied message.

---

## Practice 4 — tricky

What is wrong?

```java
try {
    ...
}
catch (Exception e) {
    ...
}
catch (IOException e) {
    ...
}
```

### Answer

The `IOException` catch is unreachable because `Exception` already catches it.

---

## Practice 5

Write a method that rejects negative scores.

### Answer

```java
public static void validateScore(int score) {

    if (score < 0) {
        throw new IllegalArgumentException(
            "Score cannot be negative: " + score
        );
    }
}
```

---

# AI-assisted practice

Ask AI:

> Write a method that reads an integer from the user and keeps asking until a valid integer is entered.

Then critique:

- Is exception handling being used appropriately?
- Which exception is caught?
- Is the Scanner state correct?
- Does the loop terminate?
- What happens at EOF?
- Is the error message useful?
