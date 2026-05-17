package lab.command;


import lab.collection.CollectionManager;

import java.io.IOException;
import java.io.Serializable;

public interface Command extends Serializable {

    String getDescription();
    String[] execute(String[] args) throws IOException, ClassNotFoundException;
    void setCollectionManager (CollectionManager collectionManager);

}
