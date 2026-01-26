//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        int age=19;
        // Bad example of using switch...case, should use if...else instead
        switch (age) {
            case 1:
            case 2:
            case 3:

            case 17:
                System.out.println("You cannot drive");
                break;
            case 18:
            case 19:

                System.out.println("You can drive");
        }
        String mystr="vaniercollege";
        for (int i = 0, j = mystr.length() - 1; i < j; i++, j--) {
            System.out.println("i = " + i + ", j = " + j);

        }
        Student s1 = new Student(101);
        Student s2 = new Student(101);

        System.out.println(s1.equals(s2)); // true

    }
}