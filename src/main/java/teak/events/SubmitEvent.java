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

        int numGuesses = app.getNumGuesses();
        Word[] answers = app.getAnswers();
        boolean allCorrect = true;

        app.setNumGuesses(numGuesses + 1);

        for(Word answer : answers)
        {
            Position[] pos = answer.compare(new Word(word));
            if(!pos.equals(correctPos)) allCorrect = false;
            System.out.print(pos.equals(correctPos) + " ");
            // TODO: implement rendering code
        }

        if(allCorrect) app.setGameState(GameState.END);
    }
}
