import java.io.IOException;

public class ClientApp {
    public static void main(String[] args) {
        try {
            new Client(9999);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
