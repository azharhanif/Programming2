
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Animal[] animals = {new Dog("Rocky"), new Cat("Milo")};

        System.out.println("Warm-up:");
        ShelterService.showDogTrickWithCatch(animals[0]);
        ShelterService.showDogTrickWithCatch(animals[1]);
        ShelterService.showDogTrickSafely(animals[1]);

        System.out.println("
Average fee examples:");
        System.out.println(ShelterService.calculateAverageFee(100, 0, animals[0]));
        System.out.println(ShelterService.calculateAverageFee(100, 2, null));

        ArrayList<Animal> shelterAnimals = new ArrayList<>();
        shelterAnimals.add(new Dog("Rocky"));

        try {
            ShelterService.addAnimal(shelterAnimals, new Cat("rocky"));
        } catch (DuplicateAnimalNameException e) {
            System.out.println(e.getMessage());
        }

        try {
            ShelterService.feedAnimal(new Cat("Luna"), 0);
        } catch (InvalidFoodAmountException e) {
            System.out.println(e.getMessage());
        }

        Vehicle vehicle = new Vehicle("ABC123", 5000);
        try {
            ShelterService.recordMileage(vehicle, 4000);
        } catch (InvalidMileageException e) {
            System.out.println(e.getMessage());
        }

        BankAccount account = new BankAccount("Sam", 100.0);
        try {
            account.withdraw(150.0);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
    }
}
