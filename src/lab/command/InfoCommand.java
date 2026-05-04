package lab.command;

import lab.collection.CollectionManager;

/**
 * Команда {@code info} – выводит список всех доступных команд с их описанием.
 *
 * @see Command
 */
public class InfoCommand implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager экземпляр менеджера коллекции (для доступа к данным коллекции)
     */
    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду: выводит на экран все команды и их описания.
     *
     * @param args аргументы не используются
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Тип коллекции: " + collectionManager.getType());
        System.out.println("Дата инициализации: " + collectionManager.getInitializationDate());
        System.out.println("Количество элементов: " + collectionManager.size());
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка "вывести информацию о коллекции"
     */
    @Override
    public String getDescription() {
        return "вывести информацию о коллекции";
    }
}
