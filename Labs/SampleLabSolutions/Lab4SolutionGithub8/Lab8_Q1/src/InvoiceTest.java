import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class InvoiceTest {

    @Test
    public void testConstructorAndGetters() {
        Customer c = new Customer(10,"Paul",10);
        Invoice inv = new Invoice(100,c,500);

        assertEquals(100, inv.getID());
        assertEquals(c, inv.getCustomer());
        assertEquals(500.0, inv.getAmount());
    }

    @Test
    public void testSetCustomerAndAmount() {
        Customer c1 = new Customer(1,"A",5);
        Customer c2 = new Customer(2,"B",10);
        Invoice inv = new Invoice(101,c1,200);

        inv.setCustomer(c2);
        inv.setAmount(300);

        assertEquals(c2, inv.getCustomer());
        assertEquals(300.0, inv.getAmount());
    }

    @Test
    public void testCustomerInfoMethods() {
        Customer c = new Customer(5,"Zed",25);
        Invoice inv = new Invoice(201,c,400);

        assertEquals(5, inv.getCustomerID());
        assertEquals("Zed", inv.getCustomerName());
        assertEquals(25, inv.getCustomerDiscount());
    }

    @Test
    public void testAmountAfterDiscount() {
        Customer c = new Customer(7,"Kim",20);
        Invoice inv = new Invoice(301,c,1000);

        assertEquals(800.0, inv.getAmountAfterDiscount());
    }

    @Test
    public void testToString() {
        Customer c = new Customer(8,"Neo",30);
        Invoice inv = new Invoice(401,c,700);

        assertEquals("Invoice[id=401,customer=Neo(8)(30%),amount=700.0]", inv.toString());
    }
}
