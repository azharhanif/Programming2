import java.util.List;

public class JaggedIntMatrix {

    private final List<List<Integer>> data;

    public JaggedIntMatrix(List<List<Integer>> data) {
        this.data = data;
    }

    public QueryResult query(int row, int col) {
        int r = row - 1;
        int c = col - 1;

        if (!isValidRow(r)) {
            return QueryResult.error();
        }

        if (!isValidColumn(r, c)) {
            return QueryResult.error();
        }

        return QueryResult.success(data.get(r).get(c));
    }

    private boolean isValidRow(int r) {
        return r >= 0 && r < data.size();
    }

    private boolean isValidColumn(int r, int c) {
        return c >= 0 && c < data.get(r).size();
    }
}
