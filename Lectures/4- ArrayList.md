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

Java automatically performs boxing and unboxing in many expressions. 

Boxing converts a primitive such as int to its wrapper object Integer, which allows us to write `nums.add(10)` even though an `ArrayList<Integer>` stores objects. 

Unboxing converts an `Integer` back to an `int`, allowing expressions such as 

`int x = nums.get(0)`  or 

`nums.get(0) + nums.get(1)`.
```
ArrayList<Integer> nums = new ArrayList<>();

nums.add(10);                 // boxing: int → Integer

int x = nums.get(0);          // unboxing: Integer → int

int sum = nums.get(0) + 5;    // unboxing
```
The `boxing/unboxing → ArrayList → remove(Integer)` trap connection?
```
nums.remove(1);               // index 1
nums.remove(Integer.valueOf(1)); // value 1
```


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
You may think:

"Remove the value 1."

But because `nums` is an `ArrayList<Integer>`, there are two overloaded `remove` methods:
```
remove(int index)
remove(Object object)
```
Therefore:`nums.remove(1);` means, Remove the element at index 1. 

It does not mean remove the value `1`.

The result is: '[10, 30]' because `1` is interpreted as an index.

If you want to remove the integer value `1`, write: 
```
nums.remove(Integer.valueOf(1));
```
Now Java knows you are asking for the `remove(Object)` version.

This distinction is a frequent debugging trap.

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
Before I explain how to avoid such exception safely, first review three ways to traverse an `ArrayList`

#### A. Traditional for loop
```
for (int i = 0; i < names.size(); i++) {
    System.out.println(names.get(i));
}
```

#### B. Enhanced for loop
```
for (String name : names) {
    System.out.println(name);
}
```
Ques: Where did the index go?

Answer: Java manages it for us.

#### C. Iterator
```
Iterator<String> it = names.iterator();

while (it.hasNext()) {
    String name = it.next();
    System.out.println(name);
}
```
Ques: What is the Iterator doing for us?

Answer: It keeps track of where we are in the collection and gives us the next element.

A concise safe alternative is:

```java
names.removeIf(name -> name.equals("Ali"));
```

Or use an `Iterator` when you need more control.

#### Iterator:
An Iterator is another object that helps us walk through a collection one element at a time.

For example:
```
Iterator<String> it = names.iterator();
```
Think of the Iterator as a cursor that moves through the ArrayList.

Visually:
```
[Ali] [John] [Sara]
  ↑
iterator
```
After: `iterator.next();` the iterator moves:
```
[Ali] [John] [Sara]
       ↑
    iterator
```
Another: 'iterator.next();` and:
```
[Ali] [John] [Sara]
              ↑
           iterator
```
At this stage we will not go into interfaces, concrete iterator classes, internal data structures, etc. We have enught to use iterator object in `ArrayList`.
```
next():
"Give me the next element"

hasNext():
"Is there another element?"

remove():
"Remove the element I just visited"
```
#### Exercise
Given:
```
ArrayList<Integer> numbers =
    new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50));
```
First print the numbers using:
```
A. a traditional for loop.

B. an enhanced for loop.

C. an Iterator.
```
Now remove every number greater than `25`.

With Iterator:
```
Iterator<Integer> it = numbers.iterator();

while (it.hasNext()) {
    int number = it.next();

    if (number > 25) {
        it.remove();
    }
}
```
Result:
```
[10, 20]
```
Iteration simply means repeatedly visiting elements, while an iterator is the Java object that helps you perform that traversal.

So:
```
iteration = the process
Iterator  = the object helping us do the process
```
#### How to safely remove elements from an `ArrayList` while iterating through it?

##### `removeIf()` — the concise approach

The code is:
```
names.removeIf(name -> name.equals("Ali"));
```
Suppose we have:
```
ArrayList<String> names = new ArrayList<>();

names.add("Ali");
names.add("John");
names.add("Ali");
names.add("Sara");
```
The list is:
```
[Ali, John, Ali, Sara]
```
After:
```
names.removeIf(name -> name.equals("Ali"));
```
we get:
```
[John, Sara]
```
Every element for which the condition is true is removed.

##### What does name -> name.equals("Ali") mean?

