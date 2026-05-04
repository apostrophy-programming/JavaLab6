package lab.command;

import lab.collection.CollectionManager;

/**
 * Команда {@code remove_first}.
 * Удаляет первый (наименьший) элемент коллекции согласно естественному порядку.
 *
 * @author Max
 */
public class RemoveFirstCommand implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции
     */
    public RemoveFirstCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду:
     * <ol>
     *   <li>Проверяет, не пуста ли коллекция.</li>
     *   <li>Удаляет первый элемент через {@link CollectionManager#removeFirst()}.</li>
     *   <li>Выводит сообщение об успешном удалении или о пустоте коллекции.</li>
     * </ol>
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        if (collectionManager.size() == 0) {
            System.out.println("Коллекция пуста, нечего удалять.");
            return;
        }
        collectionManager.removeFirst();
        System.out.println("Первый элемент удалён.");
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка "удалить первый элемент из коллекции"
     */
    @Override
    public String getDescription() {
        return "удалить первый элемент из коллекции";
    }
}