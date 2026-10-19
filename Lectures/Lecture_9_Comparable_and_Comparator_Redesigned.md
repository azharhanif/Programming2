# Lecture 9 — Comparable and Comparator

## Learning goals

By the end of this lecture, you should be able to:
1. Explain why Java can sort `Integer` and `String` objects without extra instructions.
2. Implement `Comparable<T>` and override `compareTo()`.
3. Create multiple `Comparator<T>` rules for the same class.
4. Trace comparisons, handle ties, and test edge cases.
5. Explain why comparison methods should avoid subtraction and arbitrary numeric weights.

## 1. The problem: what does “smaller” mean?

### Concept
`Comparable` is a Java interface, and `compareTo()` is the abstract method declared inside that interface.

Java provides the `Comparable<T>` interface as part of the Java Class Library, in the java.lang package.

Conceptually, its definition looks like this:
```
public interface Comparable<T> {
    public abstract int compareTo(T other);
}
```
Interface methods are implicitly public and abstract in Java, unless they are declared as default, static, or private methods with a body.

The definition is equivalent to writing:
```
public interface Comparable<T> {
    int compareTo(T other);
}
```
Here, `T` is a generic type parameter. If we write `Comparable<Student>`, then `T` represents `Student`.
```
public interface Comparable<Student> {
    int compareTo(Student other);
}
```
The interface declares `compareTo()`, but it does not provide a method body. There is no comparison logic yet.

Because an interface's `compareTo()` method is `abstract`, the class is implementing the interface method.


Sorting arranges objects according to a rule.

```java
[8, 3, 10, 1]  ->  [1, 3, 8, 10]
["Zoe", "Ali", "Mina"]  ->  ["Ali", "Mina", "Zoe"]
```

Java knows how to order `Integer` and `String` because those classes provide natural ordering. 

Natural ordering is the default ordering that the class chooses for its objects. Java's built-in classes Integer and String implement `Comparable`. 

- Natural ordering for integers is numerical order, from smallest to largest.
- Natural ordering for strings is lexicographical order, based on the sequence of characters and their Unicode values.

Java's `Integer` class already implements `Comparable<Integer>` and provides its own `compareTo()` implementation. We do not write that implementation.

Java's `String` class already implements `Comparable<String>` and provides the comparison logic for strings.


But consider an `ArrayList<Student>` where each student has an ID, a name, and a GPA. Should students be sorted by ID, name, GPA, or GPA and then name?

Java cannot guess your application’s intention. You must define the rule.

| Situation | Possible ordering |
|---|---|
| Student registry | ID |
| Scholarship shortlist | GPA, highest first |
| Class roster | Name |
| Library catalogue | Title, then author |

### Example: built-in types

```java
import java.util.ArrayList;
import java.util.Collections;

ArrayList<Integer> numbers = new ArrayList<>();
numbers.add(8);
numbers.add(3);
numbers.add(10);
numbers.add(1);

Collections.sort(numbers);
System.out.println(numbers);
```

Output:

```text
[1, 3, 8, 10]
```

`Integer` implements `Comparable<Integer>`, so Java already knows its natural ordering. `Collections.sort(list)` uses that natural ordering.

**Key idea:** For a user-defined class, `Collections.sort(list)` generally requires that its objects implement `Comparable` with a consistent comparison rule.

---

## 2. `Comparable` and `compareTo()`

### Concept

`Comparable<T>` lets a class define its natural or default ordering.

```java
public class Student implements Comparable<Student> {
    // fields and other methods

    @Override
    public int compareTo(Student other) {
        // define how this object compares with other
    }
}
```

The method returns an `int`. Only the sign matters:

| Result of `this.compareTo(other)` | Meaning |
|---|---|
| Negative | `this` comes before `other` |
| Zero | They tie under this ordering |
| Positive | `this` comes after `other` |

The exact non-zero number does not matter. `-1` and `-100` both mean “before”; `1` and `100` both mean “after.”

### Example: natural ordering by GPA, lowest first

