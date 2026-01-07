package com.prog2.labs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * DO NOT MODIFY ANYTHING IN THIS FILE.
 */
class LabFourTests {
	
	LabFour labFour = new LabFour();

	@Test
	void containerWithMostWaterTest1() {
		int[] input = {1, 8, 6, 2, 5, 4, 8, 3, 7};
		
		assertEquals(49, labFour.containerWithMostWater(input));
	}
	
	@Test
	void containerWithMostWaterTest2() {
		int[] input = {1, 1};
		
		assertEquals(1, labFour.containerWithMostWater(input));
	}
	
	@Test
	void threeSumClosestTest1() {
		int[] input = {-1, 2, 1, -4};
		
		assertEquals(2, labFour.threeSumClosest(input, 1));
	}
	
	@Test
	void threeSumClosestTest2() {
		int[] input = {0, 0, 0};
		
		assertEquals(0, labFour.threeSumClosest(input, 1));
	}
}
