import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Problem 1: Max Water Container ===");
        System.out.print("Enter number of heights: ");
        int n = scanner.nextInt();
        int[] height = new int[n];
        System.out.println("Enter the height values (space separated):");
        for (int i = 0; i < n; i++) {
            height[i] = scanner.nextInt();
        }
        int maxArea = Lab4Utils.maxArea(height);
        System.out.println("Maximum water container area: " + maxArea);

        System.out.println("\n=== Problem 2: Three Sum Closest ===");
        System.out.print("Enter number of array elements: ");
        int m = scanner.nextInt();
        int[] nums = new int[m];
        System.out.println("Enter the array elements (space separated):");
        for (int i = 0; i < m; i++) {
            nums[i] = scanner.nextInt();
        }
        System.out.print("Enter target value: ");
        int target = scanner.nextInt();
        int closest = Lab4Utils.threeSumClosest(nums, target);
        System.out.println("Closest sum to target: " + closest);

        scanner.close();
    }
}
