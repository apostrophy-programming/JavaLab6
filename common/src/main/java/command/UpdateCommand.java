package command;


import connection.CollectionManager;
import connection.Response;
import model.Vehicle;

public class UpdateCommand implements ServerCommand {
    private static final long serialVersionUID = 1L;
    private Vehicle vehicle;
    private Long id;

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        collectionManager.update(id,vehicle);
        if (vehicle!=null) {
            return new Response(new String[]{"Обновлён элемент: " + vehicle.getName()});
        }
        return null;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setId(Long id) {
        this.id = id;
    }
}