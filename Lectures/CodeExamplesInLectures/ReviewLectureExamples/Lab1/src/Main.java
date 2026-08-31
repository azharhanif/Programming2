import java.util.Scanner;
import java.util.Random;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Question 1: String concatenation
        System.out.println("=== String Concatenation ===");
        System.out.print("Enter first string: ");
        String str1 = scanner.nextLine();
        System.out.print("Enter second string: ");
        String str2 = scanner.nextLine();

        String concatenated = Lab1Utils.concatenateStrings(str1, str2);
        System.out.println("The concatenated string: " + concatenated);

        // Question 2: Ends with check
        System.out.println("\n=== Ends With Check ===");
        System.out.print("Enter main string: ");
        String mainString = scanner.nextLine();
        System.out.print("Enter ending string: ");
        String endingString = scanner.nextLine();

        boolean endsWith = Lab1Utils.endsWith(mainString, endingString);
        System.out.println("Ends with result: " + endsWith);

        // Question 3: Random number generation
        System.out.println("\n=== Random Number Generation ===");
        System.out.print("Input the starting number of the range: ");
        int start = scanner.nextInt();
        System.out.print("Input the ending number of the range: ");
        int end = scanner.nextInt();

        int randomNumber = Lab1Utils.generateRandomInRange(start, end);
        System.out.println("The randomly generated number is: " + randomNumber);

        scanner.nextLine(); // clear buffer

        // Question 4: Password validation
        System.out.println("\n=== Password Validation ===");
        System.out.print("Please input a password: ");
        String password = scanner.nextLine();

        if (Lab1Utils.isValidPassword(password)) {
            System.out.println("The password is valid!");
        } else {
            System.out.println("The password is invalid!");
        }

        scanner.close();
    }

}