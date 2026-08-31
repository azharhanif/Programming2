# Programming 2 — Lecture 11: Recursion

## Learning objectives

You should be able to:

- define recursion;
- identify a base case;
- identify the recursive case;
- trace recursive calls;
- explain the call stack;
- distinguish recursion from infinite recursion;
- write recursive methods on numbers, Strings, arrays, and object structures;
- recognize when recursion is a good design choice.

---

## 1. What is recursion?

A recursive method calls itself.

```java
public static void countdown(int n) {

    if (n == 0) {
        return;
    }

    System.out.println(n);
    countdown(n - 1);
}
```

Calling:

```java
countdown(3);
```

produces:

```text
3
2
1
```

---

## 2. Every useful recursion needs two ideas

### Base case

When should recursion stop?

```java
if (n == 0) {
    return;
}
```

### Recursive case

How does the problem become smaller?

```java
countdown(n - 1);
```

Think:

```text
recursive problem
      ↓
smaller problem
      ↓
smaller problem
      ↓
base case
```

---

## 3. Trace a sum

```java
public static int sum(int n) {

    if (n == 0) {
        return 0;
    }

    return n + sum(n - 1);
}
```

For:

```java
sum(4)
```

we get:

```text
sum(4)
= 4 + sum(3)
= 4 + 3 + sum(2)
= 4 + 3 + 2 + sum(1)
= 4 + 3 + 2 + 1 + sum(0)
= 10
```

---

## 4. Call stack

During recursive calls, Java keeps unfinished method calls on the call stack.

For:

```java
sum(3)
```

the stack grows:

```text
sum(3)
sum(2)
sum(1)
sum(0)
```

Then returns happen backward:

```text
sum(0) → 0
sum(1) → 1
sum(2) → 3
sum(3) → 6
```

---

## 5. Infinite recursion

This is bad:

```java
public static int f(int n) {
    return f(n - 1);
}
```

There is no base case.

Eventually the call stack becomes exhausted, producing a `StackOverflowError`.

---

## 6. Recursion and loops

Many simple repetitions can be written with loops:

```java
for (int i = 1; i <= n; i++) {
    sum += i;
}
```

Recursion becomes particularly useful when the problem itself is recursive, such as:

- tree traversal;
- directory structures;
- nested objects;
- divide-and-conquer algorithms;
- recursive mathematical definitions.

---

## 7. Recursive String example

Count a character:

```java
public static int countChar(String s, char target) {

    if (s.length() == 0) {
        return 0;
    }

    int first = s.charAt(0) == target ? 1 : 0;

    return first + countChar(s.substring(1), target);
}
```

Each call removes one character.

---

## 8. Recursive thinking

When writing recursion, ask:

1. What is the smallest problem?
2. What is the answer to that smallest problem?
3. How can I reduce the current problem to a smaller version?
4. Does every recursive call move toward the base case?

---

# Practice

## Practice 1

What is wrong?

```java
public static void f(int n) {
    System.out.println(n);
    f(n);
}
```

### Answer

The recursive argument never changes. There is no path toward a base case. This eventually causes stack overflow.

---

## Practice 2

What does this return?

```java
public static int f(int n) {

    if (n <= 0) {
        return 0;
    }

    return n + f(n - 1);
}
```

for:

```java
f(4)
```

### Answer

```text
10
```

---

## Practice 3 — tricky

What does this print?

```java
public static void f(int n) {

    if (n == 0) {
        return;
    }

    f(n - 1);
    System.out.println(n);
}
```

for:

```java
f(3);
```

### Answer

```text
1
2
3
```

The recursive call happens before the print, so printing occurs during the return phase.

---

## Practice 4

Write a recursive method that counts down from `n` to 1.

### Answer

```java
public static void countdown(int n) {

    if (n <= 0) {
        return;
    }

    System.out.println(n);
    countdown(n - 1);
}
```

---

# AI-assisted practice

Ask AI:

> Write a recursive method that searches for a value in an integer array.

Then test:

- empty array;
- one-element array;
- target at first position;
- target at last position;
- target absent.

Explain exactly what makes the recursive call smaller.
