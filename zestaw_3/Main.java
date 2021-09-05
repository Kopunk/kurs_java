/*
 * Zadanie 3.8 Szyfrowanie
 */

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * RsaWriter
 */
class RsaWriter {
  private String publicKeyFilename = "public.key";
  private String privateKeyFilename = "private.key";
  private String dataFilename = "data.data";

  RsaWriter() {
    new RsaWriter(dataFilename);
  }

  RsaWriter(String dataFilename) {
    new RsaWriter(dataFilename, publicKeyFilename);
  }

  RsaWriter(String dataFilename, String publicKeyFilename) {
  }

  public void encryptFile() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException,
      NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
    encryptFile(dataFilename);
  }

  public void encryptFile(String dataFilename) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException,
      NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    File publicKeyFile = new File(publicKeyFilename);
    File dataFile = new File(dataFilename);

    if (!publicKeyFile.exists()) {
      throw new FileNotFoundException("Public Key Doesn't Exist");
    }

    if (!dataFile.exists()) {
      throw new FileNotFoundException("Data File Doesn't Exist");
    }

    FileInputStream publicKeyFileReader = new FileInputStream(publicKeyFile);
    byte[] publicKeyBytes = publicKeyFileReader.readAllBytes();
    publicKeyFileReader.close();

    FileInputStream dataFileReader = new FileInputStream(dataFile);
    byte[] dataFileBytes = dataFileReader.readAllBytes();
    dataFileReader.close();

    PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));

    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);

    FileOutputStream dataFileWriter = new FileOutputStream(dataFile);
    dataFileWriter.write(cipher.doFinal(dataFileBytes));
    dataFileWriter.close();
  }

  public void encryptString(String dataString, String dataFilename)
      throws IOException, InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException,
      NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
    File dataFile = new File(dataFilename);

    if (!dataFile.exists()) {
      // dataFile.getParentFile().mkdirs();
      dataFile.createNewFile();
    }

    FileOutputStream dataFileWriter = new FileOutputStream(dataFile);
    dataFileWriter.write(dataString.getBytes());
    dataFileWriter.close();
    encryptFile(dataFilename);
  }

  public void encryptString(String dataString) throws IOException, InvalidKeyException, InvalidKeySpecException,
      NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
    encryptString(dataString, dataFilename);
  }

  public void writeFile(String data) throws IOException {
    writeFile(dataFilename, data);
  }

  /**
   * writeFile Writes string in form of bytes to a file. File can be then
   * encrypted with encryptFile method.
   */
  public void writeFile(String filename, String data) throws IOException {
    File file = new File(filename);

    if (!file.exists()) {
      // file.getParentFile().mkdirs();
      file.createNewFile();
    }

    byte[] dataBytes = data.getBytes();

    FileOutputStream fileWriter = new FileOutputStream(file);
    fileWriter.write(dataBytes);
    fileWriter.close();
  }

  public void writeKeyPair() throws Exception {
    writeKeyPair(privateKeyFilename, publicKeyFilename);
  }

  public void writeKeyPair(String privateKeyFilename, String publicKeyFilename)
      throws NoSuchAlgorithmException, IOException {
    File privateKeyFile = new File(privateKeyFilename);
    File publicKeyFile = new File(publicKeyFilename);

    if (!privateKeyFile.exists()) {
      privateKeyFile.getParentFile().mkdirs();
      privateKeyFile.createNewFile();
    }

    if (!publicKeyFile.exists()) {
      publicKeyFile.getParentFile().mkdirs();
      publicKeyFile.createNewFile();
    }

    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(1024, new SecureRandom());
    KeyPair pair = generator.generateKeyPair();

    byte[] privateKeyBytes = pair.getPrivate().getEncoded();
    byte[] publicKeyBytes = pair.getPublic().getEncoded();

    FileOutputStream privateKeyFileWriter = new FileOutputStream(privateKeyFile);
    FileOutputStream publicKeyFileWriter = new FileOutputStream(publicKeyFile);

    privateKeyFileWriter.write(privateKeyBytes);
    publicKeyFileWriter.write(publicKeyBytes);

    privateKeyFileWriter.close();
    publicKeyFileWriter.close();
  }

  public String getPublicKeyFilename() {
    return publicKeyFilename;
  }

  public void setPublicKeyFilename(String publicKeyFilename) {
    this.publicKeyFilename = publicKeyFilename;
  }

  public String getPrivateKeyFilename() {
    return privateKeyFilename;
  }

  public void setPrivateKeyFilename(String privateKeyFilename) {
    this.privateKeyFilename = privateKeyFilename;
  }

  public String getDataFilename() {
    return dataFilename;
  }

  public void setDataFilename(String dataFilename) {
    this.dataFilename = dataFilename;
  }

}

/**
 * RsaReader
 */
class RsaReader {
  private String privateKeyFilename = "private.key";
  private String dataFilename = "data.data";

  RsaReader() {
    new RsaReader(dataFilename);
  }

  RsaReader(String dataFilename) {
    new RsaReader(dataFilename, privateKeyFilename);
  }

  RsaReader(String dataFilename, String privateKeyFilename) {
  }

  public String decryptFile() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException,
      NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
    return decryptFile(dataFilename);
  }

  public String decryptFile(String dataFilename) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException,
      NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    File privateKeyFile = new File(privateKeyFilename);
    File dataFile = new File(dataFilename);

    if (!privateKeyFile.exists()) {
      // privateKeyFile.getParentFile().mkdirs();
      privateKeyFile.createNewFile();
    }

    if (!dataFile.exists()) {
      // dataFile.getParentFile().mkdirs();
      dataFile.createNewFile();
    }

    FileInputStream privateKeyFileReader = new FileInputStream(privateKeyFile);
    byte[] privateKeyBytes = privateKeyFileReader.readAllBytes();
    privateKeyFileReader.close();

    FileInputStream dataFileReader = new FileInputStream(dataFile);
    byte[] dataFileBytes = dataFileReader.readAllBytes();
    dataFileReader.close();

    PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.DECRYPT_MODE, privateKey);

    dataFileBytes = cipher.doFinal(dataFileBytes);

    return new String(dataFileBytes);
  }

  public String getDataFilename() {
    return dataFilename;
  }

  public void setDataFilename(String dataFilename) {
    this.dataFilename = dataFilename;
  }

  public String getPrivateKeyFilename() {
    return privateKeyFilename;
  }

  public void setPrivateKeyFilename(String privateKeyFilename) {
    this.privateKeyFilename = privateKeyFilename;
  }
}

public class Main {
  public static void main(String[] args) {
    RsaWriter writer = new RsaWriter();

    try {
      writer.writeKeyPair();
    } catch (Exception e1) {
      System.out.print("ERROR: Something went wrong with writing key pair: ");
      e1.printStackTrace();
    }

    try {
      writer.encryptString("Lorem Ipsum You Know How It Goes");
    } catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException
        | IllegalBlockSizeException | BadPaddingException | IOException e) {
      System.out.print("ERROR: Something went wrong with encryption: ");
      e.printStackTrace();
    }

    RsaReader reader = new RsaReader();

    try {
      System.out.println(reader.decryptFile());
    } catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException
        | IllegalBlockSizeException | BadPaddingException | IOException e) {
      System.out.print("ERROR: Something went wrong with decryption: ");
      e.printStackTrace();
    }
  }
}