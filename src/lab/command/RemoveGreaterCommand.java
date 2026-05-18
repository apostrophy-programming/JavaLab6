package lab.command;

import lab.client.Client;
import lab.collection.CollectionManager;
import lab.model.Vehicle;

import java.io.IOException;
import java.util.List;

public class RemoveGreaterCommand implements Command {
    private static final long serialVersionUID = 1L;
    private transient Client client;
    private CollectionManager collectionManager;
    private Vehicle vehicle;

    public RemoveGreaterCommand(Client client) {
        this.client = client;
    }

    @Override
    public String getDescription() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }

    @Override
    public String[] execute(String[] args) throws IOException, ClassNotFoundException {
        if (client!=null) {
            vehicle = client.getInputManager().readVehicle(false, null);
            client.sendCommand(this);
            return client.receiveResponse().getText();
        }
        if (collectionManager!=null) {
            List<Vehicle> toRemove = collectionManager.getGreaterThan(vehicle);
            if (toRemove.isEmpty()) {
                return new String[]{"Нет элементов, превышающих заданный."};
            }
            for (Vehicle v : toRemove) {
                collectionManager.removeById(v.getId());
            }
            return new String[]{"Удалено элементов: " + toRemove.size()};
        }
        return new String[0];
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}