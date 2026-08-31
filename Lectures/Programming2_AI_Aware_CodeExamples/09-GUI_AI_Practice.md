# GUI Examples — AI-Aware Redesign

**Repository areas:** `GuiLectureExamples` and `GUI_lab19-solution`

The repository contains a GUI examples project and a Lab 19 solution. The Lab 19 solution combines three GUI tasks into one Swing application with tabs: Retail Price Calculator, Monthly Sales Tax Calculator, and Tip Calculator. citeturn2view5turn2view6

## Activity A — identify the event model

For the existing GUI, identify:

- JFrame/window;
- components;
- event source;
- listener/handler;
- data read from the user;
- result displayed.

## Activity B — run before changing

Students must interact with the existing GUI and describe what happens after each user action.

## Activity C — AI-assisted first implementation

Ask AI to add a fourth calculator tab related to the existing application.

The prompt should specify:

- input fields;
- calculation;
- output;
- validation.

## Activity D — human modification

Students must change at least two things without AI:

1. improve input validation;
2. change the layout or result presentation.

## Activity E — separate business logic

Move the calculation into a separate method/class where practical.

The GUI event handler should collect input, call the calculation logic, and display the result.

This makes the calculation independently testable.

## Tricky challenge

A student enters:

```text
abc
```

where a number is required.

Ask:

> Should the program crash, silently treat it as zero, or display a useful validation message?

Students must choose and justify a behavior.

## AI ownership check

Students must be able to explain what happens from:

```text
button click
    ↓
event handler
    ↓
input parsing
    ↓
calculation
    ↓
GUI update
```
