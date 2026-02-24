import java.util.Date;

public class Visit {
    private Customer customer;
    private Date date;
    private double serviceExpense;
    private double productExpense;

    public Visit(String name, Date date) {
        this.customer = new Customer(name);
        this.date = date;
    }

    public String getName() {
        return customer.getName();
    }

    public double getServiceExpense() {
        return serviceExpense;
    }

    public void setServiceExpense(double ex) {
        this.serviceExpense = ex;
    }

    public double getProductExpense() {
        return productExpense;
    }

    public void setProductExpense(double ex) {
        this.productExpense = ex;
    }

    public double getTotalExpense() {
        double serviceDiscount =
                serviceExpense * DiscountRate.getServiceDiscountRate(customer.getMemberType());
        double productDiscount =
                productExpense * DiscountRate.getProductDiscountRate(customer.getMemberType());

        return serviceExpense - serviceDiscount + productExpense - productDiscount;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "Visit[customer=" + customer +
                ", date=" + date +
                ", totalExpense=" + getTotalExpense() + "]";
    }
}
