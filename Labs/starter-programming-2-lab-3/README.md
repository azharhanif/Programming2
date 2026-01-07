# Programming 2 - Lab 3

This template repository is the starter project for Programming 2 Lab 3. Written in Java, and tested with Gradle/JUnit.

### Questions

1. Write a method that will create an array of an specified size. Then, start filling the array using startingInteger in the last position and incremented by 3 for each previous position. Once this is done rotate all the entries with even indexes 2 positions to the left (if the size is 5, whatever is in index location 4 is stored in index position 2, whatever is in 2, is stored in index position 0, and so on). The first entry is to be stored in the last location (size -1) of the array. Display the resulting array (for your testing purposes only). Return the array after rotation.

![](Q1.png)

Note 1: You are to expect a perfect user who will always enter an odd array size; that is, do not verify the validity of user input.

Note 2: The use of libraries is prohibited. Your program must work for any odd array size and starting value, not just the ones in the samples above.

Note 3: You are free to use whichever type of loop you want.

2. Write a Java method that given an array of non-negative integers nums, you are initially positioned at the first index of the array. Each element in the array represents your maximum jump length at that position. Your goal is to reach the last index in the minimum number of jumps. You can assume that you can always reach the last index.

   Example 1:

   Input: `[2, 3, 1, 1, 4]`  
   Output: `2`  
   Explanation: The minimum number of jumps to reach the last index is 2. Jump 1 step from index 0 to 1, then 3 steps to the last index.

   Example 2:

   Input: `[2, 3, 0, 1, 4]`  
   Output: `2`

### Setup Command

`gradle clean`

### Run Command

`gradle test`
