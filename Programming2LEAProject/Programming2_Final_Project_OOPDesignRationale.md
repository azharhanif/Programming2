# Programming 2 --- Reference

## College Course & Workload Management System
This document provides a complete reference design and implementation
for the final project, including bonus features. 

> **Integration note:** Assignment 3 specifies the employee hierarchy
> and public operations, but the exact constructor signatures and every
> getter in an individual group's code may differ. 
------------------------------------------------------------------------

# 1. Reference architecture and design decisions

The application is divided into four responsibilities:

1.  **Existing employee subsystem:** `Employee`, `FacultyMember`,
    concrete faculty classes, and `CollegeEmployeeSystem` remain from
    Assignment 3.
2.  **Domain model:** `Course`, `LectureCourse`, `LabCourse`,
    `TeachingEligible`, and `TeachingAssignment`.
3.  **Business logic and persistence:** `CollegeCourseSystem` enforces
    the rules; `CourseFileManager` handles text-file input/output.
4.  **Presentation:** `CollegeGUI` collects input, calls business
    methods, and displays results.

## UML reference

``` mermaid
classDiagram
direction TB

class Employee {
  <<abstract>>
  -String employeeId
  -String name
  -String department
  +calculatePay() double
  +equals(Object) boolean
  +hashCode() int
  +compareTo(Employee) int
}
class Manager
class FacultyMember {
  <<abstract>>
  +getMaximumTeachingHours() int
}
class DepartmentManager
class ProjectManager
class FullTimeFaculty
class PartTimeFaculty
class BonusEligible {
  <<interface>>
  +calculateBonus() double
}
class TeachingEligible {
  <<interface>>
  +getMaximumTeachingHours() int
}
Employee <|-- Manager
Employee <|-- FacultyMember
Manager <|-- DepartmentManager
Manager <|-- ProjectManager
FacultyMember <|-- FullTimeFaculty
FacultyMember <|-- PartTimeFaculty
FacultyMember ..|> TeachingEligible

class CollegeEmployeeSystem {
  -ArrayList~Employee~ employees
  -HashSet~Employee~ uniqueEmployees
  -HashMap~String, Employee~ employeesById
  +getEmployeeById(String) Employee
}
CollegeEmployeeSystem "1" o-- "0..*" Employee

class Course {
  <<abstract>>
  -String courseCode
  -String title
  -String department
  -int weeklyHours
  -int capacity
  +getCourseType() String
}
class LectureCourse
class LabCourse
Course <|-- LectureCourse
Course <|-- LabCourse

class TeachingAssignment {
  -FacultyMember faculty
  -Course course
  -int weeklyHours
  -String semester
}
TeachingAssignment "*" --> "1" FacultyMember
TeachingAssignment "*" --> "1" Course

class CollegeCourseSystem {
  -CollegeEmployeeSystem employeeSystem
  -ArrayList~Course~ courses
  -ArrayList~TeachingAssignment~ assignments
  +addCourse(Course) boolean
  +removeCourse(String) boolean
  +getCourseByCode(String) Course
  +assignFaculty(FacultyMember, Course, int, String) boolean
  +removeAssignment(FacultyMember, Course, String) boolean
  +getAssignedHours(FacultyMember, String) int
  +generateFacultyWorkloadReport() String
  +generateCourseAssignmentReport() String
  +generateDepartmentSummary() String
}
CollegeCourseSystem --> CollegeEmployeeSystem
CollegeCourseSystem "1" o-- "0..*" Course
CollegeCourseSystem "1" o-- "0..*" TeachingAssignment

class CourseFileManager {
  +saveCourses(ArrayList~Course~) void
  +loadCourses() ArrayList~Course~
  +saveAssignments(ArrayList~TeachingAssignment~) void
  +loadAssignments() ArrayList~TeachingAssignment~
}
CourseFileManager --> CollegeCourseSystem

class CollegeGUI
CollegeGUI --> CollegeCourseSystem
```

**OOP rationale**

-   `Course` is abstract because every stored course must be a
    `LectureCourse` or `LabCourse`.
-   `FacultyMember` implements `TeachingEligible`; the management class
    uses the interface contract rather than checking concrete faculty
    classes.
-   `TeachingAssignment` associates existing faculty and course objects.
    It does not own or duplicate them.
-   `CollegeCourseSystem` owns the course and assignment lists and is
    the single place where business rules are enforced.
-   `CourseFileManager` handles persistence; it does not decide whether
    a business operation is valid.
-   The GUI calls business methods and does not implement business rules
    inside listeners.

------------------------------------------------------------------------

# 2. Integration with Assignment 3

The existing employee classes are retained. The minimum expected
employee API is:

``` java
public abstract class Employee {
    public String getEmployeeId() { ... }
    public String getName() { ... }
    public String getDepartment() { ... }
}
```

The existing `CollegeEmployeeSystem` must support:

