package lab.common;

import lab.model.*;

import java.io.Serializable;

/**
 * Контейнер полей Vehicle, вводимых пользователем (без id и creationDate).
 */
public class VehicleData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Coordinates coordinates;
    private Integer enginePower;
    private float capacity;
    private VehicleType type;
    private FuelType fuelType;

    public VehicleData(String name, Coordinates coordinates,
                       Integer enginePower, float capacity,
                       VehicleType type, FuelType fuelType) {
        this.name = name;
        this.coordinates = coordinates;
        this.enginePower = enginePower;
        this.capacity = capacity;
        this.type = type;
        this.fuelType = fuelType;
    }

    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public Integer getEnginePower() { return enginePower; }
    public float getCapacity() { return capacity; }
    public VehicleType getType() { return type; }
    public FuelType getFuelType() { return fuelType; }

    @Override
    public String toString() {
        return ("VehicleData: "+ "name: " + name + ", coordinates: " + coordinates + ", enginePower: " + enginePower
        + ", capacity: " + capacity + ", type: " + type + ", fuelType: " + fuelType);
    }
}
