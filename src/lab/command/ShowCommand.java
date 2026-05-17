package lab.command;

import lab.client.Client;
import lab.collection.CollectionManager;
import lab.model.Vehicle;

import java.io.IOException;
import java.util.List;

public class ShowCommand implements Command {
    private static final long serialVersionUID = 1L;
    private transient Client client;
    private CollectionManager collectionManager;

    public ShowCommand(Client client) {
        this.client = client;
    }

    @Override
    public String getDescription() {
        return "show : вывести все элементы коллекции в строковом представлении";
    }

    @Override
    public String[] execute(String[] args) throws IOException, ClassNotFoundException {
        if (client!=null) {
            client.sendCommand(this);
            return client.receiveResponse().getText();
        }
        if (collectionManager!=null) {
            List<Vehicle> sorted = collectionManager.getSortedList();
            if (sorted.isEmpty()) {
                return new String[]{"Коллекция пуста."};
            } else {
                return sorted.stream().map(Vehicle::toString).toArray(String[]::new);
            }
        }
        return new String[0];
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}