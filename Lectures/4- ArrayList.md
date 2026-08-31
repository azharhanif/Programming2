# Programming 2 — Lecture 4: ArrayList

## Learning objectives

You should be able to:

- explain why `ArrayList` exists;
- distinguish `ArrayList` from an array;
- use generic types;
- add, read, replace, and remove elements;
- understand `size()` versus array `length`;
- understand the `remove(int)` versus `remove(Object)` trap;
- iterate safely;
- use `ArrayList` with objects and polymorphism;
- make an informed data-structure choice.

## 1. Why ArrayList?

An array has a fixed size:

```java
Student[] students = new Student[30];
```

An `ArrayList` can grow and shrink:

```java
ArrayList<Student> students = new ArrayList<>();
```

Import:

```java
import java.util.ArrayList;
```

Use an array when the number of positions is naturally fixed. Use an `ArrayList` when the collection size changes.

## 2. Generics

Prefer:

```java
ArrayList<String> names = new ArrayList<>();
```

The type parameter says what the list is allowed to contain.

```java
names.add("Ali");
names.add("Mina");
```

This is rejected:

```java
names.add(42); // ❌
```

For primitive types, use wrapper classes:

```java
ArrayList<Integer>
ArrayList<Double>
ArrayList<Boolean>
```

Java handles boxing/unboxing in many expressions.

## 3. Main operations

```java
ArrayList<String> names = new ArrayList<>();

names.add("Ali");
names.add("Mina");
```

### size

```java
names.size()
```

returns the current number of elements.

### get

```java
String first = names.get(0);
```

### set

```java
names.set(0, "Ahmed");
```

`set` replaces an existing element; it does not increase the size.

### insert

```java
names.add(1, "Zara");
```

This inserts at index 1 and shifts later elements.

### remove

```java
names.remove(1);
```

removes the element at index 1.

### clear

```java
names.clear();
```

removes everything.

## 4. The Integer remove trap

Consider:

```java
ArrayList<Integer> nums = new ArrayList<>();

nums.add(10);
nums.add(20);
nums.add(30);

nums.remove(1);
```

The result is:

```text
[10, 30]
```

because `1` is interpreted as an index.

To remove the value `1`:

```java
nums.remove(Integer.valueOf(1));
```

This distinction is a frequent exam and debugging trap.

## 5. Looping

Use an index when you need positions:

```java
for (int i = 0; i < names.size(); i++) {
    System.out.println(i + ": " + names.get(i));
}
```

Use enhanced-for when you only need values:

```java
for (String name : names) {
    System.out.println(name);
}
```

## 6. Removing while iterating

This can cause `ConcurrentModificationException`:

```java
for (String name : names) {
    if (name.equals("Ali")) {
        names.remove(name);
    }
}
```

A concise safe alternative is:

```java
names.removeIf(name -> name.equals("Ali"));
```

Or use an `Iterator` when you need more control.

## 7. ArrayList of objects

```java
ArrayList<Student> students = new ArrayList<>();

students.add(new Student("Ali", 101));
students.add(new Student("Mina", 102));
```

Then:

```java
for (Student s : students) {
    System.out.println(s.getName());
}
```

The list stores references to Student objects.

## 8. ArrayList and polymorphism

Suppose:

```java
class Animal { }
class Dog extends Animal { }
class Cat extends Animal { }
```

Then:

```java
ArrayList<Animal> animals = new ArrayList<>();

animals.add(new Dog());
animals.add(new Cat());
```

This is a major use of polymorphism: one collection can hold different subclasses through a common superclass type.

## 9. Array versus ArrayList

| Requirement | Natural choice |
|---|---|
| fixed number of positions | array |
| frequent add/remove | ArrayList |
| matrix/grid | array |
| dynamically sized object collection | ArrayList |
| need direct primitive array operations | array |

Do not choose a data structure merely because it is familiar. Choose it because its behavior matches the problem.

# Practice

### Q1 — predict

```java
ArrayList<String> x = new ArrayList<>();

x.add("A");
x.add("B");
x.add("C");
x.set(1, "X");
x.remove(0);

System.out.println(x);
```

**Answer:**

```text
[X, C]
```

### Q2 — error

```java
ArrayList<String> names = new ArrayList<>();
System.out.println(names.get(0));
```

**Answer:** `IndexOutOfBoundsException`; the list is empty.

### Q3 — tricky

```java
ArrayList<Integer> nums = new ArrayList<>();
nums.add(10);
nums.add(20);
nums.add(30);
nums.remove(1);
```

**Answer:** `[10, 30]`, because `remove(1)` removes index 1.

### Q4

Write:

```java
public static int countPassing(ArrayList<Integer> marks)
```

where passing means at least 60.

**Answer:**

```java
public static int countPassing(ArrayList<Integer> marks) {

    int count = 0;

    for (int mark : marks) {
        if (mark >= 60) {
            count++;
        }
    }

    return count;
}
```

### Q5 — harder

Remove all negative integers without using an index loop that skips elements.

**Answer:**

```java
nums.removeIf(n -> n < 0);
```

An explicit Iterator is another correct solution.

### Q6 — design

You need a collection of all `Animal` objects in a simulation, including Dogs, Cats, and Frogs. What should the declared type be?

**Answer:**

```java
ArrayList<Animal> animals = new ArrayList<>();
```

This supports polymorphism.

# AI-assisted practice

Ask an LLM to remove duplicate names from an `ArrayList` while preserving order. Test duplicates, case differences, and `null` if your specification allows it. Explain and modify the generated code before keeping it.
