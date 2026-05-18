package lab.command;

import lab.client.Client;
import lab.collection.CollectionManager;
import lab.model.FuelType;

import java.io.IOException;

public class CountGreaterThanCapacityCommand implements Command {
    private static final long serialVersionUID = 1L;
    private transient Client client;
    private CollectionManager collectionManager;
    private float capacity;

    public CountGreaterThanCapacityCommand(Client client) {
        this.client = client;
    }

    @Override
    public String getDescription() {
        return "count_greater_than_capacity capacity : вывести количество элементов, значение поля capacity которых больше заданного";
    }

    @Override
    public String[] execute(String[] args) throws IOException, ClassNotFoundException, IllegalArgumentException {
        if (client!=null) {
            if (args.length != 1) {
                throw new IllegalArgumentException("использование: count_greater_than_capacity capacity");
            }
            float capacity;
            try {
                capacity = Float.parseFloat(args[0]);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("capacity должно быть числом c плавающей точкой");
            }
            this.capacity = capacity;
            client.sendCommand(this);
            return client.receiveResponse().getText();
        }
        if (collectionManager!=null) {
            int count = collectionManager.countGreaterThanCapacity(capacity);
            return new String[]{"Количество элементов с capacity > " + capacity + ": " + count};
        }
        return new String[0];
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}