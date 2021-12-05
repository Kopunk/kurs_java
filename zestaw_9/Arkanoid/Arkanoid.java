import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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

        if (getMinX() < 0 || getMaxX() > p.getWidth())
            dx = -dx;
        if (getMinY() < 0)
            dy = -dy;
        else if (getMaxY() > p.getHeight()) {
            dy = -dy;
            // TODO add gameover
            // p.tekstKoncowy = "GAME OVER\nzdobyto " + punkty + " pkt.";
        }

        // odbicie od belki
        odbicie(p.b);

        // odbicie od cegielek
        for (Cegielka[] cegielka1 : p.c) {
            for (Cegielka cegielka : cegielka1) {
                System.out.println(cegielka);
                if (!(cegielka.zbita)) {
                    if (odbicie(cegielka)) {
                        cegielka.setZbita(true);
                        punkty++;
                    }
                }
            }
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
}

class SilnikKulki extends Thread {
    Kulka a;

    SilnikKulki(Kulka a) {
        this.a = a;
        start();
    }

    public void run() {
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
    Belka(int x) {
        this.x = x;
        this.y = 270;
        this.width = 60;
        this.height = 10;
    }

    void setX(int x) {
        this.x = x;
    }
}

class Cegielka extends Rectangle2D.Float {
    boolean zbita;

    Cegielka(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 55;
        this.height = 10;
        this.zbita = false;
    }

    void setZbita(boolean zbita) {
        this.zbita = zbita;
    }

    @Override
    public String toString() {
        return new String("cegielka w " + x + " : " + y + "\t" + zbita);
    }
}

class Plansza extends JPanel implements MouseMotionListener {
    Belka b;
    Kulka a;
    SilnikKulki s;
    Cegielka[][] c = {
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null },
            { null, null, null, null, null, null }
    };
    String tekstKoncowy;

    Plansza() {
        super();
        addMouseMotionListener(this);

        b = new Belka(100);
        a = new Kulka(this, 100, 250, 1, -1);
        s = new SilnikKulki(a);

        int y = 10;
        for (int i = 0; i < 5; i++) {
            int x = 10;
            for (int j = 0; j < 6; j++) {
                c[i][j] = new Cegielka(x, y);
                x += 65;
            }
            y += 20;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.fill(a);
        g2d.fill(b);

        for (Cegielka[] cegielka1 : c) {
            for (Cegielka cegielka : cegielka1) {
                if (!cegielka.zbita)
                    g2d.fill(cegielka);
            }
        }

        // if (tekstKoncowy != null) {
        // g2d.drawString(tekstKoncowy, 190, 160);
        // try {
        // TimeUnit.SECONDS.sleep(5);
        // } catch (InterruptedException e) {
        // System.exit(1);
        // }
        // System.exit(0);
        // }
    }

    public void mouseMoved(MouseEvent e) {
        b.setX(e.getX() - 50);
        repaint();
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

                jf.setTitle("Test grafiki");
                jf.setSize(400, 370);
                jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jf.setVisible(true);
            }
        });
    }
}
