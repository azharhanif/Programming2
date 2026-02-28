import java.util.ArrayList;
import java.util.Objects;

public class AnimalAlt {

    private String name;
    private String gender;
    private int age;
    private String type;

    public static ArrayList<String> validTypes =
            new ArrayList<>();

    static {
        validTypes.add("Cat");
        validTypes.add("Dog");
        validTypes.add("Monkey");
    }

    public AnimalAlt() {}

    public AnimalAlt(String name, String gender,
                  int age, String type) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        setType(type);
    }

    public AnimalAlt(AnimalAlt other) {
        this(other.name, other.gender,
                other.age, other.type);
    }

    private void validateType(String type) {
        if (!validTypes.contains(type))
            throw new IllegalArgumentException(
                    "Invalid animal type: " + type);
    }

    public boolean isTypeValid(String type) {
        return validTypes.contains(type);
    }

    public void setType(String type) {
        validateType(type);
        this.type = type;
    }

    public String getType() { return type; }
    public String getGender() { return gender; }
    public int getAge() { return age; }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof AnimalAlt)) return false;
        AnimalAlt a = (AnimalAlt) o;

        return age == a.age &&
                Objects.equals(name, a.name) &&
                Objects.equals(gender, a.gender) &&
                Objects.equals(type, a.type);
    }

    @Override
    public String toString() {
        return name + " (" + type + ", " + age + ")";
    }
}
