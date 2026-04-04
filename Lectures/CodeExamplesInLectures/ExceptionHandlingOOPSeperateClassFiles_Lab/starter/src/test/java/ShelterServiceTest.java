
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
}
