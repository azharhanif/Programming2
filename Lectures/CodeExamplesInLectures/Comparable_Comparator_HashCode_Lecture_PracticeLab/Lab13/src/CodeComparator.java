import java.util.Comparator;

public class CodeComparator implements Comparator<Country> {

    @Override
    public int compare(Country c1, Country c2) {
        return Integer.compare(c1.getCode(), c2.getCode());
    }
}
