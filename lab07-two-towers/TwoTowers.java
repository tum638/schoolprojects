import structure5.Vector;

public class TwoTowers {
    // a storing the whole tower
    protected Vector<Integer> v;
    // the height of the tower
    protected Double height = 0D;

    /**
     * The constructor two the tower class
     * 
     * @param n the number of blocks on the tower
     */
    public TwoTowers(int n) {
        // initialize a vector
        v = new Vector<>();
        // add n blocks to the vector
        for (int i = 1; i <= n; i++) {
            v.add(i - 1, i);
            height += Math.sqrt(i);
        }
    }

    /**
     * A function that calculates the best and second best sum
     */
    public void getBestSum() {

        Double halfHeight = height / 2; // half the height of the whole tower

        Double minDiff = Double.POSITIVE_INFINITY; // variable to keep track of the minimum differerence between the
                                                   // best tower and H/2
        Double secondBestDiff = Double.POSITIVE_INFINITY; // variable keeping track of the minimum difference between
                                                          // the second best tower and H/2
        Vector<Integer> bestSoFar = new Vector<>(); // a variable to store the best tower encountered so far
        Vector<Integer> secondBestSofar = new Vector<>(); // a variable to keep track of the second best tower
                                                          // encountered so far
        Double total = 0D;
        Double bestTotal = 0D;
        Double secondBestTotal = 0D;
        // instantiate the iterator object
        SubsetIterator<Integer> it = new SubsetIterator<>(v);
        // loop through all the subsets
        for (Vector<Integer> vec : it) {
            total = sum(vec);
            // check if either best subset exceeds minDiff or secondBestSubset exceeds
            // second best min difference.

            // is the total height of this subset closer to second best difference but less than best difference?
            if (halfHeight - total <= secondBestDiff && halfHeight - total >= minDiff && total < halfHeight) {
                secondBestDiff = halfHeight - total; // updated second best difference first
                secondBestSofar = vec; // update the second best tower
                secondBestTotal = total; // updated the second best total
            // is the height this subset greater than the best height?
            } else if (halfHeight - total < minDiff && total < halfHeight) {
                secondBestDiff = minDiff;
                minDiff = halfHeight - total;
                secondBestSofar = bestSoFar;
                bestSoFar = vec;
                secondBestTotal = bestTotal;
                bestTotal = total;
            }
        }
        // print the results
        PrintResults(halfHeight, bestSoFar, secondBestSofar, bestTotal, secondBestTotal);
    }

    /**
     * A function that prints out the string representation of a vector
     * 
     * @param vec the vector to be printed out
     */
    public void PrintVector(Vector<Integer> vec) {
        System.out.print("[ ");
        for (Integer value : vec) {
            System.out.print(value + " ");
        }
        System.out.print("]");
    }

    /**
     * a fucntion that formats the results of printing the best and second best
     * subset
     * 
     * @param halfHeight      the height which subset heights are being compared to
     * @param bestSoFar       the vector containing the best subset encountered so.
     * @param secondBestSoFar a vector containing the second best vector
     *                        encountered.
     * @param sumBest         the best sum
     * @param sumSecondBest   the second best sum
     */
    public void PrintResults(Double halfHeight, Vector<Integer> bestSoFar, Vector<Integer> secondBestSoFar,
            Double sumBest, Double sumSecondBest) {
        // print statements to format the results as per the lab requirement
        System.out.println("Half height (h/2) is: " + halfHeight);
        System.out.print("The best subset height is: ");
        PrintVector(bestSoFar);
        System.out.print(" = " + sumBest);
        System.out.println(" ");
        System.out.print("The second best subset height is: ");
        PrintVector(secondBestSoFar);
        System.out.print(" = " + sumSecondBest);
        System.out.println(" ");
    }

    /**
     * a function that computes the sum of all the square roots of elements in a
     * vector
     * 
     * @param vec the vector with elements to be summed
     * @return a value for the sum of all the square roots of elements in the vector
     */
    public Double sum(Vector<Integer> vec) {
        Double sum = 0D;
        for (int i = 0; i < vec.size(); i++) {
            sum += Math.sqrt(vec.get(i));
        }
        return sum;
    }
    /**
     * Entry point to the program
     * @param args an array containing the number of blocks the tower should contain
     */
    public static void main(String[] args) {
        // guidance for user to provide correctly formatted input
        if (args.length < 1) {
            System.out.println(" ");
            System.out.println("Please input an integer");
            System.out.println(" ");
            System.out.println("Example Usage: javac *.java && java TwoTowers 15");
            System.out.println(" ");
            System.exit(0);
        }
        TwoTowers t = new TwoTowers(Integer.parseInt(args[0]));
        t.getBestSum();
    }

}