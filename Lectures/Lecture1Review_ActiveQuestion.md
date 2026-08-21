# Active Learning Questions

## Q1. What is the difference between:

```java
String name = "Alice";
```

and

```java
Student s = new Student();
```

### Answer

Both are **reference variables**, but they refer to objects of different classes.

```java
String name = "Alice";
```

- `String` is a Java class.
- `"Alice"` is a String object.
- `name` is a reference variable referring to that object.

```java
Student s = new Student();
```

- `Student` is a user-defined class.
- `new Student()` creates a new `Student` object.
- `s` refers to that object.

### Conceptual View

```
name ─────► "Alice" String object

s ────────► Student object
             ├── fields
             └── methods
```

### Key Point

A variable such as `s` does **not contain the entire object directly**. It stores a reference to the object.

**Expected Mark: 1**

---

## Q2. What is the difference between:

```java
array.length
```

and

```java
str.length()
```

### Answer

For an array, `length` is a **field**.

For a String, `length()` is a **method**.

### Important Distinction

- `array.length` → no parentheses because it is a field.
- `str.length()` → parentheses because it is a method call.

### Common Mistakes

```java
numbers.length();   // WRONG
str.length;         // WRONG
```

**Expected Mark: 1**

---

## Q3. Why is this dangerous?

```java
for(int i = 0; i <= str.length(); i++)
```

### Answer

String indexes range from `0` to `str.length() - 1`.

The correct loop is:

```java
for(int i = 0; i < str.length(); i++) {
    System.out.println(str.charAt(i));
}
```

Using `<=` eventually causes a `StringIndexOutOfBoundsException`.

### Teaching Point

This is an **off-by-one error**.

**Expected Mark: 1**

---

## Q4. What is the difference between `==` and `equals()` when comparing objects?

### Answer

- `==` compares whether two references point to the **same object**.
- `equals()` compares **logical/content equality**.

Example:

```java
String a = new String("Java");
String b = new String("Java");
```

```java
a == b        // false
a.equals(b)   // true
```

**Expected Mark: 1.5**

---

## Q5. Why does `Math.random()` never produce exactly `1.0`?

### Answer

`Math.random()` returns values in the range:

```text
0.0 <= value < 1.0
```

Therefore `1.0` is never produced.

Example:

```java
int dice = (int)(Math.random() * 6) + 1;
```

Produces:

```text
1, 2, 3, 4, 5, 6
```

**Expected Mark: 1**

---

## Q6. When would you choose `for`, `while`, or `do...while`?

### for

Use when the number of iterations is known.

```java
for(int i = 0; i < 10; i++) {
    System.out.println(i);
}
```

### while

Use when repetition depends on a condition.

```java
while(balance > 0) {
    balance -= 10;
}
```

### do...while

Use when the body must execute at least once.

```java
do {
    System.out.println("Enter your PIN:");
    pin = console.next();
} while(!pin.equals(correctPin));
```

**Expected Mark: 1.5**

---

## Q7. What is the difference between:

```java
Student s1 = new Student();
Student s2 = s1;
```

and

```java
Student s2 = new Student(s1);
```

### Answer

```java
Student s2 = s1;
```

Creates a second reference to the same object.

```java
s1 == s2   // true
```

```java
Student s2 = new Student(s1);
```

Creates a new object using a copy constructor.

```java
s1 == s2   // false
```

**Expected Mark: 1.5**

---

## Q8. What is the difference between a static variable and an instance variable?

### Answer

### Instance Variable

Belongs to an individual object.

```java
class Student {
    private String name;
    private int id;
}
```

Each object gets its own copy.

### Static Variable

Belongs to the class itself.

```java
class Student {
    private static int count = 0;
}
```

Only one shared copy exists.

### Key Distinction

- **Instance Variable:** What does this object have?
- **Static Variable:** What does the class have?

**Expected Mark: 1.5**

---

## Total Teaching Focus

Students should understand:

- References vs objects
- Arrays vs Strings (`length` vs `length()`)
- Off-by-one errors
- `==` vs `equals()`
- Random number ranges
- Loop selection
- Copy constructors vs reference assignment
- Static vs instance variables
