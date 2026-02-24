//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Animal a = new Animal("Generic");
        Mammal m = new Mammal("MammalOne");
        Cat c = new Cat("Kitty");
        Dog d1 = new Dog("Buddy");
        Dog d2 = new Dog("Rocky");

        System.out.println(a);
        System.out.println(m);
        System.out.println(c);
        System.out.println(d1);

        c.greets();
        d1.greets();
        d1.greets(d2);
    }
}