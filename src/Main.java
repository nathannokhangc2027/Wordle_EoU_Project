import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public class ConsoleColors {
        // Reset
        public static final String RESET = "\033[0m";  // Text Reset
        // Regular Colors
        public static final String BLACK = "\033[0;30m";   // BLACK
        public static final String RED = "\033[0;31m";     // RED
        public static final String GREEN = "\033[0;32m";   // GREEN
        public static final String YELLOW = "\033[0;33m";  // YELLOW
        public static final String BLUE = "\033[0;34m";    // BLUE
        public static final String PURPLE = "\033[0;35m";  // PURPLE
        public static final String CYAN = "\033[0;36m";    // CYAN
        public static final String WHITE = "\033[0;37m";   // WHITE
    }

    public static int countLines(String nameOfFile) throws FileNotFoundException {

        File file = new File(nameOfFile);
        Scanner scanner = new Scanner(file);

        int lineCount = 0;

        while (scanner.hasNextLine()) {

            lineCount++;
            scanner.nextLine();

        }

        return lineCount;

    }

    public static String[] readFileIntoStringArray(String nameOfFile) throws FileNotFoundException {

        int linesInFile = countLines(nameOfFile);
        String[] array = new String[linesInFile];

        int index = 0;

        File file = new File(nameOfFile);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {

            array[index++] = scanner.nextLine();

        }

        return array;
    }

    public static int randInt(int max) {
        Random rand = new Random();
        int randInt = rand.nextInt(max + 1);
        return randInt;
    }


    public static String checkLetters(String guess, String answerWord) {
        String hint = "";
        String currentLetter = "";
        String currentAnsLetter = "";
        boolean yellow = false;
        boolean green = false;
        int yellowCheck = 0;
        for (int i = 0; i < 5; i++) {
            currentLetter = guess.substring(i, i + 1);
            currentAnsLetter = answerWord.substring(i, i + 1);
            if (currentLetter.equals(currentAnsLetter)) {
                hint = hint + ("\033[0;32m" + currentLetter);
                green = true;
            } else for (int j = 0; j < 5; j++) {
                if (currentLetter.equals(answerWord.substring(j, j + 1)) && green == false && yellow == false) {

                    hint = hint + ("\033[0;33m" + currentLetter);
                    yellow = true;
                }
            }
            if (yellow == false && !currentLetter.equals(currentAnsLetter)) {
                hint = hint + ("\033[0;31m" + currentLetter);
            }
            green = false;
            yellow = false;
        }

        return hint;
    }

    public static boolean valid(String playerGuess, String answerWord) throws FileNotFoundException {
        String[] listOfGuessableWords = readFileIntoStringArray("listOfGuessableWords");
        for (int i = 0; i < listOfGuessableWords.length; i++) {
            if (playerGuess.equals(listOfGuessableWords[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean win(String playerGuess, String answerWord) {
        if (playerGuess.equals(answerWord)) {
            return true;
        }
        return false;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String[] listOfWords = readFileIntoStringArray("listOfWords");
        String[] listOfGuessableWords = readFileIntoStringArray("listOfGuessableWords");
        int numForListOfWords = randInt(3103);
        String answerWord = listOfWords[numForListOfWords];
        Scanner scanner = new Scanner(System.in);
        int guesses = 0;
        for (int i = 0; i < 6; i++) {
            System.out.println("\033[0;37m" + "Guess " + (guesses + 1) + ":");
            String playerGuess = scanner.nextLine();

            while (valid(playerGuess, answerWord) == false) {
                System.out.println("\033[0;37m" + "Word is Invalid. Guess another Word:");
                String newPlayerGuess = scanner.nextLine();
                playerGuess = newPlayerGuess;
            }
            if (valid(playerGuess, answerWord) == true) {
                System.out.println(checkLetters(playerGuess, answerWord));
                guesses += 1;
            }
            if (win(playerGuess, answerWord) == true) {
                System.out.println("\033[0;37m" + "Congratulations, you beat this wordle in " + guesses + " guesses.");
                break;
            }
            if (win(playerGuess, answerWord) == false && guesses == 6){
                System.out.println("\033[0;37m" + "Sorry, you've run out of guesses. The word was " + answerWord + ".");
            }
        }
    }


    }
