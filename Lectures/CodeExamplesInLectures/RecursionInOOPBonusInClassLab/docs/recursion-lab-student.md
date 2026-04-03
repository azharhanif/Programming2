# Programming 2 Lab — Recursion with Familiar OOP Designs

## Time
- Warm-up bridge: 5 minutes  
- Main lab: 30 minutes  
- Exercise 1: 10 minutes  
- Exercise 2: 10 minutes  

---

## Important rule for this lab
For **this lab**, every recursive solution should be written **without using loops**.

Why?
- The recursion lecture explains that recursion is another way to write a repeated structure.
- If you mix a loop into the main recursive logic, it becomes harder to clearly identify the **base case** and the **general pattern**.

So in this lab, each recursive method must:
1. clearly label the **base case**
2. clearly label the **general pattern**
3. avoid `for` and `while`

---

## Goal
In the polymorphism lecture, you saw that one parent reference can refer to different child objects, and the same method call can behave differently depending on the real object.

In the recursion lecture, you learned that every recursive method must contain:
- a **base case**
- a **general pattern**

This lab combines both ideas.

---

# Part 0 — Warm-up Bridge from Polymorphism to Recursion

## Task
Complete the recursive method below. Do **not** use a loop.

```java
abstract class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public abstract void makeSound();
}

class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " says woof");
    }
}

class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " says meow");
    }
}

public class Main {
    public static void playSounds(Animal[] animals, int index) {
        // BASE CASE:

        // GENERAL PATTERN:
    }

    public static void main(String[] args) {
        Animal[] animals = {
            new Dog("Rocky"),
            new Cat("Milo"),
            new Dog("Luna")
        };

        playSounds(animals, 0);
    }
}
```

## Questions
1. What is the base case?
2. What is the general pattern?
3. Why is this still polymorphism?

---

# Main Lab — Recursive Animal Shelter

## Big idea
An animal shelter can contain:
- simple animal objects
- section objects
- and a section can contain more shelter units

That means:
- **Animal objects** act as simple known-answer cases
- **Section objects** act as recursive container cases

---

## Starter Code

### ShelterUnit.java
```java
abstract class ShelterUnit {
    protected String name;

    public ShelterUnit(String name) {
        this.name = name;
    }

    public abstract int countAnimals();
    public abstract int totalFoodPerDay();
    public abstract void printStructure(String indent);
    public abstract boolean containsAnimal(String targetName);
}
```

### Animal.java
```java
abstract class Animal extends ShelterUnit {
    protected int foodPerDay;

    public Animal(String name, int foodPerDay) {
        super(name);
        this.foodPerDay = foodPerDay;
    }

    @Override
    public int countAnimals() {
        // BASE CASE: one animal counts as 1
        return 1;
    }

    @Override
    public int totalFoodPerDay() {
        // BASE CASE: one animal already knows its own food amount
        return foodPerDay;
    }

    @Override
    public boolean containsAnimal(String targetName) {
        // BASE CASE: compare this single animal name with target
        return name.equalsIgnoreCase(targetName);
    }
}
```

### Dog.java
```java
class Dog extends Animal {
    public Dog(String name, int foodPerDay) {
        super(name, foodPerDay);
    }

    @Override
    public void printStructure(String indent) {
        // BASE CASE: print one simple animal line
        System.out.println(indent + "Dog: " + name + " (" + foodPerDay + " food)");
    }
}
```

### Cat.java
```java
class Cat extends Animal {
    public Cat(String name, int foodPerDay) {
        super(name, foodPerDay);
    }

    @Override
    public void printStructure(String indent) {
        // BASE CASE: print one simple animal line
        System.out.println(indent + "Cat: " + name + " (" + foodPerDay + " food)");
    }
}
```

### Bird.java
```java
class Bird extends Animal {
    public Bird(String name, int foodPerDay) {
        super(name, foodPerDay);
    }

    @Override
    public void printStructure(String indent) {
        // BASE CASE: print one simple animal line
        System.out.println(indent + "Bird: " + name + " (" + foodPerDay + " food)");
    }
}
```

### Section.java
```java
import java.util.ArrayList;

class Section extends ShelterUnit {
    private ArrayList<ShelterUnit> units;

    public Section(String name) {
        super(name);
        units = new ArrayList<>();
    }

    public void addUnit(ShelterUnit unit) {
        units.add(unit);
    }

    @Override
    public int countAnimals() {
        return countAnimalsHelper(0);
    }

    private int countAnimalsHelper(int index) {
        // BASE CASE:

        // GENERAL PATTERN:
    }

    @Override
    public int totalFoodPerDay() {
        return totalFoodPerDayHelper(0);
    }

    private int totalFoodPerDayHelper(int index) {
        // BASE CASE:

        // GENERAL PATTERN:
    }

    @Override
    public void printStructure(String indent) {
        System.out.println(indent + "Section: " + name);
        printStructureHelper(indent, 0);
    }

    private void printStructureHelper(String indent, int index) {
        // BASE CASE:

        // GENERAL PATTERN:
    }

    @Override
    public boolean containsAnimal(String targetName) {
        return containsAnimalHelper(targetName, 0);
    }

    private boolean containsAnimalHelper(String targetName, int index) {
        // BASE CASE:

        // GENERAL PATTERN:
    }
}
```

