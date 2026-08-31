# Programming 2 — Lecture 15: Serialization and Deserialization

## Learning objectives

You should be able to:

- explain serialization and deserialization;
- implement `Serializable`;
- serialize objects to a file;
- deserialize objects;
- understand object graphs;
- recognize `serialVersionUID`;
- understand why serialization is different from ordinary text output;
- recognize security and compatibility concerns.

---

## 1. Why serialization?

Suppose an application has:

```java
ArrayList<Student> students;
```

When the program ends, the objects in memory disappear.

Serialization allows an object graph to be converted into a byte stream that can be stored and later reconstructed.

Conceptually:

```text
objects in memory
      ↓
serialization
      ↓
file/byte stream
```

Later:

```text
file/byte stream
      ↓
deserialization
      ↓
objects in memory
```

---

## 2. Serializable

A class can implement:

```java
implements Serializable
```

Example:

```java
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
```

`Serializable` is a marker interface: it signals that instances may participate in Java's serialization mechanism.

---

## 3. ObjectOutputStream

```java
try (ObjectOutputStream out =
         new ObjectOutputStream(
             new FileOutputStream("students.ser"))) {

    out.writeObject(students);
}
catch (IOException e) {
    e.printStackTrace();
}
```

The entire object graph referenced by `students` must be serializable.

---

## 4. ObjectInputStream

```java
try (ObjectInputStream in =
         new ObjectInputStream(
             new FileInputStream("students.ser"))) {

    ArrayList<Student> students =
        (ArrayList<Student>) in.readObject();
}
catch (IOException | ClassNotFoundException e) {
    e.printStackTrace();
}
```

Deserialization reconstructs the object graph.

---

## 5. Object graph

If:

```java
ArrayList<Student> students
```

contains Student objects, the serialized graph includes the list and its serializable Student objects.

If Student contains a non-serializable object, serialization can fail.

---

## 6. `serialVersionUID`

A class can declare:

```java
private static final long serialVersionUID = 1L;
```

It helps Java determine whether a serialized representation is compatible with the current class definition.

If a class evolves incompatibly, deserialization may fail.

---

## 7. Serialization versus TextIO

TextIO:

```text
human-readable
```

Serialization:

```text
binary/object-oriented representation
```

Use TextIO when interoperability/readability matters.

Serialization can be convenient when Java needs to persist and restore object structures.

---

## 8. Security warning

Do not treat serialized data from an untrusted source as automatically safe.

Deserialization can have security implications.

For a classroom project, focus on serialization within a controlled application, but understand that real systems need stronger safeguards.

---

# Practice

## Practice 1

Why must Student implement Serializable if you want to serialize an ArrayList<Student>?

### Answer

The serialized object graph includes the Student objects. They must be serializable.

---

## Practice 2

What is the difference?

```text
serialization
```

versus:

```text
deserialization
```

### Answer

Serialization converts objects to a byte stream. Deserialization reconstructs objects from the byte stream.

---

## Practice 3 — tricky

Suppose:

```java
class Student implements Serializable {
    private Course course;
}
```

but `Course` does not implement `Serializable`.

What may happen?

### Answer

Serialization can fail with `NotSerializableException` because the reachable object graph contains a non-serializable object.

---

## Practice 4

Why is serialization different from:

```java
writer.write(student.toString());
```

### Answer

`toString()` produces text. Serialization preserves an object graph in Java's serialization format and can reconstruct objects.

---

# AI-assisted practice

Ask AI to create save/load methods for an `ArrayList<Student>`.

Then inspect:

- whether both Student and nested objects are serializable;
- whether streams are closed;
- what exceptions are handled;
- whether unchecked casts are being used;
- what happens if the file is missing or corrupted.
