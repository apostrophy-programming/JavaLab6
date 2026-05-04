package lab.command;

import lab.collection.CollectionManager;
import lab.model.FuelType;

/**
 * Команда {@code count_by_fuel_type fuelType}.
 * Выводит количество элементов коллекции, у которых поле fuelType равно заданному.
 *
 * @author Max
 */
public class CountByFuelTypeCommand implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции
     */
    public CountByFuelTypeCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду:
     * <ol>
     *   <li>Проверяет, что передан ровно один аргумент – название типа топлива.</li>
     *   <li>Преобразует аргумент в константу {@link FuelType}.</li>
     *   <li>Подсчитывает количество элементов с таким топливом через {@link CollectionManager#countByFuelType(FuelType)}.</li>
     *   <li>Выводит результат.</li>
     * </ol>
     *
     * @param args массив аргументов (ожидается args[0] = fuelType)
     * @throws IllegalArgumentException если аргумент не передан или не является допустимым значением FuelType
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("использование: count_by_fuel_type fuelType");
        }
        FuelType fuelType;
        try {
            fuelType = FuelType.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Нет такого типа топлива. Допустимы: GASOLINE, MANPOWER, PLASMA, ANTIMATTER");
        }
        int count = collectionManager.countByFuelType(fuelType);
        System.out.println("Количество элементов с fuelType " + fuelType + ": " + count);
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка "вывести количество элементов с заданным fuelType"
     */
    @Override
    public String getDescription() {
        return "вывести количество элементов с заданным fuelType";
    }
}
