# Multi-dimensional Array

## 1. Definition

**A Multi-dimension array is an array of arrays**.

A `int` single-dimension array, e.g.: `int[] nums`, is an array of `int`, each element is an integer.

A `int` 2-d array, e.g.: `int[][] numss`, is an array of `int[]`, each element is an integer array.

For naming an array, we can

1. add `s` after the noun for 1-d array, e.g.: `clocks`, and `ss` after the noun for 2-d array, e.g.: `clockss`.
2. add `Array` after the noun for 1-d array, e.g.: `personArray`, and `2Array` or `2dArray` after the noun for 2-d array, e.g.: `person2dArray`.

## 2. Initialization

1. Use the keyword `new` to initialize a 2-d array

```java
// initialize a 2-d array with 1 size
// the first [] indicates the row, the second [] indicates the column
// [3][]: 3 rows, the column is not fixed

int[][] numss = new int[3][];			// most general way to initialize a 2-d array {null, null, null}
int[][][] numsss = new int[4][][];		// {null, null, null, null}

// initialize a 2-d array with 2 sizes
// [2][3]: 2 rows, 3 columns
int[][] numss2 = new int[2][3];				// {{0, 0, 0}, {0, 0, 0}}
int[][][] numsss = new int[2][3][4];		// {{{ 0, 0, 0, 0}, { 0, 0, 0, 0}, { 0, 0, 0, 0}}, {{ 0, 0, 0, 0}, { 0, 0, 0, 0}, { 0, 0, 0, 0}}}

```

2. Use `{}` to initialize a 2-d array

```java
// each element inside of a 2-d array is an 1-d array
int[][] numss = {null, {}, {1, 2, 3}, nums, new int[]{1, 2, 3}};
int[][][] numsss = {null, {{}}, {null}, {{1, 2, 3}, {1, 2}}, new int[1][2]};

// Error: cannot convert double[] to int[]
int[][] numss6 = { new double[]{5.5, 2.2} };
```

In general, if you know the data have some specific values, use the second way, else use the first way, then check if the data should be stored in a matrix, then put two sizes there, else just give the first size.

## 2 Index system

For 2-d array, since there are two sizes (row and column), if you indicate one single index (should be in the first []), you will get a single-dimension array; if you indicate two indexes, you can get a real value from the array

```java
// 1 2 3
// 4 5
// 7 8 9 0
int[][] numss = {{1, 2, 3}, {4, 5}, {7, 8, 9, 0}};
// numss[row][col]
// numss[1]   ->   a 1-d array: {4, 5}
// numss[1][0]  -> a real value: 4
```

Your index may get out of bound on both the row-level or the column-level.

```java
int[][] numss = {{1, 2, 3}, {4, 5}, {7, 8, 9, 0}};
// numss[3] -> out of bound
// numss[3][0] -> out of bound
// numss[0][3] -> out of bound
```

## 3. for loop

### 3.1 regular-for

To use regular-for loop, you need to first find the size of the array:

1. to check the number of rows of a 2-d array, use `numss.length`
2. to check the number of columns of a matrix-like 2-d array , use `numss[0].length`
3. to check the number of columns of a non-matrix-like 2-d array , use `numss[i].length`

```java
// 1 2 3
// 4 5 6
int[][] numss = {{1, 2, 3}, {4, 5, 6}};

for (int i = 0; i < numss.length; i++)			// row level
    for (int j = 0; j < numss[i].length; j++) {	// column level
        System.out.print(numss[i][j]);			// read an element
        numss[i][j]++;				// modify an element
    }

```

### 3.2 enhanced for

```java
/**
 * Calculates the sum of a 2d array
 * @param numss the input 2d array
 * @return the sum of the 2d array
 */
public static double sum(double[][] numss) {
    int sum = 0;

    for (double[] nums : numss)
        for (double num : nums)
            sum += num;

    return sum;
}

```

### 3.3 mix of the two

It is common to see a mix of the regular-for and enhanced-for in multi-dimension arrays

```java
/**
 * Increases each element in the array by a specific value
 * @param numss the input array
 * @param value the amount to increase
 */
public static void increaseValue(double[][] numss, double value) {
    for (double[] nums : numss)						// enhanced-for, nothing to change on 2d-array-level
        for (int j = 0; j < nums.length; j++)		// regular-for, value changes on 1d-array-level
            nums[j] += value;
}
```

## 4 Arrays class methods

1. `Arrays.deepToString()` for multi-dimension (not necessary only 2d) array
2. `Arrays.deepEquals()` for multi-dimension array
### 4.1 `Arrays.deepToString()`
(Works for ANY depth: 2D, 3D, n-D)
#### Problem it solves
`Arrays.toString()` prints references for nested arrays:

```java
int[][] a = { {1, 2}, {3, 4} };
System.out.println(Arrays.toString(a));
```
#### Output (bad):

