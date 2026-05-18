package lab.command;

import lab.client.Client;
import lab.collection.CollectionManager;
import lab.model.FuelType;
import lab.model.Vehicle;

import java.io.IOException;
import java.util.List;

public class CountByFuelTypeCommand implements Command {
    private static final long serialVersionUID = 1L;
    private transient Client client;
    private CollectionManager collectionManager;
    private FuelType fuelType;

    public CountByFuelTypeCommand(Client client) {
        this.client = client;
    }

    @Override
    public String getDescription() {
        return "count_by_fuel_type fuelType : вывести количество элементов, значение поля fuelType которых равно заданному";
    }

    @Override
    public String[] execute(String[] args) throws IOException, ClassNotFoundException, IllegalArgumentException {
        if (client!=null) {
            if (args.length != 1) {
                throw new IllegalArgumentException("использование: count_by_fuel_type fuelType");
            }
            FuelType fuelType;
            try {
                fuelType = FuelType.valueOf(args[0].toUpperCase());
            } catch (IllegalArgumentException  e) {
                throw new IllegalArgumentException("Нет такого типа топлива. Допустимы: GASOLINE, MANPOWER, PLASMA, ANTIMATTER");
            }
            this.fuelType = fuelType;
            client.sendCommand(this);
            return client.receiveResponse().getText();
        }
        if (collectionManager!=null) {
            int count = collectionManager.countByFuelType(fuelType);
            return new String[]{"Количество элементов с fuelType " + fuelType + ": " + count};
        }
        return new String[0];
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}