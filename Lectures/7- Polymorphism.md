# Programming 2 — Lecture 7: Polymorphism

## Learning objectives

You should be able to:

- explain polymorphism in Java, stack vs heap;
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
                                  │ Employee: Guy 	│
  │								  │		......			│
                                  │ name = "Guy"    │
                                  │ salary = 50000  │
                                  └─────────────────┘


sarah ─────────────────────────► ┌──────────────────────────┐
                                 │ Manager: Sarah           │
                                 │       .....                   │
                                 │ Employee state:          │
                                 │   name = "Sarah"         │
                                 │   salary = 70000         │
                                 │                          │
                                 │ Manager state:           │
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

#### A. Member (instance) variable

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

#### B. Local variable

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
#### C. And parameters are also local to the method

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
- Reference type determines what members the program can **access** through the reference.
  
- Actual object type determines overridden method **behavior** at runtime.

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


#### A. `toString()` — good example

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

#### B. Question 1 — Access
```
e.getDepartment();
```
Why doesn't this compile?

- Because the reference type is `Employee`, and `Employee` does not declare `getDepartment()`.

#### C. Question 2 — Behavior
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

## 3. Reference type controls what you can call (repeating from Section 1.2 above)

Suppose:

```java
class Dog extends Animal {

    public void bark() {
        System.out.println("Woof");
    }
}

Animal a = new Dog();
```

Now, the reference type `Animal` does not declare `bark()`. So, this is not allowed:

```java
a.bark(); // ❌
```

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

## 4. Polymorphic Collections

One of the most useful applications of polymorphism is, 

putting objects of different subclasses into the same collection.

Suppose we have:
```
class Employee {
    private String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void work() {
        System.out.println(name + " is working.");
    }
}
```
and:
```
class Manager extends Employee {
    public Manager(String name) {
        super(name);
    }

    @Override
    public void work() {
        System.out.println(getName() + " is managing.");
    }
}
```
and:
```
class Programmer extends Employee {
    public Programmer(String name) {
        super(name);
    }

    @Override
    public void work() {
        System.out.println(getName() + " is programming.");
    }
}
```
Because:
```
Manager IS-A Employee
Programmer IS-A Employee
```
we can write:
```
ArrayList<Employee> employees = new ArrayList<>();

employees.add(new Employee("John"));
employees.add(new Manager("Sarah"));
employees.add(new Programmer("Mike"));
```
Notice something important:
```
ArrayList<Employee>
        │
        ├── Employee object
        ├── Manager object
        └── Programmer object
```
The collection's type is `Employee`, but the actual objects can be different subclasses.

## 4.1 What Happens When We Loop?
```
for (Employee e : employees) {
    e.work();
}
```
Output:
```
John is working.
Sarah is managing.
Mike is programming.
```
## 4.2 Why does Java call different versions of work()?

Because `work()` is overridden.

At runtime Java looks at the actual object, not merely the reference type.
```
Reference type       Actual object
────────────────     ──────────────
Employee e     ───►  Employee
Employee e     ───►  Manager
Employee e     ───►  Programmer
```
Therefore:
```
e.work();
```
can execute:
```
Employee.work()
Manager.work()
Programmer.work()
```
depending on the actual object.

This is runtime polymorphism.

## 4.3 Tricky Case: The Collection Type Does NOT Change the Objects

Consider:
```
ArrayList<Employee> employees = new ArrayList<>();

employees.add(new Manager("Sarah"));
```
somehow converts the `Manager` into an `Employee` object?

- It does not.

The object is still a `Manager`.
```
ArrayList<Employee>
        │
        ▼
     references

        ├────────► Employee object
        │
        ├────────► Manager object
        │
        └────────► Programmer object
```
The `ArrayList<Employee>` means:

"This collection can hold references that are compatible with `Employee`."

It does not mean:

"Every object stored here must actually be an `Employee` object created with `new Employee()`."




## 4.4 Tricky Case: `Manager`-Specific Methods

Suppose `Manager` has:
```
public void holdMeeting() {
    System.out.println("Manager is holding a meeting.");
}
```

Now:
```
ArrayList<Employee> employees = new ArrayList<>();

employees.add(new Manager("Sarah"));
```
This does not allow:
```
for (Employee e : employees) {
    e.holdMeeting();       // ❌ Does not compile
}
```
Why?

- Because `e` is an `Employee` reference, and `holdMeeting()` is not defined in Employee.

The actual object being referred to might be a `Manager`, 

but the compiler cannot assume that every `Employee` is a `Manager`.

## 4.5 — instanceof and a Preview of Downcasting

If we need to access a method that exists only in `Manager`, we need to first determine whether the object is actually a `Manager`.

For example:
```
for (Employee e : employees) {

    if (e instanceof Manager) {
        Manager m = (Manager) e;
        m.holdMeeting();
    }
}
```
The expression:
```
e instanceof Manager
```
asks:

"Is the object currently referred to by `e` actually a `Manager`?"

