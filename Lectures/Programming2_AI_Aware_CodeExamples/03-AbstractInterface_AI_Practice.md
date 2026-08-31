# Abstract Class and Interface Examples — AI-Aware Redesign

**Repository area:** `Lectures/CodeExamplesInLectures/Abstract_Interface_Lecture_PracticeLab`

The repository currently contains `Lab10_Q1`, `Lab10_Q2`, and a pointer to the corresponding Lab 10 starter project. citeturn1view0turn3view0

## Activity A — predict the design

Before opening the solution, answer:

> Which behavior belongs in the abstract superclass, and which behavior is better represented as a capability/interface?

Use the lecture's familiar OOP style.

## Activity B — implement from the existing design

Run the starter code first.

Identify:

- superclass;
- abstract method;
- concrete subclass;
- interface;
- implementing class;
- polymorphic reference.

## Activity C — AI-assisted first implementation

Ask AI:

> Add a new subclass to the existing design. It must implement the abstract behavior and one interface capability.

Do not ask AI to redesign the entire project.

## Activity D — human modification

Add a second interface capability yourself.

For example:

```java
interface Insurable {
    double insuranceCost();
}
```

Decide which class(es) should implement it.

## Activity E — design defense

Students must answer verbally or in writing:

1. Why is this an abstract class?
2. Why is this an interface?
3. Could the interface be an abstract class instead?
4. What would happen if the abstract method were removed?
5. What does polymorphism buy us here?

## Tricky challenge

Suppose:

```text
Car
Boat
Airplane
```

all have:

```text
move()
```

but only some can:

```text
fly()
```

Design the hierarchy without forcing every vehicle to implement `fly()`.

**Expected reasoning:** common vehicle identity/state can belong in a superclass; flying can be a separate interface capability.
