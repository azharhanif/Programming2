//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
    import java.util.Date;
        public class Main {
        public static void main(String[] args) {
            Visit v = new Visit("Alice", new Date());
            v.getCustomer().setMember(true);
            v.getCustomer().setMemberType("Premium");

            v.setServiceExpense(200);
            v.setProductExpense(100);

            System.out.println(v);
        }
    }

