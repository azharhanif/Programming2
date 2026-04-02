import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PetTest {

    @Test
    void testPetInterfaceImplementation() {
        Pet pet1 = new Cat("Kitty");
        Pet pet2 = new Fish();

        pet2.setName("Bubbles");

        assertEquals("Kitty", pet1.getName());
        assertEquals("Bubbles", pet2.getName());
    }
}
