# Exception Handling Examples — AI-Aware Redesign

**Repository area:** `Lectures/CodeExamplesInLectures/ExceptionHandleInOOP_Lab`

The repository's current exception-handling bundle explicitly covers Java's default handler, `try-catch`, `throws`, checked versus unchecked exceptions, user-defined exceptions, and polymorphism-friendly designs such as Animal, Account, Vehicle, and Student. It includes starter and solution source trees. citeturn1view2turn3view3

## Activity A — predict the failure

Before running the example:

1. identify the statement that can fail;
2. name the likely exception;
3. decide whether it is checked or unchecked;
4. predict whether the exception is caught locally or propagates.

## Activity B — AI-assisted first implementation

Ask AI:

> Add validation to the existing OOP example. Invalid data should cause an appropriate exception with a useful message.

Students must identify whether the AI chose:

```java
throw
```

or:

```java
throws
```

and explain why.

## Activity C — human modification

Create a domain-specific exception.

Example:

```java
class InvalidBalanceException extends Exception {
    public InvalidBalanceException(String message) {
        super(message);
    }
}
```

Then decide whether the method should declare:

```java
throws InvalidBalanceException
```

## Activity D — exception design

Students must compare:

```java
catch (Exception e)
```

with:

```java
catch (NumberFormatException e)
```

and explain why the specific exception is usually preferable when the failure is known.

## Tricky challenge

What is wrong with:

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

**Answer:** The `IOException` catch is unreachable because `Exception` already catches it.

## AI ownership check

Students must identify one place where the AI's exception handling is too broad, too narrow, or unnecessary, and improve it.
