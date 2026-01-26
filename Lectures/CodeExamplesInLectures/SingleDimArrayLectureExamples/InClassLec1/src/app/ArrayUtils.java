package app;

public class ArrayUtils {

    /**
     * Finds the minimum and maximum values in an array.
     * @param arr input array (assumed non-empty)
     * @return int array of size 2: [min, max]
     */
    public static int[] findMinAndMax(int[] arr) {
        int min = arr[0];
        int max = arr[0];

        for (int value : arr) {
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }
        return new int[]{min, max};
    }

    /**
     * Finds the sum of elements at odd indices.
     * Array length must be even.
     */
    public static int sumOddIndices(int[] arr) {
        if (arr.length % 2 != 0) {
            throw new IllegalArgumentException("Array length must be even");
        }

        int sum = 0;
        for (int i = 1; i < arr.length; i += 2) {
            sum += arr[i];
        }
        return sum;
    }
}
