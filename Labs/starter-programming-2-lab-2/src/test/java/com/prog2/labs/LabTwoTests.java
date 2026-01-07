package com.prog2.labs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * DO NOT MODIFY ANYTHING IN THIS FILE.
 */
class LabTwoTests {
	
	LabTwo labTwo = new LabTwo();
	Account account = new Account(10, "James", 25.5);

	@Test
	void calcAmountOfShippingTest1() {
		assertEquals(10.99, labTwo.calcAmountOfShipping(60));
	}
	
	@Test
	void calcAmountOfShippingTest2() {
		assertEquals(15.99, labTwo.calcAmountOfShipping(70));
	}
	
	@Test
	void findPrimeNumbersUptoInputTest1() {
		assertEquals("2 3 5 7 11 13 17 19 23", labTwo.findPrimeNumbersUptoInput(25));
	}
	
	@Test
	void findPrimeNumbersUptoInputTest2() {
		assertEquals("2 3 5", labTwo.findPrimeNumbersUptoInput(5));
	}
	
	@Test
	void generateShapeTest1() {
		assertEquals("1\n2 3", labTwo.generateShape(2));
	}
	
	@Test
	void generateShapeTest2() {
		assertEquals("1\n2 3\n4 5 6\n7 8 9 10", labTwo.generateShape(4));
	}
	
	@Test
	void depositTest1() {
		assertEquals(30, account.deposit(4.5));
	}
	
	@Test
	void withdrawTest1() {
		assertEquals(20, account.withdraw(5.5));
	}
	
	@Test
	void calculateInterestTest1() {
		assertEquals(26.01, account.calculateInterest());
	}
	
	@Test
	void equals1() {
		Account anotherAccount = new Account(12, "James", 25.5);
		
		assertFalse(account.equals(anotherAccount));
	}

}
