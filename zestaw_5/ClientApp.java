import java.io.IOException;

public class ClientApp {
    public static void main(String[] args) {
        try {
            new Client(9998);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
