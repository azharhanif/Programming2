import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CountryTest {

    @Test
    void testCompareToByPopulation() {
        Country c1 = new Country("A", 1, 100);
        Country c2 = new Country("B", 2, 200);

        assertTrue(c2.compareTo(c1) < 0);
    }

    @Test
    void testHashCodeConsistency() {
        Country c1 = new Country("India", 91, 1148);
        Country c2 = new Country("India", 91, 1148);

        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testEquals() {
        Country c1 = new Country("France", 33, 65);
        Country c2 = new Country("France", 33, 65);

        assertEquals(c1, c2);
    }
}
