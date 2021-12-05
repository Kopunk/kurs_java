import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) {
            try {
                new Server(9998);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
