
# P2 - Assignment03Win26

## **Due Date:** 

Apr-27 23:57:00. **Late submission will directly be marked as 0**.

## **Submission:**

**Please submit .java files for the code and a .png or .jpg for the class diagram**.

**Full Score**: 100

**Grading**: 

60% on the submitted project code.

40% on the post-submission challange. 

**What is post-submission challange?** 
After final code submission there will be an in class extension challenges built directly on the submitted project. The challange is designed to fit a 30-minute pen-and-paper coding task, and will test high-level understanding, not memorization.

Each challenge:

•	plugs into the existing submitted solution

•	targets a different OOP skill

•	is specific enough to grade consistently

## Key Concepts for This Assignment

1. Recursion
2. Exception Handling

***

## Task 1

### Requirement

1. Create a method `void printShape(int row, char symbol)` that prints a certain pattern displayed as below:

   (Note, for all `pringShape` methods, you allow to use for loop to print a row, but you should only use recursion to shift from one row to another)
```java
5, @

@ @ @ @ @
@ @ @ @
@ @ @
@ @
@

// There is a space between each @
```

2. Create a method `void printShape2(int row, char symbol)` that prints a certain pattern displayed as below:
```java
5, @

@
@ @
@ @ @
@ @ @ @
@ @ @ @ @

// There is a space between each @
```

3. Create a method `void printShape3(int totalRow, char symbol, int currentRowId)` that prints a certain pattern displayed as below:

   Hint: you need the total number of rows to calculate the number of space for each row. For example, if there is only one row, then you need zero spaces, if there are two rows, then you need two spaces in front, if there are three rows, then you need four spaces, etc. You should have two formulas,  one to calculate the number of spaces and the other one to calculate the number of symbols.
```java
4, !, 4
    
      !
    ! ! !
  ! ! ! ! !
! ! ! ! ! ! ! 

// There is a space between each !
```

4. **[Bonus Question]** Create a method `void printShape4(int row, char symbol, int currentRowId)` that prints a certain pattern displayed as below. *Note: here, 3 indicates that the longest row will be the 4th row, so that there are 3 rows above it, and 3 rows below it.*

   Hint: you can have one general pattern for the upper part, and another general pattern for the lower part.
```java
3, !, 3 
    
      !
    ! ! !
  ! ! ! ! !
! ! ! ! ! ! !
  ! ! ! ! !
    ! ! !
      !

// There is a space between each !
```

5. Create a method `int[][] generateMatrix(int row, int boundary1, int boundary2, int iteration)` that generates a random square matrix  (`row` equals `col`) with random numbers between `[min(boundary1, boundary2), max(boundary1, boundary2))`.
    * The sum of the diagonal and the sub-diagonal should be the same. If not, regenerate it again, until a matrix that satisfies the condition is generated (return that matrix).
    
    * If you try `iteration` times and none of the matrixes satisfy the condition, return `null`.

## Task 2

### Requirement

Define a new exception class: `InvalidNumberException`, which contains two constructors:
1. Default constructor
2. Constructor with `String` as a parameter

***

## Task 3

### Requirement

1. Create a method `Integer[][] generateRandomMatrix(int row, int col, double upperBound)` to generate some random numbers in range `[1, upperBound]`. 
   
    * If  the `upperBound` is lower than `1`, replace it by a default value of `10`. 
    * For each number in the matrix, there needs to be a `1/5` chance it will be a `null`, a `2/5` chance it will be a normal random number, and a `2/5` chance it will be a negative random number (generate a random number and then multiply it by `-1`)


2. Create a method `String[][] calcResult(Integer[][] numss)` that takes a matrix and then reads the values from the left to the right, and from the top to the bottom. This value will be stored as `num1`. Then, it should read from the right to the left, and from the bottom to the top. This value will be stored as `num2`.
   * Calculate `num1/num2` as a result, with two decimals.
   * If any value of `num1` or `num2` is negative, throw the `InvalidNumberException`.
   * The method should be able to handle `ArithmeticException`, `NullPointerException`, and `InvalidNumberException`.
   * If it is an `ArithmeticException`, put an `A` in the result. If it is a `NullPointerException`, put a `N` in the result. If it is an `InvalidNumberException`, put a `I` in the result.

*Example*

```java
// input matrix
1       2       3
null    5       0
7      -8       0

/**
Cell 1: num1 = 1, num2 = 0, result = 1 / 0 -> A 
Cell 2: num1 = 2, num2 = -8, result = 2 / -8 -> I
Cell 3: num1 = 3, num2 = 7, result = 3 / 7 -> 0.43
Cell 4: num1 = null, num2 = 0, result = null / 0 -> N
Cell 5: num1 = 5, num2 = 5, result = 5 / 5 -> 1.00
Cell 6: num1 = 0, num2 = null, result = 0 / null -> N
Cell 7: num1 = 7, num2 = 3, result = 7 / 3 -> 2.33
Cell 8: num1 = -8, num2 = 2, result = -8 / 2 -> I
Cell 9: num1 = 9, num2 = 1, result = 0 / 1 -> 0.00
*/
