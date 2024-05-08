package teak;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.util.Map;
import java.util.HashMap;
import teak.events.KeystrokeEvent;
import teak.render.*;

// Singleton to manage GUI.
public class AppGUI extends JComponent implements KeyListener, MouseWheelListener {
    private static volatile AppGUI instance = null;

    private App app;
    private JFrame frame;
    private Map<String, IScene> scenes;
    private IScene scene;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    public static final int SQUARE_SIZE = 40;
    public static final int SQUARE_OFFSET = 2;
    public static final String TITLE = "Duotrislordle";
    public static final Color BG_COLOR = new Color(18,18,19);
    public static final Color ABSENT_COLOR = new Color(58, 58, 60);
    public static final Color WRONGPOS_COLOR = new Color(181, 169, 59);
    public static final Color CORRECT_COLOR = new Color(83, 141, 78);
    public static final Color TEXT_COLOR = new Color(248, 248, 248);

    public static AppGUI getInstance()
    {
        if(instance != null) return instance;

        synchronized(AppGUI.class) // for thread safety
        {
            if(instance == null) instance = new AppGUI();
        }

        return instance;
    }

    private AppGUI() // private prevents external initialization of class
    {
        app = App.getInstance();

        scenes = new HashMap<>();

        frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.setResizable(true);
        frame.getContentPane().add(this);
        frame.getContentPane().setBackground(BG_COLOR);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void addScene(String sceneName, IScene scene)
    {
        scenes.put(sceneName, scene);
    }

    public void setScene(String sceneName)
    {
        scene = scenes.get(sceneName);
    }

    public void paintComponent(Graphics g)
    {
        if(scene == null) return;

        scene.render(g);
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        // fire keystroke event
        app.getEventSystem().publish(KeystrokeEvent.class, e);
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {

    }

    private void drawBorderedRect(Graphics g, int x, int y, Color color)
    {
        g.setColor(color);
        g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
        g.setColor(AppGUI.BG_COLOR);
        g.fillRect(x + 2, y + 2, SQUARE_SIZE - (2 * 2), SQUARE_SIZE - (2 * 2));

        repaint();
    }

    public void drawWordleSquare(Graphics g, int x, int y, WordleLetter letter)
    {
        if(letter.pos == Position.INITIAL)
        {
            drawBorderedRect(g, x, y, ABSENT_COLOR);
        } else
        {
            Color c = BG_COLOR;
            if(letter.pos == Position.ABSENT) c = ABSENT_COLOR;
            if(letter.pos == Position.WRONGPOS) c = WRONGPOS_COLOR;
            if(letter.pos == Position.PRESENT) c = CORRECT_COLOR;
            g.setColor(c);
            g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
        }
        
        g.setColor(TEXT_COLOR);
        g.setFont(new Font("Consolas", Font.BOLD, 20));
        if(Character.isLetter(letter.letter))
            g.drawString(Character.toString(letter.letter), x + 14, y + 30); // rules were made to be broken (line 28 app.java)

        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        
    }
}
