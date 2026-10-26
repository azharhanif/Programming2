# Lecture 10 — `equals()`, `hashCode()`, `HashSet`, and `HashMap`

## Learning goals

By the end of this lecture, you should be able to:

1. Explain why a `Set` is useful and how a `HashSet` differs from an `ArrayList`.
2. Explain what `hashCode()` does and why Java uses it in hash-based collections.
3. Explain the contract between `equals()` and `hashCode()`.
4. Implement `equals()` and `hashCode()` using the same fields to define object identity.
5. Use `Objects.hash(...)` to write a concise `hashCode()` implementation.
6. Explain what a `HashMap<K, V>` stores and how it uses keys.
7. Recognize collisions and explain why equal hash codes do not necessarily mean objects are equal.
8. Test these ideas with small Java programs.

---

## 1. The problem: counting unique authors

### 1.1 Why might counting books give the wrong answer?

Imagine a library has these books:

| Book | Author | Canadian author? |
|---|---|---|
| *Java Basics* | Alice Chen | Yes |
| *Java Projects* | Alice Chen | Yes |
| *Python for Beginners* | Omar Ali | Yes |
| *Web Design* | Maya Singh | No |
| *Advanced Java* | Alice Chen | Yes |

How many books have Canadian authors? There are four such books.

How many **different Canadian authors** are represented? There are only two: Alice Chen and Omar Ali.

If we increase a counter every time we find a book by a Canadian author, Alice is counted three times. We need a way to remember which authors we have already counted.

### 1.2 What is a `Set`?

A `Set` is a Java collection that does not allow duplicate elements according to its equality rules. Think of it as a collection of unique items.

For example, a set of integers:

```java
Set<Integer> numbers = new HashSet<>();

numbers.add(10);
numbers.add(20);
numbers.add(10);  // duplicate value

System.out.println(numbers.size());
System.out.println(numbers.contains(20));
```

Output:

```text
2
true
```

The second `10` is not added as a second element. `size()` returns `2` because the set contains only `10` and `20`.

A `Set` is an **interface**. `HashSet` is one class that implements it. The declaration

```java
Set<Integer> numbers = new HashSet<>();
```

means “use a `Set` reference, with a `HashSet` object to do the work.” This is a common polymorphic style in Java.

Imports for the examples in this lecture:

```java
import java.util.HashSet;
import java.util.Set;
```

### 1.3 Why use `HashSet`?

`HashSet` is a common implementation of `Set`. It uses a hash table internally to help find items efficiently. For ordinary operations such as `add()` and `contains()`, it is typically very fast when hash values are distributed well.

A `HashSet` does **not** promise to keep elements in insertion order. Do not rely on the order in which elements appear when you print or iterate over a set.

Now return to the library problem. We can keep a set of authors already counted:

```java
Set<String> canadianAuthors = new HashSet<>();

canadianAuthors.add("Alice Chen");
canadianAuthors.add("Omar Ali");
canadianAuthors.add("Alice Chen");

System.out.println(canadianAuthors.size());
```

Output:

```text
2
```

This simple example uses `String`. Java's `String` class already implements suitable `equals()` and `hashCode()` methods. What if we want to store our own `Author` objects instead of strings? We must define when two `Author` objects should count as the same author.

---

## 2. First understand `equals()`

### 2.1 Two objects can contain the same data

Consider these two objects:

```java
Author a1 = new Author("Alice Chen", "Canada");
Author a2 = new Author("Alice Chen", "Canada");
```

They are two separately created objects. However, our application may want to consider them the same author because their names and countries match.

By default, `Object.equals()` checks whether the two references refer to the same object. It does not automatically compare the fields of our custom class. We can override `equals()` to define logical equality for our class.

For this example, we will define two authors as equal when both their names and countries match.

### 2.2 An `Author` class with `equals()`

```java
public class Author {
    private String name;
    private String country;

    public Author(String name, String country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Author other = (Author) obj;
        return name.equals(other.name)
                && country.equals(other.country);
    }
}
```

Read the method in steps:

1. `this == obj` checks whether both references point to the exact same object. If so, they are equal.
2. `obj == null` checks whether the other reference is `null`.
3. `getClass() != obj.getClass()` checks that the other object is an `Author` of the same runtime class.
4. `(Author) obj` casts the reference so we can access `Author` fields.
5. The final line compares the fields chosen to define equality.

