package lab.command;

import lab.collection.CollectionManager;
import lab.io.FileManager;

import java.util.PriorityQueue;

/**
 * Команда {@code save}.
 * Сохраняет текущее состояние коллекции в файл, указанный при запуске программы.
 * Использует {@link FileManager} для записи XML.
 *
 * @author Max
 */
public class SaveCommand implements Command {
    private final CollectionManager collectionManager;
    private final FileManager fileManager;

    /**
     * Конструктор команды.
     *
     * @param collectionManager менеджер коллекции
     * @param fileManager       менеджер работы с файлами
     */
    public SaveCommand(CollectionManager collectionManager, FileManager fileManager) {
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    /**
     * Выполняет команду: вызывает {@link FileManager#saveToFile(PriorityQueue)}.
     * В случае ошибки выводит сообщение, но не завершает программу.
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        try {
            fileManager.saveToFile(collectionManager.getCollection());
            System.out.println("Коллекция сохранена в файл.");
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка "сохранить коллекцию в файл"
     */
    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }
}