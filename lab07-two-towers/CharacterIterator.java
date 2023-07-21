import structure5.*;

public class CharacterIterator extends AbstractIterator<Character> {
    // the iteration tracker
    private int i;
    // the iterable (the string to be iterated over)
    private String string;

    /**
     * The constructor to the CharacterIterator class
     * 
     * @param string the string to be iterated over
     */
    public CharacterIterator(String string) {
        this.i = 0;
        this.string = string;
    }

    /**
     * a function that returns the current string and advances the iteration tracker
     * 
     * @returns the current character
     */
    public Character next() {
        // Character ret = this.string.charAt(this.i);
        // this.i++;
        // return ret;
        return this.string.charAt(this.i++);
    }

    /**
     * a function that checks to see if there are any more characters in the string
     * 
     * @returns true if there are more characters, otherwise returns true.
     */
    public boolean hasNext() {
        return (this.i < this.string.length());
    }

    /**
     * NOT IMPLMENTED
     * a function meant to reset the iteration to the initial point
     */
    public void reset() {
        return;
    }

    /**
     * NOT IMPLEMENTED
     * a function that returns the current character in the string
     */
    public Character get() {
        return (char) 0;
    }

    /**
     * Entry point to the character iterator program
     * 
     * @param args an array containing user input
     */
    public static void main(String[] args) {
        for (char c : new CharacterIterator("Hello world!")) {
            System.out.println(c);
        }
    }
}
