package teak.events;

import teak.App;
import teak.GameState;
import teak.Word;

public class SubmitEvent implements IEventHandler<char[]> {
    public void handleEvent(char[] word)
    {
        App app = App.getInstance();

        char[] empty = {' ', ' ', ' ', ' ', ' '};

        int numGuesses = app.getNumGuesses();
        Word[] answers = app.getAnswers();
        boolean allCorrect = true;
        
        app.setSingleGuess(numGuesses, new Word(word));
        app.setGuess(empty);
        app.setPosition(0);

        app.setNumGuesses(numGuesses + 1);

        int amountCorrect = 0;

        for(int i = 0; i < App.WORDLES; i++)
        {
            if(answers[i].equals(new Word(word))) amountCorrect++;
            else allCorrect = false;
        }

        app.setWordlesSolved(amountCorrect);
        if(allCorrect) app.setGameState(GameState.END);
    }
}
