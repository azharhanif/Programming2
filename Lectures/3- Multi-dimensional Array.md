# Programming 2 — Lecture 3: Multi-Dimensional Arrays

## Learning objectives

You should be able to:

- explain a 2-D array as an array of arrays;
- access rows and columns;
- use nested loops;
- distinguish `matrix.length` from `matrix[row].length`;
- work with ragged arrays;
- process rows, columns, and individual cells.

## 1. Key concept

A Java 2-D array is an array whose elements are themselves arrays.

```java
int[][] matrix = new int[3][4];
```

means:

```text
3 row arrays
each containing 4 integers
```

The first index is the row; the second is the column.

```java
matrix[1][2]
```

means row 1, column 2.

## 2. Length

```java
matrix.length
```

is the number of rows.

```java
matrix[0].length
```

is the number of columns in row 0.

For a rectangular matrix:

```java
int[][] matrix = new int[3][4];
```

we get:

```text
matrix.length       → 3
matrix[0].length    → 4
```

## 3. Nested loops

```java
for (int row = 0; row < matrix.length; row++) {

    for (int col = 0; col < matrix[row].length; col++) {
        System.out.print(matrix[row][col] + " ");
    }

    System.out.println();
}
```

Use `matrix[row].length` when you want code that also works with ragged arrays.

## 4. Ragged arrays

Java permits:

```java
int[][] seats = new int[3][];

seats[0] = new int[10];
seats[1] = new int[14];
seats[2] = new int[8];
```

The rows have different lengths.

This is one reason that:

```java
matrix[row].length
```

is safer than assuming all rows have the same number of columns.

## 5. Initial values

```java
int[][] a = {
    {1, 2, 3},
    {4, 5, 6}
};
```

Visualize:

```text
1 2 3
4 5 6
```

## 6. Row processing

```java
public static int rowSum(int[][] matrix, int row) {

    int sum = 0;

    for (int col = 0; col < matrix[row].length; col++) {
        sum += matrix[row][col];
    }

    return sum;
}
```

## 7. Entire matrix

```java
public static int total(int[][] matrix) {

    int sum = 0;

    for (int[] row : matrix) {
        for (int value : row) {
            sum += value;
        }
    }

    return sum;
}
```

Enhanced-for is excellent when indexes are not needed.

## 8. A common null-row error

This is dangerous:

```java
int[][] a = new int[3][];

for (int row = 0; row < a.length; row++) {
    System.out.println(a[row].length);
}
```

The rows are still `null`.

Initialize them first.

## Practice

### Q1

What is printed?

```java
int[][] a = {
    {1,2},
    {3,4},
    {5,6}
};

System.out.println(a.length);
System.out.println(a[1].length);
System.out.println(a[2][0]);
```

**Answer:**

```text
3
2
5
```

### Q2

Write a method that returns the largest value in a 2-D array.

**Answer:**

```java
public static int max(int[][] values) {

    int max = values[0][0];

    for (int[] row : values) {
        for (int value : row) {
            if (value > max) {
                max = value;
            }
        }
    }

    return max;
}
```

### Q3 — tricky

Why can this fail for a ragged array?

```java
for (int row = 0; row < matrix.length; row++) {
    for (int col = 0; col < matrix[0].length; col++) {
        System.out.println(matrix[row][col]);
    }
}
```

**Answer:** Different rows may have different lengths. Use `matrix[row].length`.

### Q4 — design

A theatre has rows with different numbers of seats. Should a ragged array be considered?

**Answer:** Yes. A ragged 2-D array can represent the actual capacity of each row without inventing unused cells.

# AI-assisted practice

Ask AI to rotate a square matrix 90 degrees clockwise. Test it on a 2×2 and 3×3 matrix, explain the index transformation, and identify whether the solution modifies the original matrix or creates a new one.
