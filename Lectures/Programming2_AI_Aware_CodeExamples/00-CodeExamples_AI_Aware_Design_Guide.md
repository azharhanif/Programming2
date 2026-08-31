# Programming 2 — AI-Aware CodeExamples Design Guide

## Purpose

This document redesigns the existing `Lectures/CodeExamplesInLectures` collection for the new semester.

The existing repository contains focused practice collections for:

- Single-dimension arrays
- Multi-dimensional arrays
- Abstract classes and interfaces
- Comparable / Comparator / HashCode
- Recursion
- Exception handling
- TextIO
- GUI
- review examples
- serialization/deserialization examples

The redesign keeps those examples as the starting point, but changes the student workflow from:

```text
Read code → Run code → Copy/modify
```

to:

```text
Observe → Predict → Run → Explain → AI-assisted first implementation
→ Critique → Modify → Test → Explain again
```

The repository currently separates several activities into starter/solution structures, particularly in the recursion and exception-handling bundles. This redesign keeps that separation and adds an explicit AI reflection stage. citeturn3view2turn3view3

## The five-stage AI workflow

### Stage 1 — Predict

Before running the existing example, students write down:

- expected output;
- object/reference state;
- likely exception, if any;
- the line they think is most important.

### Stage 2 — Run

Students execute the original example without changing it.

### Stage 3 — Explain

Students explain the example in their own words.

A student should be able to answer:

> What would break if I removed this line?

### Stage 4 — AI-assisted implementation

Students may ask an LLM, Codex, or another approved AI model for a first implementation of a **new but related requirement**.

The prompt must be saved.

Example:

> "Using the same concept demonstrated in this example, write a method that ..."

The AI output is a starting point, not the final answer.

### Stage 5 — Modify and validate

Students must:

1. identify at least one design choice in the AI output;
2. change the generated implementation;
3. add at least two meaningful tests;
4. explain why the modification is correct;
5. demonstrate the final result.

## What counts as learning evidence?

The instructor should look for:

- correct prediction;
- correct explanation;
- meaningful modification;
- test design;
- ability to explain the modified code without asking AI.

A student who submits perfect AI-generated code but cannot explain it has not completed the learning objective.

## Recommended student submission note

```text
AI was used for the first implementation.

AI tool:
Prompt:
What the AI generated:
What I changed:
Why I changed it:
Tests I added:
One thing the AI got wrong or could improve:
```

## Instructor principle

AI assistance is intentionally permitted in the first implementation. The assessment target is **understanding and ownership of the resulting code**, not typing speed.
