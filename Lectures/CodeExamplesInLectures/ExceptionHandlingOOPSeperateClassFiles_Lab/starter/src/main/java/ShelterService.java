
import java.util.ArrayList;

public class ShelterService {

    public static void showDogTrickWithCatch(Animal animal) {
        // TODO: use try-catch to handle ClassCastException

    }

    public static void showDogTrickSafely(Animal animal) {
        // TODO: use instanceof to avoid unsafe casting

    }

    public static double calculateAverageFee(int totalFee, int animalCount, Animal featuredAnimal) {
        double result = 0;
        try {
            result = totalFee / animalCount;
            System.out.println("Featured animal: " + featuredAnimal.getName());
        } catch (ArithmeticException e) {
            // TODO
        } catch (NullPointerException e) {
            // TODO
        } catch (Exception e) {
            // TODO
        }

        return result;
    }

    public static void feedAnimal(Animal animal, int foodAmount)
            throws InvalidFoodAmountException {
        // TODO: throw InvalidFoodAmountException when foodAmount <= 0
        System.out.println("Feeding " + animal.getName() + " with " + foodAmount + " units of food.");

    }

    public static void addAnimal(ArrayList<Animal> animals, Animal newAnimal)
            throws DuplicateAnimalNameException {
        // TODO: throw DuplicateAnimalNameException if a name already exists
        animals.add(newAnimal);

    }

    public static void recordMileage(Vehicle vehicle, int newMileage)
            throws InvalidMileageException {
        // TODO: throw InvalidMileageException when newMileage < current mileage
        vehicle.setMileage(newMileage);

    }
}
