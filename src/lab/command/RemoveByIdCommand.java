package lab.command;

import lab.collection.CollectionManager;

/**
 * Команда {@code remove_by_id id}.
 * Удаляет элемент коллекции по заданному идентификатору.
 *
 * @author Max
 */
public class RemoveByIdCommand implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции
     */
    public RemoveByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду:
     * <ol>
     *   <li>Проверяет, что передан ровно один аргумент – id.</li>
     *   <li>Удаляет элемент с указанным id из коллекции.</li>
     *   <li>Выводит результат (успешно или элемент не найден).</li>
     * </ol>
     *
     * @param args массив аргументов (ожидается args[0] = id)
     * @throws IllegalArgumentException если аргумент не передан или id не является числом
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("использование: remove_by_id id");
        }
        long id;
        try {
            id = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("id должен быть числом");
        }
        boolean removed = collectionManager.removeById(id);
        if (removed) {
            System.out.println("Элемент с id " + id + " удалён.");
        } else {
            System.out.println("Элемент с id " + id + " не найден.");
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка "удалить элемент по id"
     */
    @Override
    public String getDescription() {
        return "удалить элемент по id";
    }
}