Because `name` and `country` are strings, calling their `equals()` methods compares their text rather than their object references.

Test it:

```java
Author a1 = new Author("Alice Chen", "Canada");
Author a2 = new Author("Alice Chen", "Canada");
Author a3 = new Author("Alice Chen", "China");

System.out.println(a1 == a2);
System.out.println(a1.equals(a2));
System.out.println(a1.equals(a3));
```

Output:

```text
false
true
false
```

`a1 == a2` is `false` because they are different object references. `a1.equals(a2)` is `true` because our method says their data defines the same author. `a1.equals(a3)` is `false` because the countries differ.

**Design decision:** Equality depends on the rules chosen for the class. If the application treats an author's name as the only identity, the method would be different. In this lecture, the chosen rule is name plus country.

---

## 3. What is `hashCode()` and why does Java need it?

### 3.1 The basic idea

A hash code is an integer returned by an object's `hashCode()` method. Java's hash-based collections use it to help decide where to look for an object.

Imagine a library with 10,000 authors. Every time a new author is found, comparing the new author with every existing author would require many `equals()` calls. A hash table divides storage into areas commonly called **buckets**. A hash code helps Java choose a bucket to examine first.

A useful mental model is:

- `hashCode()` helps answer: **Where should I look?**
- `equals()` helps answer: **Is this the same logical item?**

A hash code is not a unique ID. Different objects can return the same hash code. That situation is called a **collision**.

### 3.2 Important rules

Java's `hashCode()` contract includes these rules:

1. If `a.equals(b)` is `true`, then `a.hashCode()` and `b.hashCode()` **must be equal**.
2. If two objects have the same hash code, they are **not necessarily equal**. Java must use `equals()` when needed to distinguish objects that share a bucket/hash value.
3. If information used by `equals()` does not change, repeated calls to `hashCode()` on the same object during a program execution must return the same value.
4. A good hash function spreads objects reasonably well to reduce collisions. It does not need to produce a unique number for every object.

The most important rule to remember is:

> If you override `equals()`, you should also override `hashCode()` so that equal objects return the same hash code.

This is not merely a style preference. It is essential when objects are used in `HashSet` or as keys in `HashMap`.

### 3.3 A simple collision example

Suppose two different books return the hash code `42`:

```text
Book A: hashCode() -> 42
Book B: hashCode() -> 42
```

That does **not** prove they are the same book. Java checks equality as needed:

```text
Same hash code
       |
       v
equals() says true? ---- yes ---> treat as duplicate key/element
       |
       no
       v
Treat as different objects; both may be stored
```

A collision may make a hash table do more work, but it must not cause unequal objects to be incorrectly treated as equal.

---

## 4. Implementing `hashCode()` with `Objects.hash()`

### 4.1 What is `Objects`?

`Objects` is a utility class in `java.util`. It provides static helper methods for working with objects. It is not the same as `Object`, the superclass of Java classes.

One of its methods is:

```java
Objects.hash(value1, value2, value3)
```

`Objects.hash(...)` takes one or more values and returns an `int` hash code based on those values. It is a convenient way to combine the fields that define equality.

Add this import:

```java
import java.util.Objects;
```

### 4.2 Add `hashCode()` to `Author`

Our `equals()` method defines authors as equal when `name` and `country` match. Therefore, our `hashCode()` should use those same fields:

```java
@Override
public int hashCode() {
    return Objects.hash(name, country);
}
```

The complete class is:

```java
import java.util.Objects;

public class Author {
    private String name;
    private String country;

    public Author(String name, String country) {
        this.name = name;
        this.country = country;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Author other = (Author) obj;
        return Objects.equals(name, other.name)
                && Objects.equals(country, other.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }

    @Override
    public String toString() {
        return name + " (" + country + ")";
    }
}
```

Notice that `Objects.equals(name, other.name)` is null-safe: it can compare the two values even if one or both are `null`. Using `Objects.hash(...)` also handles `null` fields. This version permits null fields; a class could instead reject null values in its constructor.

### 4.3 Why use the same fields?

In this class, equality depends on `name` and `country`:

```java
return Objects.equals(name, other.name)
        && Objects.equals(country, other.country);
```

So the hash code uses:

```java
return Objects.hash(name, country);
```

Do not define equality using `name` and `country` but calculate the hash code using only `name` in a way that causes equal objects to have different hash codes. The hash calculation must respect the equality rule.

