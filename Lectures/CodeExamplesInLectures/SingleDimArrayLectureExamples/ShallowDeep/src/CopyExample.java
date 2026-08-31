import java.util.Arrays;

public class CopyExample {

    private int[] data;

    // Constructor with choice of copy type
    public CopyExample(int[] values, boolean deepCopy) {

        if (deepCopy) {
            // DEEP COPY
            data = new int[values.length];
            for (int i = 0; i < values.length; i++) {
                data[i] = values[i];
            }
        } else {
            // SHALLOW COPY
            data = values;
        }
    }

    public void showData() {
        System.out.println(Arrays.toString(data));
    }


}