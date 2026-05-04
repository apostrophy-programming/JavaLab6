package lab.command;

import lab.collection.CollectionManager;
import lab.io.InputManager;
import lab.model.Vehicle;

import java.util.List;

/**
 * Команда {@code remove_greater {element}}.
 * Удаляет из коллекции все элементы, которые превышают заданный (по естественному порядку).
 *
 * @author Max123
 */
public class RemoveGreaterCommand implements Command {
    private final CollectionManager collectionManager;
    private final InputManager inputManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager      объект для ввода данных
     */
    public RemoveGreaterCommand(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    /**
     * Выполняет команду:
     * <ol>
     *   <li>Запрашивает у пользователя значения полей через {@link InputManager}.</li>
     *   <li>Получает список элементов, больших заданного, через {@link CollectionManager#getGreaterThan(Vehicle)}.</li>
     *   <li>Удаляет каждый найденный элемент из коллекции по id.</li>
     *   <li>Выводит количество удалённых элементов.</li>
     * </ol>
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        Vehicle vehicle = inputManager.readVehicle(false, null);
        List<Vehicle> toRemove = collectionManager.getGreaterThan(vehicle);
        if (toRemove.isEmpty()) {
            System.out.println("Нет элементов, превышающих заданный.");
            return;
        }
        for (Vehicle v : toRemove) {
            collectionManager.removeById(v.getId());
        }
        System.out.println("Удалено элементов: " + toRemove.size());
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка "удалить все элементы, превышающие заданный"
     */
    @Override
    public String getDescription() {
        return "удалить все элементы, превышающие заданный";
    }
}