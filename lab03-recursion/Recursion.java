
//I CHANGED HOW SOME OF THE TESTS ARE PRINTED OUT, JIM SAID IT WAS FINE :)
//I apologize if this makes the code harder to grade!
/*

 * Recursion.java
 *
 * Starter code for the recursion lab.
 *
 */
// import structure5.*;

public class Recursion {
  // color coding for test cases
  protected static final String ANSI_GREEN = "\u001B[32m";
  protected static final String ANSI_RED = "\u001B[31m";
  protected static final String ANSI_RESET = "\u001B[0m";
  protected static final String ANSI_CYAN = "\u001B[36m";

  // Note: Warmup questions are not graded, but you may wish to
  // complete & test them since later, graded questions build
  // on them.

  // YOU MAY NOT CHANGE THE PARAMETERS OR RETURN TYPES FOR ANY
  // METHOD IN THIS LAB. BE SURE TO PROVIDE ANY MISSING PARTS
  // FOR JAVADOCS THAT SAY [fill this in].

  /***** Warmup 0.1 ********************************************/

  // prints test case numbers and input.
  protected static <T> void printTestCase(String testCase, T test) {
    System.out.println(ANSI_CYAN + "INPUT: " + ANSI_RESET + test);
  }

  /**
   * prints out details of whether a function passed or failed
   * 
   * @param <E>         the type of value expected for the function to pass the
   *                    test
   * @param <T>         the type of value that the function being tested returns
   * @param testCase    the testcase number
   * @param expectation the value expected for the function to pass the test
   * @param result      the value that is returned by the function being tested
   * @param test        the input being passed to the function being tested
   */
  protected static <E, T> void test(String testCase, E expectation, E result, T test) {
    printTestCase(testCase, test);
    System.out
        .println("EXPECTED: " + expectation + " RESULT: " + result);
    if (expectation.equals(result)) {
      pass();
    } else {
      fail();
    }
    System.out.println(" ");
  }

  // print 'FAILED' for failed test cases.
  protected static void fail() {
    System.out.println(ANSI_RED + "FAILED!" + ANSI_RESET);
  }

  // prints 'PASSED' for passed test cases.
  protected static void pass() {
    System.out.println(ANSI_GREEN + "PASSED!" + ANSI_RESET);
  }

  /**
   * Takes a non-negative integer and returns the sum of its digits.
   * 
   * @param n a non-negative integer
   * @pre n is non-negative
   * @post returns the sum of the digits in n.
   * @return the sum of n's digits
   */

  public static int digitSum(int n) {
    if (n == 0 || n == 1) {
      return n;
    }
    return n % 10 + digitSum(n / 10);
  }

  /***** Warmup 0.2 ********************************************/

  /**
   * Given a set of integers and a target number, determines
   * whethere there is a subset of those numbers that sum to the
   * target number.
   *
   * @param setOfNums a set of numbers that may appear in the subset
   * @param targetSum the target value for the subset
   * @return true if some subset of numbers in setOfNums sums to targetSum
   */

  public static boolean canMakeSum(int[] setOfNums, int targetSum) {
    if (targetSum != 0) {
      return canMakeSumHelper(setOfNums, targetSum, 0);
    }
    return false;
  }

  /**
   * Given a set of integers and a target number, determines
   * whethere there is a subset of those numbers that sum to the
   * target number.
   *
   * @param setOfNums a set of numbers that may appear in the subset
   * @param targetSum the target value for the subset
   * @return true if some subset of numbers in setOfNums sums to targetSum
   */
  public static boolean canMakeSumHelper(int[] setOfNums, int targetSum, int i) {

    if (targetSum == 0) {
      return true;
    }
    if (i >= setOfNums.length) {
      return false;
    }
    return canMakeSumHelper(setOfNums, targetSum, i + 1)
        || canMakeSumHelper(setOfNums, targetSum - setOfNums[i], i + 1);
  }

  /***** 1 ***************************************************/

  /**
   * Return number of cannoballs in pyramid with the given `height`.
   *
   * @param height the height of the cannonball tower
   * @pre height is non negative
   * @post returns the number of cannonballs in the tower
   * @return the number of cannonballs in the entire tower
   */
  public static int countCannonballs(int height) {
    if (height == 1 || height == 0) {
      return height;
    } else {
      return (height * height) + countCannonballs(height - 1);
    }
  }

  /***** 2 ***************************************************/

  /**
   * A method that determines if a string reads the same forwards
   * and backwards.
   *
   * @param str the string to check is it is a palindrome.
   * @return true if `str` is a palindrome.
   */
  public static boolean isPalindrome(String str) {
    if (str.length() == 1 || str.length() == 0) {
      return true;
    } else {
      int endIndex = str.length() - 1;
      return str.charAt(0) == str.charAt(str.length() - 1) && isPalindrome(str.substring(1, endIndex));

    }
  }

