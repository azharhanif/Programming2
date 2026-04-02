public abstract class Ball implements Tossable {

    protected String brandName;

    public Ball(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public abstract void bounce();
    //Why abstract?
    //UML shows Ball is not instantiated directly and leaves toss() behavior to subclasses.
}
