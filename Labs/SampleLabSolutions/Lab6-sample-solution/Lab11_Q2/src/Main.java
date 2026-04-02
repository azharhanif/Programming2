//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  public static void main(String[] args) {

        Animal spider = new Spider();
        spider.walk();
        spider.eat();

        Cat cat = new Cat("Whiskers");
        cat.walk();
        cat.play();
        cat.eat();

        Fish fish = new Fish();
        fish.setName("Nemo");
        fish.walk();
        fish.play();
        fish.eat();
    }
}