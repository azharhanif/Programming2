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

