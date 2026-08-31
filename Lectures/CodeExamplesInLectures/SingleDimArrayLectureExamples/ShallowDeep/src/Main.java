//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {

        int[] values = { 1, 2, 3 };

        // Shallow copy example
        CopyExample shallow = new CopyExample(values, false);
        // Deep copy example
        CopyExample deep = new CopyExample(values, true);

        System.out.print("Before change:\nShallow: ");
        shallow.showData();
        System.out.print("Deep:    ");
        deep.showData();

        values[0] = 13;

        System.out.print("\nAfter change to values[0]:\nShallow: ");
        shallow.showData();
        System.out.print("Deep:    ");
        deep.showData();
        // Sort an array
        int[] nums = {1, 6, 3, -2, 5, 0};
// Arrays.sort() is a void method, it direclty modifies the original array instead of create a new array. If you want to keep the original array, you should create a copy of the array manually before sorting it.
        int[] numsCopy = Arrays.copyOf(nums, nums.length);
// sort part of the array, ascending
        Arrays.sort(numsCopy, 1, 4);
        System.out.print(Arrays.toString(numsCopy));
// sort the entire array, ascending
       Arrays.sort(numsCopy);
       System.out.print(Arrays.toString(numsCopy));

    }
}