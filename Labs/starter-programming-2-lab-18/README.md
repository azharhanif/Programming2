# Programming 2 - Lab 18

This template repository is the starter project for Programming 2 Lab 18. Written in Java, and tested with Gradle/JUnit.

### Question(s)

**Reading from and Writing to Text Files**

Write a program that will read in a file of student academic credit data and create a list of students on academic warnings. The list of students on the warning will be written in a file. Each line of the input file will contain the student name (a single String with no spaces), the number of semester hours earned (an integer), and the total quality points earned (a double). The following shows part of a typical data file:

Smith 27 83.7  
Jones 21 28.35  
Walker 96 182.4  
Doe 60 150

The program should compute the GPA (grade point or quality point average) for each student (the total quality points divided by the number of semester hours) and then write the student information to the output file if that student should be put on academic warning. A student will be on warning if he/she has a GPA less than 1.5 for students with fewer than 30 semester hours credit, 1.75 for students with fewer than 60 semester hours credit, and 2.0 for all other students. The file Warning.java contains a skeleton of the program. Do the following:

1. Set up a Scanner object scan from the input file and a PrintWriter outFile to the output file inside the try clause (see the comments in the program). Note that you’ll have to create the PrintWriter from a FileWriter, but you can still do it in a single statement.

2. Inside the while loop add code to read and parse the input—get the name, the number of credit hours, and the number of quality points. Compute the GPA, determine if the student is on academic warning, and if so write the name, credit hours, and GPA (separated by spaces) to the output file.

3. After the loop close the PrintWriter.

4. Think about the exceptions that could be thrown by this program:  
   • A FileNotFoundException if the input file does not exist  
   • A NumberFormatException if it can’t parse an int or double when it tries to – this indicates an error in the input file format  
   • An IOException if something else goes wrong with the input or output stream  
   Add a catch for each of these situations, and in each case give as specific a message as you can. The program will terminate if any of these exceptions are thrown, but at least you can supply the user with useful information.

5. Test the program. Test data is in the file students.dat. Be sure to test each of the exceptions as well.
