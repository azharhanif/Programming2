# Single-Dimension Array Examples — AI-Aware Redesign

**Repository area:** `Lectures/CodeExamplesInLectures/SingleDimArrayLectureExamples`

The current repository contains examples including `AccountTest`, `InClassLec1`, `InClassLec1V2`, and `ShallowDeep`. 

## Activity A — Predict before running

Open the existing `ShallowDeep` example.

Before running it, answer:

1. Which variables refer to the same array/object?
2. Which assignment creates a new object?
3. If one reference changes an element, which other reference can observe the change?
4. What would happen if `Arrays.copyOf` were used instead?

Run the example and compare your prediction.

## Activity B — Explain the existing example

Do not modify the original first.

Draw:

```text
reference → object/array
```

for every important variable.

Then explain the difference between:

```java
b = a;
```

and:

```java
b = Arrays.copyOf(a, a.length);
```

## Activity C — AI-assisted first implementation

Ask AI to implement:

```java
public static int countAboveAverage(int[] values)
```

Requirements:

- return how many values are strictly greater than the average;
- observe what happens when you call empty array.

## Activity D — Human modification

Change the requirement:

> Count values greater than or equal to the average, but ignore negative values when calculating the average.

Modify the AI code yourself.

Write down:

- original AI approach;
- what you changed;
- why the change is necessary.

## Activity E — test

At minimum test:

```text
{10, 20, 30}
{5}
{}
{-10, 10, 20}
{2, 2, 2}
```

## Tricky question

Why does:

```java
int[] b = a;
```

not make a copy?

**Answer:** Both variables refer to the same array object.

## Reflection

Complete:

> The most important difference between a reference assignment and copying an array is ...
