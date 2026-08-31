# Programming 2 — Lecture 2: Single-Dimension Arrays

## Learning objectives

You should be able to:

- explain why an array has a fixed size;
- create arrays with default or explicit values;
- distinguish `null` from an empty `String`;
- access and modify elements safely;
- use the important `Arrays` utility methods;
- distinguish reference copying from copying an array;
- choose between regular `for` and enhanced `for`;
- work with arrays of objects.

## 1. What is an array?

An array is an object containing a fixed number of elements of the same declared type.

```java
int[] scores = new int[5];
```

Conceptually:

```text
index:  0   1   2   3   4
value:  0   0   0   0   0
```

The size is fixed after creation.

Use arrays when the number of positions is naturally fixed, such as seven days, twelve months, or an 8×8 board. When the collection grows and shrinks frequently, consider `ArrayList`.

## 2. Creating arrays

Known size:

```java
double[] scores = new double[5];
```

Known values:

```java
int[] marks = {82, 75, 91, 88};
```

Declare first and initialize later:

```java
double[] nums;
nums = new double[]{1, 2, 3};
```

This is illegal:

```java
double[] nums;
nums = {1, 2, 3}; // ❌
```

### Default values

| Element type | Default |
|---|---|
| numeric primitive | `0` / `0.0` |
| `boolean` | `false` |
| `char` | `'\u0000'` |
| object reference | `null` |

Remember:

```java
String a = null;
String b = "";
```

`a` references no String object. `b` references a real String whose length is zero.

## 3. Indexing

For:

```java
int[] values = {10, 20, 30, 40};
```

valid indexes are `0` through `3`.

The last valid index is:

```java
values.length - 1
```

Use:

```java
for (int i = 0; i < values.length; i++) {
    System.out.println(values[i]);
}
```

not:

```java
i <= values.length
```

The latter eventually accesses an invalid index.

## 4. Arrays utility class

```java
import java.util.Arrays;
```

### Print

```java
System.out.println(Arrays.toString(values));
```

### Compare contents

```java
Arrays.equals(a, b)
```

Do not use `a == b` to compare array contents. `==` compares references.

### Copy

```java
int[] copy = Arrays.copyOf(values, values.length);
```

This creates a separate array.

### Copy a range

```java
int[] part = Arrays.copyOfRange(values, 1, 4);
```

The range is `[1,4)`: index 1 included, index 4 excluded.

### Sort

```java
Arrays.sort(values);
```

This modifies the original array.

### Fill

```java
Arrays.fill(values, 0);
```

This also modifies the original array.

## 5. Reference copy versus copied array

```java
int[] a = {1, 2, 3};
int[] b = a;
```

Both references point to the same array.

```java
b[0] = 99;
```

Now `a[0]` is also `99`.

For a separate array:

```java
int[] b = Arrays.copyOf(a, a.length);
```

## 6. Enhanced-for

Use an enhanced-for loop when you need each value but not its index:

```java
for (int mark : marks) {
    System.out.println(mark);
}
```

This does not modify primitive array elements:

```java
for (int mark : marks) {
    mark++;
}
```

Use an index:

```java
for (int i = 0; i < marks.length; i++) {
    marks[i]++;
}
```

For object arrays, however, the enhanced-for variable refers to the object, so calling a mutating method can modify that object:

```java
for (Student s : students) {
    s.setGrade(100);
}
```

## 7. Two arrays synchronized by index

When two arrays must be processed at the same position, use an index:

```java
public static double sumSelected(
        double[] values, boolean[] selected) {

    int length = Math.min(values.length, selected.length);
    double sum = 0;

    for (int i = 0; i < length; i++) {
        if (selected[i]) {
            sum += values[i];
        }
    }

    return sum;
}
```

## 8. Arrays of objects

```java
Student[] students = new Student[3];
```

Initially:

```text
[null, null, null]
```

Create objects before using their methods:

```java
students[0] = new Student(...);
```

Otherwise:

```java
students[0].getName();
```

can cause `NullPointerException`.

# Practice

### Q1 — predict

```java
int[] a = {2, 4, 6, 8};

for (int i = 0; i < a.length; i += 2) {
    System.out.print(a[i] + " ");
}
```

**Answer:** `2 6`

### Q2 — error

```java
int[] a = new int[3];
a[3] = 10;
```

**Answer:** `ArrayIndexOutOfBoundsException`; valid indexes are 0, 1, 2.

### Q3 — aliasing

```java
int[] a = {1,2,3};
int[] b = a;
b[1] = 99;
System.out.println(a[1]);
```

**Answer:** `99`, because both references point to the same array.

### Q4 — copy

```java
int[] a = {1,2,3};
int[] b = Arrays.copyOf(a, a.length);
b[1] = 99;
```

**Answer:** `a[1]` remains `2`; `b[1]` is `99`.

### Q5 — design

Seven fixed positions are required for days of the week. Array or ArrayList?

**Answer:** Array is a natural choice because the number of positions is fixed.

### Q6 — harder

Write a method that returns the number of values greater than the average.

**Answer:**

```java
public static int countAboveAverage(int[] values) {

    if (values.length == 0) {
        return 0;
    }

    double sum = 0;

    for (int value : values) {
        sum += value;
    }

    double average = sum / values.length;
    int count = 0;

    for (int value : values) {
        if (value > average) {
            count++;
        }
    }

    return count;
}
```

# AI-assisted practice

Ask an AI tool for a method that returns the second-largest value in an array. Then test duplicates, a one-element array, negative values, and an empty array. Explain the generated code and modify it to match your chosen specification.
