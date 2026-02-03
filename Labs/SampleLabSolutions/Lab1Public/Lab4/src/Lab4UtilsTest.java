import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Lab4UtilsTest {

    @Test
    void testMaxAreaExample1() {
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        assertEquals(49, Lab4Utils.maxArea(height));
    }

    @Test
    void testMaxAreaExample2() {
        int[] height = {1, 1};
        assertEquals(1, Lab4Utils.maxArea(height));
    }

    @Test
    void testThreeSumClosestExample1() {
        int[] nums = {-1, 2, 1, -4};
        int target = 1;
        assertEquals(2, Lab4Utils.threeSumClosest(nums, target));
    }

    @Test
    void testThreeSumClosestExample2() {
        int[] nums = {0, 0, 0};
        int target = 1;
        assertEquals(0, Lab4Utils.threeSumClosest(nums, target));
    }

    @Test
    void testThreeSumClosestNegativeSolution() {
        int[] nums = {1, 2, -1, -2};
        int target = -1;
        // Possible sums: (1+2-1)=2, (1-1-2)=-2, (2-1-2)=-1 → closest is -1.
        assertEquals(-1, Lab4Utils.threeSumClosest(nums, target));
    }
}
