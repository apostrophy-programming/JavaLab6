package lab.command;

import lab.collection.CollectionManager;
import lab.io.InputManager;
import lab.model.Vehicle;

/**
 * Команда {@code add}.
 * Добавляет новый элемент в коллекцию. Поля вводятся построчно с помощью {@link InputManager}.
 * Идентификатор и дата создания генерируются автоматически.
 *
 * @author Max
 */
public class AddCommand implements Command {
    private final CollectionManager collectionManager;
    private final InputManager inputManager;
    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции
     * @param inputManager      объект для ввода данных
     */
    public AddCommand(CollectionManager collectionManager, InputManager inputManager) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
    }

    /**
     * Выполняет команду: запрашивает у пользователя значения полей через {@link InputManager},
     * создаёт объект {@link Vehicle}, добавляет его в коллекцию и выводит присвоенный id.
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        Vehicle vehicle = inputManager.readVehicle(false, null);
        collectionManager.add(vehicle);
        System.out.println("Элемент " + vehicle.getName() + " добавлен с id = " + vehicle.getId());
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка "добавить новый элемент (ввод полей построчно)"
     */
    @Override
    public String getDescription() {
        return "добавить новый элемент (ввод полей построчно)";
    }
}