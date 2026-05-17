package lab.command;

import lab.client.Client;
import lab.collection.CollectionManager;

public class ExitCommand implements Command {

    public ExitCommand() {
    }

    @Override
    public String getDescription() {
        return "exit: завершить программу";
    }

    @Override
    public String[] execute(String[] args) {
        System.out.println("Завершение программы...");
        System.exit(0);
        return new String[0];
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {

    }


}