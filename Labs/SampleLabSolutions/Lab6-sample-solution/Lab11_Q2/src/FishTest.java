import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class FishTest {

    @Test
    void testFishName() {
        Fish fish = new Fish();
        fish.setName("Goldie");
        assertEquals("Goldie", fish.getName());
    }

    @Test
    void testFishLegs() {
        Fish fish = new Fish();
        assertEquals(0, fish.legs);
    }
}
