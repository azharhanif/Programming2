import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        ArrayList<Book> books = new ArrayList<>();

        books.add(new Book("Java Basics", "Alice Smith", 2018, 59.99));
        books.add(new Book("Data Structures", "Bob White", 2020, 79.50));
        books.add(new Book("Algorithms", "Alice Smith", 2016, 89.00));
        books.add(new Book("Database Systems", "Carol Green", 2019, 69.25));
        books.add(new Book("Computer Networks", "David Black", 2015, 72.10));

        System.out.println("Original list:");
        printBooks(books);

        Collections.sort(books);
        System.out.println("\nSorted by title (Comparable):");
        printBooks(books);

        Collections.sort(books, new AuthorComparator());
        System.out.println("\nSorted by author:");
        printBooks(books);

        Collections.sort(books, new YearComparator());
        System.out.println("\nSorted by year:");
        printBooks(books);

        Collections.sort(books, new PriceDescendingComparator());
        System.out.println("\nSorted by price descending:");
        printBooks(books);
    }

    private static void printBooks(ArrayList<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }
}