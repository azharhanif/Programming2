import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NameComparatorTest {

    @Test
    void testNameSorting() {
        Country a = new Country("China", 86, 1321);
        Country b = new Country("India", 91, 1148);

        NameComparator comp = new NameComparator();
        assertTrue(comp.compare(a, b) < 0);
    }
}
