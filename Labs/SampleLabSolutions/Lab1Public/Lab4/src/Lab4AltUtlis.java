import java.util.Arrays;
public class Lab4AltUtlis {
    // Key idea
    //
    //Separate:
    //
    //area calculation
    //
    //pointer movement policy
    //
    //validation
    //Design rationale
    //
    //Makes the greedy decision explicit and testable
    //
    //Easier to reason about correctness
    //
    //Easily extendable (e.g., variant container rules)

    public static int maxArea(int[] heights) {
        validateInput(heights);

        int left = 0, right = heights.length - 1;
        int max = 0;

        while (left < right) {
            max = Math.max(max, computeArea(heights, left, right));
            if (shouldMoveLeft(heights, left, right)) {
                left++;
            } else {
                right--;
            }
        }
        return max;
    }

    private static int computeArea(int[] h, int l, int r) {
        return (r - l) * Math.min(h[l], h[r]);
    }

    private static boolean shouldMoveLeft(int[] h, int l, int r) {
        return h[l] < h[r];
    }

    private static void validateInput(int[] h) {
        if (h == null || h.length < 2) {
            throw new IllegalArgumentException("At least two heights required");
        }
    }

    // Key idea
    //
    //Instead of tracking the closest sum, track the smallest delta from the target.
    //
    //This simplifies comparisons and avoids repeated subtraction logic.
    //Algorithmic shift
    //
    //Original:Math.abs(target - currentSum) < Math.abs(target - closestSum)
    //Alternate:
    //delta = currentSum - target
    //minAbsDelta = min(|delta|)


    public static int threeSumClosest(int[] nums, int target) {
        validate(nums);
        Arrays.sort(nums);

        int bestDelta = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1, right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                int delta = sum - target;

                if (Math.abs(delta) < Math.abs(bestDelta)) {
                    bestDelta = delta;
                }

                if (delta < 0) {
                    left++;
                } else if (delta > 0) {
                    right--;
                } else {
                    return target; // perfect match
                }
            }
        }
        return target + bestDelta;
    }

    private static void validate(int[] nums) {
        if (nums == null || nums.length < 3) {
            throw new IllegalArgumentException("At least 3 numbers required");
        }
    }
}