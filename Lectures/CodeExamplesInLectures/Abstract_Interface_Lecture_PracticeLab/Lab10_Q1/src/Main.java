//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Animal cat = new Cat("Kitty");
        cat.greets();

        Dog dog1 = new Dog("Buddy");
        Dog dog2 = new Dog("Max");
        dog1.greets();
        dog1.greets(dog2);

        BigDog bigDog1 = new BigDog("Rocky");
        BigDog bigDog2 = new BigDog("Bruno");

        bigDog1.greets();
        bigDog1.greets(dog1);
        bigDog1.greets(bigDog2);
    }
}