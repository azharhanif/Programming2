package com.prog2.labs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class Warning {

    public static void main(String[] args) {
        int creditHrs;
        double qualityPts;
        double gpa;
        String name;
        String inputName = "students.dat";
        String outputName = "warning.dat";

        try (Scanner scan = new Scanner(new File(inputName));
             PrintWriter outFile = new PrintWriter(new FileWriter(outputName))) {

            outFile.println();
            outFile.println("Students on Academic Warning");
            outFile.println();

            while (scan.hasNext()) {
                name = scan.next();
                creditHrs = scan.nextInt();
                qualityPts = scan.nextDouble();

                gpa = qualityPts / creditHrs;

                if (isOnWarning(creditHrs, gpa)) {
                    outFile.printf(Locale.US, "%s %d %.2f%n", name, creditHrs, gpa);
                }
            }

            System.out.println("Academic warning report created: " + outputName);

        } catch (FileNotFoundException e) {
            System.out.println("The input file '" + inputName + "' was not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in the input file: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("An I/O error occurred while processing the files: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected input format error: " + e.getMessage());
        }
    }

    public static boolean isOnWarning(int creditHrs, double gpa) {
        if (creditHrs < 30) {
            return gpa < 1.5;
        } else if (creditHrs < 60) {
            return gpa < 1.75;
        } else {
            return gpa < 2.0;
        }
    }
}
