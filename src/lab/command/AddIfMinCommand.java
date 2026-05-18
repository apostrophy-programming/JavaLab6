package lab.command;

import lab.client.Client;
import lab.collection.CollectionManager;
import lab.common.Response;
import lab.model.Vehicle;

import java.io.IOException;

public class AddIfMinCommand implements Command{
    private static final long serialVersionUID = 1L;
    private transient Client client;
    private transient CollectionManager collectionManager;
    private Vehicle vehicle;

    public AddIfMinCommand(Client client){
        this.client = client;
    }
    @Override
    public String getDescription() {
        return "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    @Override
    public String[] execute(String[] args) throws IOException, ClassNotFoundException {
        if (client!=null) {
            vehicle = client.getInputManager().readVehicle(false, null);
            client.sendCommand(this);
            return client.receiveResponse().getText();
        }
        if (collectionManager.size()==0) {
            collectionManager.add(vehicle);
            return new String[]{"Коллекция была пуста. Добавлен элемент"};
        }
        else {
            Vehicle min = collectionManager.getMin();
            if (vehicle.compareTo(min)<0) {
                collectionManager.add(vehicle);
                return new String[]{"Элемент добавлен как минимальный."};
            }
            else return new String[]{"Элемент не добавлен, так как не является минимальным."};
        }
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }


}
