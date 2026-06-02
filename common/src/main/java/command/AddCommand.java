package command;


import connection.CollectionManager;
import connection.Response;
import model.Vehicle;

public class AddCommand implements ServerCommand {
    private static final long serialVersionUID = 1L;
    private Vehicle vehicle;

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "add {element} : добавить новый элемент в коллекцию";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        collectionManager.add(vehicle);
        if (vehicle!=null) {
            return new Response(new String[]{"Добавлен элемент: " + vehicle.getName()});
        }
        return null;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}