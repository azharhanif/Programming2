package com.prog2.labs;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * DO NOT MODIFY ANYTHING IN THIS FILE.
 */
class LabFiveTests {
	
	LabFive labFive = new LabFive();

	@Test
	void isMagicSquareTest1() {
		int[][] theArray = { { 4, 9, 2 }, { 3, 5, 7 }, { 8, 1, 6 } };
		
		assertTrue(labFive.isMagicSquare(theArray));
	}
	
	@Test
	void isMagicSquareTest2() {
		int[][] theArray = { { 4, 9, 2 }, { 8, 1, 6 }, { 3, 5, 7 } };
		
		assertFalse(labFive.isMagicSquare(theArray));
	}
	
	@Test
	void magicSquareGeneratorTest1() {
		int[][] result = { { 4, 9, 2 }, { 3, 5, 7 }, { 8, 1, 6 } };
		
		assertArrayEquals(result, labFive.magicSquareGenerator(3));
	}
	
	@Test
	void magicSquareGeneratorTest2() {
		int[][] result = { { 11, 18, 25, 2, 9 }, { 10, 12, 19, 21, 3 }, { 4, 6, 13, 20, 22 },
				{ 23, 5, 7, 14, 16 }, { 17, 24, 1, 8, 15 }};
		
		assertArrayEquals(result, labFive.magicSquareGenerator(5));
	}
}
