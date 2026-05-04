package lab.collection;

import lab.model.Vehicle;
import lab.model.FuelType;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Управляет коллекцией элементов типа {@link Vehicle}, хранящихся в {@link PriorityQueue}.
 * Обеспечивает основные операции добавления, удаления, обновления и поиска,
 * а также генерирует уникальные идентификаторы и дату создания.
 *
 * @author Max
 */
public class CollectionManager {
    private PriorityQueue<Vehicle> collection;
    private final LocalDate initializationDate;

    /**
     * Конструктор, инициализирует пустую коллекцию и фиксирует дату инициализации.
     */
    public CollectionManager() {
        this.collection = new PriorityQueue<>();
        this.initializationDate = LocalDate.now();
    }

    /**
     * Устанавливает коллекцию (используется при загрузке из файла).
     *
     * @param collection новая коллекция
     */
    public void setCollection(PriorityQueue<Vehicle> collection) {
        this.collection = collection;
    }

    /**
     * Возвращает текущую коллекцию.
     *
     * @return коллекция
     */
    public PriorityQueue<Vehicle> getCollection() {
        return collection;
    }

    /**
     * Возвращает дату инициализации менеджера коллекции.
     *
     * @return дата создания объекта
     */
    public LocalDate getInitializationDate() {
        return initializationDate;
    }

    /**
     * Возвращает имя класса коллекции.
     *
     * @return строка с именем класса
     */
    public String getType() {
        return collection.getClass().getName();
    }

    /**
     * Возвращает количество элементов в коллекции.
     *
     * @return размер коллекции
     */
    public int size() {
        return collection.size();
    }

    /**
     * Добавляет элемент в коллекцию. Генерирует уникальный id и устанавливает текущую дату.
     *
     * @param vehicle добавляемый объект (поля id и creationDate будут перезаписаны)
     */
    public void add(Vehicle vehicle) {
        long newId = generateId();
        vehicle.setId(newId);
        vehicle.setCreationDate(LocalDate.now());
        collection.add(vehicle);
    }

    /**
     * Генерирует новый уникальный id на основе максимального существующего id.
     *
     * @return новый id (maxId + 1)
     */
    private long generateId() {
        long maxId = collection.stream().mapToLong(Vehicle::getId).max().orElse(0);
        return maxId + 1;
    }

    /**
     * Обновляет элемент с указанным id.
     * Удаляет старый элемент и добавляет новый с сохранением id и даты.
     *
     * @param id          идентификатор обновляемого элемента
     * @param newVehicle  новый объект (id и creationDate будут заменены на старые)
     */
    public void update(Long id, Vehicle newVehicle) {
        collection.removeIf( v -> v.getId().equals(id));
        newVehicle.setId(id);
        collection.add(newVehicle);
    }

    /**
     * Удаляет элемент по id.
     *
     * @param id идентификатор элемента
     * @return true если элемент был удалён, false если элемент не найден
     */
    public boolean removeById(Long id) {
        return collection.removeIf(v -> v.getId().equals(id));
    }

    /**
     * Очищает коллекцию.
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Возвращает наименьший элемент коллекции (согласно естественному порядку).
     *
     * @return наименьший элемент или null, если коллекция пуста
     */
    public Vehicle getMin() {
        return collection.peek();
    }

    /**
     * Удаляет первый (наименьший) элемент коллекции.
     */
    public void removeFirst() {
        collection.poll();
    }

    /**
     * Возвращает список элементов, которые больше заданного (сравнение по id).
     *
     * @param vehicle эталонный объект
     * @return список элементов, превышающих эталон
     */
    public List<Vehicle> getGreaterThan(Vehicle vehicle) {
        return collection.stream().filter(v -> v.compareTo(vehicle) > 0).collect(Collectors.toList());
    }

    /**
     * Подсчитывает количество элементов с заданным типом топлива.
     *
     * @param fuelType тип топлива
     * @return количество элементов
     */
    public int countByFuelType(FuelType fuelType) {
        return (int) collection.stream().filter(v -> fuelType.equals(v.getFuelType())).count();
    }

    /**
     * Подсчитывает количество элементов, у которых capacity больше заданного значения.
     *
     * @param capacity пороговое значение
     * @return количество элементов
     */
    public int countGreaterThanCapacity(float capacity) {
        return (int) collection.stream().filter(v -> v.getCapacity() > capacity).count();
    }

    /**
     * Возвращает список элементов, у которых имя содержит заданную подстроку.
     *
     * @param substring подстрока для поиска
     * @return список отфильтрованных элементов
     */
    public List<Vehicle> filterContainsName(String substring) {
        return collection.stream().filter(v -> v.getName().contains(substring)).collect(Collectors.toList());
    }

    /**
     * Находит элемент по его id.
     *
     * @param id идентификатор
     * @return объект Vehicle или null, если не найден
     */
    public Vehicle getById(Long id) {
        return collection.stream().filter(v -> v.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Возвращает отсортированный список всех элементов коллекции.
     *
     * @return список элементов в порядке возрастания id
     */
    public List<Vehicle> getSortedList() {
        return collection.stream().sorted().collect(Collectors.toList());
    }


}
