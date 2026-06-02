package command;



import connection.CollectionManager;
import connection.Response;
import model.Vehicle;

import java.util.List;

public class FilterContainsNameCommand implements ServerCommand {
    private static final long serialVersionUID = 111L;
    private String substring;

    @Override
    public String getName() {
        return "count_by_fuel_type";
    }

    @Override
    public String getDescription() {
        return "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        List<Vehicle> filterList = collectionManager.filterContainsName(substring);
        if (filterList.isEmpty()) {
            return new Response(new String[]{"Элементы, содержащие подстроку \"" + substring + "\", не найдены."});
        } else {
            return new Response(filterList.stream().map(Vehicle::toString).toArray(String[]::new));
        }
    }

    public void setSubstring(String substring) {
        this.substring = substring;
    }
}