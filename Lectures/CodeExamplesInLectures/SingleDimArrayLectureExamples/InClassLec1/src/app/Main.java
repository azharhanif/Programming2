//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
package app;
//import model.Car;
import java.util.Scanner;
public class Main {

        public static Car[] getBlueCars(Car[] cars) {
                int count = 0;

                // First pass: count blue cars
                for (Car car : cars) {
                        if (car.getColor().equalsIgnoreCase("blue")) {
                                count++;
                        }
                }

                // Create result array
                Car[] blueCars = new Car[count];
                int index = 0;

                // Second pass: store blue cars
                for (Car car : cars) {
                        if (car.getColor().equalsIgnoreCase("blue")) {
                                blueCars[index++] = car;
                        }
                }

                return blueCars;
        }
    public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);

            // Problem 1
            System.out.print("Enter array size: Array length must be even ");
            int n = sc.nextInt();
            int[] arr = new int[n];

            System.out.println("Enter array elements:");
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }

            int[] minMax = ArrayUtils.findMinAndMax(arr);
            System.out.println("Min = " + minMax[0]);
            System.out.println("Max = " + minMax[1]);

            // Problem 2
            System.out.println("Sum of odd indices = " +
                    ArrayUtils.sumOddIndices(arr));

            // Problem 3
           // Scanner sc = new Scanner(System.in);

            System.out.print("Enter number of cars: ");
            int nn = sc.nextInt();
            sc.nextLine(); // consume newline

            Car[] cars = new Car[nn];

            for (int i = 0; i < nn; i++) {
                    System.out.print("Enter brand: ");
                    String brand = sc.nextLine();

                    System.out.print("Enter color: ");
                    String color = sc.nextLine();

                    System.out.print("Enter year: ");
                    int year = sc.nextInt();
                    sc.nextLine(); // consume newline

                    cars[i] = new Car(brand, color, year);
            }

            Car[] blueCars = getBlueCars(cars);

            System.out.println("\nBlue Cars:");
            for (Car car : blueCars) {
                    System.out.println(car);
            }

            sc.close();
        }
}