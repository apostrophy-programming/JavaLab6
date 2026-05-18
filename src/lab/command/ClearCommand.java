package lab.command;

import lab.client.Client;
import lab.collection.CollectionManager;
import lab.model.Vehicle;

import java.io.IOException;
import java.util.List;

public class ClearCommand implements Command {
    private static final long serialVersionUID = 1L;
    private transient Client client;
    private CollectionManager collectionManager;

    public ClearCommand(Client client) {
        this.client = client;
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }

    @Override
    public String[] execute(String[] args) throws IOException, ClassNotFoundException {
        if (client!=null) {
            client.sendCommand(this);
            return client.receiveResponse().getText();
        }
        if (collectionManager!=null) {
            if (collectionManager.size() > 0) {
                collectionManager.clear();
                return new String[]{"Коллекция очищена."};
            }
            else {
                return new String[]{"Коллекция была пуста."};
            }
        }
        return new String[0];
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}