
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {
    @Test
    void withdrawTooMuchShouldThrow() {
        BankAccount account = new BankAccount("Sam", 100.0);
        assertThrows(InsufficientFundsException.class,
                () -> account.withdraw(150.0));
    }
}