```java
public class Student implements Comparable<Student> {
    private String name;
    private double gpa;

    public Student(String name, double gpa) {
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
**Note:** `Double.compare()` returns an int, even though it compares two double values.

Look at the method signature:
```
public static int compare(double d1, double d2)
```
Java's Double class provides this static method to compare two double values. It returns an integer indicating the comparison result.

**How does it work?**

```
Double.compare(3.5, 3.9);  // Negative integer
Double.compare(3.9, 3.5);  // Positive integer
Double.compare(3.5, 3.5);  // 0
```
The exact negative or positive integer is not important. Only its sign matters.
```
Return value	    Meaning
Negative	        First value is smaller
Zero	            Values are equal
Positive	        First value is larger
```
Test in the main driver:

```java
ArrayList<Student> students = new ArrayList<>();
students.add(new Student("Mina", 3.7));
students.add(new Student("Ali", 3.9));
students.add(new Student("Zoe", 3.2));

Collections.sort(students);
System.out.println(students);
```

Output:

```text
[Zoe (3.2), Mina (3.7), Ali (3.9)]
```

### Trace the comparison in the main driver

```java
Student a = new Student("Mina", 3.7);
Student b = new Student("Ali", 3.9);

System.out.println(a.compareTo(b) < 0);
System.out.println(b.compareTo(a) > 0);
```

Both lines print `true`. Mina’s GPA is lower, so Mina comes before Ali in ascending GPA order.

### Descending GPA

```java
@Override
public int compareTo(Student other) {
    return Double.compare(other.gpa, this.gpa);
}
```

Reversing the arguments reverses the ordering.
## 3. Relative order of elements
We saw before, built-in `Integer` implements `Comparable<Integer>`, so Java already knows its natural ordering.

`Collections.sort(list)` uses that natural ordering.

We can call `Collections.reverse(numbers)` after sorting, but it is often clearer to express the desired descending order directly. **Why?**

**Because**, reversing a whole list also reverses the relative order of elements tied under the original ordering.

Suppose we have the same four students (three students have the same GPA) again sorted by GPA in ascending order: 
```
Zoe (3.5), Mina (3.5), Ali (3.5), Sara (3.9)
```
Now we want the students sorted by GPA in descending order.

**Option A:** Sort ascending, then reverse the entire list
```
Collections.sort(students);  // GPA ascending
Collections.reverse(students);
```
The result is:
```
Sara (3.9), Zoe (3.5), Mina (3.5), Ali (3.5)

