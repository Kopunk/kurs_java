import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

// Singleton
class FileEncryptor {
   private static FileEncryptor instance;

   private Cipher cipher;

   private FileEncryptor() throws NoSuchAlgorithmException, NoSuchPaddingException {
      cipher = javax.crypto.Cipher.getInstance("DES");
   }

   public static FileEncryptor getInstance() throws NoSuchAlgorithmException, NoSuchPaddingException {
      if (instance == null) {
         instance = new FileEncryptor();
      }
      return instance;
   }

   void generateKey(String password) throws NoSuchAlgorithmException {
   }

   void encryptFile(File file, String data, String password) throws IOException,
         FileNotFoundException, InvalidKeyException, NoSuchAlgorithmException {

      KeyGenerator kgen = KeyGenerator.getInstance("DES");
      kgen.init(new java.security.SecureRandom(password.getBytes()));
      SecretKey key = kgen.generateKey();

      cipher.init(Cipher.ENCRYPT_MODE, key);

      ObjectOutputStream out;
      out = new ObjectOutputStream(new CipherOutputStream(new FileOutputStream(file), cipher));
      out.write(data.getBytes());
      out.flush();
      out.close();
   }

   String decryptFile(File file, String password) throws IOException, FileNotFoundException,
         InvalidKeyException, ClassNotFoundException, NoSuchAlgorithmException {

      KeyGenerator kgen = KeyGenerator.getInstance("DES");
      kgen.init(new java.security.SecureRandom(password.getBytes()));
      SecretKey key = kgen.generateKey();

      cipher.init(Cipher.DECRYPT_MODE, key);

      ObjectInputStream in;
      in = new ObjectInputStream(new CipherInputStream(new FileInputStream(file), cipher));
      String data = new String((byte[]) in.readAllBytes());
      in.close();

      return data;
   }
}

public class SecretNotepad extends JFrame {
   private static FileEncryptor fileEncryptor;

   JPanel panel;
   JTextArea textArea;

   String password;

   public SecretNotepad() {
      try {
         fileEncryptor = FileEncryptor.getInstance();
      } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
         System.err.println("ERROR: error while initializing FileEncryptor: \n" + e);
         e.printStackTrace();
      }

      initMenu();
      initTextArea();

      setTitle("Secret Notepad");
      setMinimumSize(new Dimension(500, 500));
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

   private void initMenu() {
      JMenuBar menuBar = new JMenuBar();

      JMenu fileMenu = new JMenu("File");
      // JMenuItem newFileMenuItem = new JMenuItem("New File");
      JMenuItem openFileMenuItem = new JMenuItem("Open File");
      JMenuItem saveFileMenuItem = new JMenuItem("Save As");
      // fileMenu.add(newFileMenuItem);
      fileMenu.add(openFileMenuItem);
      fileMenu.add(saveFileMenuItem);

      // newFileMenuItem.addActionListener(new OpenFileAction());
      openFileMenuItem.addActionListener(new OpenFileAction());
      saveFileMenuItem.addActionListener(new SaveAction());

      JMenu encryptionMenu = new JMenu("Encryption");
      // JMenuItem encryptFileMenuItem = new JMenuItem("Encrypt File");
      // JMenuItem decryptFileMenuItem = new JMenuItem("Decrypt File");
      JMenuItem setPasswordMenuItem = new JMenuItem("Set Password");
      // encryptionMenu.add(encryptFileMenuItem);
      // encryptionMenu.add(decryptFileMenuItem);
      encryptionMenu.add(setPasswordMenuItem);

      setPasswordMenuItem.addActionListener(new PasswordAction());

      menuBar.add(fileMenu);
      menuBar.add(encryptionMenu);

      setJMenuBar(menuBar);
   }

   private void initTextArea() {
      textArea = new JTextArea();
      add(textArea);
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(() -> {
         SecretNotepad secretNotepad = new SecretNotepad();
         secretNotepad.setVisible(true);
      });

   }

   private class OpenFileAction extends AbstractAction {
      @Override
      public void actionPerformed(ActionEvent event) {

         JFileChooser fileChooser = new JFileChooser();
         // var filter = new FileNameExtensionFilter("Markdown files", "md");
         // fileChooser.addChoosableFileFilter(filter);

         int ret = fileChooser.showDialog(panel, "Select");

         if (ret == JFileChooser.APPROVE_OPTION) {

            File file = fileChooser.getSelectedFile();
            String data = "Error :(";
            try {
               data = fileEncryptor.decryptFile(file, password);
            } catch (InvalidKeyException | ClassNotFoundException | IOException | NoSuchAlgorithmException e) {
               System.err.println("ERROR: Could't read file: \n" + e);
               // e.printStackTrace();
            }

            textArea.setText(data);
         }
      }

   }

   private class SaveAction extends AbstractAction {
      @Override
      public void actionPerformed(ActionEvent event) {

         JFileChooser fileChooser = new JFileChooser();
         // var filter = new FileNameExtensionFilter("Markdown files", "md");
         // fileChooser.addChoosableFileFilter(filter);

         int ret = fileChooser.showSaveDialog(panel);

         if (ret == JFileChooser.APPROVE_OPTION) {

            File file = fileChooser.getSelectedFile();
            try {
               fileEncryptor.encryptFile(file, textArea.getText(), password);
            } catch (InvalidKeyException | IOException | NoSuchAlgorithmException e) {
               System.err.println("ERROR: Could't wite to file: \n" + e);
               // e.printStackTrace();
            }
         }
      }

   }

   private class PasswordAction extends AbstractAction {
      @Override
      public void actionPerformed(ActionEvent event) {

         JPasswordField passwordField = new JPasswordField();
         int ret = JOptionPane.showConfirmDialog(null, passwordField, "Enter Password",
               JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

         if (ret == JOptionPane.OK_OPTION) {
            password = new String(passwordField.getPassword());
         }

      }
   }
}
