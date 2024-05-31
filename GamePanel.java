import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.JPanel;

/**
 * The GamePanel class is a JPanel that implements the core functionality of the Snake game.
 * It handles the game logic, drawing, and user input.
 *
 * <p>This class includes methods to start the game, draw the game components,
 * generate new apples, move the snake, check for collisions, and display the game over screen.</p>
 *
 * <p>The game board is represented as a grid, with the snake moving to eat apples and grow in size.
 * The game ends if the snake collides with itself or the walls.</p>
 *
 * @author Pranav Kale
 */
public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    /**
     * Constructs the GamePanel, sets its properties, and starts the game.
     */
    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(new Color(88, 184, 84));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }

    /**
     * Starts the game by generating a new apple, setting the game running state to true,
     * and starting the game timer.
     */
    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, (ActionListener) this);
        timer.start();
    }

    /**
     * Overrides the paintComponent method to draw the game elements on the panel.
     *
     * @param g the Graphics object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    /**
     * Draws the game grid, snake, apple, and score on the panel.
     *
     * @param g the Graphics object to draw with
     */
    public void draw(Graphics g) {
        if(running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.setColor(new Color(50, 102, 48));
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(26, 94, 31));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(34, 122, 41));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //Score points
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2,g.getFont().getSize());
        } else {
            gameOver(g);
        }
        //END
        }

    /**
     * Generates a new apple at a random location on the game grid.
     */
    public void newApple() {
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    /**
     * Moves the snake in the current direction and updates its position.
     */
    public void move() {
        for(int i = bodyParts;i>0;i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }
    }

    /**
     * Checks if the snake's head has collided with an apple.
     * If so, increases the snake's body size and the score, and generates a new apple.
     */
    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    /**
     * Checks if the snake has collided with itself or the borders of the game area.
     * If a collision is detected, the game is stopped.
     */
    public void checkCollisions() {
        // Checks if head collides with body
        for(int i=bodyParts;i>0;i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        // Checks if head collides with left border
        if(x[0] < 0) {
            running = false;
        }
        // Checks if head collides with right border
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // Checks if head collides with upper border
        if(y[0] < 0) {
            running = false;
        }
        // Checks if head collides with lower border
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if(!running) {
            timer.stop();
        }
    }

    /**
     * Displays the "Game Over" screen with the final score.
     *
     * @param g the Graphics object to draw with
     */
    public void gameOver(Graphics g) {
        //Game Over Text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over!",(SCREEN_WIDTH - metrics1.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
        // Game Over Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten,(SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten))/2,g.getFont().getSize());
    }
    /**
     * Handles the game action events, updating the game state and repainting the game panel.
     *
     * @param e the action event
     */
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

    }

    /**
     * The MyKeyAdapter class handles keyboard input to control the direction of the snake.
     */
    public class MyKeyAdapter extends KeyAdapter {
        /**
         * Responds to key presses to change the direction of the snake.
         *
         * @param e the key event
         */
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if (direction != 'R') {
                        direction = 'L';
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    if (direction != 'L') {
                        direction = 'R';
                    }
                }
                case KeyEvent.VK_UP -> {
                    if (direction != 'D') {
                        direction = 'U';
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    if (direction != 'U') {
                        direction = 'D';
                    }
                }
            }
        }
    }

}
