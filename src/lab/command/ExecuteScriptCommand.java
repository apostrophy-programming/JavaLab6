package lab.command;

import lab.App;
import lab.io.InputManager;
import lab.io.ScriptManager;

/**
 * Команда {@code execute_script file_name}.
 * Выполняет скрипт из указанного файла. Скрипт должен содержать команды в том же формате,
 * что и при интерактивном вводе. Поддерживается защита от рекурсии.
 *
 * @author Max
 */
public class ExecuteScriptCommand implements Command {
    private final App app;
    private final ScriptManager scriptManager;
    private final InputManager inputManager;

    /**
     * Конструктор команды.
     *
     * @param app           экземпляр приложения (для выполнения команд из скрипта)
     * @param scriptManager менеджер скриптов
     */
    public ExecuteScriptCommand(App app, ScriptManager scriptManager, InputManager inputManager) {
        this.app = app;
        this.scriptManager = scriptManager;
        this.inputManager = inputManager;
    }

    /**
     * Выполняет команду:
     * <ol>
     *   <li>Проверяет, что передан ровно один аргумент – имя файла.</li>
     *   <li>Передаёт управление {@link ScriptManager#executeScript(String, App)}.</li>
     * </ol>
     *
     * @param args массив аргументов (ожидается args[0] = имя файла)
     * @throws IllegalArgumentException если аргумент не передан
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("использование: execute_script file_name");
        }
        String fileName = args[0];
        scriptManager.executeScript(fileName, app);

    }

    /**
     * Возвращает описание команды.
     *
     * @return строка "считать и исполнить скрипт из файла"
     */
    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из файла";
    }
}