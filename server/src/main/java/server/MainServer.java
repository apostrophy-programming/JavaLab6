package server;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("server.MainServer аргументы: порт, файл");
            System.exit(1);
        }
        String file = args[1];
        int port = 0;
        try {
            port = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException e) {
            System.err.println("Неверный формат порта, введено: " + args[0] + ". Введите целое число.");
        }
        Server server = new Server(port, file);
        try {
            server.start();
        } catch (IOException e) {
            System.err.println("Ошибка запуска сервера <- server.MainServer: " + e.getMessage());
        }
    }
}
