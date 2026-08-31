# TextIO Examples — AI-Aware Redesign

**Repository area:** `Lectures/CodeExamplesInLectures/TextIOLecturePractice`

The repository's TextIO practice project includes input/output data files, CSV files, serialized files, and Java source under `src`. citeturn2view4

## Activity A — inspect the data

Before running code, inspect the provided text/CSV files.

Ask:

- What is one record?
- What separates fields?
- Are there headers?
- Can a line be malformed?
- Is the output meant for humans or another program?

## Activity B — AI-assisted input implementation

Ask AI:

> Write a method that reads the supplied CSV data and converts each valid record into an object.

Students must test malformed records rather than assuming every line is valid.

## Activity C — human modification

Modify the AI solution to:

- skip blank lines;
- report malformed records;
- continue processing after one bad record.

Document the chosen behavior.

## Activity D — output

Ask AI to export the objects.

Then change the requirement yourself:

> Add a header and ensure every record is written in deterministic order.

## Tricky challenge

Why is:

```java
writer.write(object.toString());
```

not automatically a reliable persistence format?

**Answer:** `toString()` is a representation, not necessarily a stable machine-readable serialization format.

## AI ownership check

Students must explain:

```text
where the file is opened
where data is parsed
where exceptions can occur
where the resource is closed
```
