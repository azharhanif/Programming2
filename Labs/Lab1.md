# Lab 1 — Java Review: Student Access Validator

**Programming 2 — New Semester**  
**Estimated time: 70–90 minutes for the core lab + 30–45 minutes for challenge/testing**  
**Submission:** Java project + JUnit tests + short reflection

---

# 1. Why are we doing this lab?

Programming 2 assumes that you remember Java syntax from Programming 1.

However, this semester we will deliberately practice a different programming habit:

> **Do not merely make the program run. Be able to explain why it works.**

For every substantial exercise, you will practice:

```text
Read the requirement
      ↓
Predict
      ↓
Plan
      ↓
Code
      ↓
Test
      ↓
Debug
      ↓
Explain
      ↓
Modify
```

This lab reviews:

- methods;
- parameters and return values;
- `String`;
- `Character`;
- loops;
- Boolean flags;
- `Random`;
- conditional logic;
- debugging;
- JUnit testing.

It also introduces the course's approach to responsible AI-assisted programming.

---

# 2. Learning Objectives

By the end of this lab you should be able to:

- write and call methods;
- distinguish parameters from arguments;
- trace a method call;
- use `String` and `Character` methods;
- use Boolean flags;
- validate input;
- identify off-by-one and stray-semicolon bugs;
- write JUnit tests;
- test boundary cases;
- explain code produced or suggested by an AI tool;
- modify an existing solution rather than starting from scratch.

---

# 3. Part A — Predict Before You Run

Do **not** run this code until you have answered the questions.

```java
String password = "Vanier2026";

boolean upper = false;
boolean lower = false;
boolean digit = false;

for (int i = 0; i < password.length(); i++) {
    char c = password.charAt(i);

    if (Character.isUpperCase(c))
        upper = true;
    else if (Character.isLowerCase(c))
        lower = true;
    else if (Character.isDigit(c))
        digit = true;
}

System.out.println(upper);
System.out.println(lower);
System.out.println(digit);
System.out.println(upper && lower && digit);
```

## Questions

### A1
What are the four lines printed?

### A2
Why is `charAt(i)` needed?

### A3
Why do we use `< password.length()` rather than `<= password.length()`?

### A4
What would happen if `password` were `null`?

### A5
Would `"VANIER2026"` pass the test? Explain.

### A6
Would `"vanier2026"` pass? Explain.

> **Instructor checkpoint:** Be prepared to explain your answer before continuing.

---

# 4. Part B — Method Review

Create:

```java
public static int countDigits(String text)
```

The method returns the number of digit characters in `text`.

Examples:

```text
countDigits("Vanier2026") → 4
countDigits("Java")       → 0
countDigits("P2 Lab 1")   → 2
```

## Requirements

- Use a loop.
- Use `Character.isDigit()`.
- Do not convert the entire String to an integer.
- Return the count.

## Before coding

Write down:

1. What should the accumulator be initially?
2. What condition increments it?
3. When does the method return?

---

# 5. Part C — Password Validator

Create:

```java
public static boolean isValidPassword(String password)
```

A password is valid when:

1. it contains at least one uppercase letter;
2. it contains at least one lowercase letter;
3. it contains at least one digit;
4. it contains at least 8 characters.

Examples:

```text
"Vanier2026" → true
"vanier2026" → false
"VANIER2026" → false
"Vanier"     → false
"Vanier!!"   → false
```

## Suggested design

You may use:

```java
boolean upper = false;
boolean lower = false;
boolean digit = false;
```

and separately check the length.

### Think first

Why are Boolean flags better here than counting every uppercase/lowercase/digit character?

---

# 6. Part D — Debugging Challenge

The following method is supposed to count uppercase letters.

```java
public static int countUppercase(String text) {
    int count = 0;

    for (int i = 0; i <= text.length(); i++) {
        if (Character.isUpperCase(text.charAt(i)));
            count++;
    }

    return count;
}
```

## Your task

Find **every** problem you can identify.

At minimum, consider:

- loop boundary;
- `if` statement;
- String indexing;
- whether the result is logically correct.

Then correct the method.

### Required tests

Test at least:

```text
"Vanier"
"VANIER"
"vanier"
"V1A2"
""
```

