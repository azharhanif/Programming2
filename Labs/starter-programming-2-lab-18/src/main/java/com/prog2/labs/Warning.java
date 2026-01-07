package com.prog2.labs;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Warning {

	public static void main(String[] args) {
		int creditHrs;	// number of semester hours earned
		double qualityPts;	// number of quality points earned
		double gpa;		// grade point (quality point) average
		
		String line, name, inputName = "students.dat";
		String outputName = "warning.dat";
		
		try {
			// Set up scanner to input file
			
			// Set up the output file stream
			
			// Print a header to the output file
			outFile.println();
			outFile.println("Students on Academic Warning");
			outFile.println();
			
			//Process the input file one token at a time
			
			while() {
				// Get the credit hours and quality points and
				// determine if the student is on warning. If so,
				// write the student data to the output file.
			}
			
			// Close output file
			
		} catch(FileNotFoundException e) {
			System.out.println("The file " + inputName + " was not found.");
		} catch(IOException e) {
			System.out.println(e);
		} catch(NumberFormatException e) {
			System.out.println(e);
		}

	}

}