```
**Why?**

Sara has the highest GPA. The other three students have equal GPAs, so compareTo() returns 0 when comparing them. Java's stable sort preserves their original relative order: Zoe, Mina, Ali.

**Option B:** Define descending order directly (Or, use a `Comparator`, see later).

If the natural ordering is based on GPA, we can reverse the comparison arguments:
```
@Override
public int compareTo(Student other) {
    return Double.compare(other.gpa, this.gpa);
}
```
Output after `Collections.sort(students)`:
```
Sara (3.9), Zoe (3.5), Mina (3.5), Ali (3.5)
```
**Why?**

The key line is:
```
Double.compare(other.gpa, this.gpa)
```
- Sara has the highest GPA, so she moves to the first position.
- Zoe, Mina, and Ali all have a GPA of 3.5.
- When comparing these three students, `Double.compare(other.gpa, this.gpa)` returns 0, because their GPAs are equal.
- Java's list sorting is stable, so students who compare as equal retain their original relative order: Zoe, Mina, Ali.

  
Java's sorting algorithm generally preserves their existing relative order for equal comparison results because object-list sorting is stable. 

**Important:** When three students have equal GPAs, this method does not specify which student should come first. 

If we want students with equal GPAs to be sorted alphabetically, we have to add a **tie-breaker**:
```
@Override
public int compareTo(Student other) {
    int result = Double.compare(other.gpa, this.gpa);

    if (result != 0) {
        return result;
    }

    return this.name.compareTo(other.name);
}
```
Now the ordering is GPA descending, then name ascending.
```
Sara (3.9), Ali (3.5), Mina (3.5), Zoe (3.5)
```

**Design question:** Is GPA really the natural ordering for every use of `Student`? 

For custom classes, these are design decisions. Java does not automatically know what makes one `Employee` or `Student` come before another.

Also, natural ordering does not have to be the most important order in every situation. It should be a sensible default for that class.

**Common mistake:** Do not combine comparison results using arbitrary weights, such as `100 * nameResult + idResult`. 

Comparison results are not fixed-size scores; an arbitrary multiplier does not reliably preserve key priority. 

Use sequential comparisons or comparator (?) chaining.

**What if we need a different ordering?**

Suppose the registrar wants students sorted by ID, but the scholarship committee wants the highest GPA first.

We could change `compareTo()` to compare GPA, but then the registrar's default ordering would change too. That is one reason Java provides `Comparator`.
- `Comparable` defines the class's natural or default ordering.
- `Comparator` lets us define alternative ordering rules for different situations.

We will explore `Comparator` later in the lecture.

---

## 4. Safer comparison: do not subtract

A common implementation is:

```java
return this.id - other.id;
```

This may appear to work for ordinary integers, but subtraction can overflow and produce the wrong sign for extreme values.

Prefer:

```java
return Integer.compare(this.id, other.id);
```

Use the relevant comparison method for the type:

```java
Integer.compare(a, b);       // int
Double.compare(a, b);        // double
aString.compareTo(bString);  // String
```

For strings, `compareTo()` gives lexicographic order. Do not use `==` to decide alphabetical order: `==` checks reference identity, not text ordering. Use `equals()` to test text equality and `compareTo()` to compare ordering.

---

## 5. `Comparator`: an external rule

### Concept
`Comparator<T>` is a Java interface, just like `Comparable<T>`.

A simplified version of its definition is:
```
public interface Comparator<T> {
    int compare(T first, T second);
}
```
The method `compare()` is abstract here. It declares what the implementing class must do, but does not specify how to compare two objects.

For `Comparator<Student>`, the method becomes effectively:
```
int compare(Student first, Student second);
```
The difference from Comparable is:
```
Comparable	                                            Comparator
public interface Comparable<T>	                        public interface Comparator<T>
int compareTo(T other)	                                int compare(T first, T second)
Compares the current object with another object	        Compares two supplied objects
Usually defines the class's natural ordering	        Defines an alternative ordering rule
```
`Comparable` puts the natural ordering rule inside the class. 

`Comparator` provides a separate rule, often defined in the driver or a separate class, and passed to the sorting method when needed.

```java
public int compare(T first, T second)
```

Its sign has the same meaning as `compareTo()`:
```
| Result of `compare(first, second)`   | Meaning                         |
|--------------------------------------|---------------------------------|
| Negative                             | `first` comes before `second`   |
| Zero                                 | They tie under this comparator  |
| Positive                             | `first` comes after `second`    |
```
### Example: sort by name

```java
import java.util.Comparator;

public class NameComparator implements Comparator<Student> {
    @Override
    public int compare(Student first, Student second) {
        return first.getName().compareTo(second.getName());
    }
}
```

Use it:

```java
Collections.sort(students, new NameComparator());
// or:
students.sort(new NameComparator());
```

The `Student` class does not need to change. The comparator uses public accessors to compare two students.

A `Comparator` is usually defined outside the `Student` class, often in the main() method when you need a particular sorting rule for that program.

Unlike `Comparable`, which defines natural ordering inside the class, `Comparator` lets you define different sorting rules without modifying `Student`.

You can organize your program into two files.

```
Student.java

Contains:
- Fields such as name and gpa
- Constructor and getters
- Optionally, compareTo() if the class implements Comparable<Student>
  
Main.java
  
  Contains:
- Creating the ArrayList<Student>
- Adding students
- Defining the Comparator
- Sorting and displaying the list
```

### Example: GPA, highest first

```java
Comparator<Student> byGpaDescending = new Comparator<Student>() {
    @Override
    public int compare(Student first, Student second) {
        return Double.compare(second.getGpa(), first.getGpa());
    }
};

students.sort(byGpaDescending);
```
An interface cannot be instantiated directly using `new Comparator<Student>()` as if it were a normal **class** constructor. 

However, Java allows this syntax because it creates an **anonymous class** that implements the interface.

At first glance, it looks like we are creating an object of an interface. We are not.

Java is effectively doing two things:
- Defining a new, unnamed class that implements `Comparator<Student>`.
- Creating an object of that unnamed class.
This feature is called an `anonymous inner class` (more precisely, an `anonymous class`).

Conceptually, it is similar to writing:
```
class GpaComparator implements Comparator<Student> {

