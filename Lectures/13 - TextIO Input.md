# Programming 2 — Lecture 13: TextIO Input

## Learning objectives

You should be able to:

- distinguish console input from file input;
- read text files with Java;
- use `File`/`Path` and scanners appropriately;
- process lines safely;
- handle missing files and I/O exceptions;
- use try-with-resources;
- separate file-reading logic from business logic.

---

## 1. What is TextIO?

TextIO means reading from and writing to external text files.

Input:

```text
file → Java program
```

Output:

```text
Java program → file
```

A common workflow is:

```text
read file
   ↓
parse data
   ↓
create objects
   ↓
process objects
   ↓
produce result
```

---

## 2. Reading with Scanner

A simple traditional approach:

```java
File file = new File("students.txt");

try (Scanner input = new Scanner(file)) {

    while (input.hasNextLine()) {
        String line = input.nextLine();
        System.out.println(line);
    }

}
catch (FileNotFoundException e) {
    System.out.println("File not found.");
}
```

Try-with-resources automatically closes the Scanner.

---

## 3. Relative paths

If the program uses:

```java
new File("students.txt")
```

the meaning depends on the program's working directory.

Do not assume the file is beside the `.java` file.

When debugging, print the working directory:

```java
System.out.println(System.getProperty("user.dir"));
```

This is often the first thing to check when a file "cannot be found."

---

## 4. Parsing data

Suppose a file contains:

```text
101,Ali,85
102,Mina,91
```

A line can be split:

```java
String[] parts = line.split(",");
```

Then:

```java
int id = Integer.parseInt(parts[0]);
String name = parts[1];
double grade = Double.parseDouble(parts[2]);
```

This can become an object:

```java
Student s = new Student(id, name, grade);
```

---

## 5. Validate before parsing

If the file might contain:

```text
101,Ali,unknown
```

then:

```java
Double.parseDouble("unknown")
```

throws `NumberFormatException`.

The program should decide whether to:

- reject the record;
- report the record;
- skip the record;
- use a default;
- stop processing.

That is a design decision.

---

## 6. Try-with-resources

Prefer:

```java
try (Scanner input = new Scanner(file)) {
    ...
}
```

because the resource is automatically closed.

The general pattern is:

```java
try (Resource r = ...) {
    ...
}
catch (...) {
    ...
}
```

---

# Practice

## Practice 1

Why is this useful?

```java
while (input.hasNextLine()) {
    String line = input.nextLine();
}
```

### Answer

It checks whether another line exists before attempting to read it.

---

## Practice 2

What exception can occur if the file does not exist when using `new Scanner(file)`?

### Answer

`FileNotFoundException`.

---

## Practice 3 — tricky

A file contains:

```text
A,10
B,20
C,not-a-number
D,40
```

If the third line causes `NumberFormatException`, what design choices are possible?

### Answer

Possible designs include:

- stop processing and report the bad record;
- skip only the bad record and continue;
- collect errors and report them after processing.

There is no single correct choice without a specification.

---

## Practice 4

Why is this better than manually closing the Scanner?

```java
try (Scanner input = new Scanner(file)) {
    ...
}
```

### Answer

The resource is closed automatically, including when an exception occurs.

---

# AI-assisted practice

Ask AI to write a method that reads a CSV file into an `ArrayList<Student>`.

Then inspect:

- how the path is handled;
- whether the resource is closed;
- how malformed lines are handled;
- whether empty lines are handled;
- whether numeric parsing can fail;
- whether the method should throw or catch the exception.
