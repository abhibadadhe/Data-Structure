import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JFrame implements ActionListener, KeyListener 
{
    private static final int GRID_SIZE = 20;
    private static final int CELL_SIZE = 30;
    private static final int INITIAL_LENGTH = 3;

    private LinkedList<Point> snake;
    private Point food;
    private Direction currentDirection;

    private enum Direction 
    {
        UP, DOWN, LEFT, RIGHT
    }
    private class Point 
    {
        int x, y;
        public Point(int x, int y) 
        {
            this.x = x;
            this.y = y;
        }
    }
    public SnakeGame() 
    {
        snake = new LinkedList<>();
        currentDirection = Direction.RIGHT;

        initializeSnake();
        spawnFood();

        setTitle("Snake Game");
        setSize(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(this);

        Timer timer = new Timer(220, this);
        timer.start();
    }
    private void initializeSnake() 
    {
        // Initialize the snake with a specific length
        for (int i = 0; i < INITIAL_LENGTH; i++) 
        {
            snake.addLast(new Point(i, 0));
        }
    }
    private void spawnFood() 
    {
        Random random = new Random();
        int x, y;

        do 
        {
            x = random.nextInt(GRID_SIZE);
            y = random.nextInt(GRID_SIZE);
        } 
        while (snake.contains(new Point(x, y)));

        food = new Point(x, y);
    }
    private void move() 
    {
        Point head = snake.getFirst();
        Point newHead = new Point(head.x, head.y);

        switch (currentDirection) 
        {
            case UP:
                newHead.y--;
                break;
            case DOWN:
                newHead.y++;
                break;
            case LEFT:
                newHead.x--;
                break;
            case RIGHT:
                newHead.x++;
                break;
        }
        if (newHead.x < 0 || newHead.x >= GRID_SIZE || newHead.y < 0 || newHead.y >= GRID_SIZE
                || snake.contains(newHead)) 
                {
            gameOver();
            return;
        }
        snake.addFirst(newHead);

        if (newHead.x == food.x && newHead.y == food.y) 
        {
            // Snake ate the food, spawn new food and don't remove the tail
            spawnFood();
        } 
        else 
        {
            // Snake didn't eat the food, remove the tail
            snake.removeLast();
        }
        repaint();
    }
    private void gameOver() 
    {
        JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.CANCEL_OPTION);
        System.exit(0);
         return;
    }
    private void drawGrid(Graphics g) 
    {
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);
        for (Point point : snake) 
        {
            g.fillRect(point.x * CELL_SIZE, point.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
        g.setColor(Color.RED);
        g.fillRect(food.x * CELL_SIZE, food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        move();
    }
    @Override
    public void paint(Graphics g) 
    {
        super.paint(g);
        drawGrid(g);
    }
    @Override
    public void keyPressed(KeyEvent e) 
    {
        switch (e.getKeyCode()) 
        {
            case KeyEvent.VK_UP:
                if (currentDirection != Direction.DOWN)
                    currentDirection = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
                if (currentDirection != Direction.UP)
                    currentDirection = Direction.DOWN;
                break;
            case KeyEvent.VK_LEFT:
                if (currentDirection != Direction.RIGHT)
                    currentDirection = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                if (currentDirection != Direction.LEFT)
                    currentDirection = Direction.RIGHT;
                break;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) 
    {
        // Not needed for this example
    }
    @Override
    public void keyReleased(KeyEvent e) 
    {
        // Not needed for this example
    }
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            SnakeGame snakeGame = new SnakeGame();
            snakeGame.setVisible(true);
        });
    }
}