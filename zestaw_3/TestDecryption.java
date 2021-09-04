import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * TestDecryption
 */
public class TestDecryption {
    public static void main(String[] args) {
        RsaReader reader = new RsaReader("wrongname1.data");

        reader.setDataFilename("wrongname2.data");

        try {
            System.out.println(reader.decryptFile("lorem.data"));
        } catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException | IOException e) {
            e.printStackTrace();
        }
    }
}