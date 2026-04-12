
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class ShelterServiceTest {
    @Test
    void duplicateAnimalNamesShouldThrow() {
        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(new Dog("Rocky"));
        assertThrows(DuplicateAnimalNameException.class,
                () -> ShelterService.addAnimal(animals, new Cat("rocky")));
    }

    @Test
    void averageFeeWithZeroAnimalsShouldReturnMinusOne() {
        double result = ShelterService.calculateAverageFee(100, 0, new Dog("Rocky"));
        assertEquals(-1, result, 0.001);
    }

    @Test
    void recordMileageWithSmallerValueShouldThrow() {
        Vehicle v = new Vehicle("ABC123", 5000);
        assertThrows(InvalidMileageException.class,
                () -> ShelterService.recordMileage(v, 4000));
    }
}
