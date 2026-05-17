package lab.command;

import lab.client.Client;
import lab.collection.CollectionManager;
import lab.model.Vehicle;

import java.io.IOException;
import java.util.List;

public class InfoCommand implements Command {
    private static final long serialVersionUID = 1L;
    private transient Client client;
    private CollectionManager collectionManager;

    public InfoCommand(Client client) {
        this.client = client;
    }

    @Override
    public String getDescription() {
        return "info : вывести в стандартный поток вывода информацию о коллекции";
    }

    @Override
    public String[] execute(String[] args) throws IOException, ClassNotFoundException {
        if (client!=null) {
            client.sendCommand(this);
            return client.receiveResponse().getText();
        }
        if (collectionManager!=null) {
            return new String[]{
                    "Тип коллекции: " + collectionManager.getType(),
                    "Дата инициализации: " + collectionManager.getInitializationDate(),
                    "Количество элементов: " + collectionManager.size()
            };
        }
        return new String[0];
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

}