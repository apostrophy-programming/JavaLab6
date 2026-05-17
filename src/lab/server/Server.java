package lab.server;

import lab.collection.CollectionManager;
import lab.command.Command;
import lab.common.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Server {
    private final int port;
    private Selector selector;
    private final CollectionManager collectionManager;
    private final Map<String, Command> commands;

    public Server(int port, String file) {
        this.port = port;
        commands = new HashMap<>();
        collectionManager = new CollectionManager();
    }

    public void start() throws IOException {
        selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server started on port " + port);

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
            System.out.println("Accepted connection from " + clientChannel.getRemoteAddress());
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
            System.out.println("Connection closed: " + sc.getRemoteAddress());
            key.cancel();
            sc.close();
            return;
        }

        if (clientData.advanceAfterRead()) {
            byte[] commandData = clientData.getRequestData();
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(commandData);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Command command = (Command) objectInputStream.readObject();
                System.out.println("Received: " + command);

                command.setCollectionManager(collectionManager);
                Response response = new Response(command.execute(new String[0]));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(response);
                oos.flush();
                byte[] responseData = baos.toByteArray();

                clientData.prepareWrite(responseData);
                key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                for (String s :response.getText()) {
                    System.out.println(s);
                }
                clientData.resetForNextMessage();
            } catch (ClassNotFoundException e) {
                System.err.println("Deserialization error: " + e.getMessage());
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

        // Если всё записано, убираем интерес к записи
        if (buf != null && !buf.hasRemaining()) {
            key.interestOps(SelectionKey.OP_READ); // оставляем только чтение
            // Очищать writeBuffer не обязательно, следующий prepareWrite перезапишет ссылку
        }
    }

}
