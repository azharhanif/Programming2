package app;

public class CarUtils {

    public static Car[] getCarsByColor(Car[] cars, String color) {
        int count = 0;

        for (Car car : cars) {
            if (car.getColor().equalsIgnoreCase(color)) {
                count++;
            }
        }

        Car[] result = new Car[count];
        int index = 0;

        for (Car car : cars) {
            if (car.getColor().equalsIgnoreCase(color)) {
                result[index++] = car;
            }
        }

        return result;
    }
}

