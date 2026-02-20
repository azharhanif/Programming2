import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    public void testConstructorAndGetters() {
        Customer c = new Customer(1,"John",10);
        assertEquals(1, c.getID());
        assertEquals("John", c.getName());
        assertEquals(10, c.getDiscount());
    }

    @Test
    public void testSetDiscount() {
        Customer c = new Customer(2,"Amy",5);
        c.setDiscount(15);
        assertEquals(15, c.getDiscount());
    }

    @Test
    public void testToString() {
        Customer c = new Customer(3,"Max",20);
        assertEquals("Max(3)(20%)", c.toString());
    }
}
