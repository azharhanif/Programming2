# Multi-Dimensional Array Examples — AI-Aware Redesign

**Repository area:** `Lectures/CodeExamplesInLectures/MultiDimensionArray`

The repository contains a Java project with source code under `src/multidimensionarray`. citeturn2view0

## Activity A — trace

Open the existing project and identify:

```java
array.length
array[row].length
array[row][column]
```

Predict the dimensions before running.

## Activity B — modify

Change the data so that the rows have different lengths.

Then answer:

> Why does `array[row].length` become safer than `array[0].length`?

## Activity C — AI-assisted first implementation

Ask AI:

> Write a Java method that receives a 2-D `int` array and returns the largest row sum.

Then test the generated method.

## Activity D — human modification

Change the specification:

> Return the index of the row with the largest sum. If multiple rows tie, return the first one.

Modify the AI implementation.

## Activity E — edge cases

Test:

```text
one row
one column
ragged array
negative values
empty outer array
```

Document which cases your method supports.

## Challenge

Ask AI for a matrix rotation method.

Then explain why a 90-degree clockwise rotation changes:

```text
old[row][col]
```

into a different row/column position.

Do not accept the AI implementation until you can explain the index mapping.
