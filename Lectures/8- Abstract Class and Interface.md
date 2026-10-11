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
new Animal("A"); // ❌ because Animal is incomplete.

❌ Not allowed
Animal a = new Animal("A");
Attempts to instantiate the abstract class itself.

Allowed
Animal a = new Dog("A");
Creates a concrete Dog object and stores its reference in an Animal variable.

```

#### Then Why `Animal` has a constructor?

An abstract class can have constructors. Its constructor runs when a subclass object is created.
```
abstract class Animal {
    String name;

    public Animal(String name) {
        this.name = name;
        System.out.println("Animal constructor");
    }

    public abstract void makeSound();
}

class Dog extends Animal {
    public Dog(String name) {
        super(name);
        System.out.println("Dog constructor");
    }

    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }
}
```
Running:
```
Dog d = new Dog("Buddy");
```
Output:
```
Animal constructor
Dog constructor
```
The Animal constructor initializes the inherited state first; then the Dog constructor completes the subclass initialization.

An abstract class is not a class with no constructor. 

Its constructors exist to initialize the inherited portion of concrete subclass objects.

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
A Java class can extend only one class directly. However, it can implement multiple interfaces.

This is useful because Java does not support multiple inheritance of classes.

Multiple inheritance means that a class inherits from two or more parent classes.

#### What is multiple inheritance?
Multiple inheritance means that a class inherits from two or more parent classes.
Imagine we have these two classes:
```
class Animal {
    public void eat() {
        System.out.println("Eating...");
    }
}

class Pet {
    public void play() {
        System.out.println("Playing...");
    }
}
```
Suppose we want to create a Dog that inherits from both Animal and Pet.

In a language that supports multiple inheritance of classes, the idea might look like this:
```
// Not valid Java!
class Dog extends Animal, Pet {
}
```
Java does not allow this syntax. A Java class can have only one direct superclass.

The question is: How can we give a class capabilities from multiple sources without extending multiple classes?
#### The problem: What if both parent classes have the same method?
Consider this example:
```
Animal
eat()
|
Pet
eat()
|
Dog
Which eat() should it inherit?
```
If both parent classes provide an eat() method, which implementation should Dog use?

- The one from Animal?
- The one from Pet?
  
This is an example of an inheritance ambiguity.  

#### The solution: Use an abstract class and interfaces

Suppose we want every dog to be an animal, while allowing some animals to have pet-related behavior.

We can use an abstract class for shared state and implementation, and interfaces for additional capabilities.

```
abstract class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println(name + " is eating.");
    }

    public abstract void makeSound();
}

interface Pet {
    void play();
}

interface Trainable {
    void train();
}
```
Now Dog can extend Animal and implement both interfaces:
```
class Dog extends Animal implements Pet, Trainable {

    public Dog(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }

    @Override
    public void play() {
        System.out.println(name + " is playing.");
    }

    @Override
    public void train() {
        System.out.println(name + " is training.");
    }
}
```
Usage:
```
Dog d = new Dog("Buddy");

d.eat();
d.makeSound();
d.play();
d.train();
```
Output:
```
Buddy is eating.
Woof!
Buddy is playing.
Buddy is training.
```
Notice how Java achieves the design goal:
- Animal provides shared state (name) and implemented behavior (eat()).
- Pet defines the required play() behavior.
- Trainable defines the required train() behavior.
- Dog extends one class but implements two interfaces.

An abstract class and an interface serve different purposes.
```
Abstract class	                                        Interface
--------------                                          ----------
Can store instance state, such as name.	                Primarily defines a contract or capability.
Can have constructors.	                                Cannot have ordinary instance constructors.
Can provide implemented and abstract methods.	        Can declare abstract methods and, in modern Java, also default and static methods.
A class can extend only one class.	                    A class can implement multiple interfaces.
```
An abstract class is useful when related subclasses share state and implementation. 

Interfaces are useful when classes need to promise particular behaviors, even if those classes are otherwise unrelated.
#### Takeaway
Java's restriction does not mean a class can have only one kind of behavior. 

It means a class can inherit its class implementation from only one direct superclass.

For example:
```
class Dog extends Animal implements Pet, Trainable
```
This is valid Java because Dog extends exactly one class, Animal, while implementing two interfaces.

Remember: `extends`  establishes the class inheritance relationship; `implements` allows a class to satisfy multiple interface contracts.

---

## 6. Polymorphism with interfaces

```java
Flyable f = new Bird();
f.fly();
```
The declared type is `Flyable`, while the actual object is `Bird`.

This is demonstrating how Java allows an interface as is like a class (?), even though the interface has no constructor. 

Interface is used just the type to share fly() behavior among different unrelated classes.

- A name: Flyable
- A method declaration: fly()
- No constructor
- No ordinary instance state that must be initialized
  
The interface says:
- Any class that implements Flyable must provide a fly() method.
- It does not say what kind of object must fly or how flying must be implemented.

You can also have:

```java
ArrayList<Flyable> flyers = new ArrayList<>();

