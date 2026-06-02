package command;


import connection.CollectionManager;
import connection.Response;

public class CountGreaterThanCapacityCommand implements ServerCommand {
    private static final long serialVersionUID = 110L;
    private float capacity;

    @Override
    public String getName() {
        return "count_by_fuel_type";
    }

    @Override
    public String getDescription() {
        return "count_greater_than_capacity capacity : вывести количество элементов, значение поля capacity которых больше заданного";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        int count = collectionManager.countGreaterThanCapacity(capacity);
        return new Response(new String[]{"Количество элементов с capacity > " + capacity + ": " + count});
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }
}