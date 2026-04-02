import java.util.Objects;

public class Country implements Comparable<Country> {

    private String name;
    private int code;
    private int population;

    public Country(String name, int code, int population) {
        this.name = name;
        this.code = code;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public int getPopulation() {
        return population;
    }

    @Override
    public int compareTo(Country other) {
        // Sort by population (descending)
        return Integer.compare(other.population, this.population);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code, population);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Country)) return false;
        Country other = (Country) obj;
        return code == other.code &&
                population == other.population &&
                Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return String.format("%-15s %3d %5d", name, code, population);
    }
}
