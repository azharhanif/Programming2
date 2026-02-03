//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        Clock c1 = new Clock(0, 0, 0);
        Clock c2 = new Clock(0, 0, 0);
        Clock c3 = new Clock(0, 0, 0);
        Clock c4 = new Clock(0, 0, 0);

        Clock[] cs1 = {c1, c2};
        Clock[] cs2 = {c3, c4};
        Clock[][] css1 = {{c1, c2},{c3,c4}};
        Clock[][] css2 = {{c1, c2},{c3,c4}};

        int P1= 5, P2=5, P3=5,P4=5;
        int[] ps1= {P1,P2};
        int[] ps2= {P3,P4};
        System.out.println("printing int[] ps1 and int[] ps2, deepEquals does not work");
        // reference of ps1 and ps2
        System.out.println(ps1 == ps2);
        // calling equals() in Clock class to comapre c1 and c3, c2 and c4
        System.out.println(Arrays.equals(ps1, ps2));
        // calling equals() to compare c1 and c3, c2 and c4
        //System.out.println(Arrays.deepEquals(ps1, ps2));
        int[] a1 = {5};
        int[] a2 = {5};
        int[] a3 = {5};
        int[] a4 = {5};

        int[][][] as1 = { {a1,a2},{a3, a4} };
        int[][][] as2 = { {a1,a2},{a3, a4} };
        System.out.println("printing int [][][] as1 and int [][][] as2, deepEquals work");
        // reference of cs1 and cs2
        System.out.println(as1 == as2);
        // calling equals() in Clock class to comapre c1 and c3, c2 and c4
        System.out.println(Arrays.equals(as1, as2));
        // calling equals() to compare c1 and c3, c2 and c4
        System.out.println(Arrays.deepEquals(as1, as2));


        System.out.println("printing Clock[] cs1 and Clock[] cs2");
        // reference of cs1 and cs2
        System.out.println(cs1 == cs2);
        // calling equals() in Clock class to comapre c1 and c3, c2 and c4
       System.out.println(Arrays.equals(cs1, cs2));
        // calling equals() to compare c1 and c3, c2 and c4
       System.out.println(Arrays.deepEquals(cs1, cs2));
        System.out.println("printing Clock[][] css1 and Clock[][] css2");
        // reference of cs1 and cs2
        System.out.println(css1 == css2);
        // calling equals() in Clock class to comapre c1 and c3, c2 and c4
        System.out.println(Arrays.equals(css1, css2));
        // calling equals() to compare c1 and c3, c2 and c4
        System.out.println(Arrays.deepEquals(css1, css2));
    }
}