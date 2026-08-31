# Comparable, Comparator, and HashCode — AI-Aware Redesign

**Repository area:** `Lectures/CodeExamplesInLectures/Comparable_Comparator_HashCode_Lecture_PracticeLab`

The repository currently contains a `ComparatorPractice` folder and `Lab13`. citeturn1view1turn3view1

## Activity A — separate the concepts

Students must create a three-column note:

| Concept | Question |
|---|---|
| Comparable | What is this object's natural ordering? |
| Comparator | What alternative ordering does the caller want? |
| hashCode | When should logically equal objects hash the same way? |

## Activity B — run and predict

Use the existing Lab 13 material.

Predict the order before sorting.

Then identify whether the code uses:

```java
compareTo
```

or:

```java
Comparator
```

## Activity C — AI-assisted first implementation

Ask AI to create:

```text
Comparator<Movie> byTitle
Comparator<Movie> byRatingDescending
Comparator<Movie> byYearAscending
```

Students must test the generated comparators.

## Activity D — human modification

Add a tie-breaker:

```text
rating descending
then title ascending
```

Students should implement and explain the comparator chain.

## Activity E — hashCode challenge

Ask AI to implement `equals()` and `hashCode()` for a class whose logical identity is:

```text
id only
```

Then deliberately create two objects with:

```text
same id
different title
```

Students must determine whether they are equal.

## Tricky questions

### Q1

Why is this poor?

```java
return a.rating - b.rating;
```

**Answer:** The expression is a `double`, not the required comparison result, and subtraction is not the appropriate general comparison pattern.

Use:

```java
Double.compare(a.rating, b.rating);
```

### Q2

If:

```java
a.equals(b)
```

is true, what must be true?

```java
a.hashCode() == b.hashCode()
```

### Q3

Can unequal objects have the same hash code?

**Answer:** Yes. That is a collision.

## AI ownership check

Each student must explain one line of AI-generated comparator code and one line of AI-generated hashCode code without AI assistance.
