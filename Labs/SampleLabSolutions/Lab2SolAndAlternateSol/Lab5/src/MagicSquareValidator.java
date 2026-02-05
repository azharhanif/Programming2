// Alternate 1B Key idea
//
//Encapsulate validation logic inside a domain object.
//Encapsulation
//Domain-driven design
public class MagicSquareValidator {

    private final int[][] square;

    public MagicSquareValidator(int[][] square) {
        this.square = square;
    }

    public boolean isValid() {
        int n = square.length;
        int target = sumRow(0);

        for (int i = 0; i < n; i++) {
            if (sumRow(i) != target || sumCol(i) != target)
                return false;
        }

        return sumPrimaryDiag() == target &&
                sumSecondaryDiag() == target;
    }

    private int sumRow(int r) {
        int sum = 0;
        for (int v : square[r]) sum += v;
        return sum;
    }

    private int sumCol(int c) {
        int sum = 0;
        for (int[] row : square) sum += row[c];
        return sum;
    }

    private int sumPrimaryDiag() {
        int sum = 0;
        for (int i = 0; i < square.length; i++)
            sum += square[i][i];
        return sum;
    }

    private int sumSecondaryDiag() {
        int sum = 0;
        for (int i = 0; i < square.length; i++)
            sum += square[i][square.length - 1 - i];
        return sum;
    }
}
