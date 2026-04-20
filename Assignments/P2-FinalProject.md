# Programming 2 - Project 

>>> Three-Phase Implementation Model

Course: Programming 2

Total: 100 marks
**Grading: in Phase 1 (40 marks) , Phase2 (30 marks), and Phase 3 (30 marks)**

- **in each phase, 50% on the submitted project code.**

- **in each phase 50% on the coding challange.**

**What is coding challange?**

There will be in class extension challenges built directly on the Phase 1, Phase 2, and Phase 3 of the project. The challange is designed to fit a 30-minute coding task, and will test high-level understanding, not memorization.

Each challenge:

• plugs into the existing submitted solution

• targets a different OOP skill

• is specific enough to grade consistently


Each group (2 students in a group) completes the same project in 3 phases: 
#### Phase 1 — Core OOP Design
Build the foundational HR management system using core Programming 2 concepts.
#### Phase 2 — Student-ID-Based Extension + AI Module 1
Extend your own Phase 1 design using rules derived from either one of the group member's student ID. Also add the first AI-inspired module.
#### Phase 3 — Advanced Extension + AI Module 2
Extend the design further, based on the completion of the first two phases. This phase adds more advanced behavior, a second AI-inspired module, and requires the UML diagram to be updated to reflect all modifications.

### Project Description
Small City Police Department Human Resources Management System

You will implement a model of a human resources management system for a small city police department.

The project is divided into three phases. Each phase extends the previous one. 

Your final submission must include 
 ```
  - the final codebase,
  - test suite, 
  - UI,
  - file handling,
  - AI-style modules, and
  - an updated UML class diagram that reflects the final Phase 3 design.
```    
The project is about human resources management, **not criminal case tracking**. The focus is on employee records, division assignment, supervisors, compensation, data persistence, sorting, validation, and intelligent recommendation features.

**Instructions**

###### PHASE 1 — Core OOP Design
###### Goal

Build the core police HR system using the main OOP concepts from the course.

Required Features

###### 1.	Create an abstract class Person with common fields such as: 
```
o	employeeId 
o	firstName 
o	lastName 
o	age 
o	hireDate 
o	address 
o	phoneNumber
```
###### 2.	Add at least one abstract method such as: 
```
o	calculateMonthlyCompensation() 
o	getRoleCategory()
```
###### 3.	Build an inheritance hierarchy with at least: 
```
o	PoliceEmployee 
o	SwornOfficer 
o	CivilianStaff
```
###### 4.	Add deeper specialized subclasses such as: 
```
o	PatrolOfficer 
o	Detective 
o	Dispatcher 
o	RecordsClerk 
o	Technician
```
###### 5.	Create at least one interface, such as: 
```
o	PayRoll 
o	ShiftAssignable 
o	Trainable
```
###### 6.	Implement: 
```
o	encapsulation 
o	constructors 
o	getters/setters 
o	overridden toString() 
o	overridden equals() 
o	overridden hashCode()
```
###### 7.	Add one Comparable implementation and at least two Comparator classes. 
###### 8.	Use collections such as: 
```
o	ArrayList 
o	HashSet 
o	HashMap
```
###### 9.	Create a Division class. Example divisions: 
```
o	Patrol 
o	Investigations 
o	Community Safety 
o	Records 
o	Administration
```
###### 10.	Support: 
```
•	adding employees 
•	assigning employees to divisions 
•	assigning division supervisors 
•	displaying employees 
•	sorting employees 
•	searching employees
```
###### 11.	Implement custom exceptions such as: 
```
•	DivisionNotFoundException 
•	DuplicateEmployeeException 
•	InvalidSupervisorAssignmentException
```
###### 12.	Add file processing and serialization for the basic registry. 
###### 13.	Create a basic GUI that can: 
```
•	add employee 
•	search employee 
•	display all employees 
•	save and load data 
```
###### Deliverables for Phase 1
```
•	initial UML diagram 
•	pseudocode for major methods 
•	working code 
•	test suite 
•	driver class 
```
###### PHASE 2 — Student-ID-Based Extension + AI Module 1
###### Goal
Extend the project in a way that changes from student to student, and add the first AI-inspired module.
###### Student-ID-Based Extension Rules
Extend the police HR system using either one your own student ID.

Use the **last digit of the student ID** to determine the required extension category:

  •	0, 1, 2 → add a Rank Management Extension 
  
  •	3, 4, 5 → add a Shift Management Extension 
  
  •	6, 7, 8, 9 → add a Training and Certification Extension 
