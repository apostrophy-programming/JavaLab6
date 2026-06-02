package server;

import command.Command;
import connection.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {
    private final int port;
    private Selector selector;
    private final RealCollectionManager realCollectionManager;
    private final FileManager fileManager;

    public Server(int port, String file) {
        this.port = port;
        realCollectionManager = new RealCollectionManager(this);
        fileManager = new FileManager(file);
        loadCollection();
    }

    private void loadCollection() {
        try {
            realCollectionManager.setCollection(fileManager.loadFromFile());
            System.out.println("Коллекция загружена из файла.");
        } catch (Exception e) {
            System.err.println("Не удалось загрузить коллекцию: " + e.getMessage());
        }
    }

    public void start() throws IOException {
        selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Сервер запущен на порту: " + port);

        while (true) {
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();

                if (!key.isValid()) {
                    continue;
                }
                if (key.isAcceptable()) {
                    doAccept(key);
                } else if (key.isReadable()) {
                    doRead(key);
                } else if (key.isWritable()) {
                    doWrite(key);
                }
            }
        }
    }

    private void doAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        if (clientChannel != null) {
            System.out.println("Принято подключение от: " + clientChannel.getRemoteAddress());
            clientChannel.configureBlocking(false);
            ClientData clientData = new ClientData();
            clientChannel.register(selector, SelectionKey.OP_READ, clientData);
        }
    }

    private void doRead(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        ClientData clientData = (ClientData) key.attachment();
        ByteBuffer buf = clientData.getReadBuffer();

        int bytesRead = sc.read(buf);
        if (bytesRead == -1) {
            System.out.println("Соединение закрыто: " + sc.getRemoteAddress());
            key.cancel();
            sc.close();
            save();
            return;
        }

        if (clientData.advanceAfterRead()) {
            byte[] commandData = clientData.getRequestData();
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(commandData);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Command command = (Command) objectInputStream.readObject();
                System.out.println("Получена команда: " + command);

                Response response = command.execute(realCollectionManager);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(response);
                oos.flush();
                byte[] responseData = baos.toByteArray();

                clientData.prepareWrite(responseData);
                key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                clientData.resetForNextMessage();
            } catch (ClassNotFoundException e) {
                System.err.println("Ошибка десериализации: " + e.getMessage());
            }
        }
    }

    private void doWrite(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        ClientData data = (ClientData) key.attachment();
        ByteBuffer buf = data.getWriteBuffer();

        if (buf != null && buf.hasRemaining()) {
            sc.write(buf);
        }
        if (buf != null && !buf.hasRemaining()) {
            key.interestOps(SelectionKey.OP_READ);
        }
    }

    public void save() {
        try {
            fileManager.saveToFile(realCollectionManager.getCollection());
            System.out.println("Коллекция сохранена в файл.");
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

}
