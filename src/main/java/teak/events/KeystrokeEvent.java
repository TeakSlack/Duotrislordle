package teak.events;

import java.awt.event.KeyEvent;

public class KeystrokeEvent implements IEventHandler<KeyEvent> {
    public void handleEvent(KeyEvent e)
    {
        System.out.println(e.getKeyChar());
    }
}
