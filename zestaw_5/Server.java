import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

class Server extends Thread {
    final ServerSocket serverSocket;
    private final int port;
    LinkedList<Connection> clientList;
    LinkedList<Message> messageQueue;

    Server(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
        clientList = new LinkedList<Connection>();
        messageQueue = new LinkedList<Message>();
        new Broadcaster().start();
    }

    public void run() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept(); // blocks

                clientList.addLast(new Connection(clientSocket));
                clientList.getLast().start();

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } finally {
            closeServer();
        }
    }

    public void closeServer() {
        // TODO implement
        while (!clientList.isEmpty()) {
            clientList.pop().closeConnection();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    class Connection extends Thread {
        Socket clientSocket;

        InputStream inputStream;
        DataInputStream dataInputStream;

        Message inputMessage;

        Connection(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                clientSocket = serverSocket.accept();

                inputStream = clientSocket.getInputStream();
                dataInputStream = new DataInputStream(inputStream);

                inputMessage = new Message(dataInputStream.readUTF());

                while (inputMessage.getCode() != "EXIT") {
                    if (!inputMessage.isCode()) {
                        messageQueue.add(inputMessage);
                    }
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                closeConnection();
            }
        }

        void closeConnection() {
            try {
                clientSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        Socket getSocket() {
            return clientSocket;
        }
    }

    class Broadcaster extends Thread {
        public void run() {
            while (clientList.isEmpty()) {
            }
            while (!clientList.isEmpty()) {
                while (!messageQueue.isEmpty()) {
                    for (Connection client : clientList) {
                        try {
                            DataOutputStream dataOutputStream = new DataOutputStream(client.getSocket().getOutputStream());
                            dataOutputStream.writeUTF(messageQueue.getFirst().toString());
                            dataOutputStream.flush();
                            dataOutputStream.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                    messageQueue.removeFirst();
                }
            }

        }
    }
}