```java
[[I@1b6d3586, [I@4554617c]
```
#### Correct solution: `deepToString()`
```java
import java.util.Arrays;

public class DeepToStringDemo {
    public static void main(String[] args) {
        int[][] a = { {1, 2}, {3, 4} };
        int[][][] b = {
            { {1, 2}, {3, 4} },
            { {5, 6} }
        };

        System.out.println(Arrays.deepToString(a));
        System.out.println(Arrays.deepToString(b));
    }
}
```
#### Output:
```java
[[1, 2], [3, 4]]
[[[1, 2], [3, 4]], [[5, 6]]]
```
✔ Works for any depth
✔ Handles null safely
### 4.2 `Arrays.deepEquals()`
(Compares contents, not references)
#### Problem it solves
`equals()` compares references, not values:
```java
int[][] a = { {1, 2}, {3, 4} };
int[][] b = { {1, 2}, {3, 4} };
System.out.println(a.equals(b));   // false
```
#### Correct solution: `deepEquals()`
```java
import java.util.Arrays;

public class DeepEqualsDemo {
    public static void main(String[] args) {
        int[][] a = { {1, 2}, {3, 4} };
        int[][] b = { {1, 2}, {3, 4} };
        int[][] c = { {1, 2}, {4, 3} };

        System.out.println(Arrays.deepEquals(a, b)); // true
        System.out.println(Arrays.deepEquals(a, c)); // false
    }
}
```
✔ Compares values recursively
✔ Works for mixed dimensions
✔ Handles null correctly

### 4.3 `Arrays.deepHashCode()`
(Used with deepEquals())
#### Why it matters
If you override equality logic (e.g., in tests or hashing), use:
```java
import java.util.Arrays;

public class DeepHashCodeDemo {
    public static void main(String[] args) {
        int[][] a = { {1, 2}, {3, 4} };
        int[][] b = { {1, 2}, {3, 4} };

        System.out.println(Arrays.deepHashCode(a));
        System.out.println(Arrays.deepHashCode(b)); // same value
    }
}
```
✔ Ensures consistent hashing
✔ Required for correctness in hash-based structures
### 4.4 Arrays.copyOf() (Outer array only)
```java
int[][] original = { {1, 2}, {3, 4} };
int[][] copy = Arrays.copyOf(original, original.length);

copy[0][0] = 99;

System.out.println(Arrays.deepToString(original));
```
#### Output:
```java
[[99, 2], [3, 4]]
```
#### Why?
`copyOf()` performs a shallow copy:
New outer array, Same inner arrays
### 4.5 Deep copy of a multidimensional array (correct way)
```java
int[][] original = { {1, 2}, {3, 4} };
int[][] deepCopy = new int[original.length][];

for (int i = 0; i < original.length; i++) {
    deepCopy[i] = Arrays.copyOf(original[i], original[i].length);
}
```
### 4.6 Arrays.fill() (Row-wise usage)
You cannot fill a whole 2D array at once.
Correct approach:
```java
int[][] matrix = new int[3][4];

for (int i = 0; i < matrix.length; i++) {
    Arrays.fill(matrix[i], -1);
}

System.out.println(Arrays.deepToString(matrix));
```
#### Output:
```java
[[-1, -1, -1, -1], [-1, -1, -1, -1], [-1, -1, -1, -1]]
```
## 5 Some Examples
How to access a 2-D array using index relationships? Calculate diagonal sums of a 4 x 4 square matrix. 
```java
Row\Col  0   1   2   3
       ----------------
0 |     1   2   3   4
1 |     5   6   7   8
2 |     9  10  11  12
3 |    13  14  15  16
```
### What are the two diagonals?
#### Primary (main) diagonal
```java
1   x   x   x
x   6   x   x
x   x  11   x
x   x   x  16
```
#### Secondary (anti) diagonal
```java
x   x   x   4
x   x   7   x
x  10   x   x
13  x   x   x
```

```java
/**
 *
 * @author 
 */
public class Week3Part1MultiExample {
    public static void main(String[] args) {

        int[][] theArray = {
            {1, 2, 3, 4}, 
            {5, 6, 7, 8}, 
            {9, 10, 11, 12}, 
            {13, 14, 15, 16}
        };

        int sumOfPrimaryDiagonal = 0;
        int sumOfSecondaryDiagonal = 0;

        for(int row = 0; row < theArray.length; row++) {
            sumOfPrimaryDiagonal += theArray[row][row];
            sumOfSecondaryDiagonal += theArray[row][theArray.length - 1 - row];
        }

        System.out.println(sumOfPrimaryDiagonal);
        System.out.println(sumOfSecondaryDiagonal);

        int sum = sumOfPrimaryDiagonal + sumOfSecondaryDiagonal;

        if (theArray.length % 2 != 0) {
            sum -= theArray[theArray.length/2][theArray.length/2];
            }
            System.out.println("total sum:" + sum);
    }
}
```
#### Why is there a subtraction at the end?
```java
if (theArray.length % 2 != 0)
    sum -= theArray[theArray.length/2][theArray.length/2];
```
#### Concept
1. In an odd-sized matrix (3×3, 5×5), the center element belongs to BOTH diagonals

2. It gets counted twice

3. So we subtract it once

Example (3×3):
```java
2   x   4
x   5   x   ← center (counted twice)
6   x   8
```
1. But your array is 4×4 (even), so:

2. No center element

3. This if does nothing
   
---
