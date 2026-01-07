package com.prog2.labs;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * DO NOT MODIFY ANYTHING IN THIS FILE.
 */
class LabThreeTests {
	
	LabThree labThree = new LabThree();

	@Test
	void rotateTheArrayTest1() {
		int[] result = {24, 27, 18, 21, 12, 15, 6, 9, 0, 3, 30};
		
		assertArrayEquals(result, labThree.rotateTheArray(11, 0));
	}
	
	@Test
	void rotateTheArrayTest2() {
		int[] result = {7, 10, 1, 4, 13};
		
		assertArrayEquals(result, labThree.rotateTheArray(5, 1));
	}
	
	@Test
	void jumpGameTest1() {
		int[] input = {2, 3, 1, 1, 4};
		
		assertEquals(2, labThree.jumpGame(input));
	}
	
	@Test
	void jumpGameTest2() {
		int[] input = {2, 3, 0, 1, 4};
		
		assertEquals(2, labThree.jumpGame(input));
	}
}
