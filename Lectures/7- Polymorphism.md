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
## Why use abstract class Animal?
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
#### Without abstract method
```java
class Animal {
    void makeSound() {
        System.out.println("Some sound");
    }
}
```
❌ polymorphism is optional overriding.
#### With abstract method
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
## Classroom demo (reference type vs object type, runtime method selection)
✅ Java decides WHAT you can call using the reference type

✅ but decides WHAT actually runs using the object type

#### Step 1 — with NO Polymorphism
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

        Dog d = new Dog();

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

        Animal a = new Dog();

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

#### Step 3 — Compiler Restriction
Add:
```java
a.wagTail();
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

#### Step 5 — TRUE Polymorphism
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
```
#### POLYMORPHISM: ONE loop. ONE method call. DIFFERENT behaviors.
```java
Compiler:
"I only know they are Animals."

JVM:
"I know exactly which animal each one is."

Polymorphism =
Parent reference + Child object + Overridden method
```
#### Downcasting (The Crash Demo)
Add in the main: 
```java
Dog d = (Dog) a;
d.wagTail();
```
Output:
```java
Dog wagging tail
```
#### Casting always works? No. The Crash Demo
Change ONLY ONE LINE:
```java
Animal a = new Cat();
```
Full code:
```java
public class Main {

    public static void main(String[] args) {

        Animal a = new Cat();

        Dog d = (Dog) a;   // dangerous cast
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
##### Why did it crash?
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







