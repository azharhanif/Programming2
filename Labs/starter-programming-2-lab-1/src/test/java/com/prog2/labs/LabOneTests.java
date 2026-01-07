package com.prog2.labs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LabOneTests {
	
	LabOne labOne = new LabOne();

	@Test
	void concatStringToEndTest1() {
		String firstString = "Hello";
		String secondString = "John";
		
		assertEquals("Hello John", labOne.concatStringToEnd(firstString, secondString));
	}
	
	@Test
	void concatStringToEndTest2() {
		String firstString = "Programming 2";
		String secondString = "is the best";
		
		assertEquals("Programming 2 is the best", labOne.concatStringToEnd(firstString, secondString));
	}
	
	@Test
	void doesEndWithSecondStringTest1() {
		String firstString = "Java LAB";
		String secondString = "ab";
		
		assertTrue(labOne.doesEndWithSecondString(firstString, secondString));
	}
	
	@Test
	void doesEndWithSecondStringTest2() {
		String firstString = "Winter 2022";
		String secondString = "35";
		
		assertFalse(labOne.doesEndWithSecondString(firstString, secondString));
	}
	
	@Test
	void doesEndWithSecondStringTest3() {
		String firstString = "James Smith";
		String secondString = "smith";
		
		assertTrue(labOne.doesEndWithSecondString(firstString, secondString));
	}
	
	@Test
	void randomIntegerGeneratorTest1() {
		int result = labOne.randomIntegerGenerator(5, 20);

		assertTrue(result >= 5 && result <= 20);
	}
	
	@Test
	void randomIntegerGeneratorTest2() {
		int result = labOne.randomIntegerGenerator(100, 150);

		assertTrue(result >= 100 && result <= 150);
	}
	
	@Test
	void randomIntegerGeneratorTest3() {
		int result = labOne.randomIntegerGenerator(1, 2);

		assertTrue(result >= 1 && result <= 2);
	}
	
	@Test
	void isPasswordValidTest1() {
		assertTrue(labOne.isPasswordValid("Vanier1234"));
	}
	
	@Test
	void isPasswordValidTest2() {
		assertFalse(labOne.isPasswordValid("Vanier1"));
	}
	
	@Test
	void isPasswordValidTest3() {
		assertFalse(labOne.isPasswordValid("vanier123"));
	}
	
	@Test
	void isPasswordValidTest4() {
		assertFalse(labOne.isPasswordValid("VanierCollege"));
	}

}
