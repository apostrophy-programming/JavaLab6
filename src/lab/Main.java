package lab;

/**
 * Точка входа в приложение.
 * Ожидает один аргумент командной строки – имя файла для загрузки/сохранения коллекции.
 *
 * @author Max
 * @version 1.0
 */
public class Main {
    /**
     * Основной метод программы.
     *
     * @param args аргументы командной строки (первый аргумент – имя файла)
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Ошибка: не указано имя файла.");
            System.exit(1);
        }

        App app = new App(args[0]);
        app.start();
    }
}