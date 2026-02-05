public class MagicSquareCheckerSinglePass {
// Alternate 1A Key idea
//
//Accumulate row sums, column sums, and diagonals in one traversal
//
//Fewer loops → clearer invariant reasoning
    public static boolean isMagicSquare(int[][] matrix) {
        int n = matrix.length;
        int[] rowSum = new int[n];
        int[] colSum = new int[n];
        int diag1 = 0, diag2 = 0;

        for (int i = 0; i < n; i++) {
            if (matrix[i].length != n) return false;

            for (int j = 0; j < n; j++) {
                rowSum[i] += matrix[i][j];
                colSum[j] += matrix[i][j];

                if (i == j) diag1 += matrix[i][j];
                if (i + j == n - 1) diag2 += matrix[i][j];
            }
        }

        int magic = rowSum[0];
        if (diag1 != magic || diag2 != magic) return false;

        for (int k = 0; k < n; k++) {
            if (rowSum[k] != magic || colSum[k] != magic)
                return false;
        }

        return true;
    }
}
