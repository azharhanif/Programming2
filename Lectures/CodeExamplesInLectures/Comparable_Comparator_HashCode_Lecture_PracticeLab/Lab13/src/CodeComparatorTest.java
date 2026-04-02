import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CodeComparatorTest {

    @Test
    void testCodeSorting() {
        Country a = new Country("France", 33, 65);
        Country b = new Country("Mexico", 52, 110);

        CodeComparator comp = new CodeComparator();
        assertTrue(comp.compare(a, b) < 0);
    }
}
