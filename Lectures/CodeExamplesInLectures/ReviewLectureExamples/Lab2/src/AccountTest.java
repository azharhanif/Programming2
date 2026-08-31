import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void testParameterizedConstructorAndGetters() {
        Account acc = new Account(1001, "John Doe", 500.0);

        assertEquals(1001, acc.getAccountNumber());
        assertEquals("John Doe", acc.getName());
        assertEquals(500.0, acc.getAmount());
    }

    @Test
    void testDeposit() {
        Account acc = new Account(1002, "Jane Doe", 200.0);
        acc.deposit(100.0);

        assertEquals(300.0, acc.getAmount());
    }

    @Test
    void testWithdraw() {
        Account acc = new Account(1003, "Alex", 400.0);
        acc.withdraw(150.0);

        assertEquals(250.0, acc.getAmount());
    }

    @Test
    void testWithdrawMoreThanBalance() {
        Account acc = new Account(1004, "Chris", 100.0);
        acc.withdraw(200.0);

        assertEquals(100.0, acc.getAmount());
    }

    @Test
    void testCalculateInterest() {
        Account acc = new Account(1005, "Sam", 1000.0);
        acc.calculateInterest();

        assertEquals(1020.0, acc.getAmount());
    }

    @Test
    void testEqualsTrue() {
        Account acc1 = new Account(2001, "Taylor", 300.0);
        Account acc2 = new Account(2001, "Taylor", 300.0);

        assertTrue(acc1.equals(acc2));
    }

    @Test
    void testEqualsFalse() {
        Account acc1 = new Account(2002, "Taylor", 300.0);
        Account acc2 = new Account(2003, "Taylor", 300.0);

        assertFalse(acc1.equals(acc2));
    }

    @Test
    void testToStringContainsValues() {
        Account acc = new Account(3001, "Morgan", 750.0);
        String output = acc.toString();

        assertTrue(output.contains("3001"));
        assertTrue(output.contains("Morgan"));
        assertTrue(output.contains("750.0"));
    }
}
