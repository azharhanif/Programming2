# Programming 2 — Lecture 7: Polymorphism

## Learning objectives

You should be able to:

- explain polymorphism in Java;
- distinguish declared/reference type from actual object type;
- assign subclass objects to superclass references;
- explain dynamic method dispatch;
- predict overridden method calls;
- understand polymorphic arrays and `ArrayList`s;
- distinguish what is available through the reference type from what is executed by the object type;
- avoid unsafe casts.

---

## 1. The central idea

Polymorphism allows a superclass reference to refer to an object of a subclass.

```java
Animal a = new Dog();
```

Here:

```text
reference/declared type → Animal
actual object type      → Dog
```

This is legal because a Dog is an Animal.

---

## 2. Overridden method selection

```java
class Animal {
    public void speak() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {
    @Override
    public void speak() {
        System.out.println("Woof");
    }
}
```

Now:

```java
Animal a = new Dog();
a.speak();
```

prints:

```text
Woof
```

Why?

The compiler sees an `Animal` reference, but at runtime the actual object is a `Dog`.

The overridden method is selected dynamically.

---

## 3. Reference type controls what you can call

Suppose:

```java
class Dog extends Animal {

    public void bark() {
        System.out.println("Woof");
    }
}

Animal a = new Dog();
```

This is not allowed:

```java
a.bark(); // ❌
```

The reference type `Animal` does not declare `bark()`.

But:

```java
a.speak();
```

is allowed if `speak()` is declared in Animal.

This creates a crucial distinction:

```text
Reference type
    ↓
What methods can I request?

Actual object type
    ↓
Which overridden implementation runs?
```

---

## 4. Polymorphic collections

```java
ArrayList<Animal> animals = new ArrayList<>();

animals.add(new Dog());
animals.add(new Cat());
animals.add(new Frog());
```

Then:

```java
for (Animal animal : animals) {
    animal.speak();
}
```

Each object can respond differently.

This is one of the most powerful uses of polymorphism.

---

## 5. Upcasting

This is safe:

```java
Dog dog = new Dog();
Animal animal = dog;
```

It is called upcasting.

You can think:

```text
Dog
 ↓
Animal
```

because every Dog is an Animal.

---

## 6. Downcasting

The reverse requires caution:

```java
Animal animal = new Dog();

Dog dog = (Dog) animal;
```

This is valid because the actual object really is a Dog.

But:

```java
Animal animal = new Cat();

Dog dog = (Dog) animal; // ❌ ClassCastException
```

The reference type alone does not guarantee the cast is valid.

Use:

```java
if (animal instanceof Dog) {
    Dog dog = (Dog) animal;
}
```

when a runtime type check is appropriate.

---

## 7. Why polymorphism matters

Without polymorphism, you might write:

```java
if (animal instanceof Dog) {
    ...
} else if (animal instanceof Cat) {
    ...
}
```

everywhere.

With polymorphism:

```java
animal.speak();
```

lets each subclass define its behavior.

This reduces conditional logic and makes systems easier to extend.

---

## 8. Constructor parameters can also be polymorphic

If:

```java
public Animal(Animal other) {
    ...
}
```

a Dog can be passed where an Animal is required:

```java
Dog dog = new Dog(...);
Animal animal = new Animal(dog);
```

because a Dog is an Animal.

This is another manifestation of the "is-a" relationship.

---

# Practice

## Practice 1

What is printed?

```java
Animal a = new Dog();
a.speak();
```

### Answer

The Dog's overridden `speak()` executes.

---

## Practice 2

Is this legal?

```java
Animal a = new Dog();
a.bark();
```

### Answer

Only if `bark()` is declared in `Animal`. If it exists only in Dog, the code does not compile.

---

## Practice 3

What happens?

```java
Animal a = new Cat();
Dog d = (Dog) a;
```

### Answer

`ClassCastException` at runtime because the actual object is a Cat.

---

## Practice 4 — tricky

What is printed?

```java
class Animal {
    public void show() {
        System.out.println("Animal");
    }
}

class Dog extends Animal {
    @Override
    public void show() {
        System.out.println("Dog");
    }
}

Animal a = new Dog();
Dog d = new Dog();

a.show();
d.show();
```

### Answer

```text
Dog
Dog
```

Both actual objects are Dogs.

---

## Practice 5 — design challenge

Why is this better than maintaining separate lists?

```java
ArrayList<Dog> dogs;
ArrayList<Cat> cats;
ArrayList<Frog> frogs;
```

when all objects support:

```java
speak()
```

### Answer

A polymorphic list:

```java
ArrayList<Animal> animals;
```

can hold all three. The caller can simply call:

```java
for (Animal a : animals) {
    a.speak();
}
```

Each subclass provides its own behavior.

---

# AI-assisted practice

Ask an AI to design an `Animal` hierarchy with `Dog`, `Cat`, and `Frog`.

Then deliberately ask it to introduce an unsafe cast.

Your job:

1. predict the failure;
2. identify the actual object type;
3. repair the code;
4. explain why polymorphism is preferable to repeated `instanceof` chains.
