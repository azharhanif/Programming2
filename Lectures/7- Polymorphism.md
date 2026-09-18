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

This is legal because a `Dog` is an `Animal`.

## 1.1 Object-state / memory-map diagram

From our previous `Employee` / `Manager` example on inheritance, we recall:
```
new `Manager(...)` creates one `Manager` object.
The object has its own state, including the state defined by `Employee`.
```
For example:
```
Employee guy = new Employee("Guy", 50000);

Manager sarah = new Manager("Sarah", 70000, "Science");
```
Now the two object states look like:

```
STACK / REFERENCES                  HEAP / OBJECTS

guy ───────────────────────────►  ┌─────────────────┐
                                  │ Employee: Guy   │
                                  │ name = "Guy"    │
                                  │ salary = 50000  │
                                  └─────────────────┘


sarah ─────────────────────────► ┌──────────────────────────┐
                                 │ Manager: Sarah            │
                                 │                           │
                                 │ Employee state:           │
                                 │   name = "Sarah"          │
                                 │   salary = 70000          │
                                 │                           │
                                 │ Manager state:            │
                                 │   department = "Science" │
                                 └──────────────────────────┘
```
Note: 

-`STACK` and `HEAP` are distinct areas/concepts in Java runtime memory,

-`STACK` is a region of a program's runtime memory used mainly for method calls and local variables/references.

-`HEAP` means the part of Java's runtime memory where objects created with `new`.

 ```
             JAVA RUNTIME MEMORY
        ┌──────────────────────────┐
        │                          │
        │        STACK             │
        │  ────────────────────    │
        │  Method calls            │
        │  Local variables         │
        │  Object references       │
        │                          │
        ├──────────────────────────┤
        │                          │
        │        HEAP              │
        │  ────────────────────    │
        │  Objects created with    │
        │  new                     │
        │  Object instance data    │
        │                          │
        └──────────────────────────┘
```
-"local variable" does not mean "member/instance variable." 

They are two different kinds of variables.

#### 1. Member (instance) variable

When you have:
```
class Employee {
    private String name;
    private double salary;
}
```
and:
```
Employee guy = new Employee("Guy", 50000);
```
the instance variables belong to the `Employee` object:
```
HEAP
┌────────────────────────┐
│ Employee object        │
│                        │
│ name   = "Guy"         │
│ salary = 50000         │
└────────────────────────┘
```
So: the **object's instance data** is associated with the object in the heap.

#### 2. Local variable

Now look at the method:
```
public static void main(String[] args) {

    Employee guy = new Employee("Guy", 50000);

    int x = 10;
    double amount = 2000;

}
```
Here:
```
int x = 10;
double amount = 2000;
```
are local variables.

They don't belong to an `Employee` object.

They belong to the execution of `main()`
Conceptually:
```
STACK — main() execution
┌─────────────────────┐
│ guy      ───────────┼──────────┐
│ x = 10              │          │
│ amount = 2000       │          │
└─────────────────────┘          │
                                 ▼
HEAP                         Employee object
                             ┌───────────────┐
                             │ name = "Guy"  │
                             │ salary = 50000│
                             └───────────────┘
```
#### 3. And parameters are also local to the method

Consider:
```
public void giveRaise(double amount) {
    salary = salary + amount;
}
```
There are two very different variables here: 
- `salary` is an instance variable belonging to the `Employee` object.

- `amount` is a parameter/local variable belonging to the execution of `giveRaise()`.

Conceptually:
```
STACK — giveRaise()
┌─────────────────┐
│ amount = 2000   │
└────────┬────────┘
         │
         ▼
HEAP
┌──────────────────────┐
│ Employee object      │
│ salary = 50000       │
└──────────────────────┘
```
When `giveRaise()` finishes, its execution frame disappears, so `amount` is no longer needed.

## 1.2 From Object to Polymorphism
Consider:
```
Employee e = new Manager("Sarah", 70000, "Science");
```
Now the diagram:
```
STACK / REFERENCE                 HEAP / OBJECT

e
│
│  reference type:
│  Employee
│
└──────────────────────────────►
                              ┌───────────────────────────────┐
                              │ Manager object                │
                              ├───────────────────────────────┤
                              │ Employee-defined state        │
                              │   name   = "Sarah"            │
                              │   salary = 70000              │
                              │                               │
                              │ Manager-defined state         │
                              │   department = "Science"      │
                              └───────────────────────────────┘

```
- Reference type determines what members the program can access through the reference.
  
- Actual object type determines overridden method behavior at runtime.

For example:
```
e.getSalary();       // ✅ Employee declares getSalary()
e.giveRaise(2000);   // ✅ Employee declares giveRaise()
e.getDepartment();   // ❌ Employee does not declare getDepartment()
```
But if `Manager` overrides `toString()` when there is also another `toString()` in `Employee`:
```
System.out.println(e);
```
the `Manager` version of `toString()` executes because the actual object is a `Manager`.

Suppose `Employee` has:
```
public String getDepartment() {
    return "No department";
}
```
and `Manager` overrides it:
```
@Override
public String getDepartment() {
    return department;
}
```
Now:
```
e.getDepartment();
```
compiles, because `getDepartment()` exists in the reference type `Employee`.

And if `Manager` overrides it, the `Manager` version executes, because the actual object is a `Manager`.

So:
```
   ACCESS                         BEHAVIOR

Employee e ───────────────► Manager object
     │                            │
     │                            │
     ▼                            ▼
What can I call?             Which override runs?
Employee methods             Manager implementation
```
The reference type determines which methods you are allowed to access at compile time. 

The actual object's class determines which overridden implementation executes at runtime.

Now, 
```
`Employee` does not promise that every `Employee` has a department. A `Manager` does.

The `Employee` reference only exposes the `Employee` interface.
```
So, it is natural design that `Employee` **should not have** `getDepartment()`, **just to avoid** polymorphism concept failing. 

It's simply that:

I introduced `Employee.getDepartment()` only to illustrate the mechanics of overriding, 

but that changes the domain model and it is a poor design.

```
Polymorphism does not require the superclass to declare every method that a subclass has.

It requires the subclass to override a method that is already part of the superclass's contract.
```
In that context:

- `getDepartment()` is actually a poor example for polymorphism, if it exists only in `Manager`, 

- because it demonstrates access restriction but not runtime method selection. 

- `toString()` is much better for the polymorphism demonstration.


#### `toString()` — good example

`Employee` has:
```
@Override
public String toString() {
    return "Name: " + name + ", Salary: " + salary;
}
```
`Manager` overrides it:
```
@Override
public String toString() {
    return "Name: " + getName()
            + ", Salary: " + getSalary()
            + ", Department: " + department;
}
```
Therefore:
```
Employee e = new Manager("Sarah", 70000, "Science");

System.out.println(e);
```
The reference is `Employee`, but the actual object is `Manager`, so `Manager`'s overridden `toString()` executes.

#### Question 1 — Access
```
e.getDepartment();
```
Why doesn't this compile?

- Because the reference type is `Employee`, and `Employee` does not declare `getDepartment()`.

#### Question 2 — Behavior
```
System.out.println(e);
```
Why does `Manager`'s `toString()` execute?

- Because `toString()` is declared by `Employee` and overridden by `Manager`. The actual object is a `Manager`.

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
