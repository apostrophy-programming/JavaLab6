package lab.command;

import lab.client.Client;
import lab.collection.CollectionManager;
import lab.common.VehicleData;
import lab.client.InputManager;
import lab.model.Vehicle;

import java.io.IOException;
import java.text.CollationElementIterator;

public class AddCommand implements Command {
    private static final long serialVersionUID = 1L;
    private transient CollectionManager collectionManager;
    private transient Client client;
    private Vehicle vehicle;

    public AddCommand(Client client) {
        this.client = client;
    }

    @Override
    public String getDescription() {
        return "add {element} : добавить новый элемент в коллекцию";
    }

    @Override
    public String[] execute(String[] args) throws IOException, ClassNotFoundException {
        if (client!=null) {
            vehicle = client.getInputManager().readVehicle(false, null);
            client.sendCommand(this);
            return client.receiveResponse().getText();
        }
        if (collectionManager!=null) {
            collectionManager.add(vehicle);
            return new String[]{"Добавлен элемент: " + vehicle.toString()};
        }
        return new String[0];
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager=collectionManager;
    }


}