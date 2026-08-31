# Programming 2 — Lecture 8: Abstract Classes and Interfaces

## Learning objectives

You should be able to:

- explain an abstract method;
- explain why an abstract class cannot normally be instantiated;
- distinguish abstract class from interface;
- implement an interface;
- extend an abstract class;
- use an abstract class and an interface together;
- recognize when a behavior is better represented by an interface;
- use polymorphism with both designs.

---

## 1. Abstract method

An abstract method declares a required behavior without providing the implementation.

```java
public abstract double calculateCost();
```

There is no method body.

The subclass must provide the implementation unless it is itself abstract.

---

## 2. Abstract class

```java
public abstract class Animal {

    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public abstract void speak();

    public void sleep() {
        System.out.println(name + " sleeps.");
    }
}
```

An abstract class can contain:

- fields;
- constructors;
- concrete methods;
- abstract methods.

You cannot normally write:

```java
new Animal("A"); // ❌
```

because Animal is incomplete.

---

## 3. Interface

An interface describes a capability/contract.

```java
public interface Flyable {
    void fly();
}
```

A class implements it:

```java
public class Bird implements Flyable {

    @Override
    public void fly() {
        System.out.println("Bird flies.");
    }
}
```

---

## 4. Abstract class versus interface

A useful first distinction:

### Abstract class

Use when classes share a strong common identity and often common state/implementation.

```text
Dog is an Animal.
Cat is an Animal.
```

### Interface

Use when unrelated classes share a capability.

```text
Bird can fly.
Airplane can fly.
Drone can fly.
```

They are not all the same kind of object, but they can share:

```java
Flyable
```

---

## 5. A class can implement multiple interfaces

```java
class Duck extends Animal implements Flyable, Swimmable {
}
```

This is useful because Java does not support multiple inheritance of classes.

---

## 6. Polymorphism with interfaces

```java
Flyable f = new Bird();
f.fly();
```

The declared type is `Flyable`, while the actual object is `Bird`.

You can also have:

```java
ArrayList<Flyable> flyers = new ArrayList<>();

flyers.add(new Bird());
flyers.add(new Drone());
flyers.add(new Airplane());
```

if all implement `Flyable`.

---

## 7. A common design question

Suppose we have:

```text
Car
Boat
Airplane
```

and all can:

```text
move()
```

but only some can:

```text
fly()
```

A good design might use:

```java
interface Flyable {
    void fly();
}
```

rather than forcing every vehicle to implement `fly()`.

---

## 8. Combining abstract class and interface

```java
abstract class Vehicle {

    protected String id;

    public Vehicle(String id) {
        this.id = id;
    }

    public abstract double fuelCost();
}

interface Insurable {
    double insuranceCost();
}

class Car extends Vehicle implements Insurable {

    public Car(String id) {
        super(id);
    }

    @Override
    public double fuelCost() {
        return 50.0;
    }

    @Override
    public double insuranceCost() {
        return 120.0;
    }
}
```

The abstract class handles the common vehicle identity/state. The interface represents a capability.

---

# Practice

## Practice 1

Why can't this normally be created?

```java
Animal a = new Animal("Rex");
```

### Answer

Animal is abstract and therefore cannot be directly instantiated.

---

## Practice 2

Which is better for `Flyable`: abstract class or interface?

Bird, Drone, and Airplane all fly.

### Answer

An interface is a natural fit because flying is a capability shared by different kinds of objects.

---

## Practice 3

Complete:

```java
interface Printable {
    void print();
}

class Report __________ Printable {

    @Override
    public void print() {
        System.out.println("Report");
    }
}
```

### Answer

```java
implements
```

---

## Practice 4 — tricky

Can a class do this?

```java
class SmartPhone extends Device implements Camera, GPS, MusicPlayer {
}
```

### Answer

Yes, provided `Device` is a class and `Camera`, `GPS`, and `MusicPlayer` are interfaces.

A class can extend one class and implement multiple interfaces.

---

## Practice 5 — design

You have:

```text
Animal
Dog
Cat
Frog
```

All animals need `eat()`, but each animal eats differently.

Should `eat()` be abstract in Animal?

### Answer

That is a strong design choice:

```java
abstract class Animal {
    public abstract void eat();
}
```

Each subclass must provide its own implementation.

---

# AI-assisted practice

Ask AI:

> Design a payroll system using an abstract `Employee` class and an `Taxable` interface.

Then critique the design:

- Is the abstract class really necessary?
- What belongs in the interface?
- Which members should be private?
- Can an employee be both taxable and something else?
- Can the design support a new employee type without changing existing code?
