import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AnimalTest {

    @Test
    void testSpiderLegs() {
        Animal spider = new Spider();
        assertEquals(8, spider.legs);
    }

    @Test
    void testCatLegs() {
        Cat cat = new Cat("Tom");
        assertEquals(4, cat.legs);
    }

    @Test
    void testFishLegs() {
        Fish fish = new Fish();
        assertEquals(0, fish.legs);
    }
}