### Main.java
```java
public class Main {
    public static void main(String[] args) {
        Section shelter = new Section("City Shelter");

        Section dogsRoom = new Section("Dogs Room");
        dogsRoom.addUnit(new Dog("Rocky", 5));
        dogsRoom.addUnit(new Dog("Luna", 4));

        Section catsRoom = new Section("Cats Room");
        catsRoom.addUnit(new Cat("Milo", 3));
        catsRoom.addUnit(new Cat("Nala", 2));

        Section birdsCorner = new Section("Birds Corner");
        birdsCorner.addUnit(new Bird("Sunny", 1));

        Section specialCare = new Section("Special Care");
        specialCare.addUnit(new Dog("Max", 6));
        specialCare.addUnit(new Cat("Bella", 3));

        catsRoom.addUnit(specialCare);

        shelter.addUnit(dogsRoom);
        shelter.addUnit(catsRoom);
        shelter.addUnit(birdsCorner);

        System.out.println("Total animals = " + shelter.countAnimals());
        System.out.println("Total food per day = " + shelter.totalFoodPerDay());
        System.out.println();
        shelter.printStructure("");
        System.out.println();
        System.out.println("Contains Bella? " + shelter.containsAnimal("Bella"));
        System.out.println("Contains Charlie? " + shelter.containsAnimal("Charlie"));
    }
}
```

---

## Task 1 — countAnimalsHelper
Write the recursive helper.

### Required labels inside your code
- `// BASE CASE:`
- `// GENERAL PATTERN:`

### Hint
When the index reaches the end of the list, there are no more child units to count.

---

## Task 2 — totalFoodPerDayHelper
Write the recursive helper.

### Required labels inside your code
- `// BASE CASE:`
- `// GENERAL PATTERN:`

### Hint
When the index reaches the end of the list, there is no more food to add.

---

## Task 3 — printStructureHelper
Write the recursive helper.

### Required labels inside your code
- `// BASE CASE:`
- `// GENERAL PATTERN:`

### Hint
Print one child, then recursively move to the next child.

---

## Task 4 — containsAnimalHelper
Write the recursive helper.

### Required labels inside your code
- `// BASE CASE:`
- `// GENERAL PATTERN:`

### Hint
If the current child already contains the animal, return `true`. Otherwise continue recursively.

---

## Reflection Questions
1. In this design, which class gives the simplest known answer?
2. Which class uses the recursive pattern?
3. Why did we add helper methods with an `index`?
4. Why did we remove loops from the recursive solutions?
5. Where does polymorphism happen in this lab?

---

# Exercise 1 — Vehicle Fleet

## Task
Complete the recursive helper methods below. Do **not** use loops.

```java
import java.util.ArrayList;

abstract class FleetUnit {
    protected String name;

    public FleetUnit(String name) {
        this.name = name;
    }

    public abstract int countVehicles();
    public abstract double totalMaintenanceCost();
}

abstract class Vehicle extends FleetUnit {
    protected double maintenanceCost;

    public Vehicle(String name, double maintenanceCost) {
        super(name);
        this.maintenanceCost = maintenanceCost;
    }

    @Override
    public int countVehicles() {
        // BASE CASE: one vehicle counts as 1
        return 1;
    }

    @Override
    public double totalMaintenanceCost() {
        // BASE CASE: one vehicle already knows its maintenance cost
        return maintenanceCost;
    }
}

class Car extends Vehicle {
    public Car(String name, double maintenanceCost) {
        super(name, maintenanceCost);
    }
}

class Truck extends Vehicle {
    public Truck(String name, double maintenanceCost) {
        super(name, maintenanceCost);
    }
}

class Garage extends FleetUnit {
    private ArrayList<FleetUnit> units = new ArrayList<>();

    public Garage(String name) {
        super(name);
    }

    public void add(FleetUnit unit) {
        units.add(unit);
    }

    @Override
    public int countVehicles() {
        return countVehiclesHelper(0);
    }

    private int countVehiclesHelper(int index) {
        // BASE CASE:

        // GENERAL PATTERN:
    }

    @Override
    public double totalMaintenanceCost() {
        return totalMaintenanceCostHelper(0);
    }

    private double totalMaintenanceCostHelper(int index) {
        // BASE CASE:

        // GENERAL PATTERN:
    }
}
```

---

# Exercise 2 — Bank Group with Contract-Based Polymorphism

## Task
Complete the recursive helper below. Do **not** use loops.

```java
import java.util.ArrayList;

interface BalanceProvider {
    double totalBalance();
}

class SavingsAccount implements BalanceProvider {
    private double balance;

    public SavingsAccount(double balance) {
        this.balance = balance;
    }

    @Override
    public double totalBalance() {
        // BASE CASE: one account already knows its own balance
        return balance;
    }
}

class ChequingAccount implements BalanceProvider {
    private double balance;

    public ChequingAccount(double balance) {
        this.balance = balance;
    }

    @Override
    public double totalBalance() {
        // BASE CASE: one account already knows its own balance
        return balance;
    }
}

class BankGroup implements BalanceProvider {
    private ArrayList<BalanceProvider> accounts = new ArrayList<>();

    public void add(BalanceProvider account) {
        accounts.add(account);
    }

    @Override
    public double totalBalance() {
        return totalBalanceHelper(0);
    }

    private double totalBalanceHelper(int index) {
        // BASE CASE:

        // GENERAL PATTERN:
    }
}
```

---

## Key takeaway
For this lab:
- **Base case** = simple object that already knows the answer
- **General pattern** = process one child and recurse to the next child
- **No loops** = the recursive pattern becomes easier to see and explain
