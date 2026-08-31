import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void testCat() {
        Animal cat = new Cat("Kitty");
        assertDoesNotThrow(cat::greets);
    }

    @Test
    void testDogGreeting() {
        Dog d1 = new Dog("A");
        Dog d2 = new Dog("B");
        assertDoesNotThrow(() -> d1.greets(d2));
    }

    @Test
    void testBigDogGreeting() {
        BigDog b1 = new BigDog("X");
        BigDog b2 = new BigDog("Y");
        assertDoesNotThrow(() -> b1.greets(b2));
    }
}
