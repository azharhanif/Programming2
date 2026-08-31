# Programming 2 Lab —  Solution

##  note
In the recursion lecture, recursion is presented as another way to write a repeated structure and every recursive solution must contain a **base case** and a **general pattern**. For this version of the lab, the recursive solutions are written **without loops** so students can clearly identify those two parts.

---

# Part 0 — Warm-up Bridge Solution

```java
public static void playSounds(Animal[] animals, int index) {
    // BASE CASE: if index reaches the length, there are no more animals to process
    if (index == animals.length) {
        return;
    }

    // GENERAL PATTERN: process one animal, then recurse to the next index
    animals[index].makeSound();
    playSounds(animals, index + 1);
}
```

## Answers
- **Base case:** `index == animals.length`
- **General pattern:** process the current animal, then call the same method on the next index
- **Why this is still polymorphism:** `animals[index]` has reference type `Animal`, but runtime behavior depends on whether the object is a `Dog` or `Cat`

---

# Main Lab —  Solution

## Section.java solution

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
        // BASE CASE: when index reaches the end, there are no more child units to count
        if (index == units.size()) {
            return 0;
        }

        // GENERAL PATTERN: count the current child, then recurse to the next child
        return units.get(index).countAnimals() + countAnimalsHelper(index + 1);
    }

    @Override
    public int totalFoodPerDay() {
        return totalFoodPerDayHelper(0);
    }

    private int totalFoodPerDayHelper(int index) {
        // BASE CASE: when index reaches the end, there is no more food to add
        if (index == units.size()) {
            return 0;
        }

        // GENERAL PATTERN: add the current child food, then recurse to the next child
        return units.get(index).totalFoodPerDay() + totalFoodPerDayHelper(index + 1);
    }

    @Override
    public void printStructure(String indent) {
        System.out.println(indent + "Section: " + name);
        printStructureHelper(indent, 0);
    }

    private void printStructureHelper(String indent, int index) {
        // BASE CASE: when index reaches the end, there are no more child units to print
        if (index == units.size()) {
            return;
        }

        // GENERAL PATTERN: print the current child, then recurse to the next child
        units.get(index).printStructure(indent + "   ");
        printStructureHelper(indent, index + 1);
    }

    @Override
    public boolean containsAnimal(String targetName) {
        return containsAnimalHelper(targetName, 0);
    }

    private boolean containsAnimalHelper(String targetName, int index) {
        // BASE CASE: when index reaches the end, target was not found in any child unit
        if (index == units.size()) {
            return false;
        }

        // GENERAL PATTERN: if current child contains target return true, otherwise recurse
        if (units.get(index).containsAnimal(targetName)) {
            return true;
        }
        return containsAnimalHelper(targetName, index + 1);
    }
}
```

---

## Full solution file

```java
import java.util.ArrayList;

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
        // BASE CASE: compare one animal name with the target
        return name.equalsIgnoreCase(targetName);
    }
}

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
        // BASE CASE: when index reaches the end, there are no more child units to count
        if (index == units.size()) {
            return 0;
        }

        // GENERAL PATTERN: count the current child, then recurse to the next child
        return units.get(index).countAnimals() + countAnimalsHelper(index + 1);
    }

    @Override
    public int totalFoodPerDay() {
        return totalFoodPerDayHelper(0);
    }

    private int totalFoodPerDayHelper(int index) {
        // BASE CASE: when index reaches the end, there is no more food to add
        if (index == units.size()) {
            return 0;
        }

        // GENERAL PATTERN: add the current child food, then recurse to the next child
        return units.get(index).totalFoodPerDay() + totalFoodPerDayHelper(index + 1);
    }

    @Override
    public void printStructure(String indent) {
        System.out.println(indent + "Section: " + name);
        printStructureHelper(indent, 0);
    }

    private void printStructureHelper(String indent, int index) {
        // BASE CASE: when index reaches the end, there are no more child units to print
        if (index == units.size()) {
            return;
        }

        // GENERAL PATTERN: print the current child, then recurse to the next child
        units.get(index).printStructure(indent + "   ");
        printStructureHelper(indent, index + 1);
    }

    @Override
    public boolean containsAnimal(String targetName) {
        return containsAnimalHelper(targetName, 0);
    }

    private boolean containsAnimalHelper(String targetName, int index) {
        // BASE CASE: when index reaches the end, target was not found in any child unit
        if (index == units.size()) {
            return false;
        }

        // GENERAL PATTERN: if current child contains target return true, otherwise recurse
        if (units.get(index).containsAnimal(targetName)) {
            return true;
        }
        return containsAnimalHelper(targetName, index + 1);
    }
}

public class Main {
    public static void playSounds(Animal[] animals, int index) {
        // BASE CASE: if index reaches the length, there are no more animals to process
        if (index == animals.length) {
            return;
        }

        // GENERAL PATTERN: process one animal, then recurse to the next index
        animals[index].makeSound();
        playSounds(animals, index + 1);
    }

    public static void main(String[] args) {
        Animal[] animals = {
            new Dog("Rocky", 5),
            new Cat("Milo", 3),
            new Dog("Luna", 4)
        };

        playSounds(animals, 0);
        System.out.println();

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

## Expected output

```text
Rocky says woof
Milo says meow
Luna says woof

Total animals = 7
Total food per day = 24

Section: City Shelter
   Section: Dogs Room
      Dog: Rocky (5 food)
      Dog: Luna (4 food)
   Section: Cats Room
      Cat: Milo (3 food)
      Cat: Nala (2 food)
      Section: Special Care
         Dog: Max (6 food)
         Cat: Bella (3 food)
   Section: Birds Corner
      Bird: Sunny (1 food)

Contains Bella? true
Contains Charlie? false
```

---

# Exercise 1 — Vehicle Fleet Solution

```java
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
        // BASE CASE: when index reaches the end, there are no more vehicles to count
        if (index == units.size()) {
            return 0;
        }

        // GENERAL PATTERN: count the current child, then recurse to the next child
        return units.get(index).countVehicles() + countVehiclesHelper(index + 1);
    }

    @Override
    public double totalMaintenanceCost() {
        return totalMaintenanceCostHelper(0);
    }

    private double totalMaintenanceCostHelper(int index) {
        // BASE CASE: when index reaches the end, there is no more maintenance cost to add
        if (index == units.size()) {
            return 0;
        }

        // GENERAL PATTERN: add current child cost, then recurse to the next child
        return units.get(index).totalMaintenanceCost() + totalMaintenanceCostHelper(index + 1);
    }
}
```

---

# Exercise 2 — Bank Group Solution

```java
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
        // BASE CASE: when index reaches the end, there is no more balance to add
        if (index == accounts.size()) {
            return 0;
        }

        // GENERAL PATTERN: add current account balance, then recurse to the next account
        return accounts.get(index).totalBalance() + totalBalanceHelper(index + 1);
    }
}
```

---

# notes

## 1. Important clarification
In general programming, recursion and loops can be mixed. But for **this lab**, removing loops makes the recursion pattern much more visible for beginners.

## 2. Base case in OOP recursion
Students often expect base cases to look only like `num == 0`. This lab helps them see another kind of base case:
- a simple concrete object already knows the answer directly
- for example, one `Animal`, one `Vehicle`, or one `Account`

## 3. General pattern in OOP recursion
The repeated pattern is:
- solve one child object
- recurse on the next child position

## 4. Why helper methods were added
Because `ArrayList` is not naturally recursive for beginners, the helper method with an `index` turns the list traversal into a clean recursive pattern.
