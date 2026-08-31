# Programming 2 — Lecture 10: `equals()` and `hashCode()`

## Learning objectives

You should be able to:

- explain why `equals()` and `hashCode()` matter;
- distinguish reference equality from logical equality;
- override `equals(Object)` correctly;
- override `hashCode()` consistently;
- understand why `HashSet` and `HashMap` depend on these methods;
- recognize the `equals`/`hashCode` contract;
- test custom objects in hash-based collections.

---

## 1. Why hashCode?

Suppose a library wants to count unique authors.

If the same author appears on 20 books, we do not want to count the author 20 times.

A `Set<Author>` is designed to keep unique elements.

```java
Set<Author> authors = new HashSet<>();
```

For this to work correctly, Java needs a meaningful definition of equality and a compatible hash code.

---

## 2. `equals()`

For a class such as:

```java
class Movie {
    private int id;
    private String title;
}
```

we may decide that two movies are equal when their IDs are equal.

```java
@Override
public boolean equals(Object obj) {

    if (this == obj) {
        return true;
    }

    if (!(obj instanceof Movie)) {
        return false;
    }

    Movie other = (Movie) obj;

    return this.id == other.id;
}
```

Notice the parameter type:

```java
Object
```

because that is the method defined by `Object`.

---

## 3. Null safety

This is dangerous:

```java
if (obj == null || !(obj instanceof Movie)) {
    return false;
}
```

This is a valid pattern.

Because `instanceof` is false for `null`, this can also be written in a suitable context as:

```java
if (!(obj instanceof Movie)) {
    return false;
}
```

Do not write code that casts `null` and then immediately accesses fields.

---

## 4. hashCode contract

The central rule:

> If `a.equals(b)` is true, then `a.hashCode()` must equal `b.hashCode()`.

The reverse is not required.

Two unequal objects may have the same hash code. That is a collision.

---

## 5. Example

If equality is based only on `id`:

```java
@Override
public int hashCode() {
    return Integer.hashCode(id);
}
```

Then two Movies with the same ID produce the same hash code.

A common implementation for multiple fields is:

```java
@Override
public int hashCode() {
    return Objects.hash(id, title);
}
```

provided equality also uses the same logical fields.

---

## 6. Why both methods matter

Suppose:

```java
Movie a = new Movie(10, "A");
Movie b = new Movie(10, "A");
```

If:

```java
a.equals(b)
```

is true but their hash codes differ, a `HashSet` may place them in different hash buckets and fail to behave as expected.

Therefore:

```text
equals() and hashCode()
        ↓
must agree
```

---

## 7. HashSet example

```java
HashSet<Movie> movies = new HashSet<>();

movies.add(new Movie(10, "A"));
movies.add(new Movie(10, "A"));
```

If equality and hashCode are correctly implemented, the set should contain one logical movie.

---

## 8. HashMap

A HashMap associates a key with a value:

```java
HashMap<Integer, Movie> movies = new HashMap<>();

movies.put(10, new Movie(10, "A"));
```

The key's hash code helps locate the bucket.

When looking up:

```java
movies.get(10);
```

the map uses the key's hashing/equality behavior.

For custom object keys, correct `equals()` and `hashCode()` become essential.

---

## 9. Mutable keys: a dangerous design

Suppose:

```java
class Student {
    private int id;
}
```

and `id` is used in `hashCode()`.

If a Student is placed into a HashSet and then its ID changes, the object may effectively belong to a different hash bucket than the one in which it was stored.

This is why fields used in equality/hashing should generally be stable while the object is acting as a hash key.

---

# Practice

## Practice 1

If:

```java
a.equals(b) == true
```

what must be true?

### Answer

```java
a.hashCode() == b.hashCode()
```

---

## Practice 2

Can two unequal objects have the same hash code?

### Answer

Yes. That is a hash collision.

---

## Practice 3

What is wrong?

```java
@Override
public boolean equals(Object o) {
    Movie m = (Movie)o;
    return id == m.id;
}
```

### Answer

It blindly casts `o`. If `o` is null or another type, it can fail.

A safer implementation checks:

```java
if (!(o instanceof Movie)) {
    return false;
}
```

---

## Practice 4 — tricky

Suppose:

```java
equals() uses id
hashCode() uses title
```

Is that a correct implementation?

### Answer

Not necessarily. It can violate the contract. If two objects have the same ID but different titles, they may be equal but have different hash codes.

The same logical equality fields should normally be used to compute the hash code.

---

## Practice 5

Why is this acceptable?

```java
@Override
public int hashCode() {
    return Integer.hashCode(id);
}
```

### Answer

If `equals()` defines equality solely by `id`, this guarantees equal objects have equal hash codes.

---

# AI-assisted practice

Ask AI to implement `equals()` and `hashCode()` for a `Student`.

Then deliberately test:

```java
Student a = new Student(100, "Ali");
Student b = new Student(100, "Different Name");
```

Decide whether they should be equal according to the application's definition.

Then verify that your hash code follows that definition.
