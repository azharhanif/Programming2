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

Before using extends, say the relationship as a sentence:
```
A [child] is a [parent].
```
```
| Classes                   | Relationship                 | Inheritance? |
| ------------------------- | ---------------------------- | ------------ |
| `Manager` → `Employee`    | Manager **is an** Employee   | ✅            |
| `Dog` → `Animal`          | Dog **is an** Animal         | ✅            |
| `Car` → `Vehicle`         | Car **is a** Vehicle         | ✅            |
| `Car` → `Engine`          | Car **has an** Engine        | ❌            |
| `Library` → `Book`        | Library **has** Books        | ❌            |
| `Student` → `College`     | Student **attends** College  | ❌            |
| `Teacher` → `Course`      | Teacher **teaches** Course   | ❌            |
| `Person` → `Address`      | Person **has an** Address    | ❌            |
| `Department` → `Employee` | Department **has** Employees | ❌            |
```
Inheritance means the child is a specialized form of the parent. 

If the relationship is `has-a`, `uses-a`, `contains-a`, `works-with`, `teaches`, `attends`, `owns`, or `interacts-with`, inheritance is usually not the right relationship.
But "is-a" alone isn't always enough

##### Consider:

`Penguin IS AN Animal`

That's true.

So:

`class Penguin extends Animal`

is reasonable.

But suppose `Animal` has:
```
public void fly() {
    ...
}
```
Then we have a design problem because penguins don't fly.

This shows that inheritance isn't merely about finding an English `is-a` relationship.

We also need to ask:

Does the child genuinely satisfy the expectations of the parent?

This is your teaser for later encounter with polymorphism.
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

## 6. `protected`: Controlled Access for Subclasses
Suppose we have:
```
class Employee {

    public String publicName;

    protected double salary;

    private String employeeId;
}
```
Think about who can access each member:
```
| Member       | Inside `Employee` | `Manager extends Employee` | Other class |
| ------------ | ----------------- | -------------------------- | ----------- |
| `publicName` | ✅                 | ✅                          | ✅           |
| `salary`     | ✅                 | ✅                          | ❌           |
| `employeeId` | ✅                 | ❌                          | ❌           |
```
       
             ACCESS
               │
       ┌───────┼────────┐
       ↓       ↓        ↓
    private  protected  public
       │       │        │
       │       │        └── everyone
       │       │
       │       └── base + subclasses
       │
       └── base class only

##### Important Java detail

In Java, `protected` also provides access to classes in the same package. 

A `protected` member is available to the base class and its subclasses, but is not part of the ordinary public interface.

`protected` can allow subclasses to access inherited members.

However, prefer good encapsulation over making everything protected.

However, again,
```
protected is generally not "more encapsulated" than private.
private provides stronger information hiding.
The advantage of protected is that it provides controlled access specifically for an inheritance hierarchy, without making the member public.
```


## 6.2 Protected DATA — concrete example

Consider:
```
class Vehicle {
    protected int speed;

    public Vehicle() {
        speed = 0;
    }

    public void showSpeed() {
        System.out.println(speed);
    }
}
```
Now:
```
class Car extends Vehicle {

    public void accelerate() {
        speed += 10;
    }
}
```
This is allowed:
```
Car car = new Car();

car.accelerate();
car.accelerate();

car.showSpeed();
```
Output:
```
20
```
Why can Car access speed?

Because:
```
Car IS A Vehicle
```
and speed was deliberately made available to subclasses.
## 6.3 Compare with private

Now change:
```
protected int speed;
```
to:
```
private int speed;
```
Then this will not compile:
```
class Car extends Vehicle {

    public void accelerate() {
        speed += 10;       // ❌
    }
}
```
The field belongs to `Vehicle`, but `Car` cannot directly access it because it is private.

This is an important distinction:

The private member is still part of the `Vehicle` object. It is not "lost" when `Car` inherits from `Vehicle`. 

It is simply inaccessible directly from `Car`.
## 6.4 So why not always use private?

Suppose the base class wants subclasses to participate in maintaining an internal value.

For example:
```
class BankAccount {
    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }
}
```
Now imagine:
```
class SavingsAccount extends BankAccount {
    public void addInterest() {
        // How do I modify balance?
    }
}
```
Because `balance` is private, the subclass cannot directly access it.

