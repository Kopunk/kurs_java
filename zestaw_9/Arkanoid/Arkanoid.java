import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Kulka extends Ellipse2D.Float {
    Plansza p;
    int dx, dy;

    int punkty;

    Kulka(Plansza p, int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.width = 10;
        this.height = 10;

        this.p = p;
        this.dx = dx;
        this.dy = dy;

        this.punkty = 0;
    }

    void nextKrok() {
        x += dx;
        y += dy;

        if (getMinX() < 0)
            dx = java.lang.Math.abs(dx);
        else if (getMaxX() > p.getWidth())
            dx = -java.lang.Math.abs(dx);
        if (getMinY() < 0 || (getMaxY() > p.getHeight()))
            dy = -dy;

        if (p.go != null && p.go.enabled && this.intersects(p.go)) {
            p.go.gameover = true;
            p.tekstPrzegrana = "GAME OVER! zdobyto " + punkty + " pkt.";
            // System.out.println(p.tekstPrzegrana);
        }

        // odbicie od belki
        odbicieOdBelki();

        // odbicie od cegielek
        boolean wszystkieZbite = true;
        for (Cegielka[] cegielka1 : p.c) {
            for (Cegielka cegielka : cegielka1) {
                // System.out.println(cegielka);
                if (!(cegielka.zbita)) {
                    wszystkieZbite = false;
                    if (odbicie(cegielka)) {
                        cegielka.setZbita(true);
                        punkty++;
                    }
                }
            }
        }

        if (wszystkieZbite) {
            p.tekstWygrana = "Gratulacje!";
        }

        p.repaint();

        Toolkit.getDefaultToolkit().sync();

    }

    boolean odbicie(Rectangle2D.Float obiekt) {
        if (this.intersects(obiekt)) {

            if (getMaxY() >= obiekt.getMinY()
                    && (getMinX() + this.width / 2 >= obiekt.getMinX()
                            && getMaxX() - this.width / 2 <= obiekt.getMaxX())) {
                dy = -dy;
            } else {
                dx = -dx;
            }
            return true;
        }
        return false;
    }

    void odbicieOdBelki() {
        if (this.intersects(p.b)) {
            dy = -1; // odbijamy nawet jak dotknie bokiem
            if (this.intersects(p.b.czesciBelki[0])) {
                dx = -2;
            } else if (this.intersects(p.b.czesciBelki[1])) {
                dx = -1;
                dy = -2;
            } else if (this.intersects(p.b.czesciBelki[2])) {
                dx = 0;
                dy = -2;
            } else if (this.intersects(p.b.czesciBelki[3])) {
                dx = 1;
                dy = -2;
            } else if (this.intersects(p.b.czesciBelki[4])) {
                dx = 2;
            } else {
                System.err.println("BELKA NIEZSYNCHRONIZOWANA");
            }
        }
    }
}

class SilnikKulki extends Thread {
    Kulka a;

    SilnikKulki(Kulka a) {
        this.a = a;
        start();
    }

    public void run() { // jeśli gameover wysyłamy interrupt do silnika kulki
        try {
            while (true) {
                a.nextKrok();
                sleep(15);
            }
        } catch (InterruptedException e) {
        }
    }
}

class Belka extends Rectangle2D.Float {
    Rectangle2D.Float[] czesciBelki = { null, null, null, null, null };

    Belka(int x) {
        this.x = x;
        this.y = 270;
        this.width = 60;
        this.height = 10;

        for (int i = 0; i < czesciBelki.length; i++) {
            czesciBelki[i] = new Rectangle2D.Float(this.x + (i * (this.width / czesciBelki.length)), this.y,
                    (this.width / czesciBelki.length), this.height);
        }
    }

    void setX(int x) {
        this.x = x;
        updateCzesciBelki();
    }

    void updateCzesciBelki() {
        for (int i = 0; i < czesciBelki.length; i++) {
            czesciBelki[i].x = this.x + (i * (this.width / czesciBelki.length));
        }
    }

}

class Cegielka extends Rectangle2D.Float {
    boolean zbita;
    Color kolor;

    Cegielka(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 55;
        this.height = 10;
        this.zbita = false;

        Random ran = new Random();
        this.kolor = new Color(ran.nextInt(0, 256), ran.nextInt(0, 256), ran.nextInt(0, 256));
    }

    void setZbita(boolean zbita) {
        this.zbita = zbita;
    }

    @Override
    public String toString() {
        return new String("cegielka w " + x + " : " + y + "\t" + zbita);
    }
}

class GameOverLine extends Rectangle2D.Float implements Runnable {
    boolean enabled;
    boolean gameover;

    GameOverLine() {
        this.x = 0;
        this.y = 320;
        this.width = 400;
        this.height = 10;

        enabled = false;
        gameover = false;
        Thread thread = new Thread(this);
        thread.start();
        // System.out.println("started gameoverline");
    }

    public void run() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.err.println("ERROR: " + e);
        }

        enabled = true;
        // System.out.println("enabled gameoverline");
    }
}

class Plansza extends JPanel implements MouseMotionListener, Runnable {
    Belka b;
    Kulka a;
    SilnikKulki s;
    GameOverLine go;
    Cegielka[][] c = {
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null }
    };
    String tekstPrzegrana;
    String tekstWygrana;
    Thread thread;
    BufferedImage img;

    Color defaultColor;

    Plansza() {
        super();
        addMouseMotionListener(this);

        int y = 10;
        for (int i = 0; i < 5; i++) {
            int x = 10;
            for (int j = 0; j < 6; j++) {
                c[i][j] = new Cegielka(x, y);
                x += 65;
            }
            y += 20;
        }

        b = new Belka(100);
        a = new Kulka(this, (int) b.x, 270, 1, -1);
        s = new SilnikKulki(a);
        go = new GameOverLine();

        thread = new Thread(this);

        try {
            img = ImageIO.read(new File("bg.png"));
        } catch (IOException e) {
            System.err.println("ERROR: couldn't read bg.png: " + e);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(img, 0, 0, null);

        g2d.fill(a);
        g2d.fill(b);
        // g2d.fill(go); // standardowo niewidzialna

        for (Cegielka[] cegielka1 : c) {
            for (Cegielka cegielka : cegielka1) {
                if (!cegielka.zbita) {
                    // defaultColor = g2d.getColor();

                    g2d.setColor(cegielka.kolor);
                    g2d.fill(cegielka);

                    g2d.setColor(Color.BLACK);
                }
            }
        }

        if (go.gameover && tekstPrzegrana != null) {
            s.interrupt();
            g2d.drawString(tekstPrzegrana, 120, 230);

            thread.start(); // game over timer - po upływie czasu wychodzi z gry
        }

        if (tekstWygrana != null) {
            s.interrupt();
            g2d.drawString(tekstWygrana, 120, 230);

            thread.start();
        }
    }

    public void run() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        System.exit(0);
    }

    public void mouseMoved(MouseEvent e) {
        b.setX(e.getX() - 50);
        if (!go.gameover && tekstWygrana == null) {
            repaint();
        }
    }

    public void mouseDragged(MouseEvent e) {

    }
}

public class Arkanoid {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Plansza p;
                p = new Plansza();

                JFrame jf = new JFrame();
                jf.add(p);

                jf.setTitle("A R K A N O I D");
                jf.setSize(400, 370);
                jf.setResizable(false);
                jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jf.setVisible(true);
            }
        });
    }
}
