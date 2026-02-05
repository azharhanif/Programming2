public class MagicSquare {

    /**
     * Tests whether a matrix is a magic square.
     */
    public static boolean isMagicSquare(int[][] matrix) {
        int n = matrix.length;

        // Must be square
        for (int[] row : matrix) {
            if (row.length != n) {
                return false;
            }
        }

        int magicSum = 0;
        for (int j = 0; j < n; j++) {
            magicSum += matrix[0][j];
        }

        // Check rows
        for (int i = 0; i < n; i++) {
            int rowSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum += matrix[i][j];
            }
            if (rowSum != magicSum) {
                return false;
            }
        }

        // Check columns
        for (int j = 0; j < n; j++) {
            int colSum = 0;
            for (int i = 0; i < n; i++) {
                colSum += matrix[i][j];
            }
            if (colSum != magicSum) {
                return false;
            }
        }

        // Check diagonals
        int diag1 = 0;
        int diag2 = 0;
        for (int i = 0; i < n; i++) {
            diag1 += matrix[i][i];
            diag2 += matrix[i][n - 1 - i];
        }

        return diag1 == magicSum && diag2 == magicSum;
    }

    /**
     * Generates a magic square of odd size n.
     */
    public static int[][] generateMagicSquare(int n) {
        if (n <= 0 || n % 2 == 0) {
            throw new IllegalArgumentException("Size must be a positive odd number");
        }

        int[][] square = new int[n][n];

        int row = n - 1;      // bottom row
        int col = n / 2;      // middle column

        for (int num = 1; num <= n * n; num++) {
            square[row][col] = num;

            int nextRow = (row + 1) % n;
            int nextCol = (col + 1) % n;

            if (square[nextRow][nextCol] != 0) {
                row = (row - 1 + n) % n;
            } else {
                row = nextRow;
                col = nextCol;
            }
        }

        return square;
    }
}
