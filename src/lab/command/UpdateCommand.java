package lab.command;

import lab.client.Client;
import lab.collection.CollectionManager;
import lab.common.Response;
import lab.model.Vehicle;

import java.io.FilterOutputStream;
import java.io.IOException;

public class UpdateCommand implements Command{
    private static final long serialVersionUID = 1L;
    private transient Client client;
    private transient CollectionManager collectionManager;
    private Vehicle vehicle;

    public UpdateCommand(Client client){
        this.client = client;
    }
    @Override
    public String getDescription() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String[] execute(String[] args) {
        if (client!=null) {
            if (args.length != 1) {
                throw new IllegalArgumentException("использование: update id");
            }
            long id;
            try {
                id = Long.parseLong(args[0]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("id должен быть числом");
            }
            try {
                client.sendCommand(new CheckIdCommand(id));
                try {
                    Response response = client.receiveResponse();
                    if (Boolean.parseBoolean(response.getText()[0])) {
                        vehicle = client.getInputManager().readVehicle(true, id);
                        vehicle.setId(id);
                        client.sendCommand(this);
                    }
                    else {
                        System.out.println("В коллекции нет элемента с id: " + id);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                System.err.println("Ошибка проверки Id <- UpdateCommand :");
            }
            return new String[0];
        }
        if (collectionManager!=null) {
            collectionManager.update(vehicle.getId(), vehicle);
            return new String[]{"Элемент с id " + vehicle.getId() + " обновлён."};
        }
        return new String[0];
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }


}
