import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

public class Game extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;
    private final int WIDTH = 800, HEIGHT = 600;
    private Rectangle player;
    private ArrayList<Rectangle> obstacles;
    private Random rand;
    private int score;

    public Game() {
        setFocusable(true);
        setPreferredSize(new DimensionUIResource(WIDTH, HEIGHT));
        addKeyListener(this);
        player = new Rectangle(WIDTH / 2 - 10, HEIGHT - 50, 20, 20);
        obstacles = new ArrayList<Rectangle>();
        rand = new Random();
        score = 0;
    }

    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        g.fillRect(player.x, player.y, player.width, player.height);
        g.setColor(Color.RED);
        for (Rectangle obstacle : obstacles) {
            g.fillRect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
        }
        g.setColor(Color.BLUE);
        g.drawString("Score: " + score, 10, 20);
    }

    public void update() {
        for (Rectangle obstacle : obstacles) {
            obstacle.y += 5;
            if (obstacle.intersects(player)) {
                gameOver();
            }
        }
        if (rand.nextInt(10) == 0) {
            int x = rand.nextInt(WIDTH - 20);
            int y = -20;
            Rectangle obstacle = new Rectangle(x, y, 20, 20);
            obstacles.add(obstacle);
        }
        score++;
        repaint();
    }

    public void gameOver() {
        System.out.println("Game Over!");
        System.exit(0);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        Game game = new Game();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        while (true) {
            game.update();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (player.x > 0) {
                player.x -= 5;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (player.x < WIDTH - player.width) {
                player.x += 5;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}
