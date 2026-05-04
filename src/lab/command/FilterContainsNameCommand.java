package lab.command;

import lab.collection.CollectionManager;
import lab.model.Vehicle;

import java.util.List;

/**
 * Команда {@code filter_contains_name name}.
 * Выводит элементы коллекции, у которых поле name содержит заданную подстроку.
 *
 * @author Max
 */
public class FilterContainsNameCommand implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции
     */
    public FilterContainsNameCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду:
     * <ol>
     *   <li>Проверяет, что передан ровно один аргумент – подстрока для поиска.</li>
     *   <li>Получает список элементов, содержащих подстроку, через {@link CollectionManager#filterContainsName(String)}.</li>
     *   <li>Выводит найденные элементы (каждый на отдельной строке) или сообщение об отсутствии.</li>
     * </ol>
     *
     * @param args массив аргументов (ожидается args[0] = name)
     * @throws IllegalArgumentException если аргумент не передан
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("использование: filter_contains_name name");
        }
        String substring = args[0];
        List<Vehicle> result = collectionManager.filterContainsName(substring);
        if (result.isEmpty()) {
            System.out.println("Элементы, содержащие подстроку \"" + substring + "\", не найдены.");
        } else {
            result.forEach(System.out::println);
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка "вывести элементы, name которых содержит заданную подстроку"
     */
    @Override
    public String getDescription() {
        return "вывести элементы, name которых содержит заданную подстроку";
    }
}