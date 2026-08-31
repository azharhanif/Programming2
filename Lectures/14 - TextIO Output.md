# Programming 2 — Lecture 14: TextIO Output

## Learning objectives

You should be able to:

- create/write text files;
- distinguish overwrite from append mode;
- use try-with-resources;
- format output;
- handle `IOException`;
- design output that can later be read reliably.

---

## 1. Basic output

A traditional approach uses `FileWriter`.

```java
File file = new File("report.txt");

try (FileWriter writer = new FileWriter(file)) {
    writer.write("Hello\n");
    writer.write("Programming 2\n");
}
catch (IOException e) {
    System.out.println("Could not write file.");
}
```

---

## 2. Overwrite versus append

This:

```java
new FileWriter(file)
```

normally overwrites existing content.

To append:

```java
new FileWriter(file, true)
```

Example:

```java
try (FileWriter writer = new FileWriter(file, true)) {
    writer.write("New line\n");
}
```

Be deliberate about which behavior the assignment requires.

---

## 3. Buffered writing

For larger output, a buffered writer can be appropriate:

```java
try (BufferedWriter writer =
         new BufferedWriter(new FileWriter(file))) {

    writer.write("Hello");
    writer.newLine();
}
```

---

## 4. Output formatting

If your output is intended for another program, keep the format predictable.

For CSV:

```java
writer.write(id + "," + name + "," + grade);
writer.newLine();
```

Be careful if names can contain commas. A real CSV format has escaping rules.

---

## 5. Writing objects

Do not automatically write:

```java
writer.write(student.toString());
```

and assume it is a reversible data format.

A human-readable report and a machine-readable data file have different goals.

For a report:

```text
Student: Ali
ID: 101
Grade: 88.5
```

For structured data:

```text
101,Ali,88.5
```

The second is easier to parse consistently.

---

# Practice

## Practice 1

What is the difference between:

```java
new FileWriter(file)
```

and:

```java
new FileWriter(file, true)
```

### Answer

The first normally overwrites. The second enables append mode.

---

## Practice 2

Why should file output usually use try-with-resources?

### Answer

It ensures the writer is closed even when an exception occurs.

---

## Practice 3 — tricky

Why can writing `student.toString()` be a poor choice for persistent data?

### Answer

`toString()` is primarily intended as a readable representation. Its format may change and may not contain enough structured information to reliably reconstruct an object.

---

# AI-assisted practice

Ask AI to write a method that exports an `ArrayList<Student>` to CSV.

Then modify the design so that:

- headers are included;
- output is deterministic;
- resources are closed;
- malformed names are considered;
- an existing file is not accidentally destroyed if append mode is required.
