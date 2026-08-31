# Programming 2 — Lecture 6: UML Class Diagrams

## Learning objectives

You should be able to:

- read a UML class diagram;
- identify fields, constructors, and methods;
- interpret visibility symbols;
- identify inheritance;
- identify associations/containment;
- translate a simple UML diagram into Java;
- create a UML sketch before coding.

---

## 1. Why UML?

As a program grows, describing relationships only with sentences becomes difficult.

A UML class diagram gives a compact visual representation of classes and relationships.

A class box normally has:

```text
-------------------------
ClassName
-------------------------
fields
-------------------------
methods
-------------------------
```

---

## 2. Visibility

Common UML notation:

| Symbol | Java meaning |
|---|---|
| `+` | public |
| `-` | private |
| `#` | protected |

Example:

```text
-------------------------
Student
-------------------------
- name : String
- id : int
-------------------------
+ Student(name:String, id:int)
+ getName() : String
+ getId() : int
-------------------------
```

This corresponds approximately to:

```java
public class Student {

    private String name;
    private int id;

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
```

---

## 3. Static members

A UML underlined member often indicates `static`.

For example:

```text
-------------------------
Student
-------------------------
- name : String
- count : int
-------------------------
+ getCount() : int
-------------------------
```

If `count` is underlined in the UML notation, interpret it as class-level/static.

Java:

```java
private static int count;
```

---

## 4. Inheritance

Inheritance is commonly shown with a line and a hollow triangle pointing toward the superclass.

Conceptually:

```text
Dog
  ───────▷ Animal
```

Java:

```java
class Dog extends Animal {
}
```

The arrow points toward the more general class.

---

## 5. Association and containment

Suppose:

```text
Library
   |
   | contains
   ↓
Book
```

Java may contain:

```java
private ArrayList<Book> books;
```

This is not inheritance.

---

## 6. Multiplicity

Multiplicity describes how many objects may participate in a relationship.

Examples:

```text
1
0..1
*
1..*
0..*
```

Interpretation:

- `1` = exactly one
- `0..1` = zero or one
- `*` = many
- `1..*` = one or more
- `0..*` = zero or more

For example:

```text
Department 1 -------- 0..* Employee
```

could mean one department has zero or more employees.

---

## 7. UML before code

Suppose the requirement says:

> Every BankAccount has an account number and balance. It can deposit and withdraw.

A quick UML sketch:

```text
-----------------------------
BankAccount
-----------------------------
- accountNumber : int
- balance : double
-----------------------------
+ BankAccount(int, double)
+ deposit(double) : void
+ withdraw(double) : boolean
+ getBalance() : double
-----------------------------
```

Then implement it.

UML is therefore not decoration. It can be a design step.

---

# Practice

## Practice 1

Translate:

```text
-------------------------
Movie
-------------------------
- title : String
- rating : double
-------------------------
+ Movie(String,double)
+ getTitle() : String
+ getRating() : double
-------------------------
```

### Answer

```java
public class Movie {

    private String title;
    private double rating;

    public Movie(String title, double rating) {
        this.title = title;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }
}
```

---

## Practice 2

What does:

```text
Dog ─────▷ Animal
```

mean?

### Answer

Dog inherits from Animal:

```java
class Dog extends Animal {
}
```

---

## Practice 3 — tricky

A `University` contains many `Student` objects.

Should the diagram use inheritance?

### Answer

No. The relationship is containment/association:

```text
University 1 ---- 0..* Student
```

not:

```text
Student extends University
```

---

## Practice 4 — design challenge

Create a UML sketch for:

> A Playlist has a name and many Song objects. A Playlist can add and remove songs.

### Answer

```text
-------------------------
Playlist
-------------------------
- name : String
- songs : ArrayList<Song>
-------------------------
+ Playlist(String)
+ addSong(Song) : void
+ removeSong(Song) : boolean
+ getSongs() : ArrayList<Song>
-------------------------
```

The exact design can vary if it remains consistent with the requirement.
