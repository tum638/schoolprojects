// Replace this with your code.

import structure5.*;

public class SubsetIterator<E> extends AbstractIterator<Vector<E>> {

    // The iteration index
    protected Long i;
    // The vector with values
    protected Vector<E> v;

    /**
     * the constructor to the SubsetIterator class
     * 
     * @param v the vector to generate subsets from
     */
    public SubsetIterator(Vector<E> v) {
        this.v = v;
        this.reset();
    }

    /**
     * a function to get a subset and updates the iteration index
     */
    public Vector<E> next() {
        Vector<E> subset = get();
        this.i++;
        return subset;
    }

    /**
     * a function that resets the iteration to the starting point
     */
    public void reset() {
        this.i = 1L;
    }

    /**
     * a function that gets a subset but does NOT update the iteration index
     */
    public Vector<E> get() {
        // initialize a new vector
        Vector<E> subset = new Vector<>();
        // initialize a new byterator
        Biterator byter = new Biterator(this.i);
        int pos = 0;
        // figure out which positions have a one in them
        for (int bit : byter) {
            if (bit == 1) {
                subset.add(v.get(pos));
            }
            pos++;
        }
        return subset;
    }

    /**
     * a function that checks if there are no more subsets to be created
     */
    public boolean hasNext() {
        return (this.i) < (1 << v.size());
    }

    /**
     * Entry point to test the subiterator
     * 
     * @param args an array for the user to provide static input
     */
    public static void main(String[] args) {
        Vector<Integer> a = new Vector<>();
        a.add(1);
        a.add(2);
        a.add(3);
        a.add(4);
        a.add(5);
        SubsetIterator<Integer> it = new SubsetIterator<>(a);
        for (Vector<Integer> g : it) {
            System.out.println(g);
        }

    }

}