If the answer is yes, we can cast the `Employee` reference to a `Manager` reference:
```
Manager m = (Manager) e;
```
This is called downcasting because we are moving from the more general type (`Employee`) to the more specific type (`Manager`).
```
        Employee
           ▲
           │
        Manager

Employee reference
       │
       ▼
Manager reference
       │
       ▼
Manager object
```
#### A note about casting

You have already seen the idea of assigning a subclass object to a superclass reference:
```
Employee e = new Manager("Sarah");
```
This is called upcasting:
```
Manager → Employee
```
It is safe because every `Manager` is an `Employee`.

The reverse:
```
Manager m = (Manager) e;
```
is called downcasting:
```
Employee → Manager
```
It requires an explicit cast because not every `Employee` is a `Manager`.

More on Casting later. Here, we only need enough casting to understand why `instanceof` may be followed by a downcast.


## 4.6 Important design warning

Do not automatically use `instanceof` every time you encounter polymorphism.

If the goal is simply:
```
e.work();
```
and each subclass overrides `work()`, polymorphism already solves the problem.

The whole point is to avoid writing:
```
if (e instanceof Manager) {
    ...
}
else if (e instanceof Programmer) {
    ...
}
```
when the behavior can naturally be handled through overriding.

## 4.7 Common Mistake: Wrong Collection Type

This will not work:
```
ArrayList<Manager> employees = new ArrayList<>();

employees.add(new Manager("Sarah"));
employees.add(new Programmer("Mike"));   // ❌
```
Why?

- Because a `Programmer` is an `Employee`, but a `Programmer` is not a `Manager`.

The correct common superclass is:
```
ArrayList<Employee> employees = new ArrayList<>();
```
Then:
```
employees.add(new Manager("Sarah"));
employees.add(new Programmer("Mike"));
```
works.

```
A polymorphic collection uses a common superclass reference type to store objects of different subclass types, 

allowing the same method call to produce different behavior depending on the actual object.
```
---

## 5. Upcasting

Upcasting means using a reference of a more general superclass type to refer to an object of a more specific subclass type.

For example:
```
Employee e = new Manager("Sarah", 70000, "Science");
```
Here:
```
Reference type:     Employee
Actual object type: Manager
```
Because a `Manager` is an `Employee`, Java allows the `Manager` object to be referenced as an `Employee`.

## 5.1 Why Is Upcasting Safe?

Suppose a method expects an `Employee`:
```
public static void printEmployee(Employee e) {
    System.out.println(e.getName());
}
```
We can pass a `Manager`:
```
Manager sarah =
    new Manager("Sarah", 70000, "Science");

printEmployee(sarah);
```
This is safe because every `Manager` satisfies the requirements of an `Employee`.

The general rule is:

A subclass object can safely be treated as an object of its superclass type.

## 5.2 The Frog Prince Analogy

Imagine a fairy tale:

A `Prince` is magically turned into a `Frog`.

For our analogy:

`Prince` = **specific** type

`Frog`   = more **general** type

The `Prince` is now being treated as a `Frog`.

But there is an important detail:

The `Prince` did not become a completely different individual. 

It is still that same `Prince`.

Similarly, in Java:
```
Employee e = new Manager("Sarah", 70000, "Science");
```
does not create an `Employee` object and a `Manager` object.

There is one object:
```
                    ONE OBJECT
                ┌─────────────────┐
                │ Manager object  │
                │                 │
                │ name = Sarah    │
                │ salary = 70000  │
                │ department =    │
                │   Science       │
                └─────────────────┘
                         ▲
                         │
                    Employee e
```
The reference `e` simply looks at that `Manager` object through the more **general** `Employee` type.

Fairy-tale translation
```
Prince
  ↓
turned into a frog
  ↓
still the same Prince
  ↓
now being treated as a Frog
```
Java:
```
Manager object
  ↓
Employee reference
  ↓
still the same Manager object
  ↓
now being treated as an Employee
```
## 5.3 Upcasting Does Not Create a New Object

Consider:
```
Manager sarah =
    new Manager("Sarah", 70000, "Science");

Employee e = sarah;
```
There is only one `Manager` object.
```
STACK / REFERENCES                 HEAP

sarah ────────────────┐
                      │
e ────────────────────┼────────► Manager object
                      │          ┌─────────────────────┐
                      └─────────►│ name = Sarah        │
                                 │ salary = 70000      │
                                 │ department = Science│
                                 └─────────────────────┘
```
The assignment:
```
Employee e = sarah;
```
copies the reference, not the object.

## 5.4 What Can We Access After Upcasting?

Consider:
```
Employee e =
    new Manager("Sarah", 70000, "Science");
```
Because `e` is an `Employee` reference:

e.getName();       // ✓
e.getSalary();     // ✓
e.giveRaise(2000); // ✓

But if `getDepartment()` exists only in `Manager`:
```
e.getDepartment();    // ❌
```
Why?

Because the compiler sees:
```
Employee e
```
The object is still a `Manager`, but the `Employee` reference only provides access to members available through `Employee`.

Upcasting does not erase the object's actual type. 

It gives us a more **general** way to refer to the object.

## 5.6 Common Mistakes

#### Mistake 1 — "`Manager`-specific information disappeared."

It did not.

