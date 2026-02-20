# Polymorphism

## 1. First glance at Polymorphism

You might have realized that in the previous example (`Animal`, `DomesitcatedAnimal` and `Dog`) copy constructor in the `Dog` class calls the copy constructor in the `Animal` class . The `Animal` class copy constructor takes one parameter `Animal animal`, but when we were calling the method, we passed `Dog dog` to it. That looks wrong since the data type does not match. However, Java did not complain about it, there was no error when we execute the code. This is called `polymorphism`. You can understand it like this: The Animal class copy constructor requires a parameter of `Animal`, and since the `Dog` class extends the `Animal` class, which means `Dog` is a special kind of `Animal`. In this case, passing a dog is passing a special kind of animal to the method, the two data types match.

In general, **if a method requires a parameter of class B, when you call the method you can pass an object of class B, or you can also pass an object of any subclass of class B.**

### 1.1. Example

```java
public class Animal {
    public void animalSound() {
        System.out.println("The animal makes a sound");
    }

    public void animalSound(String name) {
        System.out.println("The animal " + name + " makes a sound");
    }
}
```

```java
public class Cat extends Animal {
    @Override
    public void animalSound() {
        System.out.println("The cat says: meow");
    }

    @Override
    public void animalSound(String name) {
        System.out.println("The cat " + name + " says: meow");
    }
}
```

```java
public class Dog extends Animal {
    @Override
    public void animalSound() {
        System.out.println("The dog says: woof");
    }

    @Override
    public void animalSound(String name) {
        System.out.println("The dog " + name + " says: woof");
    }
}
```

```java
public static void main(String[] args) {
    Animal myAnimal = new Animal();
    Animal myDog = new Dog();
    Animal myCat = new Cat();

    myAnimal.animalSound();
    myDog.animalSound();
    myCat.animalSound();

    myAnimal.animalSound("Wolfy");
    myDog.animalSound("Fluffy");
    myCat.animalSound("Berry");
}
```

## 2. A close look at Polymorphism

Now let's take a look at another example: If we have a class `Animal`, that contains a method a static method `void makeSound(Animal animal)` and an abstract method `abstract void makesound()`, two classes `Dog` and `Cat` extend from the `Animal` class and each of them override the abstract method:

```java
public abstract class Animal {
    public static void makeSound(Animal animal) {
        animal.makeSound();
    }

    public abstract void makeSound();
}
```

```java
public class Cat extends Animal {

    public void makeSound() {
        System.out.print("Meow");
    }
}
```

```java
public class Dog extends Animal {

    public void makeSound() {
        System.out.print("Woof");
    }
}
```

Now if we call the method `makeSound(Animal animal)` in the `Animal` class and pass a cat object to it, it will call the `makeSound()` method in the `Cat` class, while if we pass a dog object to it, it will call the `makeSound()` method in the `Dog` class. In this case, even though the method `makeSound(Animal animal)` is hard coded (only one version), but since we can pass different objects to it, how the method really will behave depends on the class of the object. A method can behave differently based on the parameter, this is called `polymorphism`.

```java
public static void main() {
    Cat c = new Cat();
    Animal.makeSound(c);		// call makeSound() in Cat class, "Meow"

    Dog d = new Dog();
    Animal.makeSound(d);		// call makeSound() in Dog class, "Woof"
}
```
## 3. Why use abstract class Animal?
The abstract class is used to force polymorphism as a TYPE relationship, not just inheritance.

Without abstraction,
```java
“Dog extends Animal → just reuse code.”
```
But polymorphism is actually:
```java
“Different objects share a common contract type.”
```
The abstract class accomplishes:

✅ Animal is a concept, not a concrete object

✅ We never create a generic animal

✅ Only specific animals exist
    
```java
abstract class Animal {
    public abstract void makeSound();
}
```
So instead of:
```java
Animal a = new Animal(); // ❌ meaningless conceptually
```
we enforce:
```java
Animal a = new Dog();
Animal b = new Cat();
```
Now polymorphism becomes visible:
```java
a.makeSound();
b.makeSound();
```
| Concept        | Learning         |
| -------------- | --------------------------- |
| Generalization | Dog IS-A Animal             |
| Contract       | All animals must make sound |
| Polymorphism   | Same call, different result |
The abstract class ties these together cleanly.
#### 3.1 Without abstract method
```java
class Animal {
    void makeSound() {
        System.out.println("Some sound");
    }
}
```
❌ polymorphism is optional overriding.
#### 3.2 With abstract method
```java
abstract void makeSound();
```
Now:

