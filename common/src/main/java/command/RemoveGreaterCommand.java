package command;


import connection.CollectionManager;
import connection.Response;
import model.Vehicle;

import java.util.List;

public class RemoveGreaterCommand implements ServerCommand {
    private static final long serialVersionUID = 1L;
    private Vehicle vehicle;

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        List<Vehicle> toRemove = collectionManager.getGreaterThan(vehicle);
        if (toRemove.isEmpty()) {
            return new Response(new String[]{"Нет элементов, превышающих заданный."});
        }
        for (Vehicle v : toRemove) {
            collectionManager.removeById(v.getId());
        }
        return new Response(new String[]{"Удалено элементов: " + toRemove.size()});
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}