import java.net.*;
import java.io.*;
import java.util.*;

public class Hangman {

    public static String[] getWordBank() throws Exception {

        URL wordbank = new URL("https://raw.githubusercontent.com/Tom25/Hangman/master/wordlist.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(wordbank.openStream()));
        ArrayList<String> words = new ArrayList<String>();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            words.add(inputLine);
        }
        in.close();
        return words.toArray(new String[0]);
    }

    public static String getWord(int difficulty, String[] wordbank){
        String word;
        while(true){
            word = wordbank[(int)(Math.random()*wordbank.length)];
            if(word.length() < difficulty + 5 && word.length() > difficulty + 3){
                break;
            }
        }
        return word;
    }

    public static void main(String[] args) throws Exception{
        //User input difficulty
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter desired difficulty: 1-10");
        int difficulty = sc.nextInt();
        //retrieves word
        String[] wordbank = (getWordBank());
        String word = getWord(difficulty, wordbank);
        //converts to Chars
        char[] wordChars = word.toCharArray();
        for(char i : wordChars){
            System.out.print(i + " ");
        }
    }
}