It is allowed for unequal objects to have the same hash code. The contract requires equal objects to have the same hash code; it does not require unequal objects to have different hash codes.

### 4.4 Is `Objects.hash()` magic?

No. It is a built-in convenience method. It combines the hash codes of the supplied values into one integer. You do not need to manually write a formula with multipliers for this introductory lecture.

For example:

```java
@Override
public int hashCode() {
    return Objects.hash(id, name, email);
}
```

This is appropriate when `equals()` defines equality using `id`, `name`, and `email`.

For a single field, a direct implementation may be simpler:

```java
@Override
public int hashCode() {
    return Objects.hashCode(id);
}
```

`Objects.hashCode(value)` returns `0` for `null`, or `value.hashCode()` when the value is not null. Be careful: `Objects.hash(id)` and `Objects.hashCode(id)` are not identical operations. The first is a varargs combination; the second returns the hash code of one object (or zero for null).

---

## 5. Put `Author` objects in a `HashSet`

Now that we understand `Set`, `equals()`, and `hashCode()`, the original example is easier to follow.

```java
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<Author> authors = new HashSet<>();

        Author a1 = new Author("Alice Chen", "Canada");
        Author a2 = new Author("Alice Chen", "Canada");
        Author a3 = new Author("Omar Ali", "Canada");

        authors.add(a1);
        authors.add(a2);
        authors.add(a3);

        System.out.println("Number of authors: " + authors.size());
        System.out.println("Contains Alice? " + authors.contains(
                new Author("Alice Chen", "Canada")));
    }
}
```

Expected output:

```text
Number of authors: 2
Contains Alice? true
```

Why?

1. `a1` is added.
2. `a2` has the same name and country, so `a1.equals(a2)` is true. Because their hash codes are consistent, the `HashSet` can check the relevant bucket and recognize the duplicate.
3. `a3` represents a different author and is added.
4. `contains(new Author("Alice Chen", "Canada"))` returns true because the new object is equal to the stored Alice according to our `equals()` method and has a compatible hash code.

The `HashSet` does not use `hashCode()` alone to decide whether objects are duplicates. It uses the hash code to narrow the search and equality to distinguish matching from non-matching objects.

### Important: `HashSet` does not preserve order

The set may print authors in an order different from the order in which they were added. Do not expect a particular iteration order from `HashSet`.

---

## 6. What goes wrong if we override `equals()` but not `hashCode()`?

Imagine that `Author` has the `equals()` method from Section 2, but does not override `hashCode()`. It inherits `Object.hashCode()`, whose value is not derived from the fields we use in `equals()`.

```java
Author a1 = new Author("Alice Chen", "Canada");
Author a2 = new Author("Alice Chen", "Canada");

System.out.println(a1.equals(a2));
```

The result is `true`. But the inherited hash codes are not required to be equal for these two distinct objects. If their hash codes differ, a `HashSet` or `HashMap` may search a different bucket and fail to recognize them as equal.

This violates the required relationship between `equals()` and `hashCode()`.

**Do not teach that every pair of different hash codes guarantees that `equals()` is never called in every implementation detail.** The exact bucket mechanics are implementation-specific. The essential rule is that equal objects must have equal hash codes so hash-based collections can work correctly.

### What if every object returns `1`?

This implementation is legal with respect to the basic hash-code contract:

```java
@Override
public int hashCode() {
    return 1;
}
```

Equal objects do return the same hash code. However, every object collides, so the collection has to do more equality checking. The set can still distinguish unequal objects using `equals()`, but performance can degrade significantly.

Therefore:

- **Correctness rule:** equal objects must have equal hash codes.
- **Performance goal:** distribute different objects across hash values reasonably well.

---

## 7. What is a `HashMap`?

A `HashSet` stores unique elements. A `HashMap` stores **key-value pairs**.

Think of a dictionary:

- The **key** is the word you look up.
- The **value** is the definition associated with that word.

In a program, a student ID could be the key and a student's name could be the value.

```java
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, String> studentNames = new HashMap<>();

        studentNames.put("S101", "Zoe");
        studentNames.put("S102", "Ali");
        studentNames.put("S103", "Mina");

        System.out.println(studentNames.get("S102"));
        System.out.println(studentNames.containsKey("S101"));
    }
}
```

Output:

```text
Ali
true
```

Here:

- `Map<String, String>` means keys are `String` objects and values are `String` objects.
- `put(key, value)` adds a mapping or replaces the value for an existing key.
- `get(key)` retrieves the value for a key.
- `containsKey(key)` checks whether the key exists.

