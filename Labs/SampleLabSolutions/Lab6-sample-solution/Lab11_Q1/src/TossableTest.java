import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TossableTest {

    @Test
    void testBaseballCreation() {
        Ball b = new Baseball("Rawlings");
        assertEquals("Rawlings", b.getBrandName());
    }

    @Test
    void testFootballCreation() {
        Ball f = new Football("Nike");
        assertEquals("Nike", f.getBrandName());
    }

    @Test
    void testRockToss() {
        Tossable rock = new Rock();
        assertDoesNotThrow(rock::toss);
    }

    @Test
    void testPolymorphism() {
        Tossable t = new Baseball("Wilson");
        assertDoesNotThrow(t::toss);
    }
}
