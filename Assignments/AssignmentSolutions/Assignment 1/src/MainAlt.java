public class MainAlt {

    public static void main(String[] args) {

        testArrays();
        testZoo();
    }

    private static void testArrays() {

        double[][] arr = {
                {3, 8},
                {2},
                {9, 4, 1}
        };

        System.out.println("Min value: "
                + Array2DUtils.min(arr));

        System.out.println("Column mins:");
        double[] cols =
                Array2DUtils.minOfCol(arr);

        for (double v : cols)
            System.out.print(v + " ");

        System.out.println("\nTranspose:");
        print(Array2DUtils.transposeMatrix(arr));
    }

    private static void testZoo() {

        ZooAlt zooAlt = new ZooAlt();

        zooAlt.getAnimals().add(
                new AnimalAlt("A","Male",10,"Monkey"));
        zooAlt.getAnimals().add(
                new AnimalAlt("B","Female",8,"Monkey"));
        zooAlt.getAnimals().add(
                new AnimalAlt("C","Male",20,"Monkey"));

        System.out.println("\nBalanced monkeys? "
                + zooAlt.isGenderBalanced("Monkey"));

        zooAlt.removeOldest("Monkey");

        System.out.println(zooAlt);
    }

    private static void print(double[][] arr) {
        for (double[] r : arr) {
            for (double v : r)
                System.out.print(v + " ");
            System.out.println();
        }
    }
}
