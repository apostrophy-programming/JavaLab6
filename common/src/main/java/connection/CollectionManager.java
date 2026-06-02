package connection;

import model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.PriorityQueue;

public interface CollectionManager {

    void setCollection(PriorityQueue<Vehicle> collection);
    PriorityQueue<Vehicle> getCollection();
    LocalDate getInitializationDate();
    String getType();
    int size();
    void add(Vehicle vehicle);
    void update(Long id, Vehicle newVehicle);
    boolean removeById(Long id);
    void clear();
    Vehicle getMin();
    void removeFirst();
    List<Vehicle> getGreaterThan(Vehicle vehicle);
    int countByFuelType(FuelType fuelType);
    int countGreaterThanCapacity(float capacity);
    List<Vehicle> filterContainsName(String substring);
    Vehicle getById(Long id);
    List<Vehicle> getSortedList();

}