    @Override
    public int compare(Student first, Student second) {
        return Double.compare(second.getGpa(), first.getGpa());
    }
}
```
Then creating an object:
```
Comparator<Student> byGpaDescending =
    new GpaComparator();
```
The anonymous-class version combines these two steps into one expression, so we do not need to give the class a name.

#### Why can the reference type be an interface?
Remember polymorphism from our earlier lectures?

An interface reference can refer to an object of any class that implements that interface.

For example:
```
Comparator<Student> comparator =
    new GpaComparator();
```
Here:
- Comparator<Student> is the reference type.
- GpaComparator is the concrete class.
- The object is an instance of GpaComparator.
This is the same principle as:
```
Animal animal = new Dog();
```
The difference is that the comparator's concrete class may be anonymous, so it has no programmer-defined class name.

#### A simpler alternative: lambda expressions

If lambda expressions have been introduced, the same comparator can be written more concisely::

```
Comparator<Student> byGpaDescending =
        (first, second)
            -> Double.compare(second.getGpa(), first.getGpa());
```

This works because `Comparator` is a functional interface: it has one abstract method, `compare()`. 

Java can use the lambda expression to provide that method's implementation.  

### Comparable versus Comparator
```
| Question                | `Comparable`                   | `Comparator`                          |
|-------------------------|--------------------------------|---------------------------------------|
| Method                  | `compareTo(other)`             | `compare(first, second)`              |
| Rule lives in           | Class being compared           | Separate object or expression         |
| Typical use             | Natural/default order          | Alternative, context-specific order   |
| Multiple rules?         | One natural ordering per class | Many comparators                      |
| Must edit the class?    | Usually, to implement it       | No, if needed data is accessible      |
| Sort call               | `Collections.sort(list)`       | `Collections.sort(list, comparator)`  |
```
**Takeaway:**

An interface cannot be instantiated directly. 

But `new Comparator<Student>() { ... }` is not instantiating the interface itself.

It defines and instantiates an **anonymous class** that implements the interface.

- **Comparable:** This object knows its natural order.
- **Comparator:** This separate rule compares two objects.

---

## 6. Multiple keys with a Comparator

### Scenario

A scholarship shortlist needs:
1. GPA descending (highest first).
2. If GPA ties, name ascending.
3. If names tie, ID ascending.

Assume `Student` has `getGpa()`, `getName()`, and `getId()`.

### Explicit implementation

```java
Comparator<Student> scholarshipOrder = new Comparator<Student>() {
    @Override
    public int compare(Student first, Student second) {
        int result = Double.compare(second.getGpa(), first.getGpa());
        if (result != 0) {
            return result;
        }

        result = first.getName().compareTo(second.getName());
        if (result != 0) {
            return result;
        }

        return first.getId().compareTo(second.getId());
    }
};
```

### Comparator chaining

Java can express the same rule more concisely:

```java
Comparator<Student> scholarshipOrder =
    Comparator.comparingDouble(Student::getGpa).reversed()
              .thenComparing(Student::getName)
              .thenComparing(Student::getId);
```

Read it as: GPA descending; if tied, name ascending; if still tied, ID ascending.

### Trace example

| ID | Name | GPA |
|---|---|---:|
| S104 | Mina | 3.7 |
| S102 | Ali | 3.9 |
| S101 | Zoe | 3.7 |
| S103 | Ali | 3.9 |

Expected order:

```text
S102 Ali  3.9
S103 Ali  3.9
S104 Mina 3.7
S101 Zoe  3.7
```

The 3.9 students come first. Their names tie, so ID determines their order. Among the 3.7 students, Mina comes before Zoe.

### Why not weighted arithmetic?

Multiplying comparison results by arbitrary constants is fragile. Use sequential comparisons or `thenComparing()` to express priorities explicitly.

---

## 7. Complete example: natural order plus an alternative

### `Student.java`

```java
public class Student implements Comparable<Student> {
    private String id;
    private String name;
    private double gpa;

