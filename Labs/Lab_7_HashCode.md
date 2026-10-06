# Programming 2 — Lab 10
## HashCode, equals(), and HashSet

**Estimated time:** 55–70 minutes  
**Topics:** `HashSet`, `equals()`, `hashCode()`, duplicate detection, hash collisions, subclass hashing

### Lab goal

You will build a **library membership system**.

The central question is:

> When should two different Java objects be considered the same member?

You will first observe the problem, then define equality correctly, then make `HashSet` behave as intended.

The lecture emphasizes that `hashCode()` helps Java find a likely bucket, while `equals()` makes the final equality decision. Equal objects must have the same hash code. citeturn2view0turn2view3

---

# Part 1 — Create LibraryMember

Create:

```java
public class LibraryMember
```

with:

```text
memberId
name
email
```

Create a constructor and getters.

Create at least five objects.

Two objects should intentionally represent the **same member**:

```text
same memberId
same name
same email
```

but they must be separate objects created with `new`.

Example:

```java
LibraryMember m1 =
    new LibraryMember(101, "Sara Ali", "sara@email.com");

LibraryMember m2 =
    new LibraryMember(101, "Sara Ali", "sara@email.com");
```

### Basic condition

Verify:

```java
System.out.println(m1 == m2);
```

It should print:

```text
false
```

### Tricky condition

The fact that `m1 == m2` is false does **not** automatically mean that the two members should be considered different library members.

---

# Part 2 — Define equals()

Override:

```java
public boolean equals(Object obj)
```

Two `LibraryMember` objects should be equal when:

```text
memberId is the same
AND
name is the same
AND
email is the same
```

### Basic condition

Test:

```java
System.out.println(m1.equals(m2));
```

It should print:

```text
true
```

### Tricky condition

Do not use:

```java
this == obj
```

as the only equality test.

That checks whether the two references point to the exact same object.

---

# Part 3 — Observe HashSet Before hashCode()

Create:

```java
HashSet<LibraryMember> members = new HashSet<>();
```

Add:

```java
m1
m2
```

Print:

```java
members.size()
```

First run your program **before implementing `hashCode()`**.

Record what happens.

Then explain in a comment:

```text
Why can overriding equals() without overriding hashCode()
cause a problem in HashSet?
```

---

# Part 4 — Implement hashCode()

Override:

```java
public int hashCode()
```

Use the same fields that determine equality.

A simple solution may use:

```java
return Objects.hash(memberId, name, email);
```

or a rolling hash pattern using a prime multiplier.

### Basic condition

After adding `m1` and `m2`, the set should contain only one member.

Expected:

```text
1
```

### Tricky condition

The fields used by `hashCode()` must be consistent with the fields used by `equals()`.

If two members are equal, their hash codes **must** be equal.

---

# Part 5 — Test a Different Member

Create:

```java
LibraryMember m3 =
    new LibraryMember(102, "Sara Ali", "sara@email.com");
```

Add `m3`.

Now the set should contain:

```text
2
```

Explain why `m3` is not considered equal to `m1`.

---

# Part 6 — Create a Hash Collision Experiment

Create a class:

```java
public class PoorMember
```

Give it two integer fields:

```text
a
b
```

Temporarily define:

```java
@Override
public int hashCode() {
    return a + b;
}
```

Make:

```text
PoorMember p1 = (1, 2)
PoorMember p2 = (2, 1)
```

Both objects have the same hash code.

### Basic condition

Print both hash codes.

They should be equal.

### Tricky condition

Do not conclude that the two objects are therefore equal.

Add a correct `equals()` method based on both `a` and `b`.

Then put both objects into a `HashSet`.

The set should contain **two** objects.

This demonstrates:

```text
same hashCode != automatically equal
```

The lecture specifically emphasizes that `hashCode()` does not define uniqueness; `equals()` makes the final decision. citeturn2view3

---

# Part 7 — Advanced Understanding: Change the Identity Rule

Suppose the college changes its policy:

> A library member is identified only by `memberId`.

Modify `equals()` and `hashCode()` so that only `memberId` determines equality.

Create:

```text
Member 201 — "Alex", alex1@email.com
Member 201 — "Alex", alex2@email.com
```

Add both to a `HashSet`.

Expected:

```text
1
```

### Final question

Write a comment explaining:

```text
Why must equals() and hashCode() change together when
the definition of "same member" changes?
```

---

# Final Checklist

- [ ] I understand the difference between `==` and `equals()`.
- [ ] I implemented `equals()` correctly.
- [ ] I implemented `hashCode()` using the same identity fields.
- [ ] I tested duplicate objects in a `HashSet`.
- [ ] I tested a different member.
- [ ] I created a deliberate hash collision.
- [ ] I understand that a hash collision does not necessarily mean two objects are equal.
- [ ] I understand why equal objects must have equal hash codes.
- [ ] I changed the identity rule and updated both methods consistently.
