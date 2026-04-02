public class Baseball extends Ball {

    public Baseball(String brandName) {
        super(brandName);
    }

    @Override
    public void toss() {
        System.out.println("Tossing a baseball");
    }

    @Override
    public void bounce() {
        System.out.println("Baseball bouncing");
    }
}
