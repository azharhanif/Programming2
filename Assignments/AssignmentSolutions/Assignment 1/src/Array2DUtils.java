public class Array2DUtils {

    public static double min(double[][] numss) {
        double min = Double.MAX_VALUE;

        for (double[] row : numss)
            for (double v : row)
                if (v < min) min = v;

        return min;
    }

    public static double[] minOfRow(double[][] numss) {
        double[] result = new double[numss.length];

        for (int i = 0; i < numss.length; i++) {
            double min = Double.MAX_VALUE;
            for (double v : numss[i])
                if (v < min) min = v;
            result[i] = min;
        }
        return result;
    }

    public static double[] minOfCol(double[][] numss) {
        int maxCol = 0;
        for (double[] row : numss)
            maxCol = Math.max(maxCol, row.length);

        double[] result = new double[maxCol];

        for (int c = 0; c < maxCol; c++) {
            double min = Double.MAX_VALUE;
            for (double[] row : numss)
                if (c < row.length && row[c] < min)
                    min = row[c];
            result[c] = min;
        }
        return result;
    }

    public static double[][] add(double[][] a, double[][] b) {

        int rows = Math.max(a.length, b.length);
        double[][] res = new double[rows][];

        for (int i = 0; i < rows; i++) {

            int lenA = i < a.length ? a[i].length : 0;
            int lenB = i < b.length ? b[i].length : 0;

            int cols = Math.max(lenA, lenB);
            res[i] = new double[cols];

            for (int j = 0; j < cols; j++) {
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

            int lenA = i < a.length ? a[i].length : 0;
            int lenB = i < b.length ? b[i].length : 0;

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

        int cols = 0;
        for (double[] r : m)
            cols = Math.max(cols, r.length);

        double[][] t = new double[cols][m.length];

        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[i].length; j++)
                t[j][i] = m[i][j];

        return t;
    }
}
