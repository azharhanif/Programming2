import java.util.ArrayList;
import java.util.Objects;

public class Animal {

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

    public Animal() {}

    public Animal(String name, String gender, int age, String type) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        setType(type);
    }

    public Animal(Animal other) {
        this(other.name, other.gender, other.age, other.type);
    }

    public boolean isTypeValid(String type) {
        return validTypes.contains(type);
    }

    public void setType(String type) {
        if (!isTypeValid(type))
            throw new IllegalArgumentException("Invalid type");
        this.type = type;
    }

    public String getType() { return type; }
    public String getGender() { return gender; }
    public int getAge() { return age; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Animal)) return false;
        Animal a = (Animal) o;
        return age == a.age &&
                Objects.equals(name, a.name) &&
                Objects.equals(gender, a.gender) &&
                Objects.equals(type, a.type);
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nGender: " + gender +
                "\nAge: " + age +
                "\nType: " + type;
    }
}
