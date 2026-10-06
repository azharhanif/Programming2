# Programming 2 — Lab 6
## Comparable and Comparator

**Estimated time:** 55–70 minutes  
**Topics:** `Comparable`, `compareTo()`, `Comparator`, `compare()`, `Collections.sort()`, tie-breaking, fixed vs flexible ordering

### Lab goal

You will build a **college course registration list**.

The important design question is not just "How do I sort?"

It is:

> Which sorting rule belongs to the object itself, and which rules should remain external?

Your program will use one default ordering and several optional orderings.

---

# Part 1 — Create Course

Create:

```java
public class Course
```

with:

```text
code
name
credits
```

Use suitable access control.

Create a constructor and getters.

Override `toString()` so a course can be displayed clearly.

Example:

```text
420-SN1-RE - Programming in Science - 3 credits
```

### Basic condition

Create at least six courses.

### Tricky condition

At least two courses must have the same number of credits, and at least two courses must have similar names or course codes. This will make your sorting tests meaningful.

---

# Part 2 — Give Course One Natural Ordering

Make `Course` implement:

```java
Comparable<Course>
```

The natural ordering should be based on:

```text
course code
```

Use `compareTo()`.

Example:

```java
@Override
public int compareTo(Course other) {
    return this.code.compareTo(other.code);
}
```

### Basic condition

This must work:

```java
Collections.sort(courses);
```

### Tricky condition

Do not create a `Comparator` for the natural ordering.

The purpose of `Comparable` here is to give `Course` **one default ordering**.

---

# Part 3 — Test Natural Ordering

Create an `ArrayList<Course>` and print:

```text
Before sorting
...
After sorting by course code
...
```

Use:

```java
Collections.sort(courses);
```

### Think before coding

Why does Java know what "smaller" means for `Course` after you implement `Comparable<Course>`?

Write a one- or two-sentence comment in your program.

---

# Part 4 — Create a Credits Comparator

Create:

```java
public class CreditsComparator implements Comparator<Course>
```

Sort courses by:

```text
credits ascending
```

Use:

```java
Collections.sort(courses, new CreditsComparator());
```

### Basic condition

A 2-credit course must appear before a 3-credit course.

### Tricky condition

Do not modify `compareTo()` to sort by credits.

The course's natural ordering must remain course code.

---

# Part 5 — Create a Name Comparator

Create:

```java
public class CourseNameComparator implements Comparator<Course>
```

Sort alphabetically by course name.

Test:

```java
Collections.sort(courses, new CourseNameComparator());
```

### Tricky condition

Two different courses may have names beginning with the same word.

Your comparator must still produce a valid ordering.

---

# Part 6 — Tie-Breaking Comparator

Create:

```java
public class CreditsThenNameComparator implements Comparator<Course>
```

Rules:

1. Sort by credits ascending.
2. If two courses have the same number of credits, sort those courses by name alphabetically.

Example:

```text
2-credit Biology
2-credit Chemistry
3-credit Programming
3-credit Statistics
```

### Tricky condition

Do not return `0` merely because the credits are equal.

If credits are equal, you must compare the names.

---

# Part 7 — Advanced Understanding Challenge

Add a method:

```java
public static void printCourses(ArrayList<Course> courses)
```

Then demonstrate all three ideas without changing the `Course` class again:

```text
1. Natural order       -> course code
2. Credits order       -> credits
3. Credits + name     -> credits, then name
```

Finally, add a comment answering:

```text
If Course already implements Comparable, why do we still need Comparator?
```

Your answer must explain the difference between:

```text
Comparable -> one default ordering
Comparator -> additional / alternative ordering
```

---

# Final Checklist

- [ ] `Course` implements `Comparable<Course>`.
- [ ] `compareTo()` sorts by course code.
- [ ] `Collections.sort(courses)` works.
- [ ] CreditsComparator works.
- [ ] CourseNameComparator works.
- [ ] CreditsThenNameComparator uses a tie-breaker.
- [ ] You did not modify `compareTo()` for every new sorting requirement.
- [ ] You can explain why Comparator is useful even when Comparable exists.
