package teak;

import teak.events.EventSystem;
import teak.events.KeystrokeEvent;
import teak.events.SubmitEvent;
import teak.render.EndScene;
import teak.render.IntroScene;
import teak.render.PlayingScene;

import java.util.ArrayList;

// Singleton to manage game state.
public class App {
    // This class being a singleton allows only one instance of itself. 
    // This avoids duplication of data and allows for easy global state management with a call to App.getInstance()
    private static volatile App instance = null;

    private EventSystem eventSystem; // An events system allows for a decoupled approach to internal communication.
    private ArrayList<String> guessList;
    private ArrayList<String> answerList;
    private char[] guess;
    private int position;
    private Word[] answers;
    private GameState gameState;
    private int numGuesses;
    private Word[] guesses;
    private int wordlesSolved;

    public static final int NUM_GUESSES = 37; // NO HARD CODED VALUES/MAGIC NUMBERS
    public static final int WORD_LENGTH = 5;
    public static final int WORDLES = 32;

    public static App getInstance()
    {
        if(instance != null) return instance;

        synchronized(App.class) // for thread safety
        {
            if(instance == null) instance = new App();
        }

        return instance;
    }

    private App() // private prevents external initialization of class. All true initialization of variables occurs in the run method.
    {
        
    }

    public void run()
    {
        eventSystem = new EventSystem();
        answers = new Word[WORDLES];
        gameState = GameState.INTRO;
        position = 0;
        guess = new char[5];
        numGuesses = 0;
        guesses = new Word[37];
        guesses[0] = new Word(new char[5]);
        wordlesSolved = 0;

        AppGUI gui = AppGUI.getInstance();
        gui.addScene("intro", new IntroScene());
        gui.addScene("playing", new PlayingScene());
        gui.addScene("end", new EndScene());

        gui.setScene("intro");

        registerEvents();
        initWordLists();
        initAnswers();
    }

    private void registerEvents()
    {
        eventSystem.subscribe(KeystrokeEvent.class, new KeystrokeEvent());
        eventSystem.subscribe(SubmitEvent.class, new SubmitEvent());
    }

    private void initWordLists()
    {
        answerList = WordReader.readWordStringsAsList("wordle.txt");
        guessList = WordReader.readWordStringsAsList("valid_wordle.txt");
    }

    private void initAnswers()
    {
        String[] answerStrings = new String[WORDLES];

        for(int i = 0; i < answerStrings.length; i++)
        {
            int index = (int) (Math.random() * answerList.size());
            answerStrings[i] = answerList.get(index);
            answerList.remove(index); // remove answers from list to avoid duplication
        }

        for(int i = 0; i < answerStrings.length; i++)
        {
            Word word = new Word(answerStrings[i]);
            answers[i] = word;
        }
    }

    public EventSystem getEventSystem()
    {
        return eventSystem;
    }

    public ArrayList<String> getAnswerList()
    {
        return answerList;
    }

    public ArrayList<String> getGuessList()
    {
        return guessList;
    }

    public char[] getGuess()
    {
        return guess;
    }

    public Word[] getAnswers()
    {
        return answers;
    }

    public void setAnswers(Word[] answers)
    {
        this.answers = answers;
    }

    public Word getAnswer(int index)
    {
        return answers[index];
    }

    public void setAnswer(int index, Word answer)
    {
        answers[index] = answer;
    }

    public void setGuess(char[] guess)
    {
        if(guess.length != 5) return;
        this.guess = guess;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;

        AppGUI gui = AppGUI.getInstance();
        if(gameState == GameState.INTRO) gui.setScene("intro");
        else if(gameState == GameState.PLAYING) gui.setScene("playing");
        else if(gameState == GameState.END) gui.setScene("end");
        gui.repaint();
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public int getNumGuesses()
    {
        return numGuesses;
    }

    public void setNumGuesses(int numGuesses)
    {
        if(numGuesses >= NUM_GUESSES) gameState = GameState.END; // max number of guesses exceeded
        this.numGuesses = numGuesses;
    }

    public Word[] getGuesses()
    {
        return guesses;
    }

    public void setGuesses(Word[] guesses)
    {
        this.guesses = guesses;
    }

    public void setSingleGuess(int index, Word guess)
    {
        this.guesses[index] = guess;
    }

    public int getWordlesSolved()
    {
        return wordlesSolved;
    }

    public void setWordlesSolved(int wordlesSolved)
    {
        this.wordlesSolved = wordlesSolved;
    }   
}
