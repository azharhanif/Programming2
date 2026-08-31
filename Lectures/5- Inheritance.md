# Programming 2 — Lecture 5: Inheritance

## Learning objectives

You should be able to:

- explain the "is-a" relationship;
- create a subclass with `extends`;
- identify superclass and subclass;
- understand inherited members;
- use `super`;
- understand constructor chaining;
- override methods correctly;
- distinguish overriding from overloading;
- recognize when inheritance is inappropriate;
- reason about the inheritance hierarchy before coding.

---

## 1. Why inheritance?

Inheritance helps reduce duplicated code when one class is a specialized form of another.

If:

> A Cat is an Animal

then:

```java
class Cat extends Animal {
}
```

`Animal` is the superclass. `Cat` is the subclass.

Inheritance represents an **is-a** relationship.

---

## 2. Is-a versus has-a

This is inheritance:

```java
class Dog extends Animal {
}
```

A Dog **is an** Animal.

This is composition/containment:

```java
class Library {
    private ArrayList<Book> books;
}
```

A Library **has** Books.

Do not use inheritance simply because two classes are related in English.

---

## 3. A basic example

```java
class Animal {

    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println(name + " eats.");
    }
}

class Dog extends Animal {

    public Dog(String name) {
        super(name);
    }

    public void bark() {
        System.out.println(name + " barks.");
    }
}
```

Now:

```java
Dog d = new Dog("Rex");

d.eat();   // inherited
d.bark();  // Dog's own method
```

---

## 4. `super`

`super` refers to the superclass portion of the current object.

### Constructor

```java
public Dog(String name) {
    super(name);
}
```

This calls:

```java
Animal(String name)
```

before the Dog constructor finishes.

### Method

If the subclass overrides a method:

```java
@Override
public void eat() {
    super.eat();
    System.out.println("Dog eats quickly.");
}
```

`super.eat()` calls the superclass implementation.

---

## 5. Constructor chaining

When a Dog is created:

```java
Dog d = new Dog("Rex");
```

the constructor chain is conceptually:

```text
Dog constructor
    ↓
Animal constructor
    ↓
Animal fields initialized
    ↓
Dog constructor continues
```

A subclass object contains its inherited state as well as its own state.

---

## 6. `protected`

`protected` can allow subclasses to access inherited members.

However, prefer good encapsulation over making everything protected.

Often the superclass should expose behavior through methods:

```java
public String getName() {
    return name;
}
```

rather than allowing subclasses to freely modify the field.

---

## 7. Method overriding

A subclass can provide a specialized implementation of an inherited method.

```java
class Animal {
    public void speak() {
        System.out.println("Some sound");
    }
}

class Dog extends Animal {

    @Override
    public void speak() {
        System.out.println("Woof");
    }
}
```

The method has the same name and parameter list.

Use `@Override`. It allows the compiler to catch many accidental signature mistakes.

---

## 8. Overriding versus overloading

### Overriding

Occurs between superclass and subclass.

```java
class Animal {
    void move() { }
}

class Dog extends Animal {
    @Override
    void move() { }
}
```

### Overloading

Same class name, different parameter lists:

```java
void move()
void move(int distance)
```

Return type alone does not create overloading.

---

## 9. What is inherited?

A subclass inherits accessible members of the superclass, but constructors are not inherited.

Private fields are also not directly accessible from the subclass.

This:

```java
class Animal {
    private int age;
}
```

does not allow:

```java
class Dog extends Animal {
    void test() {
        age = 5; // ❌
    }
}
```

Use an appropriate protected/public method:

```java
public void setAge(int age) {
    this.age = age;
}
```

or another controlled design.

---

## 10. Inheritance and object design

Before writing:

```java
class B extends A
```

ask:

> Is every B genuinely an A?

If not, inheritance is probably the wrong design.

For example:

```text
Car extends Vehicle     → reasonable
Dog extends Animal      → reasonable
Library extends Book    → wrong
```

A library contains books; it is not a book.

---

# Practice

## Practice 1

Identify the superclass and subclass:

```java
class ElectricCar extends Car {
}
```

### Answer

- `Car` = superclass
- `ElectricCar` = subclass

---

## Practice 2

What is printed?

```java
class A {
    public void show() {
        System.out.println("A");
    }
}

class B extends A {
    @Override
    public void show() {
        System.out.println("B");
    }
}

B b = new B();
b.show();
```

### Answer

```text
B
```

The subclass overrides `show()`.

---

## Practice 3 — constructor chain

```java
class A {
    public A() {
        System.out.println("A");
    }
}

class B extends A {
    public B() {
        System.out.println("B");
    }
}

new B();
```

### Answer

```text
A
B
```

The superclass constructor executes before the subclass constructor.

---

## Practice 4 — tricky

What is wrong?

```java
class Animal {
    private String name;
}

class Dog extends Animal {
    public void print() {
        System.out.println(name);
    }
}
```

### Answer

`name` is private to `Animal`; Dog cannot directly access it.

Use:

```java
public String getName() {
    return name;
}
```

and:

```java
System.out.println(getName());
```

---

## Practice 5 — design

Should this use inheritance?

```text
Hospital contains Patient objects.
```

### Answer

No. A hospital **has** patients. This is containment/composition, not an is-a relationship.

---

# AI-assisted practice

Ask AI to create an `Employee` superclass and `Manager` subclass.

Then inspect:

- constructor chaining;
- visibility of fields;
- use of `super`;
- overriding;
- whether any field should be private;
- whether inheritance really represents an is-a relationship.

Modify the AI solution so that all fields are properly encapsulated.
