package lab;


import lab.collection.CollectionManager;
import lab.command.*;
import lab.io.FileManager;
import lab.io.InputManager;
import lab.io.ScriptManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Главный класс приложения. Управляет жизненным циклом программы:
 * инициализация коллекции, загрузка из файла, запуск интерактивного цикла
 * обработки команд.
 *
 * @author Max123
 */
public class App {
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final InputManager inputManager;
    private final Map<String, Command> commands;
    private boolean isRunning;
    private final ScriptManager scriptManager;
    private String currentScriptFile;

    /**
     * Конструктор приложения.
     *
     * @param fileName имя файла для загрузки/сохранения коллекции
     */
    public App(String fileName) {
        this.collectionManager = new CollectionManager();
        this.fileManager = new FileManager(fileName);
        this.inputManager = new InputManager();
        this.commands = new HashMap<>();
        this.scriptManager = new ScriptManager();
        this.isRunning = true;
        initCommands();
        loadCollection();
    }

    /**
     * Инициализирует карту команд, связывая название команды с её реализацией.
     */
    private void initCommands() {
        commands.put("help", new HelpCommand(this));
        commands.put("info", new InfoCommand(collectionManager));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("add", new AddCommand(collectionManager, inputManager));
        commands.put("update", new UpdateCommand(collectionManager, inputManager));
        commands.put("remove_by_id", new RemoveByIdCommand(collectionManager));
        commands.put("clear", new ClearCommand(collectionManager));
        commands.put("save", new SaveCommand(collectionManager, fileManager));
        commands.put("execute_script", new ExecuteScriptCommand(this, scriptManager, inputManager));
        commands.put("exit", new ExitCommand(this));
        commands.put("remove_first", new RemoveFirstCommand(collectionManager));
        commands.put("add_if_min", new AddIfMinCommand(collectionManager, inputManager));
        commands.put("remove_greater", new RemoveGreaterCommand(collectionManager, inputManager));
        commands.put("count_by_fuel_type", new CountByFuelTypeCommand(collectionManager));
        commands.put("count_greater_than_capacity", new CountGreaterThanCapacityCommand(collectionManager));
        commands.put("filter_contains_name", new FilterContainsNameCommand(collectionManager));
    }

    /**
     * Загружает коллекцию из файла при старте.
     * В случае ошибки выводит сообщение, но не завершает работу.
     */
    private void loadCollection() {
        try {
            collectionManager.setCollection(fileManager.loadFromFile());
            System.out.println("Коллекция загружена из файла.");
        } catch (Exception e) {
            System.err.println("Не удалось загрузить коллекцию: " + e.getMessage());
        }
    }

    /**
     * Запускает основной цикл обработки команд.
     * Программа ожидает ввод пользователя и выполняет соответствующие команды
     * до тех пор, пока не будет вызвана команда exit.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Программа управления коллекцией Vehicle. Введите 'help' для справки.");
        System.out.print("> ");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            executeCommand(line);
            System.out.print("> ");
        }
        scanner.close();
        System.out.println("Завершение программы...");
    }

    /**
     * Разбирает строку команды и вызывает соответствующий обработчик.
     *
     * @param line строка введённой команды
     */
    public void executeCommand(String line) {
        String[] parts = line.split("\\s+", 2);
        String commandName = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";
        Command command = commands.get(commandName);
        if (command == null) {
            System.out.println("Неизвестная команда. Введите 'help' для справки.");
            return;
        }
        try {
            command.execute(args.isEmpty() ? new String[0] : args.split("\\s+"));
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка в аргументах команды: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении команды: " + e.getMessage());
        }
    }

    /**
     * Устанавливает флаг работы приложения.
     *
     * @param running false – остановить программу
     */
    public void setRunning(boolean running) {
        isRunning = running;
    }

    /**
     * Устанавливает имя текущего выполняемого файла скрипта.
     *
     * @param fileName имя файла скрипта
     */
    public void setCurrentScriptFile(String fileName) {
        this.currentScriptFile = fileName;
    }

    /**
     * Возвращает имя текущего выполняемого файла скрипта.
     *
     * @return имя файла или null
     */
    public String getCurrentScriptFile() {
        return currentScriptFile;
    }

    /**
     * Возвращает карту доступных команд.
     *
     * @return неизменяемая карта (имя команды → объект команды)
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

    public void exitApp () {
        setRunning(false);
        System.exit(0);
    }

    public InputManager getInputManager() {
        return inputManager;
    }
}