  /***** 3 ***************************************************/

  /**
   * Checks whether `str` is a string of properly nested and matched
   * parens, brackets, and braces.
   *
   * @param str a string of parens, brackets, and braces
   * @return true if str is properly nested and matched
   */
  public static boolean isBalanced(String str) {
    if (str.equals("")) {
      return true;
    } else if (str.contains("()")) {
      return isBalanced(makeString(str, "()"));
    } else if (str.contains("[]")) {
      return isBalanced(makeString(str, "[]"));
    } else if (str.contains("{}")) {
      return isBalanced(makeString(str, "{}"));
    } else {
      return false;
    }
  }

  /**
   * a function that removes a specified pa
   * 
   * @param expression the string we want to remove parenthesis from
   * @param parenth    the string with the parenthesis we want to remove
   * @return a string with specified parenthesis removed
   */
  public static String makeString(String expression, String parenth) {
    int start = expression.indexOf(parenth);
    String newString = expression.substring(0, start) + expression.substring(start + 2);
    return newString;
  }

  /***** 4 ***************************************************/

  /**
   * A method to print all subsequences of str (order does not matter).
   *
   * @param str string to generate subsequences from.
   */
  public static void subsequences(String str) {
    subsequenceHelper(str, "");
    System.out.println("");
  }

  /**
   * Helper method for subsequences method
   * `soFar` keeps track of the characters currently in the substring
   * being built
   * 
   * @param str   string to generate subsequences from
   * @param soFar str to keep track of the current string
   */
  protected static void subsequenceHelper(String str, String soFar) {
    if (str.equals("")) {
      System.out.print(soFar + ",");
    } else {
      subsequenceHelper(str.substring(1), soFar + str.charAt(0));
      subsequenceHelper(str.substring(1), soFar);
    }
  }

  /***** 5 ***************************************************/

  /**
   * A method to print the binary digits of a number.
   *
   * @param number the number to be converted to binary.
   */
  public static void printInBinary(int number) {
    if (number == 0) {
      System.out.println(0);
    } else {
      printBinaryHelper(number, "");
    }
  }

  /**
   * 
   * @param number      integer to be converted to binary
   * @param stringSoFar variable to keep track of subsequences generated so far.
   */
  protected static void printBinaryHelper(int number, String stringSoFar) {
    if (number == 0) {
      System.out.println(stringSoFar);
    } else {
      printBinaryHelper(number / 2, number % 2 + stringSoFar);
    }
  }

  /***** 6a ***************************************************/

  /**
   * Return whether a subset of the numbers in nums add up to sum,
   * and print them out.
   *
   * @param nums      the list to generate the subset from
   * @param targetSum
   * @return true if a subset that adds to the targetSum has been found
   */
  public static boolean printSubsetSum(int[] nums, int targetSum) {
    return printSubsetSumHelper(nums, targetSum, 0);
  }

  /**
   * prints out the first subset found that adds up to targetSum.
   * 
   * @param nums      a set of numbers that contains possible subsets.
   * @param targetSum the target value for the subset.
   * @param i         the index to keep track of the current position in the list.
   * @return true if a subset has been found that adds up to targetSum.
   */
  protected static boolean printSubsetSumHelper(int[] nums, int targetSum, int i) {
    if (i == nums.length) {
      return targetSum == 0;
    }
    if (printSubsetSumHelper(nums, targetSum - nums[i], i + 1)) {
      System.out.print(nums[i] + ",");
      return true;
    } else if (printSubsetSumHelper(nums, targetSum, i + 1)) {
      return true;
    }
    return false;

  }

  /***** 6b ***************************************************/

  /**
   * Return the number of different ways elements in nums can be
   * added together to equal sum (you do not need to print them all).
   *
   * @param nums      the set of numbers with potential sum solutions
   * @param targetSum the target value for the subset
   * @return the total number of subsets found that add up to targetSum.
   */
  public static int countSubsetSumSolutions(int[] nums, int targetSum) {
    if (targetSum == 0) {
      return 0;
    }
    return countSubsetSumSolutionsHelper(nums, targetSum, 0);
  }

  /**
   * Return the number of different ways elements in nums can be
   * added together to equal sum (you do not need to print them all).
   *
   * @param nums      the set of numbers with potential sum solutions
   * @param targetSum the target value for the subset
   * @param i         the index to keep track of the current position in the array
   * @return the total number of subsets found that add up to targetSum.
   */
  protected static int countSubsetSumSolutionsHelper(int[] nums, int targetSum, int i) {
    if (i >= nums.length) {
      if (targetSum == 0) {
        return 1;
      }
      return 0;
    }
    return countSubsetSumSolutionsHelper(nums, targetSum, i + 1)
        + countSubsetSumSolutionsHelper(nums, targetSum - nums[i], i + 1);
  }