We could provide:
```
public double getBalance()
```
and:
```
public void setBalance(double balance)
```
But now we have potentially exposed the state to every other class.

For example:
```
account.setBalance(-1000000);
```
That could be terrible design.
## 6.5 This is where a protected METHOD can be better

Instead of exposing the data publicly, the base class can provide a protected operation specifically for subclasses:
```
class BankAccount {

    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    protected void addToBalance(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
}
```
Now:
```
class SavingsAccount extends BankAccount {

    public SavingsAccount(double balance) {
        super(balance);
    }

    public void addInterest() {
        double interest = getBalance() * 0.03;
        addToBalance(interest);
    }
}
```
Notice the design:

BankAccount
    │
    ├── private balance
    │
    ├── public getBalance()
    │
    └── protected addToBalance()
             ↑
             │
       SavingsAccount

An ordinary user cannot call:
```
account.addToBalance(1000);    // ❌
```
But `SavingsAccount` can:
```
addToBalance(interest);        // ✅
```
This is a very good example of controlled encapsulation.

The base class says:

"I will allow my subclasses to perform this operation, but I don't want the general public to perform it."

That's exactly where protected is useful.
## 6.6 Why a protected method can be better than a protected field

This is an important distinction.

##### Option A — protected field
```
class BankAccount {
    protected double balance;
}
```
Now the subclass can do anything:
```
balance = -500000;
balance = 0;
balance *= 2;
balance += 100000;
```
The base class has very little control.

##### Option B — private field + protected method
```
class BankAccount {

    private double balance;

    protected void addToBalance(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
}
```
Now the base class controls how the subclass can modify the state.

That is usually better encapsulation.
## 6.7 A very concrete example: Employee bonus

Revisit `Employee/Manager` inheritance example.

Start with:
```
class Employee {

    private double salary;

    public Employee(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }
}
```
Suppose `Manager` needs to calculate a bonus based on salary.

It can use:
```
getSalary()
```
No need for protected.
```
class Manager extends Employee {

    public Manager(double salary) {
        super(salary);
    }

    public double calculateBonus() {
        return getSalary() * 0.10;
    }
}
```
This is actually better encapsulation than:
```
protected double salary;
```
because the subclass doesn't need direct access to the data.

## 6.8 So when is protected genuinely useful?

Here's a better example.

Suppose the base class has an internal operation that is useful only as part of implementing subclasses.
```
class Employee {

    private double salary;

    protected double calculateBaseBonus() {
        return salary * 0.05;
    }

    public double getSalary() {
        return salary;
    }
}
```
Then:
```
class Manager extends Employee {

    public double calculateBonus() {
        return calculateBaseBonus() + 2000;
    }
}
```
External code cannot do:
```
manager.calculateBaseBonus();   // ❌
```
because it is an implementation tool for the inheritance hierarchy.

But:
```
Manager
    ↓
calculateBonus()
    ↓
calculateBaseBonus()
```
works.

This is a strong example of why protected exists.
## 6.9 A tricky example students should predict

Consider:
```
class Person {

    private String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    protected void printAge() {
        System.out.println(age);
    }
}
```
and:
```
class Student extends Person {

    public Student(String name, int age) {
        super(name, age);
    }

    public void birthday() {
        age++;
    }

    public void showAge() {
        printAge();
    }
}
```
Then:
```
Student s = new Student("Ali", 19);

s.birthday();
s.showAge();
```
Output:
```
20
```
But:
```
s.age = 50;          // ❌ from ordinary external code
s.printAge();        // ❌ from ordinary external code
```
This demonstrates both protected data and protected methods in the same example.
## 6.10 Think about encapsulation

Question:

Which design is better?
```
Design A
--------
class Person {
    protected int age;
}

Design B
--------
class Person {

    private int age;

    protected void increaseAge() {
        age++;
    }
}
```
`Design B` gives the base class more control.

For example, the base class could enforce:
```
protected void increaseAge() {
    if (age < 150) {
        age++;
    }
}
```
The subclass doesn't get unrestricted access to the variable.

This leads to the principle:

If a subclass only needs an operation, prefer a `protected` method over a `protected field`.

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