    public Student(String id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    // Natural ordering: ID ascending
    @Override
    public int compareTo(Student other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return id + " | " + name + " | GPA " + gpa;
    }
}
```

### `Main.java`

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("S104", "Mina", 3.7));
        students.add(new Student("S102", "Ali", 3.9));
        students.add(new Student("S101", "Zoe", 3.7));
        students.add(new Student("S103", "Ali", 3.9));

        System.out.println("Natural ordering: ID");
        Collections.sort(students);
        for (Student student : students) {
            System.out.println(student);
        }

        Comparator<Student> scholarshipOrder =
            Comparator.comparingDouble(Student::getGpa).reversed()
                      .thenComparing(Student::getName)
                      .thenComparing(Student::getId);

        System.out.println("\\nScholarship ordering: GPA, name, ID");
        students.sort(scholarshipOrder);
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
```

Expected output:

```text
Natural ordering: ID
S101 | Zoe | GPA 3.7
S102 | Ali | GPA 3.9
S103 | Ali | GPA 3.9
S104 | Mina | GPA 3.7

Scholarship ordering: GPA, name, ID
S102 | Ali | GPA 3.9
S103 | Ali | GPA 3.9
S104 | Mina | GPA 3.7
S101 | Zoe | GPA 3.7
```

Notice:
- The same objects are sorted twice using different rules.
- `Collections.sort(students)` uses `compareTo()`.
- `students.sort(scholarshipOrder)` uses the supplied comparator.
- Sorting reorders the list in place; it does not create a new list.

---

## 8. Common mistakes and debugging

### Mistake 1: wrong method signature

Incorrect:

```java
public boolean compareTo(Student other) {
    return this.gpa > other.gpa;
}
```

`Comparable<Student>` requires `int compareTo(Student other)`, not a boolean.

Correct:

```java
@Override
public int compareTo(Student other) {
    return Double.compare(this.gpa, other.gpa);
}
```

### Mistake 2: forgetting `implements Comparable<Student>`

A method named `compareTo()` is not enough for natural sorting. The class must implement the interface with the correct type parameter.

### Mistake 3: subtraction

Risky:

```java
return this.idNumber - other.idNumber;
```

Safer:

```java
return Integer.compare(this.idNumber, other.idNumber);
```

### Mistake 4: returning zero too early

If the required ordering is GPA then name, this implementation is incomplete:

```java
if (this.gpa == other.gpa) {
    return 0;
}
```

It ignores the name tie-breaker. Instead, compare names when GPA comparison returns zero.

### Mistake 5: assuming a tie means identical objects

A comparison result of zero means equal under that ordering. Other fields may differ.

### Mistake 6: inconsistent comparator logic

A comparator should provide a consistent order. Avoid comparisons based on random values, changing external state, or contradictory conditions.

### Mistake 7: assuming sorting creates a new list

Sorting reorders the supplied list. To preserve the original:

```java
ArrayList<Student> copy = new ArrayList<>(students);
copy.sort(scholarshipOrder);
```

### Mistake 8: sorting then reversing

If you need descending GPA, express it directly. Reversing the entire list can reverse the relative order of tied items.

---

## 9. Practice — predict, run, explain

For each activity:
1. **Predict** the output before running.
2. **Run** a small test.
3. **Explain** why the result follows from the comparison contract.
4. **Modify** one rule and predict again.
5. **Test** ties and edge cases.

Answers follow each activity. Try it first, then expand the answer.

### Practice 1 — Read the sign

```java
int result = Integer.compare(12, 20);
System.out.println(result < 0);
System.out.println(result == 0);
System.out.println(result > 0);
```

<details>
<summary>Answer and reasoning</summary>

```text
true
false
false
```

12 is less than 20, so the result is negative.
</details>

### Practice 2 — Natural ordering

A class implements `Comparable<Item>`:

```java
@Override
public int compareTo(Item other) {
    return Integer.compare(this.price, other.price);
}
```

A list contains prices 30, 10, and 20. What order results from `Collections.sort(items)`?

<details>
<summary>Answer and reasoning</summary>

10, 20, 30. The natural ordering compares prices ascending.
</details>

### Practice 3 — Find the bug

```java
public class Student implements Comparable<Student> {
    private int id;

    @Override
    public int compareTo(Student other) {
        return other.id - this.id;
    }
}
```

1. Does it express ascending or descending ID for ordinary values?
2. What is the risk?
3. Rewrite it safely.

<details>
<summary>Answer and reasoning</summary>

1. Descending ID: a larger ID is intended to come first.
2. Subtraction can overflow.
3. Use:

```java
@Override
public int compareTo(Student other) {
    return Integer.compare(other.id, this.id);
}
```
</details>

### Practice 4 — Add a tie-breaker

A `Book` has `title` and `author`. Required order: title ascending, then author ascending.

```java
@Override
public int compareTo(Book other) {
    int result = this.title.compareTo(other.title);
    if (result != 0) {
        return result;
    }

    // Complete this
}
```

<details>
<summary>Answer and reasoning</summary>

```java
return this.author.compareTo(other.author);
```

Compare the author only if the titles tie.
</details>

### Practice 5 — Choose the tool

A `Product` class comes from a third-party library. You cannot edit it. Your application needs to sort products by price and sometimes by name. Which interface should you use?

<details>
<summary>Answer and reasoning</summary>

Use `Comparator`. It defines alternative rules outside the original class, provided the required fields are accessible through public methods.
</details>

### Practice 6 — Trace multiple keys

```java
Comparator<Student> order =
    Comparator.comparingDouble(Student::getGpa).reversed()
              .thenComparing(Student::getName);
```

Students:

```text
Mina 3.8
Ali  3.9
Zoe  3.8
Sara 3.9
```

Predict the order.

<details>
<summary>Answer and reasoning</summary>

```text
Ali  3.9
Sara 3.9
Mina 3.8
Zoe  3.8
```

GPA descending comes first; names are alphabetical within each GPA tie.
</details>

### Practice 7 — Tricky design choice

The admission office needs GPA descending, the registrar needs ID ascending, and a teacher needs name ascending. A developer keeps changing `Student.compareTo()` for each use case.

What is the design problem? How should it be improved?

<details>
<summary>Answer and reasoning</summary>

The class's natural ordering is being changed to serve unrelated use cases. Choose a defensible default natural ordering if one exists, and create separate comparators for GPA, ID, and name. Each caller can choose the appropriate rule without changing the class.
</details>

---

## 10. AI-assisted practice — scholarship ranking

### Scenario

A college ranks scholarship candidates by:
1. GPA descending.
2. If GPA ties, volunteer hours descending.
3. If both tie, name ascending.
4. If all three tie, ID ascending.

Each `Student` has `id`, `name`, `gpa`, and `volunteerHours`.

### Part A — Predict before coding

| ID | Name | GPA | Volunteer hours |
|---|---|---:|---:|
| S04 | Mina | 3.8 | 40 |
| S02 | Ali | 3.9 | 15 |
| S01 | Zoe | 3.8 | 60 |
| S03 | Ali | 3.9 | 25 |
| S05 | Mina | 3.8 | 40 |

Write down the expected order. Explain which key determines the order when two records are compared.

### Part B — AI-assisted first implementation

Ask an AI coding assistant to write a `Comparator<Student>` for the four rules. Specify the priority and direction of every key. Do not accept code simply because it compiles.


### Part C — Modify and test

Test:
- a higher-GPA student with fewer volunteer hours;
- equal GPA but different volunteer hours;
- equal GPA and hours but different names;
- equal GPA, hours, and name but different IDs;
- a one-student list;
- an empty list.

Predict each result before running it.
 
### AI mistakes to watch for

AI may reverse GPA order, sort volunteer hours ascending, compare names before volunteer hours, omit the ID tie-breaker, use subtraction, or invent a getter that does not exist. Code can compile and still implement the wrong priority. Your job is to test, verify, and explain it.

---

## 11. Final summary

| Question | Answer |
|---|---|
| How does a class define natural ordering? | Implement `Comparable<T>` and override `compareTo(T other)`. |
| What does a negative result mean? | The current/first object comes before the other. |
| What does zero mean? | The objects tie under that comparison rule. |
| How do you define an alternative ordering? | Supply a `Comparator<T>` to the sorting method. |
| Can one class have many comparators? | Yes; each can represent a different use case. |
| What is safer than numeric subtraction? | `Integer.compare()`, `Double.compare()`, or the relevant type's comparison method. |
| How do you handle ties? | Compare a secondary key, then another key if necessary. |
| Does sorting create a new list? | No. Standard list sorting reorders the list in place. |

### Essential design choice

Use `Comparable` when the class has a meaningful natural ordering. Use `Comparator` when ordering depends on the task, user, or application context. A program can use both: one default ordering and multiple alternative orderings.
