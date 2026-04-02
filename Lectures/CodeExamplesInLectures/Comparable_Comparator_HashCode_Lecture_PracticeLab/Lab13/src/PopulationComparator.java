import java.util.Comparator;

public class PopulationComparator implements Comparator<Country> {

    @Override
    public int compare(Country c1, Country c2) {
        return Integer.compare(c2.getPopulation(), c1.getPopulation());
    }
}
