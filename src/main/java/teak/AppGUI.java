package teak;

import javax.swing.*;

import teak.events.KeystrokeEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Singleton to manage GUI.
public class AppGUI extends JComponent implements KeyListener {
    private static volatile AppGUI instance = null;

    private App app;
    private JFrame frame;

    private static final String TITLE = "Duotrislordle";

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

        frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);
        frame.setResizable(true);
        frame.getContentPane().add(this);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g)
    {

    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        // fire keystroke event
        app.getEventSystem().publish(KeystrokeEvent.class, e);
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {

    }
}
