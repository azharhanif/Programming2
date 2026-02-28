import java.util.ArrayList;

public class Zoo {

    private ArrayList<Animal> animals;

    public Zoo() {
        animals = new ArrayList<>();
    }

    public Zoo(ArrayList<Animal> animals) {
        this.animals = new ArrayList<>(animals);
    }

    public Zoo(Zoo other) {
        this(other.animals);
    }

    public int countAnimals(String type) {
        int count = 0;
        for (Animal a : animals)
            if (a.getType().equals(type))
                count++;
        return count;
    }

    public void updateValidTypes() {

        ArrayList<String> remove = new ArrayList<>();

        for (String type : Animal.validTypes) {
            if (countAnimals(type) == 0)
                remove.add(type);
        }
        Animal.validTypes.removeAll(remove);
    }

    public boolean isGenderBalanced(String type) {

        int male = 0, female = 0;

        for (Animal a : animals)
            if (a.getType().equals(type)) {
                if (a.getGender().equalsIgnoreCase("Male")) male++;
                else female++;
            }

        int total = male + female;
        if (total == 0) return true;

        return Math.abs(male - female) < 0.2 * total;
    }

    public void removeOldest(String type) {

        if (!Animal.validTypes.contains(type)) return;

        int maxAge = -1;

        for (Animal a : animals)
            if (a.getType().equals(type))
                maxAge = Math.max(maxAge, a.getAge());

        int finalMaxAge = maxAge;
        animals.removeIf(a ->
                a.getType().equals(type) &&
                        a.getAge() == finalMaxAge);

        updateValidTypes();
    }

    @Override
    public String toString() {
        return "Total Animals: " + animals.size();
    }
}
