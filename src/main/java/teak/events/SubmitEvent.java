package teak.events;

import teak.App;
import teak.GameState;
import teak.Word;
import teak.Position;

public class SubmitEvent implements IEventHandler<char[]> {
    public void handleEvent(char[] word)
    {
        App app = App.getInstance();

        Position[] correctPos = {Position.PRESENT, Position.PRESENT, Position.PRESENT, Position.PRESENT, Position.PRESENT};
        char[] empty = {' ', ' ', ' ', ' ', ' '};

        int numGuesses = app.getNumGuesses();
        Word[] answers = app.getAnswers();
        boolean allCorrect = true;
        
        app.setSingleGuess(numGuesses, new Word(word));
        app.setGuess(empty);
        app.setPosition(0);

        app.setNumGuesses(numGuesses + 1);

        for(int i = 0; i < App.WORDLES; i++)
        {
            Position[] pos = answers[i].compare(new Word(word));
            if(!pos.equals(correctPos)) allCorrect = false;
        }

        if(allCorrect) app.setGameState(GameState.END);
    }
}
