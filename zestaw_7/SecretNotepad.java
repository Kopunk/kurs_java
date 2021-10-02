import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.ArrayList;

class SecretFile {
    private String text;
    private byte[] textBytes;
    private String password;
    File file;

    SecretFile(String fullFilename, String password) throws IOException {
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
        this.password = password;
    }

    public void encryptText() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
        keyGen.init(new SecureRandom(password.getBytes()));
        SecretKey key = keyGen.generateKey();
        
        Cipher encryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        textBytes = encryptCipher.doFinal(textBytes);
        text = new String(textBytes);
    }

    public void decryptText() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
        keyGen.init(new SecureRandom(password.getBytes()));
        SecretKey key = keyGen.generateKey();

        Cipher decryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);

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
        this.textBytes = text.getBytes("UTF8");
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
        sf.setText("LOREEEm ipdum");
        sf.encryptText();
        sf.writeFile();
        System.out.println("written");

        // sf = new SecretFile("test.txt", "letmein");
        sf.readFile();
        sf.decryptText();
        sf.writeFile();
        System.out.println(sf.getText());
    }
}