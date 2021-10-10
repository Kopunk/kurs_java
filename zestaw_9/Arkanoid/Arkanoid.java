import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class Belka extends Rectangle2D.Float {
    Belka(int x) {
        this.x = x;
        this.y = 170;
        this.width = 60;
        this.height = 10;
    }

    void setX(int x) {
        this.x = x;
    }
}

class Plansza extends JPanel implements MouseMotionListener {
    Belka b;
    Kulka a;
    SilnikKulki s;

    Kafelka t;

    Plansza() {
        super();
        addMouseMotionListener(this);

        b = new Belka(100);
        a = new Kulka(this, 100, 100, 1, 1);
        s = new SilnikKulki(a);

        t = new Kafelka(5, 5);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.fill(a);
        g2d.fill(b);

        g2d.fill(t);
    }

    public void mouseMoved(MouseEvent e) {
        b.setX(e.getX() - 50);
        repaint();
    }

    public void mouseDragged(MouseEvent e) {

    }
}

class Kulka extends Ellipse2D.Float {
    Plansza p;
    int dx, dy;

    Kulka(Plansza p, int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.width = 10;
        this.height = 10;

        this.p = p;
        this.dx = dx;
        this.dy = dy;
    }

    void nextKrok() {
        x += dx;
        y += dy;

        if (getMinX() < 0 || getMaxX() > p.getWidth())
            dx = -dx;
        if (getMinY() < 0 || getMaxY() > p.getHeight())
            dy = -dy;

        p.repaint();
        Toolkit.getDefaultToolkit().sync();
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

class Kafelka extends Rectangle2D.Float {
    Kafelka(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 30;
        this.height = 10;
    }
}

public class Arkanoid {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
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
