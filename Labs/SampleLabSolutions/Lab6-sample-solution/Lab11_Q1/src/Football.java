public class Football extends Ball {

    public Football(String brandName) {
        super(brandName);
    }

    @Override
    public void toss() {
        System.out.println("Tossing a football");
    }

    @Override
    public void bounce() {
        System.out.println("Football bouncing");
    }
}
