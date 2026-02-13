import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AltMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        List<List<Integer>> rawData = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int d = scanner.nextInt();
            List<Integer> row = new ArrayList<>();

            for (int j = 0; j < d; j++) {
                row.add(scanner.nextInt());
            }
            rawData.add(row);
        }

        JaggedIntMatrix matrix = new JaggedIntMatrix(rawData);

        int q = scanner.nextInt();

        for (int i = 0; i < q; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            QueryResult result = matrix.query(x, y);

            if (result.isValid()) {
                System.out.println(result.getValue());
            } else {
                System.out.println("ERROR!");
            }
        }

        scanner.close();
    }
}
