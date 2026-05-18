package lab.command;

import lab.client.Client;
import lab.collection.CollectionManager;

import java.io.IOException;

public class RemoveFirstCommand implements Command {
    private static final long serialVersionUID = 1L;
    private transient Client client;
    private CollectionManager collectionManager;

    public RemoveFirstCommand(Client client) {
        this.client = client;
    }

    @Override
    public String getDescription() {
        return "remove_first : удалить первый элемент из коллекции";
    }

    @Override
    public String[] execute(String[] args) throws IOException, ClassNotFoundException {
        if (client!=null) {
            client.sendCommand(this);
            return client.receiveResponse().getText();
        }
        if (collectionManager!=null) {
            if (collectionManager.size() > 0) {
                collectionManager.removeFirst();
                return new String[]{"Первый элемент удалён."};
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