This is a lambda expression.

You can think of:
```
name -> name.equals("Ali")
```
as a small function that answers: "Should this particular name be removed?"

For example:
```
name = "Ali"
        ↓
"Ali".equals("Ali")
        ↓
true
        ↓
REMOVE
```
Then:
```
name = "John"
        ↓
"John".equals("Ali")
        ↓
false
        ↓
KEEP
```
Then:
```
name = "Ali"
        ↓
true
        ↓
REMOVE
```
So removeIf() effectively asks the condition about every element.

##### Why is removeIf() useful?

Compare:
```
names.removeIf(name -> name.equals("Ali"));
```
with manually searching for "Ali".

You don't need to manage:
```
the index
shifting elements
changing the loop counter
accidentally skipping an element
an Iterator
```
Java handles the removal operation for you.

For a simple condition, this is usually the cleanest solution.

##### Example with numbers

The same idea works with `Integer`.
```
ArrayList<Integer> numbers = new ArrayList<>();

numbers.add(10);
numbers.add(15);
numbers.add(20);
numbers.add(25);
numbers.add(30);
```
Remove all numbers greater than 20:
```
numbers.removeIf(n -> n > 20);
```
Result:
```
[10, 15, 20]
```
Here:
```
n -> n > 20
```
means:

For each number `n`, return true if `n` is greater than `20`.

##### You can make the lambda more complicated

For example:
```
names.removeIf(name -> name.length() < 4);
```
This removes names shorter than four characters.

Or:
```
names.removeIf(name -> name.startsWith("A"));
```
This removes names beginning with A.

Or:
```
numbers.removeIf(n -> n % 2 == 0);
```
This removes all even numbers.

So the general pattern is:
```
list.removeIf(element -> condition);
```
The important idea is:

If the condition returns true, the element is removed.

##### What about Iterator?

Or use an Iterator when you need more control.

An Iterator gives you explicit control over walking through an `ArrayList` and safely removing the current element.

For example:
```
ArrayList<String> names = new ArrayList<>();

names.add("Ali");
names.add("John");
names.add("Ali");
names.add("Sara");

Iterator<String> iterator = names.iterator();

while (iterator.hasNext()) {
    String name = iterator.next();

    if (name.equals("Ali")) {
        iterator.remove();
    }
}
```
After the loop:
```
[John, Sara]
```
#### Why not simply use names.remove(name)?

Don't do:
```
Iterator<String> iterator = names.iterator();

while (iterator.hasNext()) {
    String name = iterator.next();

    if (name.equals("Ali")) {
        names.remove(name);       // ❌
    }
}
```
You're modifying the `ArrayList` directly while an iterator is managing the list.

Instead:
```
iterator.remove();                 // ✅
```
The iterator knows that the removal is happening and can keep its position consistent.
#### A subtle issue with null

At the end your AI-assisted exercise specifically mentions testing null, so it is relevant.

```
names.removeIf(name -> name.equals("Ali"));
```
can fail if name is null.

For example:
```
names.add(null);
```
Then:
```
name.equals("Ali")
```
tries to call `.equals()` on null.

That produces:
```
NullPointerException
```
A safer version is:
```
names.removeIf(name -> "Ali".equals(name));
```
Why?

Because `"Ali"` is definitely not null.

So:
```
"Ali".equals(null)
```
simply returns:
```
false
```
#### Finally, we can see all three approaches progressively:

Approach 1 — simple `removeIf()`
```
names.removeIf(name -> "Ali".equals(name));
```
Use when: you simply want to remove elements satisfying a condition.

Approach 2 — Iterator
```
Iterator<String> iterator = names.iterator();

while (iterator.hasNext()) {
    String name = iterator.next();

    if ("Ali".equals(name)) {
        iterator.remove();
    }
}
```
Use when: you need more control over the traversal/removal process.

Approach 3 — indexed loop

For more complex situations, you can also deliberately control the indexes:
```
for (int i = names.size() - 1; i >= 0; i--) {
    if ("Ali".equals(names.get(i))) {
        names.remove(i);
    }
}
```
The `backward` direction is important here because removing an element doesn't disturb the indexes of the elements you've already examined, rather than throwing an exception.
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
