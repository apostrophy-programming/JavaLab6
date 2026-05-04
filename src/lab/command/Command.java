package lab.command;

/**
 * Интерфейс для всех команд приложения.
 * Каждая конкретная команда должна реализовать методы execute и getDescription.
 */
public interface Command {
    /**
     * Выполняет команду.
     * @param args аргументы команды (без имени команды)
     */
    void execute(String[] args);

    /**
     * Возвращает описание команды для вывода в справке.
     *
     * @return строка с кратким описанием
     */
    String getDescription();
}
