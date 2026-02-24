import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerVisitTest {

    @Test
    public void testCustomerCreation() {
        Customer c = new Customer("Bob");
        assertEquals("Bob", c.getName());
        assertFalse(c.isMember());
    }

    @Test
    public void testDiscountRateService() {
        assertEquals(0.20, DiscountRate.getServiceDiscountRate("premium"));
        assertEquals(0.0, DiscountRate.getServiceDiscountRate("none"));
    }

    @Test
    public void testVisitTotalExpense() {
        Visit v = new Visit("John", new Date());
        v.getCustomer().setMember(true);
        v.getCustomer().setMemberType("Gold");

        v.setServiceExpense(100);
        v.setProductExpense(50);

        // Gold: 15% service, 10% product
        double expected = 100 - (100 * 0.15) + 50 - (50 * 0.10);
        assertEquals(expected, v.getTotalExpense(), 0.001);
    }
}
