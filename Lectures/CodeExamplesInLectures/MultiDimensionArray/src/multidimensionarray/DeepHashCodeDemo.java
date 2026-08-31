package multidimensionarray;

import java.util.Arrays;

public class DeepHashCodeDemo {
    public static void main(String[] args) {
        int[][] a = { {1, 2}, {3, 4} };
        int[][] b = { {1, 2}, {3, 4} };

        System.out.println(Arrays.deepHashCode(a));
        System.out.println(Arrays.deepHashCode(b)); // same value
    }
}