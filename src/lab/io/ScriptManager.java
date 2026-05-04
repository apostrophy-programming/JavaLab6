package lab.io;

import lab.App;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Управляет выполнением скриптов (файлов с командами).
 * Предотвращает рекурсивные вызовы скриптов, отслеживая уже выполняемые файлы.
 *
 * @author Max
 */
public class ScriptManager {
    private final Set<String> runningScripts = new HashSet<>();

    /**
     * Выполняет скрипт из указанного файла.
     *
     * @param fileName имя файла скрипта
     * @param app      ссылка на приложение для выполнения команд
     */
    public void executeScript(String fileName, App app) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Файл скрипта не найден: " + fileName);
            return;
        }
        if (runningScripts.contains(file.getAbsolutePath())) {
            System.out.println("Обнаружена рекурсия скрипта! Выполнение остановлено.");
            return;
        }
        runningScripts.add(file.getAbsolutePath());
        String previousScript = app.getCurrentScriptFile();
        app.setCurrentScriptFile(file.getAbsolutePath());
        for (String name:runningScripts) {
            System.out.println(name);
        }


        Scanner oldScanner = app.getInputManager().getScanner();
        try (Scanner fileScanner = new Scanner(file)) {
            app.getInputManager().setScanner(fileScanner);
            app.getInputManager().setReadingInput(false);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                System.out.println("Выполняется: " + line);
                app.executeCommand(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения файла скрипта: " + e.getMessage());
        } finally {
            app.getInputManager().setScanner(oldScanner);
            app.getInputManager().setReadingInput(true);
            runningScripts.remove(file.getAbsolutePath());
            app.setCurrentScriptFile(previousScript);
        }

    }

}
