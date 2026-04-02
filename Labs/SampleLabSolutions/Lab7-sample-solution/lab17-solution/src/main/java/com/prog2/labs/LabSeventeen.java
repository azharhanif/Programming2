package com.prog2.labs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class LabSeventeen {

    public static void main(String[] args) {
        String fileName = "testdata.txt";

        try {
            StudentResult result = readStudentResult(fileName);
            System.out.printf(Locale.US,
                    "%s's average grade is %.2f%n",
                    result.studentName(),
                    result.average());
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found: " + fileName);
        } catch (IllegalStateException e) {
            System.out.println("Input file format error: " + e.getMessage());
        }
    }

    public static StudentResult readStudentResult(String fileName) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("Missing student name.");
            }

            String name = scanner.nextLine().trim();
            int score1 = readNextInt(scanner, "first exam score");
            int score2 = readNextInt(scanner, "second exam score");
            int score3 = readNextInt(scanner, "third exam score");

            double average = (score1 + score2 + score3) / 3.0;
            return new StudentResult(name, average);
        }
    }

    private static int readNextInt(Scanner scanner, String label) {
        if (!scanner.hasNextInt()) {
            throw new IllegalStateException("Missing or invalid " + label + ".");
        }
        return scanner.nextInt();
    }

    public record StudentResult(String studentName, double average) {
    }
}