For each test, record the expected result.

---

# 7. Part E — Random Access Code

Create:

```java
public static String generateCode()
```

Generate a four-character access code.

Each character must be one of:

```text
A B C D E
```

Examples:

```text
ABCE
DDAA
BECD
```

Any valid combination is acceptable.

## Hint

```java
Random rand = new Random();

int index = rand.nextInt(5);
```

You may use:

```java
String allowed = "ABCDE";
```

and select one character at a time.

## Important

Do not write a test such as:

```java
assertEquals("ABCD", generateCode());
```

The method is random.

Instead, test properties:

- length is 4;
- every character is allowed.

---

# 8. Part F — JUnit Testing

Create JUnit tests for:

```java
countDigits()
isValidPassword()
countUppercase()
generateCode()
```

For each deterministic method, test:

- at least one normal case;
- at least one boundary/edge case;
- at least one case where the answer is false/zero.

For `generateCode()`, test properties instead of one exact value.

---

# 9. Part G — Explain Existing Code

Consider:

```java
public static boolean containsDigit(String text) {
    for (int i = 0; i < text.length(); i++) {
        if (Character.isDigit(text.charAt(i)))
            return true;
    }

    return false;
}
```

Answer:

### G1
Why can the method return immediately?

### G2
What happens if the first character is a digit?

### G3
What happens if there are no digits?

### G4
Why is a counter unnecessary?

### G5
What would happen for `""`?

---

# 10. Part H — Main Challenge: Student Access Validator

Create:

```java
public class StudentAccessValidator
```

with these methods:

```java
public static boolean isValidStudentId(String id)

public static boolean isValidPassword(String password)

public static String generateAccessCode()

public static boolean isValidAccess(
        String id,
        String password)
```

## Student ID rules

A valid ID:

- contains exactly 7 characters;
- begins with uppercase `V`;
- contains exactly 6 digits after `V`.

Examples:

```text
V123456 → valid
V000001 → valid

v123456 → invalid
V12345  → invalid
V1234567 → invalid
A123456 → invalid
V12345A → invalid
```

## Password rules

Use the rules from Part C.

## Access rule

`isValidAccess()` returns `true` only when:

```text
valid ID AND valid password
```

---

# 11. Part I — Extension Challenge

Add:

```java
public static boolean verifyAccessCode(
        String generatedCode,
        String enteredCode)
```

The comparison should be case-sensitive.

Then modify your design so a complete access attempt requires:

```text
valid student ID
+
valid password
+
correct access code
```

Do not put all logic in `main()`.

---

# 12. Part J — Code Review

Choose one of your methods and answer:

1. What is its input?
2. What is its output?
3. What assumptions does it make?
4. What is one edge case?
5. What is one possible future modification?
6. What test would catch a likely bug?

---

# 13. Responsible AI Activity

You may use an AI assistant for this activity.

Ask it to review your `countUppercase()` method and identify possible bugs.

Then:

1. compare the AI's answer with your own analysis;
2. verify every suggestion by running tests;
3. accept, reject, or modify the suggestions;
4. explain one suggestion that was useful;
5. explain one suggestion that you did **not** blindly accept.

### Rule

You are responsible for the submitted code.

If you cannot explain a method, you do not yet own the solution.

---

# 14. Submission Checklist

- [ ] Part A predictions completed
- [ ] `countDigits()` implemented
- [ ] `isValidPassword()` implemented
- [ ] debugging challenge corrected
- [ ] random access code implemented
- [ ] JUnit tests included
- [ ] Student Access Validator completed
- [ ] extension attempted
- [ ] code review completed
- [ ] AI activity/reflection completed
- [ ] code compiles and tests pass

---

# 15. Suggested Lab Grading

| Component | Marks |
|---|---:|
| Prediction and explanation | 8 |
| `countDigits()` | 7 |
| Password validator | 10 |
| Debugging | 10 |
| Random code + property testing | 8 |
| JUnit quality | 8 |
| Student Access Validator | 15 |
| Extension | 7 |
| Explanation/AI reflection | 7 |
| **Total** | **80** |

The goal is not to reward typing speed. Correct reasoning, testing, debugging, and explanation are part of the programming work.
