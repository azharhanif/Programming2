# Recursion Examples — AI-Aware Redesign

**Repository areas:** `RecursionLecturePractice` and `RecursionInOOP_Lab`

The repository includes a separate recursion practice project and a redesigned OOP recursion bundle. The latter explicitly emphasizes base cases, a shrinking general pattern, helper methods with an `index`, and familiar OOP examples such as Animal, Vehicle, and Bank. citeturn2view2turn3view2

## Activity A — trace first

For every recursive method, students identify:

```text
Base case:
What becomes smaller:
Recursive call:
What happens during return:
```

## Activity B — stack prediction

Given:

```java
f(3)
```

draw:

```text
f(3)
f(2)
f(1)
f(0)
```

Then draw the return order.

## Activity C — AI-assisted first implementation

Ask AI:

> Write a recursive method that searches an array using an index parameter. Do not use loops.

Students must compare the result with the repository's helper-method pattern.

## Activity D — human modification

Change the task:

> Return the number of occurrences of the target rather than a boolean.

The recursive structure should remain visible.

## Activity E — OOP recursion

Using an existing familiar object model, ask AI for a recursive method that processes a collection of objects.

Students must remove any loop from the recursive method if the task requires recursion.

## Tricky challenge

What is wrong with:

```java
int g(int n) {
    if (n <= 0) return 0;
    return g(n - 1);
}
```

**Answer:** It terminates, but it discards the current value. It always returns 0. A recursive solution must combine the current problem with the result of the smaller problem when the specification requires an accumulated result.

For example:

```java
return n + g(n - 1);
```

## AI ownership check

Students must point to:

1. the base case;
2. the progress toward the base case;
3. the work performed before/after recursion;
4. one test that would fail if the base case were wrong.
