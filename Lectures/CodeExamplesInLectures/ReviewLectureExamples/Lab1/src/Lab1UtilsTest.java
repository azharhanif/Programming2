import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Lab1UtilsTest {

    @Test
    void testConcatenateStrings() {
        String result = Lab1Utils.concatenateStrings("Programming 2", "Winter 2022");
        assertEquals("Programming 2 Winter 2022", result);
    }

    @Test
    void testEndsWithTrue() {
        assertTrue(Lab1Utils.endsWith("Java Lab", "Lab"));
    }

    @Test
    void testEndsWithFalse() {
        assertFalse(Lab1Utils.endsWith("Winter 2022", "35"));
    }

    @Test
    void testGenerateRandomInRange() {
        int value = Lab1Utils.generateRandomInRange(5, 25);
        assertTrue(value >= 5 && value <= 25);
    }

    @Test
    void testInvalidPasswordTooShort() {
        assertFalse(Lab1Utils.isValidPassword("Vanier1"));
    }

    @Test
    void testValidPassword() {
        assertTrue(Lab1Utils.isValidPassword("Vanier1234"));
    }

    @Test
    void testInvalidPasswordMissingDigit() {
        assertFalse(Lab1Utils.isValidPassword("VanierTest"));
    }
}
