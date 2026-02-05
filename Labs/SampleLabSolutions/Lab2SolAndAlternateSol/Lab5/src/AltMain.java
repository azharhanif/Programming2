public class AltMain {

    public static void main(String[] args) {

        int[][] sample = {
                {4, 9, 2},
                {3, 5, 7},
                {8, 1, 6}
        };

        // --- Problem 1 tests ---
        System.out.println("Single-pass check: " +
                MagicSquareCheckerSinglePass.isMagicSquare(sample));

        MagicSquareValidator validator =
                new MagicSquareValidator(sample);
        System.out.println("OOP validator check: " +
                validator.isValid());

        // --- Problem 2 tests ---
        AltGenMagicSquare magic = new AltGenMagicSquare(3);
        System.out.println("Immutable square:");
        print(magic.getSquare());
    }

    private static void print(int[][] m) {
        for (int[] row : m) {
            for (int v : row)
                System.out.print(v + " ");
            System.out.println();
        }
        System.out.println();
    }
}
