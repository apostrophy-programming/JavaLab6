package command;


import connection.CollectionManager;
import connection.Response;

public class ClearCommand implements ServerCommand {
    private static final long serialVersionUID = 105L;

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        if (collectionManager.size() > 0) {
            collectionManager.clear();
            return new Response(new String[]{"Коллекция очищена."});
        }
        else {
            return new Response(new String[]{"Коллекция была пуста."});
        }
    }

}