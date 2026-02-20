import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StudentTest {

    @Test
    public void testConstructorAndGetters() {
        Student s = new Student("Ann","Calgary","CS",3,5000);
        assertEquals("Ann", s.getName());
        assertEquals("Calgary", s.getAddress());
        assertEquals("CS", s.getProgram());
        assertEquals(3, s.getYear());
        assertEquals(5000.0, s.getFee());
    }

    @Test
    public void testSetters() {
        Student s = new Student("A","B","C",1,100);
        s.setProgram("Math");
        s.setYear(2);
        s.setFee(2000);
        assertEquals("Math", s.getProgram());
        assertEquals(2, s.getYear());
        assertEquals(2000.0, s.getFee());
    }

    @Test
    public void testToString() {
        Student s = new Student("Joe","NY","IT",1,3000);
        assertEquals("Student[Person[name=Joe,address=NY],program=IT,year=1,fee=3000.0]", s.toString());
    }
}
