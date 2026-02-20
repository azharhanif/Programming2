import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StaffTest {

    @Test
    public void testConstructorAndGetters() {
        Staff st = new Staff("Bob","LA","Science",60000);
        assertEquals("Bob", st.getName());
        assertEquals("LA", st.getAddress());
        assertEquals("Science", st.getSchool());
        assertEquals(60000.0, st.getPay());
    }

    @Test
    public void testSetters() {
        Staff st = new Staff("X","Y","Z",1000);
        st.setSchool("Arts");
        st.setPay(80000);
        assertEquals("Arts", st.getSchool());
        assertEquals(80000.0, st.getPay());
    }

    @Test
    public void testToString() {
        Staff st = new Staff("Lee","Paris","Law",70000);
        assertEquals("Staff[Person[name=Lee,address=Paris],school=Law,pay=70000.0]", st.toString());
    }
}
