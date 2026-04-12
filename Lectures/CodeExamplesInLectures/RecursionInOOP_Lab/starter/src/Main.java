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
        // BASE CASE: compare this single animal name with target
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

    @Override
    public void makeSound() {
        System.out.println(name + " says woof");
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

    @Override
    public void makeSound() {
        System.out.println(name + " says meow");
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

    @Override
    public void makeSound() {
        System.out.println(name + " says chirp");
    }
}

abstract class SoundAnimal extends Animal {
    public SoundAnimal(String name, int foodPerDay) {
        super(name, foodPerDay);
    }

    public abstract void makeSound();
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
        // BASE CASE:

        // GENERAL PATTERN:
        return 0;
    }

    @Override
    public int totalFoodPerDay() {
        return totalFoodPerDayHelper(0);
    }

    private int totalFoodPerDayHelper(int index) {
        // BASE CASE:

        // GENERAL PATTERN:
        return 0;
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
        return false;
    }
}

public class Main {
    public static void playSounds(SoundAnimal[] animals, int index) {
        // BASE CASE:

        // GENERAL PATTERN:
    }

    public static void main(String[] args) {
        SoundAnimal[] animals = {
            new Dog("Rocky", 5),
            new Cat("Milo", 3),
            new Dog("Luna", 4)
        };

        playSounds(animals, 0);

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
        shelter.printStructure("");
        System.out.println("Contains Bella? " + shelter.containsAnimal("Bella"));
    }
}
