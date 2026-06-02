package client;

import client.command.ExecuteScriptCommand;
import client.command.ExitCommand;
import client.command.HelpCommand;
import command.Command;
import command.*;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private Client client;
    private final Map<String, CommandBuilder> builders = new HashMap<>();

    public CommandFactory(Client client) {
        this.client=client;
        registerCommands();
    }

    public Command createCommand(String commandName, String[] args) {
        CommandBuilder builder = builders.get(commandName);
        if (builder == null) return null;
        try {
            return builder.build(args);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    private void registerCommands() {
        register("help", args -> new HelpCommand(client));
        register("exit", args -> new ExitCommand(client));
        register("info", args -> new InfoCommand());
        register("show", args -> new ShowCommand());
        register("clear", args -> new ClearCommand());
        register("remove_first", args -> new RemoveFirstCommand());

        register("add", args -> {
            AddCommand cmd = new AddCommand();
            cmd.setVehicle(client.getInputManager().readVehicle(false, null));
            return cmd;
        });

        register("execute_script", args -> {
            requireArgs(args, 1);
            ExecuteScriptCommand cmd = new ExecuteScriptCommand(client);
            cmd.setFileName(args[0]);
            return cmd;
        });

        register("update", args -> {
            requireArgs(args, 1);
            long id = requireLong(args[0], "id");
            UpdateCommand cmd = new UpdateCommand();
            cmd.setId(id);
            cmd.setVehicle(client.getInputManager().readVehicle(false, null));
            return cmd;
        });
    }

    private void register(String name, CommandBuilder builder) {
        builders.put(name, builder);
    }



    private void requireArgs(String[] args, int count) {
        if (args.length != count) {
            throw new IllegalArgumentException("Требуется " + count + " аргумент(ов)");
        }
    }

    private long requireLong(String value, String name) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(name + " должен быть целым числом");
        }
    }
}
