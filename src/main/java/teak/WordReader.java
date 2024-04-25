package teak;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class WordReader {

    public static String[] readWordStrings(String fileName)
    {
        ArrayList<String> wordStrings = new ArrayList<String>();
        try {
            Scanner inFile = new Scanner(new File(fileName));

            while(inFile.hasNextLine())
            {
                wordStrings.add(inFile.nextLine());
            }

            inFile.close();
        } catch (Exception e) {
            System.err.println("Could not read file: " + e.getMessage());
        }

        String[] wordArray = new String[wordStrings.size()];

        for(int i = 0; i < wordStrings.size(); i++)
            wordArray[i] = wordStrings.get(i);

        return wordArray;
    }

    public static ArrayList<String> readWordStringsAsList(String fileName)
    {
        ArrayList<String> wordStrings = new ArrayList<String>();
        try {
            Scanner inFile = new Scanner(new File(fileName));

            while(inFile.hasNextLine())
            {
                wordStrings.add(inFile.nextLine());
            }

            inFile.close();
        } catch (Exception e) {
            System.err.println("Could not read file: " + e.getMessage());
        }

        return wordStrings;
    }
}
