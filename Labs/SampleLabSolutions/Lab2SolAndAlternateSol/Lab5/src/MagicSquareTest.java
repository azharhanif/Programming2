import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MagicSquareTest {

    @Test
    void testValidMagicSquare3x3() {
        int[][] square = {
                {4, 9, 2},
                {3, 5, 7},
                {8, 1, 6}
        };
        assertTrue(MagicSquare.isMagicSquare(square));
    }

    @Test
    void testInvalidMagicSquare() {
        int[][] square = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        assertFalse(MagicSquare.isMagicSquare(square));
    }

    @Test
    void testGenerateMagicSquare3() {
        int[][] square = MagicSquare.generateMagicSquare(3);
        assertTrue(MagicSquare.isMagicSquare(square));
    }

    @Test
    void testGenerateMagicSquare5() {
        int[][] square = MagicSquare.generateMagicSquare(5);
        assertTrue(MagicSquare.isMagicSquare(square));
    }

    @Test
    void testGenerateMagicSquareEvenSize() {
        assertThrows(IllegalArgumentException.class, () ->
                MagicSquare.generateMagicSquare(4));
    }
}
