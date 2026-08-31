# Programming 2 — Lecture 16: Java GUI

> This Markdown version reorganizes the GUI material into the same explanation → example → practice format used in the updated semester lectures. It is intended to accompany the existing GUI material in the repository.

## Learning objectives

You should be able to:

- explain the basic structure of a Java GUI;
- distinguish a window, component, and event;
- create a simple Swing interface;
- place components in a container;
- respond to button events;
- separate GUI code from application logic;
- recognize common GUI design mistakes.

---

## 1. What is a GUI?

GUI means **Graphical User Interface**.

Instead of interacting only through:

```text
Scanner → console
```

the user interacts with:

- windows;
- buttons;
- labels;
- text fields;
- menus;
- lists.

A GUI is event-driven.

The program waits for events such as:

```text
button click
keyboard input
window closing
mouse action
```

and responds to them.

---

## 2. A simple Swing window

```java
import javax.swing.JFrame;

public class HelloGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Programming 2");

        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
```

The `JFrame` is the main window.

---

## 3. Components

Common Swing components include:

```text
JLabel
JButton
JTextField
JTextArea
JCheckBox
JComboBox
JList
```

Example:

```java
JLabel label = new JLabel("Name:");
JTextField field = new JTextField(15);
JButton button = new JButton("Submit");
```

A component must be placed into a container before it can appear.

---

## 4. A simple form

```java
import javax.swing.*;
import java.awt.*;

public class StudentForm {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Student");

        JPanel panel = new JPanel();

        JLabel label = new JLabel("Name:");
        JTextField field = new JTextField(15);
        JButton button = new JButton("Submit");

        panel.add(label);
        panel.add(field);
        panel.add(button);

        frame.add(panel);

        frame.setSize(400, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
```

---

## 5. Events

A button click is an event.

Use an `ActionListener`:

```java
button.addActionListener(e -> {
    System.out.println("Button clicked!");
});
```

The lambda expression is the event handler.

---

## 6. Updating a label

```java
JLabel result = new JLabel("Waiting...");

button.addActionListener(e -> {
    String name = field.getText();
    result.setText("Hello " + name);
});
```

Now the GUI responds to the user's input.

---

## 7. Event-driven thinking

Console program:

```text
ask
read
calculate
print
```

GUI program:

```text
create interface
      ↓
wait
      ↓
event happens
      ↓
handler executes
      ↓
update GUI/state
      ↓
wait again
```

This is a major conceptual difference.

---

## 8. Keep logic separate

Avoid putting a large business algorithm directly inside:

```java
button.addActionListener(...)
```

Instead:

```java
public static double calculateAverage(...) {
    ...
}
```

and let the GUI handler call it.

This makes the logic easier to:

- test;
- reuse;
- debug;
- understand.

---

## 9. Common GUI mistakes

### Mistake 1: Forgetting visibility

```java
frame.setVisible(true);
```

### Mistake 2: Forgetting close behavior

```java
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
```

### Mistake 3: Putting everything into one enormous event handler

Separate UI behavior from business logic.

### Mistake 4: Assuming the GUI runs like a console program

A GUI normally waits for events instead of executing one long sequence from top to bottom.

---

# Practice

## Practice 1

What is the purpose of:

```java
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
```

### Answer

It tells the application to terminate when the user closes the window.

---

## Practice 2

What does this do?

```java
button.addActionListener(e -> {
    System.out.println("Clicked");
});
```

### Answer

It registers an event handler that executes when the button is clicked.

---

## Practice 3

Create a button that changes a label from `"Off"` to `"On"`.

### Answer

```java
JLabel status = new JLabel("Off");
JButton button = new JButton("Turn On");

button.addActionListener(e -> {
    status.setText("On");
});
```

---

## Practice 4 — tricky

Why is this design better?

```java
button.addActionListener(e -> {
    double average = calculateAverage(marks);
    result.setText(String.valueOf(average));
});
```

than putting the entire average algorithm inside the listener?

### Answer

The calculation is separated from the user interface. It can be tested independently with JUnit and reused by other parts of the program.

---

# AI-assisted practice

Ask AI to build a small Java Swing student-grade calculator.

Then:

1. explain every component;
2. identify every event handler;
3. move the calculation into a separate method;
4. write JUnit tests for that calculation method;
5. intentionally enter invalid input;
6. add appropriate exception/input handling.

The goal is not merely to make a window appear. The goal is to connect GUI design with clean, testable Java programming.