If we execute:

```java
studentNames.put("S102", "Aly");
```

The value for key `S102` is replaced with `Aly`; a second separate `S102` mapping is not created. A map cannot contain duplicate keys, although different keys can map to the same value.

### 7.1 Why does `HashMap` use `hashCode()` and `equals()`?

A `HashMap` uses the key's hash code to help locate where a key-value mapping belongs. When looking up a key, it uses equality checks as needed to determine whether a stored key matches the requested key.

For keys such as `String`, Java already supplies suitable `equals()` and `hashCode()` implementations. For custom key classes, you should implement both methods consistently.

Example: use an employee ID object as a key.

```java
Map<Author, String> authorGenres = new HashMap<>();

Author a1 = new Author("Alice Chen", "Canada");
Author a2 = new Author("Alice Chen", "Canada");

authorGenres.put(a1, "Programming");
System.out.println(authorGenres.get(a2));
```

Because `a1` and `a2` are equal according to `Author.equals()` and have matching hash codes, the lookup using `a2` can find the value stored with `a1` as the key. Output:

```text
Programming
```

Without a consistent `hashCode()`, this lookup may fail even when `equals()` says the keys are equal.

### 7.2 `HashSet` versus `HashMap`

| Collection | Stores | Example use |
|---|---|---|
| `HashSet<E>` | Unique elements | Keep track of unique authors |
| `HashMap<K, V>` | Key-value mappings, with unique keys | Look up a student's name using student ID |

A useful connection: Java's `HashSet` is implemented using a `HashMap` internally. You do not need to know the implementation details to use a `HashSet`, but it helps explain why hash codes matter to both collections.

---

## 8. Which fields should `equals()` and `hashCode()` use?

The fields should reflect the class's definition of logical equality.

Suppose a `Book` has a title, author, and publication year. If our rule is that books are the same only when all three match, then both methods should use all three fields.

```java
import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book other = (Book) obj;
        return year == other.year
                && Objects.equals(title, other.title)
                && Objects.equals(author, other.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year);
    }
}
```

Test the chosen rule:

```java
Book b1 = new Book("Java Basics", "Alice Chen", 2020);
Book b2 = new Book("Java Basics", "Alice Chen", 2020);
Book b3 = new Book("Java Basics", "Alice Chen", 2022);

System.out.println(b1.equals(b2));
System.out.println(b1.equals(b3));
```

Output:

```text
true
false
```

The year matters in our chosen definition, so `b1` and `b3` are not equal. Another application might define identity differently; the important point is that the equality rule must be deliberate and the hash code must respect it.

### Fields that can change

Avoid changing fields used in `equals()` or `hashCode()` while an object is stored as a key in a `HashMap` or as an element in a `HashSet`. If a field changes, the object's new hash code may point to a different bucket than the one where the collection stored it. The object can then become difficult to find or remove. Prefer immutable identity fields for hash-based keys.

---

## 9. Subclasses: a brief note

If a class extends another class, designing equality becomes more subtle. The subclass and superclass must agree on what it means for objects to be equal. Simply adding fields to a subclass's `equals()` can break symmetry or transitivity if the superclass already defines equality.

For this introductory lecture, do not add subclass-specific equality unless the course exercise explicitly asks for it. Follow the equality design given in the assignment, and make sure `hashCode()` respects that same design. In real projects, consider whether a record, immutable value class, or IDE-generated `equals()` and `hashCode()` is appropriate.

---

## 10. Common mistakes

### Mistake 1: Comparing strings with `==`

Incorrect for text equality:

```java
return name == other.name;
```

Usually correct for field-value equality:

```java
return Objects.equals(name, other.name);
```

`==` compares references for objects. `Objects.equals()` compares values safely, including when either reference is `null`.

### Mistake 2: Overriding `equals()` but not `hashCode()`

This can make `HashSet` and `HashMap` behave incorrectly for logically equal objects. Override both methods as a pair.

### Mistake 3: Assuming the hash code is a unique ID

It is not. Different objects can have the same hash code. `equals()` determines logical equality.

### Mistake 4: Assuming the same hash code means a duplicate

False. Java uses equality checks to distinguish objects that share a hash code or bucket.

### Mistake 5: Using different identity fields in the two methods

If equality uses `id` and `email`, the hash code must be consistent with both. The simplest reliable pattern is to use the same fields in `Objects.hash(...)`.

