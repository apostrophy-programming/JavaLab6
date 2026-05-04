package lab.command;

import lab.collection.CollectionManager;

/**
 * Команда {@code clear}.
 * Очищает коллекцию, удаляя все элементы.
 *
 * @author Max
 */
public class ClearCommand implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции
     */
    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду: вызывает метод {@link CollectionManager#clear()}.
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        collectionManager.clear();
        System.out.println("Коллекция очищена.");
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка "очистить коллекцию"
     */
    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}
