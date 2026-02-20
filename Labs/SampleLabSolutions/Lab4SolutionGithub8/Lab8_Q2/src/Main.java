//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Student s = new Student("Alice","Toronto","CS",2,4500);
        Staff t = new Staff("Bob","Ottawa","Engineering",72000);

        System.out.println(s);
        System.out.println(t);
    }
}