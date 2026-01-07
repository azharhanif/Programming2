# Programming 2 - Lab 14

This template repository is the starter project for Programming 2 Lab 14. Written in Java, and tested with Gradle/JUnit.

### Question(s)

1. As you may know, strings in Java are not mutatable (that is, you cannot actually modify a string once it is made). You will look at a method that takes a string and then returns a new string that has the same characters as the original but in reverse order. You should start by considering the base case. What strings are the easiest ones to solve this problem for? Write a recursive method that accepts a string as its argument and prints the string in reverse order.

2. A palindrome is any word, phrase, or sentence that reads the same forward and backward (Kayak – level, etc.,)

Write a boolean method that uses recursion to determine whether a String argument is a palindrome. Your main method should read the string and call a recursive (static) method palindrome that takes a string and returns true if the string is a palindrome, false otherwise. The method should return true if the argument reads the same forward and backward. The program then prints a message saying whether it is a palindrome. Recall that for a string s in Java, here is how we may write code using iterative:

```java
public static boolean isPalindrome(String s) {
 for (int i=0; i<=(s.length()-1)/2; i++) {
 if (s.charAt(i) != s.charAt(s.length() - i - 1))
 return false; }
return true; }
```

Now, what about solving this recursively?

3. Write a method that uses recursion to count the number of times a specific character occurs in an array of characters

4. Given a string s, write a recursion method that returns a string obtained from s by replacing each blank with the underscore character ('\_'). For example, the call underscore("This is a recursive") should return the string " This_is_a_recursive".

5. Write a recursive function to count the number of occurrences of a letter in the phrase.

6. Write a recursive method that accepts an array of words and returns the longest word found in the array. If the input array is empty then the method should return null.

7. Given an array of integers array and an integer n, write a recursive method that returns the number of occurrences of n in a.
