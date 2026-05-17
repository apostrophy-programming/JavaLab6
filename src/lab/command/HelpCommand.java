package lab.command;

import lab.client.Client;
import lab.collection.CollectionManager;

public class HelpCommand implements Command {
    private Client client;

    public HelpCommand(Client client) {
        this.client = client;
    }

    @Override
    public String getDescription() {
        return "help : вывести справку по доступным командам";
    }

    @Override
    public String[] execute(String[] args) {
        System.out.println("Доступные команды:");
        for (Command command: client.getCommands().values()) {
            System.out.println(command.getDescription());
        }
        return new String[0];
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
    }


}
