import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

class Server extends ThreadLogger {
    final ServerSocket serverSocket;
    LinkedList<Connection> clientList;
    LinkedList<Message> messageQueue;

    Broadcaster broadcaster;

    Server(int port) throws IOException {
        logStart();
        serverSocket = new ServerSocket(port);

        clientList = new LinkedList<Connection>();
        messageQueue = new LinkedList<Message>();

        broadcaster = new Broadcaster();
        broadcaster.start();

        do {
            log("waiting for new clients");
            Socket clientSocket = serverSocket.accept(); // blocks

            clientList.addLast(new Connection(clientSocket));
        } while (!clientList.isEmpty());

        log("closing server socket");
        serverSocket.close();
    }

    class Connection extends ThreadLogger {
        Socket clientSocket;

        InputStream inputStream;
        DataInputStream dataInputStream;

        DataOutputStream dataOutputStream;

        Message inputMessage;

        Connection(Socket clientSocket) throws IOException {
            logStart();
            this.clientSocket = clientSocket;
            this.dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            this.start();
        }

        public void run() {
            try {
                inputStream = clientSocket.getInputStream();
                dataInputStream = new DataInputStream(inputStream);

                do {
                    log("reading messages from: " + clientSocket);
                    inputMessage = new Message(dataInputStream.readUTF());

                    if (!inputMessage.isCode()) {
                        messageQueue.addLast(inputMessage);
                    }
                } while (inputMessage.getCode() != Message.EXIT);

                messageQueue.addLast(new Message(clientSocket + " left"));

                dataOutputStream.close();
                clientSocket.close();
            } catch (IOException e) {
                log("ERROR: IO Exception: " + e);
            } finally {
                clientList.remove(this);
            }
        }

        DataOutputStream getDataOutputStream() {
            return dataOutputStream;
        }
    }

    class Broadcaster extends ThreadLogger {
        public void run() {
            logStart();
            while (messageQueue.isEmpty()) {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    log("ERROR: InterruptedException: " + e);
                }
            }
            while (!clientList.isEmpty()) {
                try {
                    sleep(10);
                } catch (InterruptedException e1) {
                    log("ERROR: InterruptedException: " + e1);
                }
                log("broadcasting message: " + messageQueue.getFirst().toString());
                while (!messageQueue.isEmpty()) {
                    for (Connection client : clientList) {
                        try {
                            client.getDataOutputStream().writeUTF(messageQueue.getFirst().toString());
                            client.getDataOutputStream().flush();
                        } catch (IOException e) {
                            log("ERROR: IO Exception: " + e);
                        }
                    }
                    messageQueue.removeFirst();
                }
            }
        }
    }
}