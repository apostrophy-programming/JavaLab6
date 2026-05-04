package lab.command;

import lab.App;

/**
 * Команда {@code exit}.
 * Завершает работу программы без автоматического сохранения коллекции.
 *
 * @author Max
 */
public class ExitCommand implements Command {
    private final App app;

    /**
     * Конструктор команды.
     *
     * @param app экземпляр приложения (для установки флага завершения)
     */
    public ExitCommand(App app) {
        this.app = app;
    }

    /**
     * Выполняет команду: выводит сообщение и останавливает главный цикл приложения.
     *
     * @param args аргументы команды (не используются)
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Завершение программы...");
        app.exitApp();
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка "завершить программу (без сохранения)"
     */
    @Override
    public String getDescription() {
        return "завершить программу (без сохранения)";
    }
}