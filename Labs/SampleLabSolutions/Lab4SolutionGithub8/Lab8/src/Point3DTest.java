import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Point3DTest {

    @Test
    public void testDefaultConstructor() {
        Point3D p = new Point3D();
        assertEquals(0.0f, p.getX());
        assertEquals(0.0f, p.getY());
        assertEquals(0.0f, p.getZ());
    }

    @Test
    public void testParameterizedConstructor() {
        Point3D p = new Point3D(1,2,3);
        assertEquals(1.0f, p.getX());
        assertEquals(2.0f, p.getY());
        assertEquals(3.0f, p.getZ());
    }

    @Test
    public void testSetZandGetZ() {
        Point3D p = new Point3D();
        p.setZ(9.5f);
        assertEquals(9.5f, p.getZ());
    }

    @Test
    public void testSetXYZandGetXYZ() {
        Point3D p = new Point3D();
        p.setXYZ(3,4,5);
        float[] xyz = p.getXYZ();
        assertArrayEquals(new float[]{3.0f,4.0f,5.0f}, xyz);
    }

    @Test
    public void testToString() {
        Point3D p = new Point3D(7,8,9);
        assertEquals("(7.0,8.0,9.0)", p.toString());
    }
}
