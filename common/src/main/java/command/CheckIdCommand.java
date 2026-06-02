package command;


import connection.CollectionManager;
import connection.Response;

public class CheckIdCommand implements ServerCommand {
    private static final long serialVersionUID = 100L;
    private transient CollectionManager collectionManager;
    private Long id;

    public CheckIdCommand(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return "check_id";
    }

    @Override
    public String getDescription() {
        return "check_id: проверяет id на сервере.";
    }

    @Override
    public Response execute(CollectionManager collectionManager){
        boolean check = collectionManager.getById(id) != null;
        return new Response(new String[]{String.valueOf(check)});
    }
}
