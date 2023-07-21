import structure5.*;

public class LexiconNodeChildrenIterator extends AbstractIterator<LexiconNode> {
    // current position of iterator
    protected int current;

    // a list of lexicon nodes
    protected List<LexiconNode> children = new Vector<>();

    /**
     * Constructor for the iterator class
     * 
     * @param children the array containing the children of the LexiconNode
     */
    public LexiconNodeChildrenIterator(LexiconNode[] children) {
        for (int i = 0; i < children.length; i++) {
            if (children[i] != null) {
                this.children.addLast(children[i]);
            }
        }
        this.reset();
    }

    /**
     * a function that gets the current LexiconNode in the list of children
     */
    public LexiconNode get() {
        return this.children.get(current);
    }

    /**
     * function that returns true if there are more nodes to iterate over otherwise
     * returns false
     */
    public boolean hasNext() {
        return this.current < this.children.size();
    }

    /**
     * function that returns the current lexiconNode and increments the pointer
     */
    public LexiconNode next() {
        LexiconNode node = this.get();
        this.current++;
        return node;
    }

    /**
     * function that resets the iteration to the start position
     */
    public void reset() {
        this.current = 0;
    }

    /**
     * entry point to the LexiconIterator function
     * 
     * @param args
     */
    public static void main(String[] args) {
        LexiconNode[] nodes = new LexiconNode[8];
        nodes[1] = new LexiconNode('w', false);
        nodes[3] = new LexiconNode('y', false);
        nodes[7] = new LexiconNode('r', false);

        LexiconNodeChildrenIterator i = new LexiconNodeChildrenIterator(nodes);
        for (LexiconNode node : i) {
            System.out.println(node.letter);
        }
    }

}