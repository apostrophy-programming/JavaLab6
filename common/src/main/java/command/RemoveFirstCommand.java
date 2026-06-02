package command;


import connection.CollectionManager;
import connection.Response;

public class RemoveFirstCommand implements ServerCommand {
    private static final long serialVersionUID = 1L;

    @Override
    public String getName() {
        return "remove_first";
    }

    @Override
    public String getDescription() {
        return "remove_first : удалить первый элемент из коллекции";
    }

    @Override
    public Response execute(CollectionManager collectionManager) {
        if (collectionManager!=null) {
            if (collectionManager.size() > 0) {
                collectionManager.removeFirst();
                return new Response(new String[]{"Первый элемент удалён."});
            }
            else {
                return new Response(new String[]{"Коллекция была пуста."});
            }
        }
        return null;
    }

}