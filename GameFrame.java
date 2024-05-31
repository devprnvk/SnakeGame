import javax.swing.JFrame;

/**
 * The GameFrame class represents the main window for the Snake game.
 * It extends JFrame and sets up the window properties, including adding the GamePanel to the frame.
 *
 * <p>This class is responsible for creating the game window, setting its title, default close operation,
 * size, visibility, and location. The game panel, where the game logic and drawing occur, is added to this frame.</p>
 *
 * @see GamePanel
 * @see JFrame
 * @see SnakeGame
 *
 * @author Pranav Kale
 */
public class GameFrame extends JFrame {
    /**
     * Constructs a new GameFrame, sets its properties, and adds the GamePanel to it.
     */
    GameFrame(){

            GamePanel panel = new GamePanel();

            this.add(panel);

            this.setTitle("Snake");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
            this.pack();
            this.setVisible(true);
            this.setLocationRelativeTo(null);
        }
}
