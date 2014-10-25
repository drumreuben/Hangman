import java.net.*;
import java.io.*;
import java.util.*;

public class Hangman{

    //retrieves list of possible words from a txt file on website
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

    //selects a word based on difficulty(number of letters)
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
    //checks whether or not a guess is correct, returns true or false
    public static boolean checkGuess(String guessString, char[] wordChars){
        boolean correctGuess = false;
        char guess = guessString.charAt(0);
        for(int i = 0; i < wordChars.length; i++){
            if(guess == wordChars[i]){
                correctGuess = true;
            }
        }
        return(correctGuess);
    }

    //modify the Boolean array of whether or not a letter has been guessed
    public static boolean[] modifyArray(boolean[] guessedLetter, String guessString, char[] wordChars){
        char guess = guessString.charAt(0);
        for(int i = 0; i < wordChars.length; i++) {
            if (guess == wordChars[i]) {
                guessedLetter[i] = true;
            }
        }
        return guessedLetter;
    }
   //returns true if all letters are guessed, winning the game
    public static boolean checkWin(boolean[] guessedLetter){
        for(boolean letter : guessedLetter){
            if(!letter){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws Exception{
           //retrieves list of possible words once when starting the program
           String[] wordbank = (getWordBank());
     //loop runs each time a new game starts
     while(true) {
            //stores all incorrect guesses
            String[] letters = {
                "null", "null", "null", "null", "null, null"
            };
            int turns = 0;
            int mistakes = 0;
            //User input difficulty
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter desired difficulty: 1-10");
            int difficulty = sc.nextInt();
            //retrieves word
            String word = getWord(difficulty, wordbank);
            //converts to Chars
            char[] wordChars = word.toCharArray();
            System.out.println("Word is " + wordChars.length + " letters long.");
            //true if letter has been correctly guessed, false otherwise
            boolean[] guessedLetter = new boolean[wordChars.length];
            boolean win = false;
            boolean playing = true;
            //loop runs each new turn in the game
            while (playing) {
                //prints the word, substituting “_” for letters not yet guessed
                for (int i = 0; i < wordChars.length; i++) {
                    if (guessedLetter[i]) {
                        System.out.print(wordChars[i] + " ");
                    } else {
                        System.out.print(" _ ");
                    }
                }
               //gets player guess and checks for correctness and repeatness 
    		  System.out.println("\nGuess a letter");
                String guess = sc.next();
                boolean correctGuess = checkGuess(guess, wordChars);
                boolean repeatguess = false;
                for(int i = 0; i < letters.length; i++) {
                    if (letters[i].equals(guess)) {
                        repeatguess = true;
                    }
                }
                if(repeatguess){
                    System.out.println("Already guessed " + guess + " !\n");
                } else if (!correctGuess) {
                    mistakes++;
                    System.out.println("\nSorry, incorrect guess! You have " + (6 - mistakes) + " tries left!\n");
                    letters[mistakes-1] = guess;
                } else {
                    guessedLetter = modifyArray(guessedLetter, guess, wordChars);
                    System.out.println("Correct!\n");
                }
                //detects endgame state, either a win or a lose
                if (mistakes == 6) {
                    playing = false;
                }
                if (checkWin(guessedLetter)) {
                    win = true;
                    playing = false;
                }
                turns++;
            }
            System.out.println(win ? "Congratulations, you guessed the word " + word + "in " + turns + " turns!" : "You are dead! The word was " + word + ".");
            //gets input for whether the player desires a new game.
     System.out.println("Again? y/n");
            if (sc.next().equals("n")) {
                return;
            }
            for(int i = 0; i < 100; i++){
                System.out.println();
            }
        }
    }
}
