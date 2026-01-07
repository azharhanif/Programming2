package com.prog2.labs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * DO NOT MODIFY ANYTHING IN THIS FILE.
 */
class LabSixTests {
	
	LabSix labSix = new LabSix();

	@Test
	void arrayListProblemTest1() {
		String input = "5\n5 41 77 74 22 44\n1 12\n4 37 34 36 52\n0\n3 20 22 33\n5\n1 3\n"
				+ "3 4\n3 1\n4 3\n5 5";
		String result = "74\n52\n37\nERROR!\nERROR!";
		
		assertEquals(result, labSix.arrayListProblem(input));
	}
	
	@Test
	void arrayListProblemTest2() {
		String input = "3\n2 1 5\n1 5\n0\n2\n1 1\n3 4";
		String result = "1\nERROR!";
		
		assertEquals(result, labSix.arrayListProblem(input));
	}
	
	@Test
	void arrayListProblemTest3() {
		String input = "2\n3 55 72 99\n1 3\n1\n1 2";
		String result = "72";
		
		assertEquals(result, labSix.arrayListProblem(input));
	}
	
	@Test
	void arrayListProblemTest4() {
		String input = "1\n4 44 3 2 1\n2\n1 1\n1 3";
		String result = "44\n2";
		
		assertEquals(result, labSix.arrayListProblem(input));
	}
}
