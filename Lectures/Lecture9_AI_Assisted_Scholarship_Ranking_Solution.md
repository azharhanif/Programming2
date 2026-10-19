# Lecture 9 — AI-Assisted Practice Solution: Scholarship Ranking

This solution follows the scholarship-ranking practice in Lecture 9. The required ordering is:

1. GPA descending (highest first).
2. If GPAs tie, volunteer hours descending (more hours first).
3. If both tie, name ascending (alphabetical).
4. If all three tie, student ID ascending.

The solution uses a `Comparator<Student>` because this is a scholarship-specific ordering, not necessarily the natural ordering for every use of `Student`.

---

## Part A — Predict before coding

### Given data

| ID | Name | GPA | Volunteer hours |
|---|---|---:|---:|
| S04 | Mina | 3.8 | 40 |
| S02 | Ali | 3.9 | 15 |
| S01 | Zoe | 3.8 | 60 |
| S03 | Ali | 3.9 | 25 |
| S05 | Mina | 3.8 | 40 |

### Expected order

```text
S03 | Ali  | GPA 3.9 | Volunteer hours 25
S02 | Ali  | GPA 3.9 | Volunteer hours 15
S01 | Zoe  | GPA 3.8 | Volunteer hours 60
S04 | Mina | GPA 3.8 | Volunteer hours 40
S05 | Mina | GPA 3.8 | Volunteer hours 40
```

### Explanation

- **GPA first:** Students with GPA `3.9` rank ahead of students with GPA `3.8`.
- **Volunteer hours second:** S03 and S02 both have GPA `3.9`; S03 has 25 volunteer hours, so S03 ranks before S02.
- **Volunteer hours within GPA 3.8:** S01 has 60 hours, so S01 ranks before S04 and S05, who each have 40 hours.
- **Name third:** S04 and S05 have the same GPA, volunteer hours, and name (`Mina`). The name comparison therefore ties.
- **ID fourth:** S04's ID (`S04`) comes before S05's ID (`S05`), so S04 ranks before S05.

---

## Part B — Complete Java implementation

Create two files: `Student.java` and `Main.java`.

### `Student.java`

```java
public class Student {
    private String id;
    private String name;
    private double gpa;
    private int volunteerHours;

    public Student(String id, String name, double gpa, int volunteerHours) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
        this.volunteerHours = volunteerHours;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    public int getVolunteerHours() {
        return volunteerHours;
    }

    @Override
    public String toString() {
        return id + " | " + name
                + " | GPA " + gpa
                + " | Volunteer hours " + volunteerHours;
    }
}
```

### `Main.java`

```java
import java.util.ArrayList;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();

        students.add(new Student("S04", "Mina", 3.8, 40));
        students.add(new Student("S02", "Ali", 3.9, 15));
        students.add(new Student("S01", "Zoe", 3.8, 60));
        students.add(new Student("S03", "Ali", 3.9, 25));
        students.add(new Student("S05", "Mina", 3.8, 40));

        Comparator<Student> scholarshipOrder =
                Comparator.comparingDouble(Student::getGpa).reversed()
                        .thenComparing(
                                Comparator.comparingInt(
                                        Student::getVolunteerHours
                                ).reversed()
                        )
                        .thenComparing(Student::getName)
                        .thenComparing(Student::getId);

        students.sort(scholarshipOrder);

        System.out.println("Scholarship ranking:");
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
```

### Expected output

```text
Scholarship ranking:
S03 | Ali | GPA 3.9 | Volunteer hours 25
S02 | Ali | GPA 3.9 | Volunteer hours 15
S01 | Zoe | GPA 3.8 | Volunteer hours 60
S04 | Mina | GPA 3.8 | Volunteer hours 40
S05 | Mina | GPA 3.8 | Volunteer hours 40
```

### How the comparator works

```java
Comparator<Student> scholarshipOrder =
        Comparator.comparingDouble(Student::getGpa).reversed()
                .thenComparing(
                        Comparator.comparingInt(
                                Student::getVolunteerHours
                        ).reversed()
                )
                .thenComparing(Student::getName)
                .thenComparing(Student::getId);
```

- `comparingDouble(Student::getGpa).reversed()` sorts GPA from highest to lowest.
- `thenComparing(...)` applies the next rule only when the earlier comparison ties.
- `comparingInt(Student::getVolunteerHours).reversed()` sorts volunteer hours from highest to lowest within a GPA tie.
- `thenComparing(Student::getName)` sorts names alphabetically if GPA and volunteer hours tie.
- `thenComparing(Student::getId)` sorts IDs ascending if the first three keys tie.

---

## Part C — Critique the AI-generated implementation

A correct implementation should pass each of these checks.

| Check | Correct answer / explanation |
|---|---|
| GPA descending? | Yes. `.comparingDouble(Student::getGpa).reversed()` puts the highest GPA first. |
| Volunteer hours descending only when GPA ties? | Yes. The volunteer-hours comparator is chained after GPA, so it is used only when the GPA comparison returns zero. Its `.reversed()` makes more hours rank first. |
| Name ascending only when GPA and hours tie? | Yes. `.thenComparing(Student::getName)` is considered only when both earlier keys tie. String natural ordering is lexicographical and case-sensitive. |
| ID ascending only when GPA, hours, and name tie? | Yes. `.thenComparing(Student::getId)` is the final tie-breaker. |
| Does the code avoid subtraction? | Yes. It uses the built-in comparator factories rather than expressions such as `first.getGpa() - second.getGpa()` or subtracting IDs. |
| Do the getters exist? | Yes. `Student` defines `getGpa()`, `getVolunteerHours()`, `getName()`, and `getId()`. |
| Does it unnecessarily change `Student`? | No. It adds the required fields and getters, but does not implement `Comparable` or alter a natural ordering. The scholarship rule remains outside the class in `Main`. |

### Example of a common AI error

This comparator would be wrong for the stated requirement:

```java
Comparator<Student> incorrectOrder =
        Comparator.comparingDouble(Student::getGpa).reversed()
                .thenComparingInt(Student::getVolunteerHours)
                .thenComparing(Student::getName)
                .thenComparing(Student::getId);
```

The volunteer-hours comparison is ascending, so a student with fewer volunteer hours would rank ahead of a student with more hours when GPAs tie. The fix is to reverse the volunteer-hours comparator as in the correct solution.

Another common error is putting the name comparison before volunteer hours. That would violate the stated priority because volunteer hours must be considered before name.

---

## Teacher notes

- This exercise is about comparison priority and `Comparator` chaining; it does not require `Student` to implement `Comparable<Student>`.
- The ID tie-breaker is exercised by S04 and S05, which have equal GPA, volunteer hours, and name.
- The output order is deterministic because the comparator includes all four specified keys.
- Predict the order before running the code, then compare your prediction with the program output.
