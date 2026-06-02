package client;

import connection.CollectionManager;
import model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.PriorityQueue;

public class ProxyCollectionManager implements CollectionManager {
    @Override
    public void setCollection(PriorityQueue<Vehicle> collection) {

    }

    @Override
    public PriorityQueue<Vehicle> getCollection() {
        return null;
    }

    @Override
    public LocalDate getInitializationDate() {
        return null;
    }

    @Override
    public String getType() {
        return "";
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void add(Vehicle vehicle) {

    }

    @Override
    public void update(Long id, Vehicle newVehicle) {

    }

    @Override
    public boolean removeById(Long id) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Vehicle getMin() {
        return null;
    }

    @Override
    public void removeFirst() {

    }

    @Override
    public List<Vehicle> getGreaterThan(Vehicle vehicle) {
        return List.of();
    }

    @Override
    public int countByFuelType(FuelType fuelType) {
        return 0;
    }

    @Override
    public int countGreaterThanCapacity(float capacity) {
        return 0;
    }

    @Override
    public List<Vehicle> filterContainsName(String substring) {
        return List.of();
    }

    @Override
    public Vehicle getById(Long id) {
        return null;
    }

    @Override
    public List<Vehicle> getSortedList() {
        return List.of();
    }
}
