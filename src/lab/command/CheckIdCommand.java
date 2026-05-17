package lab.command;

import lab.collection.CollectionManager;

public class CheckIdCommand implements Command{
    private static final long serialVersionUID = 1L;
    private transient CollectionManager collectionManager;
    private Long id;

    public CheckIdCommand(Long id) {
        this.id = id;
    }

    @Override
    public String getDescription() {
        return "check id: проверка id в коллекции";
    }

    @Override
    public String[] execute(String[] args) {
        boolean check = collectionManager.getById(id) != null;
        return new String[]{String.valueOf(check)};
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
}