The `Manager` object still has its `Manager`-specific state.

The `Employee` reference simply cannot directly access `Manager`-only members.

#### Mistake 2 — Explicitly casting unnecessarily

```
Employee e = (Employee) new Manager("Sarah");
```
The cast is unnecessary.

Simply write:
```
Employee e = new Manager("Sarah");
```
Java performs this safe upcast automatically.

## 6. Downcasting

Downcasting means using a superclass reference as a more specific subclass reference.

For example:
```
Employee e =
    new Manager("Sarah", 70000, "Science");

Manager m = (Manager) e;
```  
The direction is reversed:
```
Employee
    ↓
Manager
```
Unlike upcasting, downcasting requires an **explicit** cast.

## 6.1 The Frog Prince Analogy

Imagine there are several frogs:
```
Frog #1 → actually the Prince
Frog #2 → ordinary frog
Frog #3 → ordinary frog
```
The `Prince` was magically turned into `Frog #1`.

In Java terms, we can think of this as:
```
Employee e = new Manager("Sarah", 70000, "Science");
```
The `Employee` reference is like saying:

`I see a frog.`

But the actual object is a `Manager` — our `Prince`.

Now suppose the `Princess` knows that `Frog #1` is the `Prince`.

#### She gives `Frog #1` a kiss.

The kiss represents the **explicit cast**:
```
Manager m = (Manager) e;
```
The kiss does not create or transform the object. It allows us to treat that particular object again through its more specific identity.

Before the kiss:
```
Employee reference
       │
       ▼
   Frog #1
   (actually Prince)


Princess's Kiss
      ↓
 explicit cast
      ↓

```
After the kiss:
```
Manager reference
       │
       ▼
   Same object
   (still the Prince)
```
#### What about `Frog #2`?

`Frog #2` was never the `Prince`.

The Princess could kiss `Frog #2`, but that does not make it the `Prince`.

Similarly:
```
Employee e = new Programmer("Mike");

Manager m = (Manager) e;
```
The cast cannot make the `Programmer` object into a `Manager`.

It results in:

`ClassCastException`

So the important lesson is:

The Princess's kiss can reveal the `Prince` only because `Frog #1` was already the `Prince`. The kiss cannot transform an ordinary frog into the `Prince`.

And the Java equivalent:
```
A downcast does not transform an object into the target subclass. 

It is valid only when the actual object was already an instance of that subclass.
```
#### Where does instanceof fit?

This makes `instanceof` easy to explain:
```
if (e instanceof Manager) {
    Manager m = (Manager) e;
}
```
Think:
```
Princess:
"Is THIS frog actually the Prince?"
             │
             ▼
        instanceof
             │
             ▼
           YES
             │
             ▼
      Princess's Kiss
             │
             ▼
      explicit downcast
```
So :
```
instanceof = identifying the Prince
(Manager) e = the Princess's kiss
Downcasting = treating that known Prince/frog as the Prince again
```
- `instanceof` is not itself part of downcasting.

- It is a safety check that can be performed before a downcast when the actual type isn't already known.
```
Frog #1 → actually Prince
     ↓
instanceof → true
     ↓
Kiss → downcast succeeds


Frog #2 → never Prince
     ↓
instanceof → false
     ↓
No kiss / no downcast
```
## 6.2 The Java Version of the Story

The Frog Prince Casting Story
```
Upcasting:
Prince → Frog
"I can safely treat the Prince as a frog."

instanceof:
"Wait... is THIS frog actually my Prince?" 

Downcasting:
Princess's Kiss 
"Yes! This particular frog was already the Prince."

Invalid downcasting:
"This frog was never the Prince." → ClassCastException
```
Note the phrase **this particular frog** because it reinforces the most important technical concept: 
```
the cast depends on the actual object, not simply on the reference type.
```
## 6.3 But Not Every `Frog` Is the `Prince`

Now consider:
```
Employee e = new Programmer("Mike");
```
The reference type is:

`Employee`

but the actual object is:

`Programmer`

If we try:
```
Manager m = (Manager) e;
```
Java cannot make this work.

Why?

- Because this particular `Employee` object was never a `Manager`.

This is the most important idea in **downcasting**:

Not every superclass object is actually an instance of the subclass.

Or, using the analogy:

Not every frog is secretly the Prince.

#### Common Mistake — Casting when polymorphism already solves the problem

Suppose:
```
class Employee {
    public void work() {
        System.out.println("Employee working");
    }
}
```
and `Manager` overrides `work()`.

Then:
```
Employee e = new Manager("Sarah");
e.work();
```
already gives us the `Manager`'s behavior.

We do not need:
```
Manager m = (Manager) e;
m.work();
```
The first example is polymorphism.

The second example uses downcasting unnecessarily.
```
Use polymorphism when you want common behavior. 

Use downcasting when you genuinely need subclass-specific functionality.
```
## 6.10 Upcasting vs. Downcasting
	
Upcasting is safe because every subclass object is also a superclass object.

Downcasting is safe only when the particular object being referenced really is an instance of the target subclass.

---

## 7. Constructor parameters can also be polymorphic

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
