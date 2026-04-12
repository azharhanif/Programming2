
import java.util.ArrayList;

public class ShelterService {

    public static void showDogTrickWithCatch(Animal animal) {
        try {
            Dog dog = (Dog) animal;
            System.out.println(dog.fetch());
        } catch (ClassCastException e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }

    }

    public static void showDogTrickSafely(Animal animal) {
        if (animal instanceof Dog dog) {
            System.out.println(dog.fetch());
        } else {
            System.out.println(animal.getName() + " is not a dog.");
        }

    }

    public static double calculateAverageFee(int totalFee, int animalCount, Animal featuredAnimal) {
        double result = 0;
        try {
            result = totalFee / animalCount;
            System.out.println("Featured animal: " + featuredAnimal.getName());
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic problem: " + e.getMessage());
            result = -1;
        } catch (NullPointerException e) {
            System.out.println("Null problem: " + e.getMessage());
            result = -2;
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            result = -3;
        }

        return result;
    }

    public static void feedAnimal(Animal animal, int foodAmount)
            throws InvalidFoodAmountException {
        if (foodAmount <= 0) {
            throw new InvalidFoodAmountException(
                    "Food amount must be greater than 0. Given: " + foodAmount);
        }
        System.out.println("Feeding " + animal.getName() + " with " + foodAmount + " units of food.");

    }

    public static void addAnimal(ArrayList<Animal> animals, Animal newAnimal)
            throws DuplicateAnimalNameException {
        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(newAnimal.getName())) {
                throw new DuplicateAnimalNameException(
                        "Animal name already exists: " + newAnimal.getName());
            }
        }
        animals.add(newAnimal);

    }

    public static void recordMileage(Vehicle vehicle, int newMileage)
            throws InvalidMileageException {
        if (newMileage < vehicle.getMileage()) {
            throw new InvalidMileageException(
                    "New mileage cannot be less than current mileage.");
        }
        vehicle.setMileage(newMileage);

    }
}
