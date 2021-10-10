import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

class Ball extends Ellipse2D.Float {
    Board board;

    int x, y;
    int deltaX, deltaY;

    Ball(Board board) {
        this(board, 1, 1);
    }

    Ball(Board board, int deltaX, int deltaY) {
        this.board = board;

        this.width = Integer.max(board.getWidth(), board.getHeight()) / 100;
        this.height = this.width;

        x = (int) (board.getWidth() / 2 - this.getCenterX());
        y = (int) (board.getHeight() / 2 - this.getCenterY());

        this.deltaX = deltaX;
        this.deltaY = -deltaY; // makes the ball start moving upwards
    }

    void nextStep() {
        x += deltaX;
        y += deltaY;

        if (getMinX() < 0 || getMaxX() > board.getWidth())
            deltaX = -deltaX;
        if (getMinY() < 0 || getMaxY() > board.getHeight())
            deltaY = -deltaY;

        board.repaint();
    }
}

class Tile extends Rectangle2D.Float {
    Tile(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
    }
}

// class Brick extends Tile {
// }

class Platform extends Tile {
    Platform(Board board) {
        this(board.getWidth() / 7, 10, board.getWidth() / 2, board.getHeight() * 90 / 100);
    }

    Platform(int width, int height, int x, int y) {
        super(width, height, x, y);
    }

    public void setX(int x) {
        this.x = x;
    }
}

class Board extends JPanel implements MouseMotionListener, Runnable {
    Thread ballEngine;

    Ball ball;
    Platform platform;

    Board() {
        addMouseMotionListener(this);

        this.ball = new Ball(this);
        this.platform = new Platform(this);

        ballEngine = new Thread(this);
        ballEngine.start();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2d = (Graphics2D) graphics;

        graphics2d.fill(ball);
        graphics2d.fill(platform);
    }

    public void mouseMoved(MouseEvent e) {
        platform.setX(e.getX() - (int) platform.getCenterX());
        repaint();
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void run() {
        try {
            while (true) {
                ball.nextStep();
                Thread.sleep(15);
            }
        } catch (InterruptedException e) {
        }
    }
}

public class NewArkanoid {
    // static final Tile TILESET_CLASSIC =

    static int width;
    static int height;

    public static void main(String[] args) {
        width = Integer.valueOf(args[0]);
        height = Integer.valueOf(args[1]);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                Board board = new Board();

                JFrame jf = new JFrame();
                jf.add(board);

                jf.setTitle("Test grafiki");
                jf.setSize(width, height);
                jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jf.setVisible(true);
            }
        });

    }
}
