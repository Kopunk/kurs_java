/*
 * Zadanie 3.8 Szyfrowanie
 */

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

class RsaReader {
  PrivateKey privateKey;

  RsaReader(String privateKeyFilename) {
    // this.privateKey = privateKey;

  }

  RsaReader() {

  }
}

class RsaWriter {
  PublicKey publicKey;
  String defaultPublicKeyFilename = "public.key";

  RsaWriter(String dataFilename) throws Exception {
    new RsaWriter(dataFilename, defaultPublicKeyFilename);
  }

  RsaWriter(String dataFilename, String publicKeyFileName) throws Exception {
    File publicKeyFile = new File(publicKeyFileName);
    File dataFile = new File(dataFilename);
    byte[] publicKeyBytes;
    byte[] dataFileBytes;

    FileInputStream publicKeyFileReader = new FileInputStream(publicKeyFile);
    publicKeyBytes = publicKeyFileReader.readAllBytes();
    publicKeyFileReader.close();

    FileInputStream dataFileReader = new FileInputStream(dataFile);
    dataFileBytes = dataFileReader.readAllBytes();
    dataFileReader.close();

    this.publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
    
    Cipher cipher = Cipher.getInstance("RSA");
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);

    FileOutputStream dataFileWriter = new FileOutputStream(dataFile);
    dataFileWriter.write(cipher.doFinal(dataFileBytes));
    dataFileWriter.close();
  }

  void writeKeyPair() throws Exception {
    writeKeyPair("private.key", defaultPublicKeyFilename);
  }

  void writeKeyPair(String privateKeyFileName, String publicKeyFileName) throws Exception {
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
    generator.initialize(1024, new SecureRandom());
    KeyPair pair = generator.generateKeyPair();

    byte[] privateKeyBytes = pair.getPrivate().getEncoded();
    byte[] publicKeyBytes = pair.getPublic().getEncoded();

    File privateKeyFile = new File(privateKeyFileName);
    File publicKeyFile = new File(publicKeyFileName);

    FileOutputStream privateKeyFileWriter;
    FileOutputStream publicKeyFileWriter;

    privateKeyFileWriter = new FileOutputStream(privateKeyFile);
    publicKeyFileWriter = new FileOutputStream(publicKeyFile);

    privateKeyFileWriter.write(privateKeyBytes);
    publicKeyFileWriter.write(publicKeyBytes);

    privateKeyFileWriter.close();
    publicKeyFileWriter.close();

  }
}

public class Main {
  public static void main() {

  }
}