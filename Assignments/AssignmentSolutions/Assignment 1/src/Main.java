import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        /* =========================
           TASK 1 TESTING
        ========================== */

        double[][] a = {
                {1, 2, 3},
                {4, 5}
        };

        double[][] b = {
                {1, 2},
                {3, 4, 5},
                {6, 7}
        };

        System.out.println("Min value: "
                + Array2DUtils.min(a));

        double[][] added =
                Array2DUtils.add(a, b);

        System.out.println("\nAdded Array:");
        print2D(added);


        /* =========================
           TASK 2 TESTING
        ========================== */

        Animal a1 = new Animal("Copain","Male",5,"Dog");
        Animal a2 = new Animal("Luna","Female",7,"Cat");
        Animal a3 = new Animal("Max","Male",12,"Dog");
        Animal a4 = new Animal("Milo","Male",12,"Dog");

        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(a1);
        animals.add(a2);
        animals.add(a3);
        animals.add(a4);

        Zoo zoo = new Zoo(animals);

        System.out.println("\nDogs count: "
                + zoo.countAnimals("Dog"));

        System.out.println("Balanced dogs? "
                + zoo.isGenderBalanced("Dog"));

        zoo.removeOldest("Dog");

        System.out.println("\nAfter removing oldest dogs:");
        System.out.println(zoo);
    }

    private static void print2D(double[][] arr) {
        for (double[] row : arr) {
            for (double v : row)
                System.out.print(v + " ");
            System.out.println();
        }
    }
}
