import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Point2DTest {

    @Test
    public void testDefaultConstructor() {
        Point2D p = new Point2D();
        assertEquals(0.0f, p.getX());
        assertEquals(0.0f, p.getY());
    }

    @Test
    public void testParameterizedConstructor() {
        Point2D p = new Point2D(3.5f, 4.5f);
        assertEquals(3.5f, p.getX());
        assertEquals(4.5f, p.getY());
    }

    @Test
    public void testSettersAndGetters() {
        Point2D p = new Point2D();
        p.setX(7.2f);
        p.setY(8.3f);
        assertEquals(7.2f, p.getX());
        assertEquals(8.3f, p.getY());
    }

    @Test
    public void testSetXYandGetXY() {
        Point2D p = new Point2D();
        p.setXY(1.1f, 2.2f);
        float[] xy = p.getXY();
        assertArrayEquals(new float[]{1.1f, 2.2f}, xy);
    }

    @Test
    public void testToString() {
        Point2D p = new Point2D(5, 6);
        assertEquals("(5.0,6.0)", p.toString());
    }
}