###### Category A — Rank Management Extension
Students in this category must add:
```
•	Rank or rank-level handling 
•	promotion eligibility logic 
•	rank-based compensation adjustment 
•	rank-based sorting comparator
```
Suggested new classes:
```
•	RankedOfficer 
•	PromotionEvaluator
```
###### Category B — Shift Management Extension
Students in this category must add:
```
•	shift preferences 
•	day/night/rotation assignment 
•	shift conflict validation 
•	shift workload effect on compensation or risk
```
Suggested new classes:
```
•	ShiftRecord 
•	ShiftManager 
```
###### Category C — Training and Certification Extension
Students in this category must add:
```
•	training records 
•	certification expiry 
•	specialized skill tracking 
•	training-based division assignment eligibility
```
Suggested new classes:
```
•	TrainingRecord 
•	Certification 
•	TrainingManager 
```
###### AI Module 1
Students must implement the first AI-inspired module in a separate class such as:
```
•	PoliceAIAdvisor 
•	PoliceRecommendationEngine
```
###### Required AI Method 1
public String recommendDivision(PoliceEmployee employee, String studentId)
###### Purpose
Recommend the best division for an employee.
###### Rule
The recommendation logic must depend on:
```
•	employee data 
•	the student ID 
```
Example student-ID behavior
```
•	if last digit is 0–3, emphasize years of service 
•	if last digit is 4–6, emphasize training or qualification 
•	if last digit is 7–9, emphasize workload tolerance or role type
```
###### Output
Return:
```
•	recommended division 
•	short reason
```
**Example:**
Recommended Division: Investigations because years of service and training score are high.
###### Additional Phase 2 Requirements
```
•	Phase 1 code must remain functional 
•	add at least one new class because of the student-ID extension 
•	add at least one new relationship to the UML 
•	update tests to include new Phase 2 behavior 
```
###### Deliverables for Phase 2
```
•	updated code 
•	updated test suite 
•	short design note explaining student-ID-based extension 
•	AI Module 1 demonstration 
```
###### PHASE 3 — Advanced Extension + AI Module 2
###### Goal
Extend the project further based on what was built in Phase 1 and Phase 2. 

This phase represents a more advanced system version.

###### Must add:
```
1.	one advanced extension that depends on your already completed design 
2.	a second AI-inspired module 
3.	an updated final UML that reflects the total system
```
###### Required Advanced Extension
Must choose one extension that naturally builds on their Phase 2 branch.

- If Phase 2 was Rank Management

Add one of:
```
•	promotion board simulation 
•	chain-of-command validation 
•	leadership score system 
•	supervisor succession planning
```
Suggested classes:
```
•	PromotionBoard 
•	LeadershipProfile
```
- If Phase 2 was Shift Management

Add one of:
```
•	emergency reassignment engine 
•	overtime balancing module 
•	staffing shortage detector 
•	patrol coverage analyzer
```
Suggested classes:
```
•	EmergencyShiftPlanner 
•	CoverageAnalyzer
```

- If Phase 2 was Training and Certification

Add one of:
```
•	certification renewal warning system 
•	specialized unit eligibility analyzer 
•	training gap report generator 
•	skill-based deployment engine
```
Suggested classes:
```
•	EligibilityAnalyzer 
•	CertificationAlertManager
```

###### AI Module 2
Must add a second AI-inspired module, different from Phase 2.

###### Required AI Method 2
Choose one of the following:
```
`public String predictRetentionRisk(PoliceEmployee employee, String studentId)`
or
`public List<PoliceEmployee> getTopSupervisorCandidates(List<PoliceEmployee> employees, String studentId)`
or
`public String generateStaffingAlert(Division division, String studentId)`
```
###### Purpose
This second AI module must perform a more advanced analysis than simple recommendation.
Examples:
•	identify retention risk 
•	rank promotion or supervisor candidates 
•	detect likely staffing problems 
•	identify employees needing intervention 

###### Rule
The logic must depend on:
```
•	employee or division data 
•	student ID 
•	features added in earlier phases
```
That last point is important: Phase 3 AI must actually benefit from the work done in Phase 2.

Example:
```
•	a shift-management student can use overtime history in AI Module 2 
•	a training-management student can use certification expiry in AI Module 2 
•	a rank-management student can use promotion score in AI Module 2 
```
###### Final UML Requirement
The UML submitted at the end must be the updated final UML, not the original Phase 1 diagram.

It must show:
```
•	all new classes added in Phase 2 and Phase 3 
•	all new interfaces 
•	all associations, inheritance, and dependencies 
•	any strategy classes used for AI or extension logic 
•	any manager/helper classes introduced later
```
Students should also submit:
```
•	the original Phase 1 UML 
•	the final Phase 3 UML
```
This allows the teacher to see how the design evolved.
###### Suggested Mark Breakdown for Police Project
```
•	Phase 1 core OOP design and implementation – 35 
•	Phase 2 student-ID extension – 15 
•	AI Module 1 – 10 
•	Phase 3 advanced extension – 15 
•	AI Module 2 – 10 
•	Final updated UML and pseudocode updates – 10 
•	Tests, driver, and project quality – 5 

```
###### A Few Notes
```
•	This is a police HR project, not a crime investigation system. 
•	Keep the extension aligned with HR management. 
•	AI functionality must be simulated using OOP and logic, not external AI APIs. 
•	Students should build forward, not restart from scratch each phase.
• You have to write all the methods inside your UML diagrams excluding getters/setters, toString(), etc.
```
###### Final submission must include
```
1.	source code 
2.	screenshots of output and GUI
3.	test suite 
4.	driver class 
5.	initial Phase 1 UML 
6.	final updated Phase 3 UML 
7.	pseudocode updated where needed
```
###### Minimum AI expectations
```
•	no external AI API required 
•	no machine learning library required 
•	rule-based, weighted, or heuristic AI logic is enough 
•	AI modules must be their own logical design component, not random helper methods hidden inside Main 
```
###### What should change in the UML by Phase 3?
Typical expected changes:
```
•	new manager classes 
•	new extension classes 
•	strategy interfaces 
•	extra associations 
•	aggregation/composition updates 
•	dependency lines to AI modules 
•	optional separation of service classes from entity classes
```
###### Refinement
```
•	Checkpoint 1: verify Phase 1 UML before coding continues 
•	Checkpoint 2: verify Phase 2 student-ID branch and AI Module 1 
•	Checkpoint 3: verify Phase 3 extension and updated UML
````
This makes the project development like software evolution 



