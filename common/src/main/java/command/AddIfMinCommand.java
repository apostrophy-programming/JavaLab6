package command;


import connection.CollectionManager;
import connection.Response;
import model.Vehicle;

public class AddIfMinCommand implements ServerCommand {
    private static final long serialVersionUID = 1L;
    private Vehicle vehicle;

    @Override
    public String getName() {
        return "add_if_min";
    }

    @Override
    public String getDescription() {
        return "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        if (collectionManager.size()==0) {
            collectionManager.add(vehicle);
            return new Response(new String[]{"Коллекция была пуста. Добавлен элемент"});
        }
        else {
            Vehicle min = collectionManager.getMin();
            if (vehicle.compareTo(min)<0) {
                collectionManager.add(vehicle);
                return new Response(new String[]{"Элемент добавлен как минимальный."});
            }
            else return new Response(new String[]{"Элемент не добавлен, так как не является минимальным."});
        }
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}