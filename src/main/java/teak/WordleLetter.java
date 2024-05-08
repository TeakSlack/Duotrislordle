package teak;

public class WordleLetter {
    public char letter;
    public Position pos;

    public WordleLetter()
    {
        pos = Position.INITIAL;
    }

    public WordleLetter(Position pos, char letter)
    {
        this.pos = pos;
        this.letter = letter;
    }
}
