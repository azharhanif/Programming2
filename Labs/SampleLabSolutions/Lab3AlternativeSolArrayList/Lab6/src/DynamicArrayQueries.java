import java.util.ArrayList;
import java.util.List;

public class DynamicArrayQueries {

    /**
     * Returns the value at y-th position of x-th line (1-based indexing).
     * If not found, returns null.
     */
    public static Integer query(List<List<Integer>> data, int x, int y) {
        // Convert to 0-based indexing
        int rowIndex = x - 1;
        int colIndex = y - 1;

        if (rowIndex < 0 || rowIndex >= data.size()) {
            return null;
        }

        List<Integer> row = data.get(rowIndex);

        if (colIndex < 0 || colIndex >= row.size()) {
            return null;
        }

        return row.get(colIndex);
    }

    /**
     * Builds the data structure using ArrayLists.
     */
    public static List<List<Integer>> buildData(List<int[]> inputLines) {
        List<List<Integer>> data = new ArrayList<>();

        for (int[] line : inputLines) {
            List<Integer> row = new ArrayList<>();
            for (int value : line) {
                row.add(value);
            }
            data.add(row);
        }

        return data;
    }
}
