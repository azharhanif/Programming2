import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest2 {

    /*
     * Basic pattern for a JUnit test:
     *
     * 1. Arrange  -> create the object / prepare data
     * 2. Act      -> call the method being tested
     * 3. Assert   -> check whether the result is correct
     *
     * Account.java does not have getters, so these examples
     * use toString() to observe the Account object's state.
     */

    @Test
    void increaseInterestRate() {

        // Arrange
        Account account = new Account(1242, "Adel", 20);

        // Act
        account.increaseInterestRate();

        // Assert
        // Initial interestRate = 2.5
        // increaseInterestRate() adds 0.5
        // Expected = 3.0
        assertTrue(
                account.toString().contains("interestRate: 3.0")
        );
    }

    @Test
    void increaseInterestRateTwice() {

        Account account = new Account(1242, "Adel", 20);

        // Act
        account.increaseInterestRate();
        account.increaseInterestRate();

        // 2.5 + 0.5 + 0.5 = 3.5
        assertTrue(
                account.toString().contains("interestRate: 3.5")
        );
    }

    @Test
    void increaseInterestRateDoesNotChangeOtherFields() {

        Account account = new Account(1242, "Adel", 20);

        account.increaseInterestRate();

        String result = account.toString();

        // Interest rate should change.
        assertTrue(result.contains("interestRate: 3.0"));

        // Other information should remain unchanged.
        assertTrue(result.contains("AccountNumber: 1242"));
        assertTrue(result.contains("name: Adel"));
        assertTrue(result.contains("amount: 20.0"));
    }

    @Test
    void testToString() {

        Account account = new Account(4212, "John", 4332);

        String result = account.toString();

        // Here we know the complete expected String,
        // so assertEquals is appropriate.
        assertEquals(
                "AccountNumber: 4212\n" +
                        "name: John\n" +
                        "amount: 4332.0\n" +
                        "interestRate: 2.5",
                result
        );
    }

    @Test
    void testDefaultConstructor() {

        Account account = new Account();

        /*
         * The default constructor does not explicitly initialize
         * accountNumber, name, or amount.
         *
         * Java default values:
         * int    -> 0
         * String -> null
         * double -> 0.0
         *
         * interestRate is explicitly initialized to 2.5.
         */
        assertEquals(
                "AccountNumber: 0\n" +
                        "name: null\n" +
                        "amount: 0.0\n" +
                        "interestRate: 2.5",
                account.toString()
        );
    }

    @Test
    void increaseInterestRateThreeTimes() {

        Account account = new Account(4212, "John", 4332);

        account.increaseInterestRate();
        account.increaseInterestRate();
        account.increaseInterestRate();

        // 2.5 + (3 × 0.5) = 4.0
        assertTrue(
                account.toString().contains("interestRate: 4.0")
        );
    }


    // ============================================================
    // SINGLE-DIMENSIONAL ARRAY EXAMPLES
    // ============================================================
    //
    // These tests connect the Account class to the
    // single-dimensional-array lecture.
    //
    // Remember:
    // Account[] accounts = new Account[3];
    //
    // creates an array with 3 slots. Initially:
    //
    // { null, null, null }
    //
    // It does NOT create three Account objects.
    //
    // The objects must be created separately.

    @Test
    void testAccountArrayLength() {

        Account[] accounts = {
                new Account(),
                new Account(1242, "Adel", 20),
                new Account(4212, "John", 4332)
        };

        // The array contains exactly 3 Account references.
        assertEquals(3, accounts.length);
    }

    @Test
    void testAccountArrayContainsExpectedAccounts() {

        Account[] accounts = {
                new Account(),
                new Account(1242, "Adel", 20),
                new Account(4212, "John", 4332)
        };

        // Test the object at index 1.
        assertTrue(
                accounts[1].toString().contains("AccountNumber: 1242")
        );

        assertTrue(
                accounts[1].toString().contains("name: Adel")
        );

        // Test the object at index 2.
        assertTrue(
                accounts[2].toString().contains("AccountNumber: 4212")
        );
    }

    @Test
    void testIncreaseInterestRateForEveryAccountInArray() {

        Account[] accounts = {
                new Account(),
                new Account(1242, "Adel", 20),
                new Account(4212, "John", 4332)
        };

        // Act: change every Account in the array.
        for (Account account : accounts) {
            account.increaseInterestRate();
        }

        // Every Account should now have interestRate = 3.0.
        for (Account account : accounts) {
            assertTrue(
                    account.toString().contains("interestRate: 3.0")
            );
        }
    }

    @Test
    void testArrayIndexAccess() {

        Account[] accounts = {
                new Account(1001, "Ali", 100),
                new Account(1002, "Mina", 200),
                new Account(1003, "John", 300)
        };

        // Index starts at 0.
        assertTrue(
                accounts[0].toString().contains("AccountNumber: 1001")
        );

        assertTrue(
                accounts[2].toString().contains("AccountNumber: 1003")
        );
    }

    @Test
    void testArrayTraversalUsingIndex() {

        Account[] accounts = {
                new Account(1001, "Ali", 100),
                new Account(1002, "Mina", 200),
                new Account(1003, "John", 300)
        };

        int count = 0;

        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null) {
                count++;
            }
        }

        assertEquals(3, count);
    }

    @Test
    void testArrayWithNullSlot() {

        Account[] accounts = new Account[3];

        // Creating the array does NOT create Account objects.
        assertNull(accounts[0]);
        assertNull(accounts[1]);
        assertNull(accounts[2]);

        // Now create an object in one slot.
        accounts[1] = new Account(2001, "Sara", 500);

        assertNull(accounts[0]);
        assertNotNull(accounts[1]);
        assertNull(accounts[2]);

        assertTrue(
                accounts[1].toString().contains("AccountNumber: 2001")
        );
    }

    @Test
    void testModifyObjectsThroughArrayReference() {

        Account account1 = new Account(1001, "Ali", 100);

        Account[] accounts = { account1 };

        // accounts[0] and account1 refer to the same Account object.
        accounts[0].increaseInterestRate();

        assertTrue(
                account1.toString().contains("interestRate: 3.0")
        );
    }

}
