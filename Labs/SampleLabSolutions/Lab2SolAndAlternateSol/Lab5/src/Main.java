import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter an odd number to generate a magic square: ");
        int n = scanner.nextInt();

        try {
            int[][] square = MagicSquare.generateMagicSquare(n);

            System.out.println("\nGenerated Magic Square:");
            printSquare(square);

            boolean isMagic = MagicSquare.isMagicSquare(square);
            System.out.println("\nIs this a magic square? " + isMagic);

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }

    private static void printSquare(int[][] square) {
        for (int[] row : square) {
            for (int value : row) {
                System.out.printf("%4d", value);
            }
            System.out.println();
        }
    }
}
