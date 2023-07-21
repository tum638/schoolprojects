import structure5.*;
import java.util.Iterator;

/**
 * TODO: implement and document this class
 * You may add helper methods and member variables as you see fit.
 */
class LexiconNode implements Comparable<LexiconNode>, Iterable<LexiconNode> {

    /** single letter stored in this node */
    protected char letter;

    /** true if this node ends some path that defines a valid word */
    protected boolean isWord;

    // number of chidren tracker
    protected int numOfChildren;

    // children of lexiconNode
    protected LexiconNode[] children;

    /**
     * Initializes a LexiconNode.
     * 
     * @param letter The letter that this node represents.
     * @param isWord True if this node represents the end of a word.
     */
    LexiconNode(char letter, boolean isWord) {
        children = new LexiconNode[26];
        this.numOfChildren = 0;
        this.letter = letter;
        this.isWord = isWord;
    }

    /**
     * TODO: Compare this LexiconNode to another.
     * (You should just compare the characters stored at the Lexicon
     * Nodes.)
     */

    /**
     * Compares two LexiconNodes.
     * 
     * @param o The other LexiconNode.
     * @return a value < 0 if this node is lexicographically earlier, 0 if
     *         the two are equal, and a value > 0 if this node is later.
     */
    public int compareTo(LexiconNode o) {
        return this.letter - o.letter;
    }

    /**
     * Adds a child LexiconNode to the appropriate position in the
     * child data structure.
     * 
     * @param ln A child LexiconNode.
     */
    public void addChild(LexiconNode ln) {
        this.numOfChildren++;
        children[ln.letter - 'a'] = ln;
    }

    /**
     * Returns the LexiconNode corresponding to the given child character,
     * or null if no such child exists.
     * 
     * @param ch A character.
     * @return A LexiconNode, or null.
     */
    public LexiconNode getChild(char ch) {
        return children[ch - 'a'];
    }

    /**
     * TODO: Remove LexiconNode child for 'ch' from child data structure
     */

    /**
     * Removes the child associated with the given character.
     * Does nothing if the child does not exist.
     * 
     * @param ch A character.
     */
    public void removeChild(char ch) {
        children[ch - 'a'] = null;
        this.numOfChildren--;
    }

    /**
     * Returns an iterator that iterates through every LexiconNode
     * in the trie.
     *
     * @return An iterator for the trie.
     */
    public Iterator<LexiconNode> iterator() {
        return new LexiconNodeChildrenIterator(children);
    }

    public void print() {
        for (LexiconNode child : children) {
            if (child != null) {
                System.out.print(child.letter + " ");
            }
        }
        System.out.println(" ");
    }

    public static void main(String[] args) {
        LexiconNode node = new LexiconNode('a', false);
        LexiconNode child = new LexiconNode('b', false);
        LexiconNode childd = new LexiconNode('a', false);
        System.out.println(child.compareTo(node));

        System.out.println(node.letter);
        node.print();
    }
}
