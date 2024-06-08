import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

    private static Selector selector = null;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Server <port>");
            return;
        }

        try {
            // Abrir selector y configurar canal del servidor
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress("localhost", Integer.parseInt(args[0])));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, serverSocketChannel.validOps());
            System.out.println("Server connected ");
            
            while (true) {
                // Esperar a que haya eventos en los canales registrados
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();

                    if (key.isAcceptable()) {
                        // Aceptar nueva conexión
                        handleNewConnection(serverSocketChannel);
                    } else if (key.isReadable()) {
                        // Leer mensaje de cliente
                        handleClientMessage(key);
                    }

                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleNewConnection(ServerSocketChannel serverSocketChannel) throws IOException {
        // Aceptar nueva conexión y configurar canal no bloqueante
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        clientChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, buffer);
    }

    private static void handleClientMessage(SelectionKey key) throws IOException {
        // Leer mensaje desde el canal del cliente
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        int bytesRead = clientChannel.read(buffer);
        buffer.flip();

        if (bytesRead > 0) {
            byte[] bytes = new byte[bytesRead];
            buffer.get(bytes);
            String message = new String(bytes);

            System.out.print(message);
            broadcastMessage(message, key);
        }
        buffer.clear();
    }

    private static void broadcastMessage(String message, SelectionKey senderKey) {
        ByteBuffer messageBuffer = ByteBuffer.wrap(message.getBytes());

        for (SelectionKey key : selector.keys()) {
            if (key.isWritable() && key.channel() instanceof SocketChannel && !key.equals(senderKey)) {
                SocketChannel clientChannel = (SocketChannel) key.channel();
                try {
                    clientChannel.write(messageBuffer);
                    messageBuffer.rewind();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


