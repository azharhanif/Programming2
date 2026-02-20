import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PersonTest {

    @Test
    public void testConstructorAndGetters() {
        Person p = new Person("Alice", "Toronto");
        assertEquals("Alice", p.getName());
        assertEquals("Toronto", p.getAddress());
    }

    @Test
    public void testSetAddress() {
        Person p = new Person("Bob", "Ottawa");
        p.setAddress("Montreal");
        assertEquals("Montreal", p.getAddress());
    }

    @Test
    public void testToString() {
        Person p = new Person("Tom", "Vancouver");
        assertEquals("Person[name=Tom,address=Vancouver]", p.toString());
    }
}
