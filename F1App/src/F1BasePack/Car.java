package F1BasePack;

import java.util.ArrayList;

public class Car {

    private static ArrayList<Car> currentList = new ArrayList();

    private String name;
    private int version;
    private double performance;
    private double reliability;

    public Car() {
    }

    public Car(String name, int version, double performance, double reliability) {
        this.name = name;
        this.version = version;
        this.performance = performance;
        this.reliability = reliability;
    }

    public static ArrayList<Car> getCurrentList() {
        return currentList;
    }

    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }

    public double getPerformance() {
        return performance;
    }

    public double getReliability() {
        return reliability;
    }

    @Override
    public String toString() {
        return name + version;
    }

    public String getInfo() {
        return "Car{" +
                "name='" + name + '\'' +
                ", version=" + version +
                ", performance=" + performance +
                ", reliability=" + reliability +
                '}';
    }

    public static void setCurrentList(ArrayList<Car> currentList) {
        Car.currentList = currentList;
    }

    public static Car parseLine(String car) {

        String[] values = car.split(",");
        return new Car(values[0],Integer.parseInt(values[1]),
                Double.parseDouble(values[2]),Double.parseDouble(values[3]));
    }
}
