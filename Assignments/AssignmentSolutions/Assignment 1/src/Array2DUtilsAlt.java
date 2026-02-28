public class Array2DUtilsAlt {

    private static int maxCols(double[][] arr) {
        int max = 0;
        for (double[] r : arr)
            if (r.length > max) max = r.length;
        return max;
    }

    public static double min(double[][] arr) {
        double min = Double.MAX_VALUE;

        for (double[] row : arr)
            for (double v : row)
                min = Math.min(min, v);

        return min;
    }

    public static double[] minOfRow(double[][] arr) {

        double[] result = new double[arr.length];

        for (int i = 0; i < arr.length; i++) {
            result[i] = min(new double[][]{arr[i]});
        }
        return result;
    }

    public static double[] minOfCol(double[][] arr) {

        int cols = maxCols(arr);
        double[] result = new double[cols];

        for (int c = 0; c < cols; c++) {

            double min = Double.MAX_VALUE;

            for (double[] row : arr)
                if (c < row.length)
                    min = Math.min(min, row[c]);

            result[c] = min;
        }
        return result;
    }

    public static double[][] add(double[][] a, double[][] b) {

        int rows = Math.max(a.length, b.length);
        double[][] res = new double[rows][];

        for (int i = 0; i < rows; i++) {

            int lenA = (i < a.length) ? a[i].length : 0;
            int lenB = (i < b.length) ? b[i].length : 0;

            res[i] = new double[Math.max(lenA, lenB)];

            for (int j = 0; j < res[i].length; j++) {

                double v1 = (i < a.length && j < lenA) ? a[i][j] : 0;
                double v2 = (i < b.length && j < lenB) ? b[i][j] : 0;

                res[i][j] = v1 + v2;
            }
        }
        return res;
    }

    public static double[][] deleteRow(double[][] arr, int idx) {

        if (idx < 0 || idx >= arr.length) return arr;

        double[][] res = new double[arr.length - 1][];

        int k = 0;
        for (int i = 0; i < arr.length; i++)
            if (i != idx)
                res[k++] = arr[i];

        return res;
    }

    public static double[][] appendArray(double[][] a, double[][] b) {

        double[][] res = new double[a.length + b.length][];

        int k = 0;
        for (double[] r : a) res[k++] = r;
        for (double[] r : b) res[k++] = r;

        return res;
    }

    public static double[][] expendArray(double[][] a, double[][] b) {

        int rows = Math.max(a.length, b.length);
        double[][] res = new double[rows][];

        for (int i = 0; i < rows; i++) {

            int lenA = (i < a.length) ? a[i].length : 0;
            int lenB = (i < b.length) ? b[i].length : 0;

            res[i] = new double[lenA + lenB];

            int k = 0;

            if (i < a.length)
                for (double v : a[i]) res[i][k++] = v;

            if (i < b.length)
                for (double v : b[i]) res[i][k++] = v;
        }
        return res;
    }

    public static double[][] transposeMatrix(double[][] m) {

        int cols = maxCols(m);
        double[][] t = new double[cols][m.length];

        for (int r = 0; r < m.length; r++)
            for (int c = 0; c < m[r].length; c++)
                t[c][r] = m[r][c];

        return t;
    }
}
