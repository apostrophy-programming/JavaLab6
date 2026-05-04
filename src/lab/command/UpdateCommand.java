package lab.command;

import lab.collection.CollectionManager;
import lab.io.InputManager;
import lab.model.Vehicle;

/**
 * Команда {@code update id}.
 * Обновляет элемент коллекции с заданным идентификатором.
 * Пользователь вводит новые значения полей (кроме id и creationDate, которые копируются из старого элемента).
 *
 * @author Max
 */
public class UpdateCommand implements Command {
    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager      объект для ввода данных
     */
    public UpdateCommand(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    /**
     * Выполняет команду:
     * <ol>
     *   <li>Проверяет, что передан ровно один аргумент – id.</li>
     *   <li>Ищет элемент с таким id в коллекции. Если не найден – выводит сообщение.</li>
     *   <li>Запрашивает у пользователя новые значения полей через {@link InputManager}.</li>
     *   <li>Сохраняет старые id и creationDate в новом объекте.</li>
     *   <li>Заменяет старый элемент на новый в коллекции.</li>
     * </ol>
     *
     * @param args массив аргументов (ожидается args[0] = id)
     * @throws IllegalArgumentException если аргумент не передан или id не является числом
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("использование: update id");
        }
        long id;
        try {
            id = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("id должен быть числом");
        }
        Vehicle existing = collectionManager.getById(id);
        if (existing == null) {
            System.out.println("Элемент с id " + id + " не найден.");
            return;
        }
        Vehicle updated = inputManager.readVehicle(true, id);
        updated.setId(id); // сохраняем старый id
        updated.setCreationDate(existing.getCreationDate()); // сохраняем старую дату
        collectionManager.update(id, updated);
        System.out.println("Элемент с id " + id + " обновлён.");
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка "обновить элемент по id (ввод полей построчно)"
     */
    @Override
    public String getDescription() {
        return "обновить элемент по id (ввод полей построчно)";
    }
}