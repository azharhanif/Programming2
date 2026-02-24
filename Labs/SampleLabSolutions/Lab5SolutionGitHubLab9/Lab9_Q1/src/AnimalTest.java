import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {

    @Test
    public void testToStringAnimal() {
        Animal a = new Animal("A");
        assertEquals("Animal[name=\"A\"]", a.toString());
    }

    @Test
    public void testToStringMammal() {
        Mammal m = new Mammal("M");
        assertEquals("Mammal[Animal[name=\"M\"]]", m.toString());
    }

    @Test
    public void testToStringCat() {
        Cat c = new Cat("C");
        assertEquals("Cat[Mammal[Animal[name=\"C\"]]]", c.toString());
    }

    @Test
    public void testToStringDog() {
        Dog d = new Dog("D");
        assertEquals("Dog[Mammal[Animal[name=\"D\"]]]", d.toString());
    }
}
