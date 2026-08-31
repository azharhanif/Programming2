package app;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of cars: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        Car[] cars = new Car[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter brand: ");
            String brand = sc.nextLine();

            System.out.print("Enter color: ");
            String color = sc.nextLine();

            System.out.print("Enter year: ");
            int year = sc.nextInt();
            sc.nextLine(); // consume newline

            cars[i] = new Car(brand, color, year);
        }
        Car[] blueCars = CarUtils.getCarsByColor(cars, "blue");
        System.out.println("\nBlue Cars:");
        for (Car car : blueCars) {
            System.out.println(car);
        }

        sc.close();
    }
}