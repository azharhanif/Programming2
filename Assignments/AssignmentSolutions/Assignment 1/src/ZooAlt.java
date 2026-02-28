import java.util.ArrayList;

public class ZooAlt {

    private ArrayList<AnimalAlt> animalsAlt;

    public ZooAlt() {
        animalsAlt = new ArrayList<>();
    }

    public ZooAlt(ArrayList<AnimalAlt> animalsAlt) {
        this.animalsAlt = new ArrayList<>(animalsAlt);
    }

    public ZooAlt(ZooAlt other) {
        this(other.animalsAlt);
    }

    public ArrayList<AnimalAlt> getAnimals() {
        return animalsAlt;
    }

    public int countAnimals(String type) {

        int count = 0;
        for (AnimalAlt a : animalsAlt)
            if (a.getType().equals(type))
                count++;

        return count;
    }

    private int[] genderCount(String type) {

        int male = 0, female = 0;

        for (AnimalAlt a : animalsAlt) {
            if (!a.getType().equals(type)) continue;

            if (a.getGender().equalsIgnoreCase("Male"))
                male++;
            else
                female++;
        }
        return new int[]{male, female};
    }

    public boolean isGenderBalanced(String type) {

        int[] g = genderCount(type);
        int total = g[0] + g[1];

        if (total == 0) return true;

        return Math.abs(g[0] - g[1]) < total * 0.2;
    }

    public void updateValidTypes() {

        ArrayList<String> remove = new ArrayList<>();

        for (String type : Animal.validTypes)
            if (countAnimals(type) == 0)
                remove.add(type);

        Animal.validTypes.removeAll(remove);
    }

    public void removeOldest(String type) {

        if (!Animal.validTypes.contains(type))
            return;

        int maxAge = -1;

        for (AnimalAlt a : animalsAlt)
            if (a.getType().equals(type))
                maxAge = Math.max(maxAge, a.getAge());

        int finalMax = maxAge;

        animalsAlt.removeIf(a ->
                a.getType().equals(type)
                        && a.getAge() == finalMax);

        updateValidTypes();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ZooAlt)) return false;
        ZooAlt z = (ZooAlt) o;
        return animalsAlt.equals(z.animalsAlt);
    }

    @Override
    public String toString() {
        return "Zoo size: " + animalsAlt.size();
    }
}
