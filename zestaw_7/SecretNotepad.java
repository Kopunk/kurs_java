import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

class SecretFile {
    private String text;
    private byte[] textBytes;
    private File file;

    private byte[] salt = { 1, 2, 3, 4, 5, 6, 7, 8 };
    PBEParameterSpec pbeParamSpec;
    private SecretKey secret;
    private Cipher encryptCipher;
    private Cipher decryptCipher;

    SecretFile(String fullFilename, String password) throws IOException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        File file;

        if (fullFilename.startsWith("./")) {
            fullFilename = fullFilename.substring(2);
        } else if (fullFilename.startsWith("/")) {
            fullFilename = fullFilename.substring(1);
        }

        if (fullFilename.isEmpty()) {
            fullFilename = "note.txt";
        }

        if (fullFilename.contains("/")) {
            String dir = "./" + fullFilename.substring(0, fullFilename.lastIndexOf("/"));
            file = new File(dir);
            file.mkdir();
        }

        file = new File(fullFilename);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();

        this.file = file;

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        KeySpec spec = new PBEKeySpec(password.toCharArray());
        secret = factory.generateSecret(spec);
        pbeParamSpec = new PBEParameterSpec(salt, 2);
    }

    public void encryptText() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        encryptCipher = Cipher.getInstance("PBEWithMD5AndDES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secret, pbeParamSpec);
        textBytes = encryptCipher.doFinal(textBytes);
        text = new String(textBytes);
    }

    public void decryptText() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        decryptCipher = Cipher.getInstance("PBEWithMD5AndDES");
        decryptCipher.init(Cipher.DECRYPT_MODE, secret, pbeParamSpec);
        textBytes = decryptCipher.doFinal(textBytes);
        text = new String(textBytes);
    }

    public void writeFile() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(textBytes);
        fileOutputStream.close();
    }

    public void readFile() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        textBytes = fileInputStream.readAllBytes();
        text = new String(textBytes);
        fileInputStream.close();
    }

    public void setText(String text) throws UnsupportedEncodingException {
        this.text = text;
        this.textBytes = text.getBytes();
    }

    public String getText() {
        return text;
    }
}

/**
 * SecretNotepad
 */
public class SecretNotepad {

    public static void main(String[] args) throws Exception {
        SecretFile sf = new SecretFile("test.txt", "letmein");
        sf.setText("abc");
        sf.encryptText();
        sf.writeFile();
        System.out.println("written");

        // sf = new SecretFile("test.txt", "letmein");
        sf.readFile();
        System.out.println(sf.getText());
        sf.decryptText();
        sf.readFile();
        System.out.println(sf.getText());
    }
}
