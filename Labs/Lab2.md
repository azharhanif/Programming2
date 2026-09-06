# Lab 2 — From Arrays to Objects and ArrayList (group of 2 students allowed)

**Programming 2 — New Semester**  
**Estimated time: 75–90 minutes for the core lab + 30–45 minutes for the design challenge**  
**Submission:** Java project + JUnit tests + design responses

---

# 1. Purpose

Lab 1 reviewed Java mechanics.

Lab 2 begins the transition into **Programming 2 thinking**:

```text
single value
     ↓
array
     ↓
array of objects
     ↓
ArrayList
     ↓
class that manages a collection
     ↓
design decisions
```
# 2. Learning Objectives

You should be able to:

- traverse arrays;
- handle negative values and boundary cases;
- distinguish an array reference from an array object;
- explain shallow/reference copying;
- create classes representing objects;
- create arrays of objects;
- use `ArrayList`;
- search a collection of objects;
- modify an object through a reference;
- design a collection-management class;
- write tests for empty and non-empty collections;
- justify a data-structure/design choice.

---

# 3. Part A — Array Warm-Up

Create:

```java
public static int findHighest(int[] values)
```

Return the largest value.

Examples:

```text
{8, 3, 12, 5} → 12
{5}           → 5
{-4,-8,-2}    → -2
```

## Important

Do not assume the values are positive.

### Predict

What is wrong with:

```java
int highest = 0;
```

if the array is:

```text
{-10, -4, -20}
```

---

# 4. Part B — The Reference Trap

Run this code only after making a prediction:

```java
int[] a = {10, 20, 30};
int[] b = a;

b[0] = 99;

System.out.println(a[0]);
System.out.println(b[0]);
```

Answer:

1. What prints?
2. Why?
3. Are `a` and `b` two arrays?
4. Draw a small reference diagram.
5. How would you create an independent copy?

Then use:

```java
Arrays.copyOf(...)
```

to implement the independent copy.

---

# 5. Part C — Student Class

Create:

```java
public class Student
```

with:

```java
private String id;
private String name;
private double average;
```

Create:

```java
public Student(
        String id,
        String name,
        double average)
```

and getters.

Add:

```java
public void setAverage(double average)
```

and:

```java
@Override
public String toString()
```

Example:

```text
V123456 - Amina Rahman - 86.5
```

---

# 6. Part D — Protect the Object's State

A student's average must satisfy:

```text
0.0 <= average <= 100.0
```

Decide what should happen if someone tries:

```java
student.setAverage(-5);
```

or:

```java
student.setAverage(105);
```

### Recommended design question

Would you:

- silently ignore the value?
- store it anyway?
- throw an exception?

---

# 7. Part E — Array of Objects

Create:

```java
Student[] students = {
    new Student("V100001", "Amina", 86.5),
    new Student("V100002", "Daniel", 72.0),
    new Student("V100003", "Sofia", 91.0)
};
```

Write:

```java
public static double calculateAverage(
        Student[] students)
```

and:

```java
public static Student findStudent(
        Student[] students,
        String id)
```

Return `null` if the ID is not found.

### Test

Find:

```text
V100002
```

Then search for:

```text
V999999
```

Explain the difference between the two results.

---

# 8. Part F — Move to ArrayList

Now create:

```java
ArrayList<Student> students =
        new ArrayList<>();
```

Add at least five students.

Write:

```java
public static Student findStudent(
        ArrayList<Student> students,
        String id)
```

and:

```java
public static int countAbove(
        ArrayList<Student> students,
        double threshold)
```

---

# 9. Part G — Design Choice: Array or ArrayList?

Choose one and justify it.

### G1
A college laboratory always contains exactly 30 computer stations.

### G2
A student club can gain or lose members throughout the semester.

### G3
A program reads student records until the user enters:

```text
DONE
```

### G4
A program stores the seven days of the week.

### G5
A program stores all students enrolled in a course, but the enrollment limit may change next year.

Your answer must mention **why**, not merely say "ArrayList."

---

# 10. Part H — Object Reference Challenge

Consider:

```java
Student a =
    new Student("V100001", "Amina", 80);

Student b = a;

b.setAverage(95);
```

Answer before running:

1. What is `a.getAverage()`?
2. What is `b.getAverage()`?
3. Why?
4. Are `a` and `b` two Student objects?
5. How would you create an independent Student object?

---

# 11. Part I — StudentRegistry

Create:

```java
public class StudentRegistry
```

with:

```java
private ArrayList<Student> students;
```

Constructor:

```java
public StudentRegistry()
```

Implement:

```java
public void addStudent(Student student)

public Student findStudent(String id)

public boolean removeStudent(String id)

public double calculateAverage()

public Student findHighestAverage()

public int countAbove(double threshold)

@Override
public String toString()
```

---

# 12. Part J — Responsibility Challenge

Do **not** put the registry logic inside `main()`.

Bad design:

```java
public static void main(String[] args) {

    // 100 lines of searching,
    // removing and calculating...
}
```

Better design:

```java
StudentRegistry registry =
        new StudentRegistry();

registry.addStudent(...);

Student s =
        registry.findStudent("V100001");

System.out.println(s);
```

### Question

Why is the second design easier to maintain?

---

# 13. Part K — Tricky Removal Requirement

Implement:

```java
public boolean removeStudent(String id)
```

Rules:

- remove the student whose **ID** matches;
- return `true` if removal occurred;
- return `false` if no student matched.

Do not remove by name.

### Test:

```text
removeStudent("V100001")
removeStudent("V999999")
```

What should each return?

---

# 14. Part L — JUnit Testing

Create tests for:

- adding a student;
- finding an existing student;
- finding a missing student;
- removing an existing student;
- removing a missing student;
- average calculation;
- highest-average student;
- empty registry;
- invalid average.

At least one test must be a boundary/edge case.

---


# 15. Submission Checklist

- [ ] Array challenge
- [ ] reference/copy explanation
- [ ] `Student`
- [ ] validation strategy
- [ ] array of objects
- [ ] ArrayList methods
- [ ] design-choice questions
- [ ] `StudentRegistry`
- [ ] JUnit tests

---

# 19. Suggested Lab Grading

| Component | Marks |
|---|---:|
| Array reasoning | 7 |
| Reference/copy | 7 |
| Student class | 10 |
| State validation | 7 |
| Array of objects | 8 |
| ArrayList | 8 |
| Design choices | 8 |
| StudentRegistry | 15 |
| JUnit tests | 10 |

| **Total** | **80** |
