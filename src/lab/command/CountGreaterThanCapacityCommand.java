package lab.command;

import lab.collection.CollectionManager;

/**
 * Команда {@code count_greater_than_capacity capacity}.
 * Выводит количество элементов коллекции, у которых значение поля capacity больше заданного.
 *
 * @author Max123
 */
public class CountGreaterThanCapacityCommand implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции
     */
    public CountGreaterThanCapacityCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду:
     * <ol>
     *   <li>Проверяет, что передан ровно один аргумент – значение capacity.</li>
     *   <li>Преобразует аргумент в число с плавающей точкой.</li>
     *   <li>Подсчитывает количество элементов с большим capacity через {@link CollectionManager#countGreaterThanCapacity(float)}.</li>
     *   <li>Выводит результат.</li>
     * </ol>
     *
     * @param args массив аргументов (ожидается args[0] = capacity)
     * @throws IllegalArgumentException если аргумент не передан или не является числом
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("использование: count_greater_than_capacity capacity");
        }
        float capacity;
        try {
            capacity = Float.parseFloat(args[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("capacity должно быть числом");
        }
        int count = collectionManager.countGreaterThanCapacity(capacity);
        System.out.println("Количество элементов с capacity > " + capacity + ": " + count);
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка "вывести количество элементов с capacity больше заданного"
     */
    @Override
    public String getDescription() {
        return "вывести количество элементов с capacity больше заданного";
    }
}