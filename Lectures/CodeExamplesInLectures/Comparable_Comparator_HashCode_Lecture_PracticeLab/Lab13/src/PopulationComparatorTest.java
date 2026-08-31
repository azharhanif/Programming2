import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PopulationComparatorTest {

    @Test
    void testPopulationSorting() {
        Country a = new Country("New Zealand", 64, 5);
        Country b = new Country("China", 86, 1321);

        PopulationComparator comp = new PopulationComparator();
        assertTrue(comp.compare(b, a) < 0);
    }
}
