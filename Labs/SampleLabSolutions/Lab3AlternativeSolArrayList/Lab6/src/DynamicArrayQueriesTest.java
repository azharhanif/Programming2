import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayQueriesTest {

    private List<List<Integer>> createSampleData() {
        List<List<Integer>> data = new ArrayList<>();

        data.add(List.of(41, 77, 74, 22, 44));
        data.add(List.of(12));
        data.add(List.of(37, 34, 36, 52));
        data.add(List.of());
        data.add(List.of(20, 22, 33));

        return data;
    }

    @Test
    void testValidQuery() {
        List<List<Integer>> data = createSampleData();
        Integer result = DynamicArrayQueries.query(data, 1, 3);
        assertEquals(74, result);
    }

    @Test
    void testAnotherValidQuery() {
        List<List<Integer>> data = createSampleData();
        Integer result = DynamicArrayQueries.query(data, 3, 4);
        assertEquals(52, result);
    }

    @Test
    void testInvalidColumn() {
        List<List<Integer>> data = createSampleData();
        Integer result = DynamicArrayQueries.query(data, 4, 3);
        assertNull(result);
    }

    @Test
    void testInvalidRow() {
        List<List<Integer>> data = createSampleData();
        Integer result = DynamicArrayQueries.query(data, 5, 5);
        assertNull(result);
    }
}
