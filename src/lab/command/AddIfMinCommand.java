package lab.command;

import lab.collection.CollectionManager;
import lab.io.InputManager;
import lab.model.Vehicle;

/**
 * Команда {@code add_if_min {element}}.
 * Добавляет новый элемент в коллекцию, только если он меньше (по естественному порядку)
 * наименьшего элемента текущей коллекции.
 *
 * @author Max
 */
public class AddIfMinCommand implements Command {
    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager      объект для ввода данных
     */
    public AddIfMinCommand(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    /**
     * Выполняет команду:
     * <ol>
     *   <li>Запрашивает у пользователя значения полей через {@link InputManager}.</li>
     *   <li>Если коллекция пуста – добавляет элемент.</li>
     *   <li>Иначе получает минимальный элемент через {@link CollectionManager#getMin()}.</li>
     *   <li>Сравнивает новый элемент с минимальным (используя {@link Comparable#compareTo}).</li>
     *   <li>Если новый элемент меньше – добавляет его, иначе выводит сообщение.</li>
     * </ol>
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        Vehicle vehicle = inputManager.readVehicle(false, null);
        if (collectionManager.size() == 0) {
            collectionManager.add(vehicle);
            System.out.println("Элемент добавлен (коллекция была пуста).");
            return;
        }
        Vehicle min = collectionManager.getMin();
        if (vehicle.compareTo(min) < 0) {
            collectionManager.add(vehicle);
            System.out.println("Элемент добавлен как минимальный.");
        } else {
            System.out.println("Элемент не добавлен (не является минимальным).");
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка "добавить элемент, если он меньше наименьшего"
     */
    @Override
    public String getDescription() {
        return "добавить элемент, если он меньше наименьшего";
    }
}
