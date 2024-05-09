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
        int index = 0;
        int numGuesses = App.getInstance().getNumGuesses();
        numGuesses = numGuesses < 2 ? 1 : numGuesses;

        for(int j = 0; j < 8; j++)
        {
            for(int i = 0; i < 4; i++)
            {
                int y = 75 + (j * (80 + (numGuesses * 40)));
                drawWordle(g, 40 + (i * 225), y + AppGUI.getInstance().getYOffset(), App.getInstance().getAnswer(index));
                index++;
            }
        }

        g.setColor(AppGUI.BG_COLOR);
        g.fillRect(0, 0, 1000, 60);
        g.setFont(new Font("Arial", Font.BOLD, 26));
        g.setColor(AppGUI.TEXT_COLOR);
        g.drawString(AppGUI.TITLE, 440, 40);
        g.setFont(new Font("Arial", Font.PLAIN, 22));
        g.drawString("Solved: " + App.getInstance().getWordlesSolved() + "/32", 100, 40);

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
                gui.drawWordleSquare(g, j * (AppGUI.SQUARE_SIZE + AppGUI.SQUARE_OFFSET) + x, y, letter);
            }

            for(int k = 0; k < guess.length; k++)
            {
                gui.drawWordleSquare(g, k * (AppGUI.SQUARE_SIZE + AppGUI.SQUARE_OFFSET) + x, y + (app.getNumGuesses() + 1) * (AppGUI.SQUARE_SIZE + AppGUI.SQUARE_OFFSET), new WordleLetter(Position.INITIAL, Character.isLetterOrDigit(guess[k]) ? Character.toUpperCase(guess[k]) : correctLetters[k]));
            }
        }

        for(int i = 0; i < app.getNumGuesses(); i++)
        {
            for(int j = 0; j < guess.length; j++)
            {
                Position[] validity = {Position.ABSENT,Position.ABSENT,Position.ABSENT,Position.ABSENT,Position.ABSENT};
                if(app.getNumGuesses() > 0) validity = answer.compare(app.getGuesses()[i]);
                WordleLetter letter = new WordleLetter(validity[j], Character.toUpperCase(app.getGuesses()[i].getWord()[j]));
                gui.drawWordleSquare(g, j * (AppGUI.SQUARE_SIZE + AppGUI.SQUARE_OFFSET) + x, y + i * (AppGUI.SQUARE_SIZE + AppGUI.SQUARE_OFFSET), letter);
            }

            for(int k = 0; k < guess.length; k++)
            {
                gui.drawWordleSquare(g, k * (AppGUI.SQUARE_SIZE + AppGUI.SQUARE_OFFSET) + x, y + (app.getNumGuesses()) * (AppGUI.SQUARE_SIZE + AppGUI.SQUARE_OFFSET), new WordleLetter(Position.INITIAL, Character.isLetterOrDigit(guess[k]) ? Character.toUpperCase(guess[k]) : correctLetters[k]));
            }
        }
    }
}
