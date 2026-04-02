import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CatTest {

    @Test
    void testCatNameConstructor() {
        Cat cat = new Cat("Milo");
        assertEquals("Milo", cat.getName());
    }

    @Test
    void testCatDefaultConstructor() {
        Cat cat = new Cat();
        assertEquals("", cat.getName());
    }

    @Test
    void testSetName() {
        Cat cat = new Cat();
        cat.setName("Leo");
        assertEquals("Leo", cat.getName());
    }
}
