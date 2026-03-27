import java.util.Comparator;

public class YearComparator implements Comparator<Book> {

    @Override
    public int compare(Book b1, Book b2) {
        return Integer.compare(b1.getYear(), b2.getYear());
    }
}