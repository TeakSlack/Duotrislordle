package teak;

public class Word {
    private char[] word;

    public Word()
    {
        word = new char[6];
    }

    public Word(String word) // Words are 6 characters in length. All letters are lower case and it may not contain digits.
    {
        this.word = new char[5];
        if(word.length() > 5) word = word.substring(0, 4);

        for(int i = 0; i < word.length(); i++)
        {
            if(Character.isDigit(word.charAt(i))) continue;
            this.word[i] = Character.toLowerCase(word.charAt(i));
        }
    }

    public Word(char[] word)
    {   
        this.word = new char[5];

        if(word.length < 5)
        {
            this.word = word;
            return;
        }

        for(int i = 0; i < 5; i++)
        {
            this.word[i] = Character.toLowerCase(word[i]);
        }
    }

    public boolean equals(Word w1)
    {
        if(word != w1.word) return false;
        return true;
    }

    public char[] getWord()
    {
        return word;
    }

    public Position[] compare(Word w1)
    {
        Position[] pos = new Position[5];

        char[] duplicate = new char[5];

        for(int i = 0; i < word.length; i++) duplicate[i] = word[i];

        for(int i = 0; i < word.length; i++)
        {
           if(duplicate[i] != w1.getWord()[i]) continue;

           pos[i] = Position.PRESENT;
           duplicate[i] = ' ';
        }

        for(int i = 0; i < word.length; i++)
        {
            if(duplicate[i] == ' ') continue;
            int letterPos = getCharPosition(w1.getWord()[i], duplicate);
            if(letterPos != -1) pos[i] = Position.WRONGPOS;
            else pos[i] = Position.ABSENT;
        }

        return pos;
    }

    private int getCharPosition(char key, char[] arr)
    {
        for(int i = 0; i < arr.length; i++)
        {
            if(Character.toLowerCase(arr[i]) == Character.toLowerCase(key)) return i;
        }

        return -1;
    }

    public String toString()
    {
        String str = "";
        for(int i = 0; i < word.length; i++)
        {
            str += word[i];
        }

        return str;
    }
}
