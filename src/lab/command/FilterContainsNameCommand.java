package lab.command;

import lab.client.Client;
import lab.collection.CollectionManager;
import lab.model.Vehicle;

import java.io.IOException;
import java.util.List;

public class FilterContainsNameCommand implements Command {
    private static final long serialVersionUID = 1L;
    private transient Client client;
    private CollectionManager collectionManager;
    private String name;

    public FilterContainsNameCommand(Client client) {
        this.client = client;
    }

    @Override
    public String getDescription() {
        return "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку";
    }

    @Override
    public String[] execute(String[] args) throws IOException, ClassNotFoundException {
        if (client!=null) {
            if (args.length != 1) {
                throw new IllegalArgumentException("использование: filter_contains_name name");
            }
            name = args[0];
            client.sendCommand(this);
            return client.receiveResponse().getText();
        }
        if (collectionManager!=null) {
            List<Vehicle> filterList = collectionManager.filterContainsName(name);
            if (filterList.isEmpty()) {
                return new String[]{"Элементы, содержащие подстроку \"" + name + "\", не найдены."};
            } else {
                return filterList.stream().map(Vehicle::toString).toArray(String[]::new);
            }
        }
        return new String[0];
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}