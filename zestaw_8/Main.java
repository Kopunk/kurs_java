import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.geom.*;
import java.util.concurrent.TimeUnit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Plansza extends JPanel {
    Shape figura;
    Shape figura2;
    Point2D punkt;
    Graphics2D g2d;
    String str;
    boolean isFill;
    Color color;
    GradientPaint gradient;
    RadialGradientPaint radialGradient;
    BufferedImage img;
    Color color1, color2;

    Plansza(Shape figura) {
        this.figura = figura;
    }

    Plansza(Shape figura, Shape figura2) {
        this(figura);
        this.figura2 = figura2;
    }

    Plansza(Shape figura, Point2D punkt) {
        this(figura);
        this.punkt = punkt;
    }

    Plansza(Shape figura, Point2D punkt, String str) {
        this(figura);
        this.punkt = punkt;
        this.str = str;
    }

    Plansza(Shape figura, Shape figura2, String str) {
        this(figura, figura2);
        this.str = str;
    }

    public void setFill(boolean fill) {
        this.isFill = fill;
    }

    public void setGradient(GradientPaint gradient) {
        this.gradient = gradient;
    }

    public void setRadialGradient(RadialGradientPaint gradient) {
        this.radialGradient = gradient;
    }

    public void setImage(BufferedImage img) {
        this.img = img;
    }

    public void setColors(Color c1, Color c2) {
        this.color1 = c1;
        this.color2 = c2;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2d = (Graphics2D) g;

        g2d.draw(figura);
        if (isFill) {
            g2d.setPaint(new Color(0, 255, 0));
            g2d.fill(figura);
        } else if (gradient != null) {
            g2d.setPaint(gradient);
            g2d.fill(figura);
        } else if (radialGradient != null) {
            g2d.setPaint(radialGradient);
            g2d.fill(figura);
        } else if (img != null) {
            // g2d = img.createGraphics();
            // g2d.drawImage(img, 0, 0, null);
            g2d.setPaint(new TexturePaint(img, figura.getBounds2D()));
            g2d.fill(figura);
        }

        if (figura2 != null) {
            g2d.draw(figura2);

            if (color1 != null && color2 != null) {
                g2d.setColor(color1);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2d.fill(figura);
                g2d.setColor(color2);
                g2d.fill(figura2);
            }
        }

        if (str != null) {
            g2d.drawString(str, 10, 10);
        }
        if (punkt != null) {
            g2d.draw(new Rectangle2D.Float((float) punkt.getX(), (float) punkt.getY(), 1, 1));
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        int sleepTimeS = 1;
        Plansza p;
        JFrame jf = new JFrame();
        String str;

        jf.setTitle("Test grafiki");
        jf.setSize(400, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // przykład
        Shape obj1 = new Rectangle2D.Float(100, 100, 140, 100);
        p = new Plansza(obj1);
        jf.add(p);
        jf.setVisible(true);
        TimeUnit.SECONDS.sleep(sleepTimeS);

        // cw 8.2
        Shape prostokat1 = new Rectangle2D.Float(50, 100, 140, 100);
        Shape elipsa1 = new Ellipse2D.Float(60, 60, 50, 60);
        if (elipsa1.intersects((Rectangle2D) prostokat1)) {
            str = "przecinaja sie";
        } else {
            str = "nie przecinaja sie";
        }
        p = new Plansza(prostokat1, elipsa1, str);

        jf.add(p);
        jf.setVisible(true);
        TimeUnit.SECONDS.sleep(sleepTimeS);

        // cw 8.3
        // prostokąt zawiera elipsę
        Shape prostokat2 = new Rectangle2D.Float(50, 100, 140, 100);
        Shape elipsa2 = new Ellipse2D.Float(60, 110, 50, 60);
        if (elipsa2.contains((Rectangle2D) prostokat2)) {
            str = "elipsa zawiera prostokat";
        } else if (prostokat2.contains(elipsa2.getBounds2D())) {
            str = "prostokat zawiera elipse";
        } else {
            str = "nic sie nie zawiera";
        }
        p = new Plansza(prostokat2, elipsa2, str);

        jf.add(p);
        jf.setVisible(true);
        TimeUnit.SECONDS.sleep(sleepTimeS);

        // elipsa zawiera peostokąt
        Shape prostokat3 = new Rectangle2D.Float(80, 120, 10, 10);
        Shape elipsa3 = new Ellipse2D.Float(60, 110, 50, 60);
        if (elipsa3.contains((Rectangle2D) prostokat3)) {
            str = "elipsa zawiera prostokat";
        } else if (prostokat3.contains(elipsa3.getBounds2D())) {
            str = "prostokat zawiera elipse";
        } else {
            str = "nic sie nie zawiera";
        }
        p = new Plansza(prostokat3, elipsa3, str);

        jf.add(p);
        jf.setVisible(true);
        TimeUnit.SECONDS.sleep(sleepTimeS);

        // 8.4
        Point2D punkt1 = new Point2D.Float(45, 45);
        Rectangle2D elipsa4 = new Ellipse2D.Float(40, 40, 40, 40).getBounds2D();
        if (elipsa4.contains(punkt1)) {
            str = "elipsa zawiera punkt";
        } else {
            str = "nic sie nie zawiera";
        }
        p = new Plansza(elipsa4, punkt1, str);

        jf.add(p);
        jf.setVisible(true);
        TimeUnit.SECONDS.sleep(sleepTimeS);

        // cw 8.5
        Shape prostokat4 = new Rectangle2D.Float(100, 100, 140, 100);
        p = new Plansza(prostokat4);
        p.setFill(true);

        jf.add(p);
        jf.setVisible(true);
        TimeUnit.SECONDS.sleep(sleepTimeS);

        // cw 8.6
        Shape elipsa5 = new Ellipse2D.Float(40, 40, 240, 200);
        p = new Plansza(elipsa5);

        GradientPaint blackToGreen = new GradientPaint(240, 200, new Color(0, 0, 0), 40, 40, new Color(0, 255, 0));

        p.setGradient(blackToGreen);

        jf.add(p);
        jf.setVisible(true);

        TimeUnit.SECONDS.sleep(sleepTimeS);

        // cw 8.7
        Shape elipsa6 = new Ellipse2D.Float(40, 40, 240, 200);
        p = new Plansza(elipsa6);

        Color[] gradientColors = { new Color(255, 0, 0), new Color(0, 255, 0), new Color(255, 0, 0) };
        float[] dist = { 0.0f, 0.1f, 1.0f };
        RadialGradientPaint redGreen = new RadialGradientPaint(new Point2D.Float(100, 100), 50,
                new Point2D.Float(100, 100), dist, gradientColors, CycleMethod.REPEAT);

        p.setRadialGradient(redGreen);

        jf.add(p);
        jf.setVisible(true);

        TimeUnit.SECONDS.sleep(sleepTimeS);

        // cw 8.8
        Shape elipsa7 = new Ellipse2D.Float(80, 80, 180, 150);
        p = new Plansza(elipsa7);

        BufferedImage img = ImageIO.read(new File("logo.png"));
        p.setImage(img);

        jf.add(p);
        jf.setVisible(true);

        TimeUnit.SECONDS.sleep(sleepTimeS);

        // cw 8.9
        Shape elipsa8 = new Ellipse2D.Float(50, 120, 100, 50);
        Shape elipsa9 = new Ellipse2D.Float(80, 150, 100, 50);
        p = new Plansza(elipsa8, elipsa9);

        p.setColors(new Color(255, 0, 0), new Color(0, 10, 255));

        jf.add(p);
        jf.setVisible(true);

        TimeUnit.SECONDS.sleep(sleepTimeS);

        // cw 8.10
        Shape luk = new Arc2D.Float(50, 50, 220, 150, 45, 190, Arc2D.OPEN);
        p = new Plansza(luk);

        jf.add(p);
        jf.setVisible(true);

        TimeUnit.SECONDS.sleep(sleepTimeS);

        // cw 8.11
        Shape luk2 = new Arc2D.Float(50, 50, 220, 150, 45, 190, Arc2D.OPEN);
        p = new Plansza(luk);

        jf.add(p);
        jf.setVisible(true);

        TimeUnit.SECONDS.sleep(sleepTimeS);


    }
}
