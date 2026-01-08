# Programming 2 - Lab 17

This template repository is the starter project for Programming 2 Lab 17. Written in Java, Use JUnit for testing.

### Question(s)

1. Suppose that a file named "testdata.txt" contains the following information: The first line of the file is the name of a student. Each of the next three lines contains an integer. The integers are the student's scores on three exams.

Write a program that will read the information in the file and display (on the standard output) a message that contains the name of the student and the student's average grade on the three exams. The average is obtained by adding up the individual exam grades and then dividing by the number of exams.

2. Suppose that a file contains information about sales figures for a company in various cities. Each line of the file contains a city name, followed by a colon (:) followed by the data for that city. The data is a number of type double. However, for some cities, no data was available. In these lines, the data is replaced by a comment explaining why the data is missing. For example, several lines from the file might look like this:

```
San Francisco: 19887.32
Chicago: no report received
New York: 298734.12
```

Write a program that will compute and print the total sales from all the cities together. The program should also report the number of cities for which data was not available. The name of the file is "sales.dat".

Suggestion: For each line, read and ignore characters up to the colon. Then read the rest of the line into a variable of type String. Try to convert the string into a number, and use try..catch to test whether the conversion succeeds.

