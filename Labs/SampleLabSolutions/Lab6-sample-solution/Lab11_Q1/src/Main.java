public class Main {
    public static void main(String[] args) {

        Tossable baseball = new Baseball("Rawlings");
        Tossable football = new Football("Nike");
        Tossable rock = new Rock();

        baseball.toss();
        football.toss();
        rock.toss();

        Ball b1 = new Baseball("Wilson");
        Ball b2 = new Football("Adidas");

        b1.bounce();
        b2.bounce();

        System.out.println("Baseball brand: " + b1.getBrandName());
        System.out.println("Football brand: " + b2.getBrandName());
    }
}
