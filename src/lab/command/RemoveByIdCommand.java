package lab.command;

import lab.client.Client;
import lab.collection.CollectionManager;
import lab.common.Response;
import lab.model.Vehicle;

import java.io.IOException;

public class RemoveByIdCommand implements Command{
    private static final long serialVersionUID = 1L;
    private transient Client client;
    private transient CollectionManager collectionManager;
    private Long id;

    public RemoveByIdCommand(Client client){
        this.client = client;
    }
    @Override
    public String getDescription() {
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }

    @Override
    public String[] execute(String[] args) {
        if (client!=null) {
            if (args.length != 1) {
                throw new IllegalArgumentException("использование: remove_by_id id");
            }
            long id;
            try {
                id = Long.parseLong(args[0]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("id должен быть целым числом");
            }
            try {
                client.sendCommand(new CheckIdCommand(id));
                try {
                    Response response = client.receiveResponse();
                    if (Boolean.parseBoolean(response.getText()[0])) {
                        this.id = id;
                        client.sendCommand(this);
                    }
                    else {
                        System.out.println("В коллекции нет элемента с id: " + id);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                System.err.println("Ошибка проверки Id <- RemoveByIdCommand :");
            }
            return new String[0];
        }
        if (collectionManager!=null) {
            collectionManager.removeById(id);
            return new String[]{"Элемент с id " + id + " удалён."};
        }
        return new String[0];
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }


}
