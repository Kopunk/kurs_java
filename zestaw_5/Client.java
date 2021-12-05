import java.io.DataInputStream;
import java.io.InputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

class Client extends ThreadLogger {
    private final int port;
    private final Socket clientSocket;
    Scanner scanner;
    Message message;

    Client(int port) throws UnknownHostException, IOException {
        this.port = port;
        clientSocket = new Socket("localhost", this.port);
        DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

        new Receiver().start();

        scanner = new Scanner(System.in);

        do {
            // System.out.print(">>> ");
            message = new Message(scanner.nextLine());
            dataOutputStream.writeUTF(message.toString());
            dataOutputStream.flush();

        } while (!message.toString().startsWith(Message.EXIT));

        dataOutputStream.writeUTF(new Message(Message.EXIT).toString());
        dataOutputStream.flush();

        dataOutputStream.close();
        clientSocket.close();
    }

    class Receiver extends ThreadLogger {
        DataInputStream dataInputStream;

        public void run() {
            try {
                dataInputStream = new DataInputStream(clientSocket.getInputStream());
                log("reading messages");
                while (!clientSocket.isClosed()) {
                    System.out.println("+++ " + dataInputStream.readUTF());
                }
            } catch (IOException e) {
                if (e.toString().contains("Socket closed")) {
                    log("closed Receiver");
                } else {
                    log("ERROR: IOException: " + e);
                }
            }
        }

    }
}
