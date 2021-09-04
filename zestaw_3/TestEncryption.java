import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * TestEncryption
 */
public class TestEncryption {
    public static void main(String[] args) {
        RsaWriter writer = new RsaWriter("wrongname1.data");
        writer.setPrivateKeyFilename("wrongpath1.key");
        writer.setPublicKeyFilename("wrongpath2.key");

        writer.setDataFilename("lorem.data");

        try {
            writer.writeKeyPair("mydata/private.key", "mydata/public.key");
        } catch (NoSuchAlgorithmException | IOException e) {
            System.out.print("ERROR: Something went wrong with writing key pair: ");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            writer.encryptString("test");
        } catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException
                | IllegalBlockSizeException | BadPaddingException | IOException e) {
            System.out.print("ERROR: Something went wrong with encrypting file: ");
            e.printStackTrace();
            System.exit(1);
        }
    }

}
