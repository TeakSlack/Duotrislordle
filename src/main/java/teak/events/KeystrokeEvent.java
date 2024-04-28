package teak.events;

import java.awt.event.KeyEvent;
import teak.App;
import teak.GameState;

public class KeystrokeEvent implements IEventHandler<KeyEvent> {
    public void handleEvent(KeyEvent e)
    {
        App app = App.getInstance();

        if(app.getGameState() == GameState.INTRO)
            handleIntro(e, app);
        else if(app.getGameState() == GameState.PLAYING)
                handlePlaying(e, app);
        else if(app.getGameState() == GameState.END)
            handleEnd(e, app);
    }

    private void handlePlaying(KeyEvent e, App app)
    {
        char[] guess = app.getGuess();
        int position = app.getPosition();

        if(e.getKeyCode() == KeyEvent.VK_ENTER && position == 5)
        {
            String guessString = "";
            for(char c : guess) guessString += c;
            if(!app.getGuessList().contains(guessString.toLowerCase())) return;

            app.getEventSystem().publish(SubmitEvent.class, guess);
        }

        if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && position != 0)
        {
            app.setPosition(position - 1);
            guess[position - 1] = ' ';
            app.setGuess(guess);
            return;
        }

        if(!Character.isLetter(e.getKeyCode())) return;
        if(position == 5) return;

        guess[position] = Character.toUpperCase(e.getKeyChar());
        app.setGuess(guess);
        app.setPosition(position + 1);
    }

    private void handleIntro(KeyEvent e, App app)
    {
        app.setGameState(GameState.PLAYING);
    }

    private void handleEnd(KeyEvent e, App app)
    {

    }
}
