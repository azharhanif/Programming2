# Programming 2 — Lecture 9: Comparable and Comparator

## Learning objectives

You should be able to:

- explain why sorting custom objects requires a comparison rule;
- implement `Comparable<T>`;
- implement `compareTo`;
- create external `Comparator<T>` objects/lambdas;
- distinguish natural ordering from alternative orderings;
- avoid subtraction-based comparison bugs;
- sort an `ArrayList` using different rules.

---

## 1. Why can't Java automatically sort every object?

Suppose:

```java
class Movie {
    String title;
    double rating;
}
```

What does it mean for one Movie to be "smaller"?

Possible answers:

- lower rating;
- higher rating;
- alphabetical title;
- shorter title;
- older release year.

The programmer must define the ordering.

---

## 2. Comparable: natural ordering

A class implements `Comparable<T>` when it has a natural/default ordering.

```java
class Movie implements Comparable<Movie> {

    private String title;
    private double rating;

    @Override
    public int compareTo(Movie other) {
        return Double.compare(this.rating, other.rating);
    }
}
```

Now:

```java
Collections.sort(movies);
```

can use that natural ordering.

---

## 3. Meaning of compareTo

For:

```java
a.compareTo(b)
```

the result means:

```text
negative → a comes before b
zero     → a and b are equal in ordering
positive → a comes after b
```

Do not interpret the exact number as important. The sign is the important part.

---

## 4. Why subtraction is risky

Avoid:

```java
return this.rating - other.rating;
```

for integers and especially for floating-point values when the result is converted to an integer.

For integers, subtraction can overflow:

```java
return this.id - other.id;
```

A safer pattern:

```java
return Integer.compare(this.id, other.id);
```

For doubles:

```java
return Double.compare(this.rating, other.rating);
```

---

## 5. Comparator: external comparison rule

A Comparator lets you define a sorting rule outside the class.

```java
Comparator<Movie> byTitle =
        (a, b) -> a.getTitle().compareTo(b.getTitle());
```

Then:

```java
movies.sort(byTitle);
```

Another rule:

```java
Comparator<Movie> byRatingDescending =
        (a, b) -> Double.compare(b.getRating(), a.getRating());
```

Same Movie class, different sorting behavior.

---

## 6. Why Comparator is powerful

A Movie can naturally be ordered by rating, but users might also want:

```text
title
rating
year
duration
```

You do not want to rewrite Movie every time.

Comparators let the caller choose the rule.

---

## 7. Comparator composition

A useful pattern:

```java
Comparator<Movie> comp =
    Comparator.comparing(Movie::getTitle)
              .thenComparingDouble(Movie::getRating);
```

This means:

1. compare titles;
2. if titles are equal, compare ratings.

This is a more advanced design pattern.

---

## 8. Comparable versus Comparator

| | Comparable | Comparator |
|---|---|---|
| Where? | inside class | outside class |
| Method | `compareTo` | `compare` |
| Main purpose | natural ordering | alternative/custom ordering |
| Multiple rules? | awkward | easy |

---

# Practice

## Practice 1

What should `compareTo` return when `this` should come before `other`?

### Answer

Any negative integer.

---

## Practice 2

Write a Comparator for Movies sorted by rating descending.

### Answer

```java
Comparator<Movie> byRating =
    (a, b) -> Double.compare(b.getRating(), a.getRating());
```

---

## Practice 3

What is wrong?

```java
Comparator<Movie> comp =
    (a, b) -> a.getRating() - b.getRating();
```

### Answer

The expression is a `double`, while Comparator requires an `int`. It also represents a poor comparison pattern.

Use:

```java
Double.compare(a.getRating(), b.getRating())
```

---

## Practice 4 — tricky

Sort by title ascending, then rating descending.

### Answer

```java
Comparator<Movie> comp =
    Comparator.comparing(Movie::getTitle)
              .thenComparing(
                  Comparator.comparingDouble(Movie::getRating).reversed()
              );
```

---

## Practice 5 — reasoning

If:

```java
a.compareTo(b) == 0
```

does that necessarily mean:

```java
a.equals(b)
```

### Answer

Not necessarily. Ordering equality and object equality are related concepts but are not automatically identical. A class should define them consistently when a collection or application requires that behavior.

---

# AI-assisted practice

Ask AI:

> Create three different Comparator<Movie> objects: by title, by rating descending, and by year ascending.

Then test the generated comparators with:

- duplicate titles;
- equal ratings;
- missing/null titles if your design permits them.

Explain every comparator before using it.
