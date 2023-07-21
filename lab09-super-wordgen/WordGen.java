import java.util.Scanner;

import structure5.Assert;

// Students, please implement this class

public class WordGen {
    // analysis level
    protected int windowSize;

    protected static final int OUTPUT_LENGTH = 500;
    protected Scanner s;

    // the main table
    private Table<Character> t;
    protected String text;

    // the seed
    protected String seed;

    /**
     * constructor to the Wordgen class
     * 
     * @param windowSize the analysis level
     */
    public WordGen(Integer windowSize) {
        // initialize the scanner object
        s = new Scanner(System.in);
        // initialize the table
        t = new Table<>();
        text = "";
        this.windowSize = windowSize;
    }

    /**
     * function to read all the text into a string
     * 
     * @param s the scanner object
     * @returns the string containing the text
     */
    public String readText(Scanner s) {
        // initialize a new string buffer
        StringBuffer textBuffer = new StringBuffer();
        System.out.println("Enter text if you haven't already done so...");
        System.out.println(" ");
        while (s.hasNextLine()) {
            String line = s.nextLine();
            textBuffer.append(line);
            textBuffer.append("\n");
        }
        text = textBuffer.toString();
        return text;

    }

    /**
     * a function to generat the seed from the initial characters of the string
     * 
     * @param windowSize the analysis level
     * @return a string representing text to generate more text from
     */
    public String makeSeed(Integer windowSize) {
        //
        seed = text.substring(0, windowSize + 1);
        return seed;
    }

    /**
     * a function to run the whole program
     * 
     * @param windowSize the analysis level
     */
    public static void run(Integer windowSize) {
        WordGen w = new WordGen(windowSize);
        String text = w.readText(w.s);
        Table<Character> table = w.buildTable(text);
        System.out.println(w.generateText(w.makeSeed(windowSize), OUTPUT_LENGTH, table));
    }

    /**
     * a function that builds the main table
     * 
     * @param text the string of text containing the training data
     * @return a table containing the probalities of characters following a specific
     *         length k string.
     */
    public Table<Character> buildTable(String text) {
        int k = windowSize;
        // loop to go over all the text
        for (int i = 0; i < text.length(); i++) {
            String sequence = "";
            // loop to create length k character sequences
            for (int j = i; j < i + k; j++) {
                sequence += text.charAt(j % text.length()); // the modular arithmetic is to allow smooth wrapping
                                                            // around.
            }
            Character suffix = text.charAt((i + k) % text.length());
            t.add(sequence, suffix);

        }
        return t;
    }

    /**
     * a function to randomly generate text
     * 
     * @param seed   the text to start off the random generations from.
     * @param tokens the number length of the output to be generated
     * @param t      the table containing the data Wordgen has been trained from
     * @return a string containing the randomly generated text
     */
    public String generateText(String seed, Integer tokens, Table<Character> t) {
        Assert.pre(seed.length() >= windowSize, "seed must be at least " + windowSize + " characters in length.");
        System.out.println(" ");
        // initialize the text from the seed
        String generatedText = seed.substring(seed.length() - windowSize);
        // initialize the current sequence being looked at also from the seed.
        String currentSequence = generatedText;
        for (int i = 0; i < tokens; i++) {
            // statistically choose the next character from the trained data.
            Character nextChar = t.choose(currentSequence);
            // append the chosen character to the end of the generated string
            generatedText += nextChar;
            // progress the value of the current string sequence
            currentSequence = generatedText.substring(i + 1, i + windowSize + 1);
        }

        return seed.charAt(0) + generatedText;
    }

    /**
     * a function to guide user in entering correct input
     * 
     * @param isCharacterAnalysis tells the function whether to print Wordgen or
     *                            WholeWordgen depending on the class.
     */
    public static void incorrectInputFlag(Boolean isCharacterAnalysis) {
        System.out.println("");
        if (isCharacterAnalysis) {
            System.out
                    .println("Usage: java Wordgen <k> < <input.txt>");
            System.out.println("OR");
            System.out.println("Usage: java WordGen <k> <You input text goes here>");
        } else {
            System.out
                    .println("Usage: java WholeWordgen <k> < <input.txt>");
            System.out.println("OR");
            System.out.println("Usage: java WholeWordgen <k> <You input text goes here>");
        }

        System.out.println("");
    }

    /**
     * a function that handles user input
     * 
     * @param args the array containing user input
     * @return an integer representing the analysis level
     */
    public static Integer handleUserInput(String[] args, Boolean isCharacterAnalysis) {
        if (args.length < 1) {
            System.out.println("");
            System.out.println("Please input the analysis level");
            incorrectInputFlag(isCharacterAnalysis);
            System.exit(1);
        }
        Integer windowSize = null;
        try {
            windowSize = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("The input must be an integer");
            incorrectInputFlag(isCharacterAnalysis);
            System.exit(1);
        }
        return windowSize;
    }

    /**
     * entry point to the wordgen class
     */
    public static void main(String[] args) {
        run(handleUserInput(args, true));
    }

}
