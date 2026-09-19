# Programming 2 --- Lab 4: Polymorphism, Upcasting & Downcasting
## Answer as comment all your questions from different parts in the PolymorphismLab.java ##
## Mention each Part A to Part J before your answers ##
**Estimated time:** 60--70 minutes\
**Format:** Individual or pair\
**Prerequisite:** Lecture 7 --- Polymorphism

## Learning Goals

By the end of this lab, you should be able to:

-   distinguish a reference type from an actual object type;
-   use upcasting safely;
-   predict overridden method calls;
-   use a polymorphic `ArrayList`;
-   explain why subclass-specific methods are not available through a
    superclass reference;
-   use `instanceof` as a runtime type check;
-   perform a safe downcast;
-   recognize why an unsafe downcast can produce `ClassCastException`.

------------------------------------------------------------------------

# Part A --- Build the Hierarchy

Create these classes:

``` text
Animal
 ├── Dog
 ├── Cat
 └── Frog
```

### `Animal`

Create `Animal` with:

-   private `String name`;
-   constructor receiving `name`;
-   `getName()`;
-   `speak()` printing `<name> makes an animal sound.`

### `Dog`

`Dog extends Animal`.

-   Call `super(...)` in the constructor.
-   Override `speak()` to print `<name> says Woof!`
-   Add `fetch()` printing `<name> is fetching.`

### `Cat`

`Cat extends Animal`.

-   Call `super(...)`.
-   Override `speak()` to print `<name> says Meow!`
-   Add `scratch()` printing `<name> is scratching.`

### `Frog`

`Frog extends Animal`.

-   Call `super(...)`.
-   Override `speak()` to print `<name> says Ribbit!`
-   Add `jump()` printing `<name> is jumping.`

------------------------------------------------------------------------

# Part B --- Reference Type vs. Actual Object Type

In `PolymorphismLab`:

``` java
Animal a1 = new Dog("Buddy");
Animal a2 = new Cat("Mittens");
Animal a3 = new Frog("Kermit");
```

Before running, complete:

  Variable   Reference type   Actual object type
  ---------- ---------------- --------------------
  `a1`       ?                ?
  `a2`       ?                ?
  `a3`       ?                ?

Then run:

``` java
a1.speak();
a2.speak();
a3.speak();
```

Write a 2--3 sentence comment explaining why different implementations
execute even though all three references have type `Animal`.

------------------------------------------------------------------------

# Part C --- Polymorphic ArrayList

Create:

``` java
ArrayList<Animal> animals = new ArrayList<>();
```

Add:

``` java
new Dog("Buddy")
new Cat("Mittens")
new Frog("Kermit")
new Dog("Rex")
new Cat("Luna")
```

Then:

``` java
for (Animal animal : animals) {
    animal.speak();
}
```

Predict the output before running.

Explain why `ArrayList<Animal>` can contain Dog, Cat, and Frog objects,
but `ArrayList<Dog>` cannot contain a Cat.

------------------------------------------------------------------------

# Part D --- The Reference Type Controls What You Can Request

Use:

``` java
Animal animal = new Dog("Buddy");
```

This works:

``` java
animal.speak();
```

Temporarily try:

``` java
animal.fetch();
```

Explain the compiler error. Your answer must mention the reference type,
actual object type, and where `fetch()` is declared.

Remove or comment out the invalid line.

------------------------------------------------------------------------

# Part E --- Upcasting

Use:

``` java
Dog dog = new Dog("Buddy");
Animal animal = dog;
```

Answer:

1.  How many objects were created?
2.  What is the reference type of `animal`?
3.  What is the actual object type?
4.  Do `dog` and `animal` refer to the same object?

Then run:

``` java
animal.speak();
```

Do not write `(Animal) dog`. Explain why the explicit cast is
unnecessary.

------------------------------------------------------------------------

# Part F --- Downcasting

Use:

``` java
Animal animal = new Dog("Buddy");
```

This does not compile:

``` java
animal.fetch();
```

Downcast safely:

``` java
Dog dog = (Dog) animal;
dog.fetch();
```

Explain:

1.  Why is the downcast valid?
2.  Did the cast create a new Dog object?
3.  What is the actual object before and after the cast?

------------------------------------------------------------------------

# Part G --- `instanceof` Safety Check

Create:

``` java
Animal first = new Dog("Buddy");
Animal second = new Cat("Mittens");
```

Use:

``` java
if (first instanceof Dog) {
    Dog dog = (Dog) first;
    dog.fetch();
}
```

Write a similar check for `second`, but do not cast `second` to Dog when
the check is false.

Explain what `instanceof` checks. Your explanation must make clear that
it checks the actual object, not merely the declared reference type.

------------------------------------------------------------------------

# Part H --- Tricky Case: Unsafe Downcasting

Study:

``` java
Animal animal = new Cat("Mittens");
Dog dog = (Dog) animal;
dog.fetch();
```

Predict what happens, then run it.

You should observe `ClassCastException`.

Explain why using:

-   reference type;
-   actual object type.

------------------------------------------------------------------------

------------------------------------------------------------------------

# Part I --- One Polymorphic Method

Create:

``` java
public static void makeAnimalSpeak(Animal animal) {
    animal.speak();
}
```

Call:

``` java
makeAnimalSpeak(new Dog("Buddy"));
makeAnimalSpeak(new Cat("Mittens"));
makeAnimalSpeak(new Frog("Kermit"));
```

Explain why one method can accept all three objects. Use the is-a
relationship and polymorphism.

------------------------------------------------------------------------

# Part J --- Challenge

Use one list:

``` java
ArrayList<Animal> animals;
```

Write:

``` java
public static void makeAllAnimalsSpeak(ArrayList<Animal> animals)
```

The method should make every animal speak using one loop.

Explain why this is more flexible than maintaining separate Dog, Cat,
and Frog lists. If a new `Bird extends Animal` is later added with its
own `speak()`, what changes are needed in `makeAllAnimalsSpeak()`?

------------------------------------------------------------------------

# Submission Checklist

Submit:

``` text
Animal.java
Dog.java
Cat.java
Frog.java
PolymorphismLab.java
```

Your program should demonstrate:

-   inheritance;
-   constructor chaining with `super`;
-   method overriding;
-   reference type vs. actual object type;
-   upcasting;
-   polymorphic `ArrayList`;
-   subclass-specific methods;
-   `instanceof`;
-   safe downcasting;
-   understanding of `ClassCastException`;


------------------------------------------------------------------------

# AI-Assisted Practice

Ask an AI to explain:

``` java
ArrayList<Animal> animals = new ArrayList<>();

animals.add(new Dog("Buddy"));
animals.add(new Cat("Mittens"));
animals.add(new Frog("Kermit"));

for (Animal animal : animals) {
    animal.speak();
}
```

Then ask it to introduce one unsafe downcast.

Your job is to:

1.  predict the failure;
2.  identify the actual object type;
3.  explain why the cast is unsafe;
4.  repair the code using `instanceof`;
5.  explain why polymorphism is preferable to repeated `instanceof`
    chains.

Do not simply copy the AI explanation. Test the code and explain the
result in your own words.
