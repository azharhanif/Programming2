import java.util.Comparator;

public class PriceDescendingComparator implements Comparator<Book> {

    @Override
    public int compare(Book b1, Book b2) {
        return Double.compare(b2.getPrice(), b1.getPrice());
    }
}