# Serialization and Deserialization Examples — AI-Aware Redesign

**Repository area:** `Lectures/CodeExamplesInLectures/TextIOLecturePractice` and the repository's serialization examples.

The TextIO practice project contains `.ser` data such as `courses.ser`, `students.ser`, `teachers.ser`, and related files. citeturn2view4

## Activity A — concept prediction

Explain:

```text
object graph
    ↓
serialization
    ↓
byte stream
    ↓
file
```

and the reverse for deserialization.

## Activity B — AI-assisted implementation

Ask AI to write methods:

```java
saveStudents(...)
loadStudents(...)
```

using Java object streams.

Students must identify every object reachable from the collection that must be serializable.

## Activity C — human modification

Add a new field to the serialized class.

Students investigate:

- what `serialVersionUID` is doing;
- whether old serialized data remains compatible;
- what happens when the file is missing or corrupted.

## Activity D — compare TextIO and serialization

Students create a table:

| TextIO | Serialization |
|---|---|
| human-readable possible | binary/object representation |
| explicit parsing | object graph reconstruction |
| useful for interoperability | useful for Java object persistence |

## Tricky challenge

If:

```java
class Student implements Serializable {
    private Course course;
}
```

but `Course` does not implement `Serializable`, what can happen?

**Answer:** Serializing the Student graph can fail with `NotSerializableException`.

## AI ownership check

Students must explain why adding `implements Serializable` to only the top-level collection is not enough.
