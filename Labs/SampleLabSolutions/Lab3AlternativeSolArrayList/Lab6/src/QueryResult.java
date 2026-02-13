public class QueryResult {

    private final Integer value;
    private final boolean valid;

    private QueryResult(Integer value, boolean valid) {
        this.value = value;
        this.valid = valid;
    }

    public static QueryResult success(int value) {
        return new QueryResult(value, true);
    }

    public static QueryResult error() {
        return new QueryResult(null, false);
    }

    public boolean isValid() {
        return valid;
    }

    public Integer getValue() {
        return value;
    }
}
