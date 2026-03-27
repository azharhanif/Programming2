import java.util.Comparator;

public class AuthorThenYearComparator implements Comparator<Book> {

    @Override
    public int compare(Book b1, Book b2) {
        int result = b1.getAuthor().compareTo(b2.getAuthor());
        if (result != 0) {
            return result;
        }
        return Integer.compare(b1.getYear(), b2.getYear());
    }
}