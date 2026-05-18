package lab.client;

import lab.command.*;
import lab.common.Response;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {
    private String host;
    private int port;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private final Map<String, Command> commands;
    private final InputManager inputManager;
    private final Scanner scanner;

    public Client (String host, int port) {
        this.host = host;
        this.port = port;
        scanner = new Scanner(System.in);
        this.inputManager = new InputManager(scanner);
        this.commands = new LinkedHashMap<>();
        initCommands();
    }

    private synchronized void initCommands() {
        commands.put("help", new HelpCommand(this));
        commands.put("info", new InfoCommand(this));
        commands.put("show", new ShowCommand(this));
        commands.put("add", new AddCommand(this));
        commands.put("update", new UpdateCommand(this));
        commands.put("remove_by_id", new RemoveByIdCommand(this));
        commands.put("clear", new ClearCommand(this));
        commands.put("exit", new ExitCommand());
        commands.put("remove_first", new RemoveFirstCommand(this));
        commands.put("add_if_min", new AddIfMinCommand(this));
        commands.put("remove_greater", new RemoveGreaterCommand(this));
        commands.put("count_by_fuel_type", new CountByFuelTypeCommand(this));
        commands.put("count_greater_than_capacity", new CountGreaterThanCapacityCommand(this));
        commands.put("filter_contains_name", new FilterContainsNameCommand(this));
    }

    public void start() {
        try (Socket socket = new Socket(host, port)) {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            System.out.println("Введите help для справки:");
            System.out.print("> ");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\s+", 2);
                String commandName = parts[0].toLowerCase();
                String argsLine = parts.length > 1 ? parts[1] : "";
                String[] args = argsLine.isEmpty() ? new String[0] : argsLine.split("\\s+");
                Command command = commands.get(commandName);
                if (command == null) {
                    System.out.println("Неизвестная команда. Введите 'help' для справки.");
                    System.out.print("> ");
                    continue;
                }
                if (commandName.equals("help")) {
                    command.execute(new String[0]);
                    System.out.print("> ");
                    continue;
                }
                executeCommand(commandName, args);
                System.out.print("> ");
            }
            scanner.close();
            System.out.println("Завершение программы...");
        }
        catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }


    public void executeCommand(String commandName, String[] args) {
        Command command = commands.get(commandName);
        try {
            String[] responseText = command.execute(args);
            for (String responseLine: responseText) {
                System.out.println(responseLine);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка в аргументах команды: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении команды: " + e.getMessage());
        }
    }

    public void sendCommand (Command command) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(command);
        objectOutputStream.flush();
        byte[] data = byteArrayOutputStream.toByteArray();
        dataOutputStream.writeInt(data.length);
        dataOutputStream.write(data);
        dataOutputStream.flush();
    }

    public Response receiveResponse() throws IOException, ClassNotFoundException {
        int length = dataInputStream.readInt();
        byte[] data = new byte[length];
        dataInputStream.readFully(data);
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Response) ois.readObject();
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void exitApp () {
        System.exit(0);
    }

    public InputManager getInputManager() {
        return inputManager;
    }
}

