import structure5.*;

class Biterator extends AbstractIterator<Integer> {
    // the number to be made an `iterable`
    private Long number;
    // the index to keep track of the iteration
    private int bitIndex;

    /**
     * The constructor for the Biterator class
     * 
     * @param number a number to be iterated over
     */
    public Biterator(Long number) {
        this.number = number;
        this.reset();
    }

    /**
     * a function that sets the iteration back to its starting point
     */
    public void reset() {
        this.bitIndex = 0;
    }

    /**
     * a function that checks if the iteration is complete, returns true if not,
     * otherwise returns false
     */
    public boolean hasNext() {
        return this.bitIndex < 64;
    }

    /**
     * a function that returns the current bit and updates the iteration index
     */
    public Integer next() {
        int bit = get();
        bitIndex++;
        return bit;
    }

    /**
     * a function that returns the current bit but does NOT update the iterator
     * index
     */
    public Integer get() {
        return getBit(number, bitIndex);
    }

    /**
     * a function that gets the current bit from the number
     * 
     * @param number   the number being iterated over
     * @param bitIndex the index of the iterator
     * @return returns a 1 or a 0 depending on what the current bit is in the binary
     *         representation
     */
    public static int getBit(Long number, int bitIndex) {
        Long tmp = (1L << bitIndex) & number;
        if (tmp == 0) {
            return 0;
        }
        return 1;
    }
}