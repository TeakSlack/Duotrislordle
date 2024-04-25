package teak;

import teak.events.EventSystem;
import teak.events.KeystrokeEvent;
import java.util.ArrayList;

// Singleton to manage game state.
public class App {
    private static volatile App instance = null;

    private EventSystem eventSystem;
    private ArrayList<String> validGuesses;
    private ArrayList<String> answers;

    public static App getInstance()
    {
        if(instance != null) return instance;

        synchronized(App.class) // for thread safety
        {
            if(instance == null) instance = new App();
        }

        return instance;
    }

    private App() // private prevents external initialization of class
    {
        eventSystem = new EventSystem();

        registerEvents();
        initWordLists();
    }

    private void registerEvents()
    {
        eventSystem.subscribe(KeystrokeEvent.class, new KeystrokeEvent());
    }

    private void initWordLists()
    {
        answers = WordReader.readWordStringsAsList("wordle.txt");
        validGuesses = WordReader.readWordStringsAsList("valid_wordle.txt");
    }

    public EventSystem getEventSystem()
    {
        return eventSystem;
    }

    public ArrayList<String> getAnswersList()
    {
        return answers;
    }

    public ArrayList<String> getValidGuesses()
    {
        return validGuesses;
    }

    public void run()
    {

    }
}
