import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

    @Test
    void testArea() {
        Circle c = new Circle(2);
        assertEquals(Math.PI * 4, c.getArea(), 0.001);
    }

    @Test
    void testResize() {
        ResizableCircle rc = new ResizableCircle(10);
        rc.resize(50);
        assertEquals(5, rc.radius, 0.001);
    }
}
