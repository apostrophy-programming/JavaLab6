package command;


import connection.CollectionManager;
import connection.Response;
import model.Vehicle;

import java.util.List;

public class ShowCommand implements ServerCommand {
    private static final long serialVersionUID = 1L;

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "show : вывести все элементы коллекции в строковом представлении";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        List<Vehicle> sorted = collectionManager.getSortedList();
        if (sorted.isEmpty()) {
            return new Response(new String[]{"Коллекция пуста."});
        } else {
            return new Response(sorted.stream().map(Vehicle::toString).toArray(String[]::new));
        }
    }

}