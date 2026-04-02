public class Fish extends Animal implements Pet {

    private String name;

    public Fish() {
        super(0);
    }

    @Override
    public void walk() {
        System.out.println("Fish cannot walk and have no legs.");
    }

    @Override
    public void eat() {
        System.out.println("The fish eats plants.");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void play() {
        System.out.println(name + " the fish is playing.");
    }
}