### Mistake 6: Assuming `HashSet` keeps insertion order

It does not guarantee that. If insertion order matters, investigate `LinkedHashSet`; if sorted order matters, investigate `TreeSet` and its ordering rules.

### Mistake 7: Using a mutable object as a map key and then changing its identity fields

If a key's equality/hash fields change after insertion, later lookups may fail. Use stable key fields.

---

## 11. Practice — predict, run, explain

For each question, predict the output before running the code, then explain the role of `equals()` and `hashCode()`.

### Practice 1 — A set of integers

```java
Set<Integer> values = new HashSet<>();
values.add(5);
values.add(8);
values.add(5);
System.out.println(values.size());
```

**Answer:** `2`. The repeated integer value is not stored twice.

### Practice 2 — Same author data

Assume `Author` has the `equals()` and `hashCode()` methods from this lecture.

```java
Set<Author> authors = new HashSet<>();
authors.add(new Author("Alice Chen", "Canada"));
authors.add(new Author("Alice Chen", "Canada"));
System.out.println(authors.size());
```

**Answer:** `1`. The objects are logically equal, and their hash codes are consistent.

### Practice 3 — A collision is not equality

Assume a class has a correct `equals()` method and this hash method:

```java
@Override
public int hashCode() {
    return 1;
}
```

What happens if we add two objects that are not equal?

**Answer:** Both can be stored. They share a hash code, but `equals()` says they are different. This hash function is poor for performance because it causes many collisions, but the collision alone does not make them duplicates.

### Practice 4 — Identify the bug

A class defines equality using `id` and `name`:

```java
@Override
public boolean equals(Object obj) {
    // Assume a correct implementation that compares id and name.
}
```

But its hash method is:

```java
@Override
public int hashCode() {
    return Objects.hash(id);
}
```

Is this always incorrect?

**Answer:** Not necessarily. Equal objects have the same `id`, so this hash code can still satisfy the required contract. However, many unequal objects with the same ID will collide. Including both `id` and `name` is usually a better distribution and makes the implementation align more clearly with the equality fields:

```java
return Objects.hash(id, name);
```

The contract does not require `hashCode()` to use every field in `equals()`; it requires equal objects to have equal hash codes. Using the same fields is a simple and effective guideline.

### Practice 5 — HashMap lookup

```java
Map<String, String> codes = new HashMap<>();
codes.put("A1", "Active");
codes.put("I1", "Inactive");
codes.put("A1", "Archived");
System.out.println(codes.size());
System.out.println(codes.get("A1"));
```

**Answer:**

```text
2
Archived
```

The second `put` for key `A1` replaces the previous value. A `HashMap` cannot store two separate mappings for the same key.

### Practice 6 — The same object twice

```java
Author a = new Author("Alice Chen", "Canada");
Set<Author> authors = new HashSet<>();
authors.add(a);
authors.add(a);
System.out.println(authors.size());
```

**Answer:** `1`. The exact same object is added twice; a set keeps only one occurrence.

### Practice 7 — Explain the contract

Complete this sentence:

“If `a.equals(b)` returns `true`, then `a.hashCode()` and `b.hashCode()` must __________.”

**Answer:** Return the same integer value.

---

## 12. Final summary

| Term | Plain-language meaning |
|---|---|
| `Set` | A collection that does not allow duplicate elements according to its equality rules |
| `HashSet` | A set implementation that uses hashing to help store and find elements efficiently |
| `equals()` | Defines when two objects should count as logically equal |
| `hashCode()` | Returns an integer used by hash-based collections to help locate objects |
| Collision | Different objects have the same hash code or map to the same bucket |
| `Objects.hash(...)` | A built-in helper for combining several values into one hash code |
| `HashMap<K, V>` | Stores key-value mappings and uses keys to retrieve values |

Remember these three rules:

1. **Define equality deliberately.** Decide which fields make two objects logically equal.
2. **Keep `equals()` and `hashCode()` consistent.** If two objects are equal, their hash codes must match.
3. **A hash code is not proof of equality.** Hashing narrows the search; equality determines whether objects match.

## Further reading

- [Java `Object` API: `equals()` and `hashCode()`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Object.html)
- [Java `HashSet` API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/HashSet.html)
- [Java `HashMap` API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/HashMap.html)
- [Java `Objects` API: `Objects.hash()` and related utilities](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Objects.html)