flyers.add(new Bird());
flyers.add(new Drone());
flyers.add(new Airplane());
```

if all implement `Flyable`.

#### One reference type for different objects
Consider this example:
```
Flyable f1 = new Bird();
Flyable f2 = new Airplane();

f1.fly();
f2.fly();
```
Output:
```
The bird flaps its wings.
The airplane uses its engines.
```
Both variables have the reference type Flyable, but they refer to different kinds of objects.

This is polymorphism through an interface.

We can take the idea further:
```
Flyable[] things = {
    new Bird(),
    new Airplane()
};

for (Flyable f : things) {
    f.fly();
}
```
- The loop does not need to know whether each object is a bird or an airplane. 
- It only needs to know that each object implements Flyable.

That is the main advantage: 

code can operate on different classes through a common interface without depending on their concrete classes.
#### Why does the interface not need a constructor?
A constructor initializes an object when that object's class is instantiated.

An interface is not instantiated directly:
```
Flyable f = new Flyable(); // Compilation error
```
Instead, the concrete class is instantiated:
```
Flyable f = new Bird();
```
The Bird constructor initializes the Bird object. 

If Bird has fields, its constructor initializes those fields as needed.

The interface simply specifies which operations the object promises to support.

#### Takeaway
An interface is not a class without a constructor. 

It is a separate kind of reference type that defines a contract. 

Different classes—even unrelated ones—can implement that contract, allowing Java to treat their objects uniformly while dispatching each method call to the appropriate implementation.

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

An interface lets you define a capability that only certain classes need to provide. 

You do not have to force every class in a broad category to implement that capability.

#### The problem: Not every vehicle can fly
Suppose we start with a Vehicle class.
```
class Vehicle {
    protected String model;

    public Vehicle(String model) {
        this.model = model;
    }
}
```
Now imagine we have three types of vehicles:
- A car
- An airplane
- A boat
All three are vehicles, but they do not share every capability.

If we put fly() in the general Vehicle class as an abstract method, every concrete vehicle subclass would have to implement it:
```
abstract class Vehicle {
    public abstract void fly();
}
```

This is a poor design because the class hierarchy says every vehicle must provide flying behavior, which is not true.

A Car would then be forced to implement fly(), even though a normal car cannot fly. It might end up with an artificial implementation:

```
class Car extends Vehicle {
    @Override
    public void fly() {
        // A normal car cannot fly!
    }
}
```
This is a poor design because the class hierarchy says every vehicle must provide flying behavior, which is not true.
#### The solution: Define a separate Flyable interface
Instead of saying that every vehicle must fly, we define a separate capability:
```
interface Flyable {
    void fly();
}
```
This means:
- Any class that implements Flyable must provide a fly() method.

It does not mean that every vehicle must implement Flyable.

We can now design our classes like this:
```
class Car extends Vehicle {
    public Car(String model) {
        super(model);
    }
}

class Airplane extends Vehicle implements Flyable {
    public Airplane(String model) {
        super(model);
    }

    @Override
    public void fly() {
        System.out.println(model + " is flying.");
    }
}

class Boat extends Vehicle {
    public Boat(String model) {
        super(model);
    }
}
```
Notice the difference:
- Car extends Vehicle
- Airplane extends Vehicle implements Flyable
- Boat extends Vehicle
Only Airplane implements Flyable, so only Airplane is required to provide the fly() method.
#### What happens when we use the classes?
```
Car car = new Car("Toyota");
Airplane plane = new Airplane("Boeing 737");
Boat boat = new Boat("Sea Ray");

plane.fly();  // Valid
```
Output:
```
Boeing 737 is flying.
```
But this is not valid:
```
car.fly();  // Compilation error
boat.fly(); // Compilation error
```
#### The additional advantage: Find all flyable objects

Suppose we want to write a method that works with anything capable of flying, not just vehicles.
```
public static void testFlight(Flyable object) {
    object.fly();
}
```
We can call it with:
```
testFlight(new Airplane("Boeing 737"));
```
We could also have a bird implement Flyable:
```
class Bird implements Flyable {
    @Override
    public void fly() {
        System.out.println("The bird flaps its wings.");
    }
}
```
Now this works too:
```
testFlight(new Bird());
```
A Bird is not a Vehicle, but both Bird and Airplane implement Flyable. 

The method can accept either one because it depends on the interface, not on a particular class.

#### Takeaway: 
We use an interface when we want to represent a capability that some classes share, without requiring every class in a broader inheritance hierarchy to have that capability

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
- Can an employee be both taxable and something else?
- Can the design support a new employee type without changing existing code?
