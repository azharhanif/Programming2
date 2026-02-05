//Key idea
//
//Encapsulate generation inside an object and return defensive copies.
public class AltGenMagicSquare {

    private final int[][] square;

    public AltGenMagicSquare(int n) {
        this.square = build(n);
    }

    private int[][] build(int n) {
        if (n <= 0 || n % 2 == 0)
            throw new IllegalArgumentException("Odd size only");

        int[][] s = new int[n][n];
        int r = n - 1, c = n / 2;

        for (int num = 1; num <= n * n; num++) {
            s[r][c] = num;
            int nr = (r + 1) % n;
            int nc = (c + 1) % n;

            if (s[nr][nc] != 0)
                r = (r - 1 + n) % n;
            else {
                r = nr;
                c = nc;
            }
        }
        return s;
    }

    public int[][] getSquare() {
        int[][] copy = new int[square.length][];
        for (int i = 0; i < square.length; i++)
            copy[i] = square[i].clone();
        return copy;
    }
}
