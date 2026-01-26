import java.util.Random;

public class Lab1Utils {

    /**
     * Concatenates str2 to the end of str1 with a space in between.
     */
    public static String concatenateStrings(String str1, String str2) {
        return str1 + " " + str2;
    }

    /**
     * Checks whether mainString ends with endingString.
     */
    public static boolean endsWith(String mainString, String endingString) {
        return mainString.endsWith(endingString);
    }

    /**
     * Generates a random integer between start and end (inclusive).
     */
    public static int generateRandomInRange(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException("Start must be less than or equal to end");
        }
        Random random = new Random();
        return random.nextInt(end - start + 1) + start;
    }

    /**
     * Checks whether a password is valid.
     * Conditions:
     * - At least 8 characters
     * - Contains uppercase letter
     * - Contains lowercase letter
     * - Contains digit
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        return hasUpper && hasLower && hasDigit;
    }
}