Animal declares behavior

Subclasses implement behavior

Example:
```java
class Dog extends Animal {
    public void makeSound() {
        System.out.println("Bark");
    }
}
```
Usage:
```java
Animal a = new Dog();
a.makeSound();
```
Animal becomes a concept. The compiler enforces polymorphism. 
## 4. Classroom demo (reference type vs object type, runtime method selection)
✅ Java decides WHAT you can call using the reference type

✅ but decides WHAT actually runs using the object type

#### 4.1 Step 1 — with NO Polymorphism
File: Animal.java
```java
class Animal {
    void makeSound() {
        System.out.println("Animal makes sound");
    }
}
```
File: Dog.java
```java
class Dog extends Animal {

    @Override
    void makeSound() {
        System.out.println("Dog barks");
    }

    void wagTail() {
        System.out.println("Dog wagging tail");
    }
}
```
File: Main.java
```java
public class Main {
    public static void main(String[] args) {

        Dog d = new Dog();  // UPCAST

        d.makeSound();
        d.wagTail();
    }
}
```
Output
```java
Dog barks
Dog wagging tail
```
#### Step 2 — Introduce Polymorphism
Change ONLY ONE LINE:
```java
Animal a = new Dog();
```
Full code:
```java
public class Main {
    public static void main(String[] args) {

        Animal a = new Dog();  // UPCAST

        a.makeSound();
    }
}
```
Which sound will print?
```java
❌ Animal makes sound
✅ Dog barks
```
1) `a` LOOKS like Animal

2) but OBJECT is Dog

3) JVM uses real object at runtime

```java
STACK (reference)           HEAP (object)

Animal a  ------------->   Dog object
                              |
                              makeSound() = Dog version
```

#### 4.3 Step 3 — Compiler Restriction
Add:
```java
a.wagTail();  // UPCAST Compile error, not run time crash
```
Compile error:
```java
cannot find symbol
```
Explaination: Compiler sees, 
```java
Animal a
```
Animal has no `wagTail()`. So:

1) Reference type controls ACCESS
   
2) Object type controls BEHAVIOR

#### 4.4 Step 4 — TRUE Polymorphism
Add another class.
```java
class Cat extends Animal {

    @Override
    void makeSound() {
        System.out.println("Cat meows");
    }
}
```
Update Main
```java
public class Main {
    public static void main(String[] args) {

        Animal[] animals = {
            new Dog(),
            new Cat(),
            new Dog()
        };

        for (Animal a : animals) {
            a.makeSound();
        }
    }
}
```
Output
```java
Dog barks
Cat meows
Dog barks

ONE loop. ONE method call. DIFFERENT behaviors.

Compiler:
"I only know they are Animals."

JVM:
"I know exactly which animal each one is."

Polymorphism =
Parent reference + Child object + Overridden method
```
#### 4.5 Downcasting (The Crash Demo)
Add in the main: 
```java
public class Main {
    public static void main(String[] args) {

        Animal a = new Dog();  // UPCAST
        Dog d = (Dog) a; //DOWNCASTING
        d.wagTail();
       // a.makeSound();
    }
}

```
Output:
```java
Dog wagging tail
```
#### 4.6 Casting always works?  
1) Upcasting  → automatic & safe.

2) Downcasting → manual & risky. 

Change ONLY ONE LINE:
```java
Animal a = new Cat();
```
Full code:
```java
public class Main {

    public static void main(String[] args) {

        Animal a = new Cat();  // UPCAST

        Dog d = (Dog) a;   // DOWNCAST, dangerous cast
        d.wagTail();
    }
}
```
Runtime Error:
```java
Exception in thread "main"
java.lang.ClassCastException:
Cat cannot be cast to Dog
```
##### 4.7 Why did it crash?
Compiler checks:
```java
Is Dog related to Animal?
YES → allow compile
```
JVM checks at runtime:
```java
Is object actually a Dog?
NO → crash
```
#### 4.8 Frog Prince Analogy (Correct Version)

