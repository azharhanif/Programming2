# Programming 2 - Lab 4

This template repository is the starter project for Programming 2 Lab 4. Written in Java, Use JUnit for testing.

### Questions

1. You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]). Find two lines that together with the x-axis form a container, such that the container contains the most water. Return the maximum amount of water a container can store. Notice that you may not slant the container.

![](Q1.jpg)

Example 1:

Input: `[1, 8, 6, 2, 5, 4, 8, 3, 7]`  
 Output: `49`  
 Explanation: The above vertical lines are represented by array [1, 8, 6, 2, 5, 4, 8, 3, 7]. In this case, the max area of water (blue section) the container can contain is 49.

Constraints:

n == height.length  
2 <= n <= 105  
0 <= height[i] <= 104

Example 2:

Input: `[1, 1]`  
 Output: `1`

2. Given an integer accrray nums of length n and an integer target, find three integers in nums such that the sum is closest to target. Return the sum of the three integers. You may assume that each input would have exactly one solution.

   Example 1:

   Input: nums = `[-1, 2, 1, -4]`, target = `1`  
   Output: `2`  
   Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).

   Example 2:

   Input: nums = `[0, 0, 0]`, target = `1`  
   Output: `0`

   Constraints:

   3 <= nums.length <= 1000  
   -1000 <= nums[i] <= 1000  
   -104 <= target <= 104