``` java
public Employee getEmployeeById(String employeeId);
```

If the actual Assignment 3 code uses `getId()` instead of
`getEmployeeId()`, change the call in the integration code and file
manager consistently.

## TeachingEligible.java

``` java
public interface TeachingEligible {
    int getMaximumTeachingHours();
}
```

## FacultyMember.java --- modification to the existing class

Keep all existing fields, constructors, methods, and inheritance. Add
the interface and the abstract method:

``` java
public abstract class FacultyMember extends Employee
        implements TeachingEligible {

    // Keep the existing Assignment 3 fields and constructors.

    @Override
    public abstract int getMaximumTeachingHours();
}
```

## FullTimeFaculty.java --- addition to the existing class

``` java
@Override
public int getMaximumTeachingHours() {
    return 18;
}
```

## PartTimeFaculty.java --- addition to the existing class

``` java
@Override
public int getMaximumTeachingHours() {
    return 9;
}
```

The 18-hour and 9-hour limits are project policy values, not values
derived from the uploaded lecture notes. The instructor may change them
consistently.

------------------------------------------------------------------------

# 3. Domain model

## Course.java

## LectureCourse.java

## LabCourse.java

## TeachingAssignment.java

## InvalidTeachingAssignmentException.java

A separate `InvalidCourseException` is optional.
`IllegalArgumentException` is sufficient for the reference
implementation.

------------------------------------------------------------------------

# 4. Business logic --- CollegeCourseSystem.java

This is the central class. The GUI, tests, file manager, and driver call
its methods. Validation belongs here, not in the GUI.

------------------------------------------------------------------------

# 5. Text-file persistence

The format is deliberately simple because the taught material uses
`Scanner`, `split()`, numeric parsing, `FileWriter`, and
try-with-resources. The project specification assumes text fields do not
contain commas.

## CourseFileManager.java
`

### Persistence sequence

At application startup:

1.  Construct or load the existing employee system first.
2.  Construct `CollegeCourseSystem` using that employee system.
3.  Load courses.
4.  Add the loaded courses through `addCourse()`.
5.  Load assignments after the courses exist.
6.  Resolve each employee ID through `CollegeEmployeeSystem`.
7.  Create assignments through `assignFaculty()` so normal validation is
    applied.

At save time:

1.  Save courses.
2.  Save assignments.

------------------------------------------------------------------------

# 6. Swing GUI reference implementation

The GUI is intentionally basic. It uses only the Swing components and
event-listener approach covered in Lecture 16. Business logic remains in
`CollegeCourseSystem`.

This is a functional starter GUI for the core operations. It assumes the
employee system has already been populated with faculty records.

## CollegeGUI.java


### Main.java --- startup example

For a full deployment, call the file-loading methods before opening the
GUI. 
------------------------------------------------------------------------

# 7. Test plan and sample driver

The tests assume that the employee system has already been
populated with at least one full-time and one part-time faculty member.
Use the actual constructors from Assignment 3.

## Required test cases

  -----------------------------------------------------------------------
  Test                                Expected result
  ----------------------------------- -----------------------------------
  Add a valid lecture course          `true`

  Add a valid lab course              `true`

  Add duplicate course code           `false`

  Search for existing course          Course object returned

  Search for missing course           `null`

  Assign faculty within workload      `true`

  Assign more hours than course       Exception
  weekly hours                        

  Assign more than faculty maximum    Exception

  Assign same faculty/course/semester Exception
  twice                               

  Assign a manager rather than        Rejected
  faculty                             

  Remove a course with an assignment  `false`

  Remove an unassigned course         `true`

  Save and reload data                Records restored

  Load a malformed numeric field      Bad record skipped and reported

  Recursive course display on empty   No error
  list                                

  Recursive course display on         Every course printed once
  populated list                      
  -----------------------------------------------------------------------

## Suggested JUnit test examples


``` java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {

    @Test
    public void lectureCourseHasCorrectType() {
       
    }

    @Test
    public void labCourseHasCorrectType() {
       
    }

    @Test
    public void invalidCourseHoursAreRejected() {
    
    }
}
```

A separate `CollegeCourseSystemTest` should test duplicate course
prevention, assignment validation, workload limits, removal rules, and
reports. Avoid testing Swing components as part of the business-logic
unit tests.

------------------------------------------------------------------------

# 8. Implementation notes and limitations

1.  **Employee constructor compatibility:** The exact constructors of
    the existing Assignment 3 employee classes must be retained. The
    driver intentionally leaves those constructor calls as integration
    points.
2.  **GUI scope:** The GUI is deliberately basic and should
    demonstrate the required Swing concepts. 
3.  **Recursion:** The project uses recursive course display as the new
    recursive requirement. 
4.  **JUnit:** The sample test uses JUnit 5 syntax consistent with the
    course's existing testing workflow. The business logic should remain
    testable without launching the GUI.
