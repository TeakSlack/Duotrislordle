package teak.render;

import java.awt.*;

import teak.App;
import teak.AppGUI;
import teak.Position;
import teak.Word;
import teak.WordleLetter;

public class PlayingScene implements IScene {
    public void render(Graphics g)
    {
        drawWordle(g, 50, 75, App.getInstance().getAnswer(0));

        g.setColor(AppGUI.BG_COLOR);
        g.fillRect(0, 0, 1000, 60);
        g.setFont(new Font("Arial", Font.BOLD, 26));
        g.setColor(AppGUI.TEXT_COLOR);
        g.drawString(AppGUI.TITLE, 440, 40);
    }

    private void drawWordle(Graphics g, int x, int y, Word answer)
    {
        App app = App.getInstance();
        AppGUI gui = AppGUI.getInstance();
        char[] guess = app.getGuess();

        char[] correctLetters = new char[5];
        for(int i = 0; i < app.getNumGuesses(); i++)
        {
            Position[] validity = answer.compare(app.getGuesses()[i]);
            for(int j = 0; j < validity.length; j++)
            {
                if(validity[j] == Position.PRESENT) correctLetters[j] = Character.toUpperCase(app.getGuesses()[i].getWord()[j]);
            }
        }

        if(app.getNumGuesses() == 0)
        {
            for(int j = 0; j < guess.length; j++)
            {
                WordleLetter letter = new WordleLetter(Position.ABSENT, ' ');
                gui.drawWordleSquare(g, j * (AppGUI.SQUARE_SIZE + AppGUI.SQUARE_OFFSET) + x, y + (AppGUI.SQUARE_SIZE + AppGUI.SQUARE_OFFSET), letter);
            }

            for(int k = 0; k < guess.length; k++)
            {
                gui.drawWordleSquare(g, k * (AppGUI.SQUARE_SIZE + AppGUI.SQUARE_OFFSET) + x, y + (app.getNumGuesses() + 1) * (AppGUI.SQUARE_SIZE + AppGUI.SQUARE_OFFSET), new WordleLetter(Position.ABSENT, Character.isLetterOrDigit(guess[k]) ? guess[k] : correctLetters[k]));
            }
        }

        for(int i = 0; i < app.getNumGuesses(); i++)
        {
            for(int j = 0; j < guess.length; j++)
            {
                Position[] validity = {Position.ABSENT,Position.ABSENT,Position.ABSENT,Position.ABSENT,Position.ABSENT};
                if(app.getNumGuesses() > 0) validity = app.getGuesses()[i].compare(answer);
                WordleLetter letter = new WordleLetter(validity[j], app.getGuesses()[i].getWord()[j]);
                gui.drawWordleSquare(g, j * (AppGUI.SQUARE_SIZE + AppGUI.SQUARE_OFFSET) + x, y + i * (AppGUI.SQUARE_SIZE + AppGUI.SQUARE_OFFSET), letter);
            }

            for(int k = 0; k < guess.length; k++)
            {
                gui.drawWordleSquare(g, k * (AppGUI.SQUARE_SIZE + AppGUI.SQUARE_OFFSET) + x, y + (app.getNumGuesses() + 1) * (AppGUI.SQUARE_SIZE + AppGUI.SQUARE_OFFSET), new WordleLetter(Position.ABSENT, Character.isLetterOrDigit(guess[k]) ? guess[k] : correctLetters[k]));
            }
        }
    }
}
