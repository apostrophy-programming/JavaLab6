package lab.command;

import lab.collection.CollectionManager;
import lab.model.Vehicle;

import java.util.List;

/**
 * Команда {@code show}.
 * Выводит все элементы коллекции в строковом представлении.
 *
 */
public class ShowCommand implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции, содержащий элементы
     */
    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду: выводит каждый элемент коллекции на отдельной строке.
     * Если коллекция пуста, выводит соответствующее сообщение.
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        List<Vehicle> sorted = collectionManager.getSortedList();
        if (sorted.isEmpty()) {
            System.out.println("Коллекция пуста.");
        } else {
            sorted.forEach(System.out::println);
        }
    }
    /**
     * Возвращает описание команды.
     *
     * @return строка "вывести все элементы коллекции"
     */
    @Override
    public String getDescription() {
        return "вывести все элементы коллекции";
    }
}