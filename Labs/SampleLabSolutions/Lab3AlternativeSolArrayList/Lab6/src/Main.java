import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        List<List<Integer>> data = new ArrayList<>();

        // Read n lines of data
        for (int i = 0; i < n; i++) {
            int d = scanner.nextInt();
            List<Integer> row = new ArrayList<>();

            for (int j = 0; j < d; j++) {
                row.add(scanner.nextInt());
            }
            data.add(row);
        }

        int q = scanner.nextInt();

        // Process queries
        for (int i = 0; i < q; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            Integer result = DynamicArrayQueries.query(data, x, y);
            if (result == null) {
                System.out.println("ERROR!");
            } else {
                System.out.println(result);
            }
        }

        scanner.close();
    }
}