A prince (aka 'Dog') turned into a frog (aka `Animal`) can become a prince (aka 'Dog') again.

But not every frog ('Animal`) is secretly a prince (`Dog`).
#### 4.9 The SAFE Solution (instanceof)
Fix program:
```java
if (a instanceof Dog) {
    Dog d = (Dog) a;
    d.wagTail();
} else {
    System.out.println("Not a Dog!");
}
```
Output
```java
Not a Dog!
Java enforces type safety at compile time.
Compiler trusts TYPES
JVM trusts OBJECTS
That means:
Reference type controls ACCESS.
Object type controls BEHAVIOR.
```
## 5. Alternative Design
If you want polymorphism to call tail behavior without casting, you must declare it in the parent:
```java
abstract class Animal {
    abstract void makeSound();
    abstract void moveBody();   // shared abstraction
}
```
Then:
```java
class Dog extends Animal {
    void moveBody() { wagTail(); }
}
```
Now:
```java
Animal a = new Dog();
a.moveBody();   // polymorphic
```
No casting needed.This is proper OOP design.
#### 5.1 Polymorphism does NOT depend on inheritance — only on shared behavior contracts
Most introductory Java courses teach polymorphism using inheritance:
```java
Polymorphism = inheritance
```
But technically:
```java
Polymorphism = one interface (or contract), many implementations
```
Inheritance is just one way to create that shared contract.
#### 5.2 The REAL Requirement for Polymorphism
You only need:

A shared method definition (a contract)

Multiple objects implementing that contract differently

A reference typed as the contract

Inheritance is optional.

#### 5.3 Polymorphism needs agreement, not family relationship.

Inheritance → family tree (is-a relationship)

Contract → agreement to behave a certain way

#### 5.4 Example analogy:

Drivers, pilots, and captains are not related.

But all follow the “VehicleOperator” rules.

Same contract → polymorphism possible.

#### 5.5 Example: No Inheritance Between Classes
##### 5.5.1 Step 1 — Define a contract (interface)
```java
interface Payable {
    double calculatePay();
}
```
This is the behavior contract.
##### 5.5.2 Step 2 — Completely unrelated classes
```java
class Employee implements Payable {
    public double calculatePay() {
        return 3000;
    }
}
```
```java
class Freelancer implements Payable {
    public double calculatePay() {
        return 1500;
    }
}
```
```java
class Invoice implements Payable {
    public double calculatePay() {
        return 800;
    }
}
```
Notice:

Employee is NOT a subclass of Freelancer

Freelancer is NOT related to Invoice

No inheritance hierarchy exists.

##### 5.5.3 Step 3 — Polymorphism happens here
```java
Payable p;

p = new Employee();
System.out.println(p.calculatePay());

p = new Freelancer();
System.out.println(p.calculatePay());

p = new Invoice();
System.out.println(p.calculatePay());
```
Same variable. Same method call. Different behavior.

This is polymorphism. It works because all classes promise: 'calculatePay()`

The compiler only cares about: 'Does this object follow the Payable contract?'

Not: 'Are these classes related by inheritance?'

```java
Inheritance creates polymorphism indirectly.
Inheritance creates polymorphism indirectly.
```
Real systems rely more on interfaces than inheritqance
Examples in real Java:

`Comparable`

`Runnable`

`Serializable`

`Iterable`

These enable polymorphism without shared ancestry.

##### 5.5.4 Finally These enable polymorphism without shared ancestry. Substitutability
If an object can be substituted wherever a contract is expected, polymorphism exists.

Polymorphism is not about parents and children.

It is about promises and behavior. 

#### 5.6 Polymorphism via inheritance (Animal example)
```java
        Animal
           |
    ----------------
    |              |
   Dog            Cat
```

#### 5.7 Polymorphism via interface only (industry-style design)
```java
          SoundMaker
         /     |     \
       Dog   Alarm  BabyToy
(No relationship between implementations.)
```
#### 5.8 Why Industry Prefers Interface Polymorphism
Large systems avoid rigid hierarchies.
```java
Example from real Java:

Thread system → Runnable

Collections → Comparable

Event systems → listeners

Objects just agree to behave, not belong to one family.
```









