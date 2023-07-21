import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class WholeWordgen extends WordGen {

    protected Table<String> t;

    /**
     * constructor to the WholeWordgen class.
     * 
     * @param windowSize
     */
    public WholeWordgen(Integer windowSize) {
        super(windowSize);
        t = new Table<>();
    }

    /**
     * a function to read in the file
     */
    public String readText(Scanner s) {
        return super.readText(s);
    }

    /**
     * a function to add all the add all the words to an array.
     * 
     * @param text the text to be added to the array.
     * @return the array containing all the words
     */
    public ArrayList<String> wordsList(String text) {
        return new ArrayList<String>(Arrays.asList(text.split("[\\n\\s]")));
    }

    /**
     * A function to build the main table
     * 
     * @param wordList the array containing all the sentences of the book
     * @return the table containing probabilities
     */
    public Table<String> buildWordTable(ArrayList<String> wordList) {
        int k = windowSize;
        for (int i = 0; i < wordList.size(); i++) {
            String sequence = "";
            for (int j = i; j < i + k; j++) {
                sequence += wordList.get(j % wordList.size()) + " ";
            }
            String proceedingWord = wordList.get((i + k) % wordList.size());
            t.add(sequence, proceedingWord);
        }
        return t;
    }

    /**
     * a function to create a text for the starting point of the text generation.
     * 
     * @param wordList the array containing all the words.
     * @return a string representing the seed
     */
    public String makeSeed(ArrayList<String> wordList) {
        String seed = "";
        for (int i = 0; i < windowSize; i++) {
            seed += wordList.get(i) + " ";
        }
        return seed;
    }

    /**
     * a function to randomly generate the text
     * 
     * @param table  the table containing the trained model data
     * @param tokens the number of tokens to generate
     * @return the string representing the generated text
     */
    public String generateText(Table<String> table, int tokens) {
        String generatedText = makeSeed(wordsList(text));
        String currentSequence = generatedText;
        for (int i = 0; i < tokens; i++) {
            String nextWord = table.choose(currentSequence);
            generatedText += nextWord + " ";
            currentSequence += nextWord + " ";
            Integer lowerBound = findNewLowerIndex(currentSequence);
            currentSequence = currentSequence.substring(lowerBound);
        }
        return generatedText;
    }

    /**
     * a function to progress the lower index of the dynamic window
     * 
     * @param sentence the sentence to determine the new lower bound for the
     *                 sentence
     * @return an integer representing the lower bound for window
     */
    public static int findNewLowerIndex(String sentence) {
        Boolean done = false;
        int i = 0;
        while (!done) {
            if (sentence.charAt(i) == ' ') {
                i++;
                done = true;
                break;
            }
            i++;
        }
        return i;
    }

    /**
     * entry point to the WholeWordgen class
     * 
     * @param args an array containing the user input
     */
    public static void main(String[] args) {
        Integer windowSize = handleUserInput(args, false);
        WholeWordgen w = new WholeWordgen(windowSize);
        String text = w.readText(w.s);
        ArrayList<String> words = w.wordsList(text);
        Table<String> table = w.buildWordTable(words);
        System.out.println(w.generateText(table, OUTPUT_LENGTH));

    }

}