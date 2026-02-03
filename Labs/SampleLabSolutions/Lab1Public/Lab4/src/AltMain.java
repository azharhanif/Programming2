public class AltMain {
            public static void main(String[] args) {

            // =========================
            // Test Problem 1:
            // Max Water Container
            // =========================
            int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};

            int maxArea = Lab4AltUtlis.maxArea(heights);

            System.out.println("Alternate Problem 1: Max Water Container");
            System.out.println("Input heights: ");
            printArray(heights);
            System.out.println("Expected max area: 49");
            System.out.println("Computed max area: " + maxArea);
            System.out.println("----------------------------------");


            // =========================
            // Test Problem 2:
            // Three Sum Closest
            // =========================
            int[] nums = {-1, 2, 1, -4};
            int target = 1;

            int closestSum = Lab4AltUtlis.threeSumClosest(nums, target);

            System.out.println("Alternate Problem 2: Three Sum Closest");
            System.out.println("Input numbers: ");
            printArray(nums);
            System.out.println("Target: " + target);
            System.out.println("Expected closest sum: 2");
            System.out.println("Computed closest sum: " + closestSum);
            System.out.println("----------------------------------");
        }

        // Utility method to print arrays
        private static void printArray(int[] arr) {
            System.out.print("[ ");
            for (int value : arr) {
                System.out.print(value + " ");
            }
            System.out.println("]");
        }
    }

