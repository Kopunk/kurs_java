import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class SecretNotepad extends JFrame {

   public SecretNotepad() {
      initMenu();

      setTitle("Secret Notepad");
      setMinimumSize(new Dimension(500, 500));
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

   private void initMenu() {
      JMenuBar menuBar = new JMenuBar();

      JMenu fileMenu = new JMenu("File");
      JMenuItem newFileMenuItem = new JMenuItem("New File");
      JMenuItem openFileMenuItem = new JMenuItem("Open File");
      JMenuItem saveFileMenuItem = new JMenuItem("Save");
      fileMenu.add(newFileMenuItem);
      fileMenu.add(openFileMenuItem);
      fileMenu.add(saveFileMenuItem);

      JMenu encryptionMenu = new JMenu("Encryption");
      JMenuItem encryptFileMenuItem = new JMenuItem("Encrypt File");
      JMenuItem decryptFileMenuItem = new JMenuItem("Decrypt File");
      JMenuItem setPasswordMenuItem = new JMenuItem("Set Password");
      encryptionMenu.add(encryptFileMenuItem);
      encryptionMenu.add(decryptFileMenuItem);
      encryptionMenu.add(setPasswordMenuItem);

      menuBar.add(fileMenu);
      menuBar.add(encryptionMenu);

      setJMenuBar(menuBar);
   }

   public static void main(String[] args) {
      EventQueue.invokeLater(() -> {
         SecretNotepad secretNotepad = new SecretNotepad();
         secretNotepad.setVisible(true);
      });

   }
}
