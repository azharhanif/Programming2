
public class Vehicle {
    private String plate;
    private int mileage;

    public Vehicle(String plate, int mileage) {
        this.plate = plate;
        this.mileage = mileage;
    }

    public String getPlate() {
        return plate;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
}