  /***********************************************************/

  /**
   * Add testing code to main to demonstrate that each of your
   * recursive methods works properly.
   *
   * Be sure to consider "corner cases"!
   */

  protected static void testCannonballs() {

    System.out.println("Testing cannonballs: ....");
    test("test 1", 14, countCannonballs(3), 3);
    test("test 2", 385, countCannonballs(10), 10);
    test("test 3", 0, countCannonballs(0), 0);
    test("test 4", 55, countCannonballs(5), 5);
    test("test 5", 5, countCannonballs(2), 2);
    test("test 6", 140, countCannonballs(7), 7);

  }

  protected static void testPalindrome() {
    System.out.println("Testing isPalindrome: ....");
    test("test 1", true, isPalindrome("mom"), "mom");
    test("test 2", false, isPalindrome("deeded"), "deeded");
    test("test 3", true, isPalindrome("ablewasIereIsawelba"), "ablewasIereIsawelba");
    test("test 4", true, isPalindrome(""), "");
    test("test 5", true, isPalindrome("a"), "a");
    test("test 6", true, isPalindrome("racecar"), "racecar");
    test("test 7", false, isPalindrome("hello"), "hello");
    test("test 8", true, isPalindrome(" "), " ");

  }

  protected static void testBalanced() {
    System.out.println("Testing isBalanced: ....");
    test("test 1", true, isBalanced("[{[()()]}]"), "[{[()()]}]");
    test("test 2", true, isBalanced("[{[()()]}][{[()()]}]"), "[{[()()]}][{[()()]}]");
    test("test 3", false, isBalanced("[{[()()]}{]{[()()]}]"), "[{[()()]}{]{[()()]}]");
    test("test 4", false, isBalanced("("), "(");
    test("test 5", true, isBalanced("([])"), "([])");

  }

  protected static void testSubsequence() {
    System.out.println("Testing subsequences: ....");
    subsequences("abc");
    System.out.println();
    subsequences("CSCI136");
    System.out.println();
    subsequences("a");
    System.out.println();
    subsequences("");
    System.out.println();
  }

  protected static void testBinary() {
    System.out.println("Testing printInBinary: ....");
    System.out.println();
    printInBinary(0);
    printInBinary(30);
    System.out.println();
    printInBinary(1);
    System.out.println();
    printInBinary(110);
    System.out.println();
    printInBinary(2048);
    System.out.println();
    printInBinary(43);
    System.out.println();
    printInBinary(1000);
    System.out.println();
    printInBinary(Integer.MAX_VALUE);
    System.out.println();

  }

  protected static void testCanMakeSum() {
    System.out.println("Testing canMakeSum: ....");
    int[] numSet = { 2, 5, 7, 12, 16, 21, 30 };
    test("test 1", true, canMakeSum(numSet, 21), 21);
    test("test 2", false, canMakeSum(numSet, 22), 22);
    test("test 3", false, canMakeSum(numSet, 3), 3);
    test("test 4", true, canMakeSum(numSet, 30), 30);
    test("test 5", true, canMakeSum(numSet, 93), 93);
    test("test 6", false, canMakeSum(numSet, 0), 0);
  }

  protected static void testPrintSubsetSum() {
    System.out.println("Testing printSubsetSum: ....");
    int[] numSet = { 2, 5, 7, 12, 16, 21, 30 };
    System.out.println(printSubsetSum(numSet, 21));
    System.out.println(printSubsetSum(numSet, 22));
    System.out.println(printSubsetSum(numSet, 3));
    System.out.println(printSubsetSum(numSet, 30));

  }

  protected static void testCountSubsetSum() {
    System.out.println("Testing countSubsetSumSolutions: ....");
    int[] numSet = { 2, 5, 7, 12, 16, 21, 30 };
    test("1", 3, countSubsetSumSolutions(numSet, 21), 21);
    test("2", 0, countSubsetSumSolutions(numSet, 22), 22);
    test("3", 0, countSubsetSumSolutions(numSet, 3), 3);
    test("4", 4, countSubsetSumSolutions(numSet, 30), 30);
    test("5", 1, countSubsetSumSolutions(numSet, 93), 93);
    test("6", 0, countSubsetSumSolutions(numSet, 0), 0);

  }

  /**
   * Main method that calls testing methods to verify
   * the functionality of each lab exercise.
   *
   * Please supplement the testing code with additional
   * correctness tests as needed.
   */
  public static void main(String[] args) {
    // testCannonballs();
    // testPalindrome();
    // testBalanced();
    // testSubsequence();
    // testBinary();
    // testCanMakeSum();
    // testPrintSubsetSum();
    // testCountSubsetSum();
    System.out.println(Math.floor(-1.5));

  }
}
