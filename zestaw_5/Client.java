import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

class Client {
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

        while (true) {
            message = new Message(scanner.nextLine());
            if (message.getCode() == "EXIT") {
                break;
            }
            dataOutputStream.writeUTF(message.toString());
            dataOutputStream.flush();
        }

        dataOutputStream.close();
        clientSocket.close();
    }

    class Receiver extends Thread {
        public void run() {
            try {
                DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
                while (!clientSocket.isClosed()) {
                    System.out.println(dataInputStream.readUTF());
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}
