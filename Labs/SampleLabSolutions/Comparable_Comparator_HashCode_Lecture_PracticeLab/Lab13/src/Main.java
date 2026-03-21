//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.ArrayList;
import java.util.Collections;
public class Main {
    public static void main(String[] args) {

        ArrayList<Country> countries = new ArrayList<>();

        countries.add(new Country("China", 86, 1321));
        countries.add(new Country("France", 33, 65));
        countries.add(new Country("India", 91, 1148));
        countries.add(new Country("Mexico", 52, 110));
        countries.add(new Country("New Zealand", 64, 5));

        System.out.println("Here is the list of countries, sorted by name.");
        Collections.sort(countries, new NameComparator());
        printList(countries);

        System.out.println("\nHere is the list of countries, sorted by dialing code.");
        Collections.sort(countries, new CodeComparator());
        printList(countries);

        System.out.println("\nHere is the list of countries, sorted by population.");
        Collections.sort(countries, new PopulationComparator());
        printList(countries);
    }

    private static void printList(ArrayList<Country> list) {
        for (Country c : list) {
            System.out.println(c);
        }
    }
} //Console output matches Q1 & Q2 images exactly (spacing + ordering).