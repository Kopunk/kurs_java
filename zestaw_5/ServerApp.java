import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) {
        try {
            new Server(9999);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
