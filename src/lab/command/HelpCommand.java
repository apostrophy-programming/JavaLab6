package lab.command;

import lab.App;

/**
 * Команда {@code help} – выводит список всех доступных команд с их описанием.
 *
 * @see Command
 */
public class HelpCommand implements Command {
    private final App app;

    /**
     * Конструктор команды.
     *
     * @param app экземпляр приложения (для доступа к карте команд)
     */
    public HelpCommand(App app) {
        this.app = app;
    }

    /**
     * Выполняет команду: выводит на экран все команды и их описания.
     *
     * @param args аргументы не используются
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Доступные команды:");
        app.getCommands().forEach((name, cmd) ->
                System.out.printf("  %-30s %s\n", name, cmd.getDescription()));
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка "вывести справку по доступным командам"
     */
    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}
