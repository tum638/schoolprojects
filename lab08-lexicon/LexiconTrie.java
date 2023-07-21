import structure5.*;
import java.util.Iterator;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * STUDENTS: fill in the following class with your implementation.
 * We have included the names of the methods that you should fill in
 * as a starting point.
 *
 * Don't forget to add Javadocs!
 */
public class LexiconTrie implements Lexicon {
    // The entry point to the Lexicon trie
    protected LexiconNode root;

    /**
     * the constructor for the lexicon trie
     */
    public LexiconTrie() {
        // initializes the root to the trie with a space
        root = new LexiconNode(' ', false);
    }

    /**
     * function to make words case insensitive
     * 
     * @param word the word to be made case insensitive
     * @return a string in lower case
     */
    public String caseInsensitize(String word) {
        return word.toLowerCase();
    }

    /**
     * function that adds words to the lexicon
     */
    public boolean addWord(String word) {

        String wordNotCased = this.caseInsensitize(word); // change word to lowercase
        if (containsWord(wordNotCased)) { // is the word already in the lexicon?, return false if true.
            return false;
        }
        return addWordHelper(wordNotCased, root);
    }

    /**
     * a recursive helper method for the addWord function
     * 
     * @param word the word to be added to the lexicon
     * @param root the root of the lexicon
     * @return true if the word was succesfully added
     */
    public boolean addWordHelper(String word, LexiconNode root) {
        // has the whole word been added to the lexicon character by character?
        if (word.length() == 0) {
            return true;
        }
        // is this character of the word already in the lexicon?
        if (root.getChild(word.charAt(0)) != null) {

            if (word.length() == 1) { // has the whole word been added except for the last character?
                LexiconNode child = root.getChild(word.charAt(0));
                child.isWord = true; // mark this character as a word.
                return true;
            }
            // else the length of the word is not yet one, we haven't finished adding word
            // to lexicon.
            return addWordHelper(word.substring(1), root.getChild(word.charAt(0)));
            // else, the current character of the word is not alrealdy in the lexicon
        } else {
            LexiconNode child;
            // is the length of the word now one?
            if (word.length() == 1) {
                // create a new child since we know this character is not already in the lexicon
                child = new LexiconNode(word.charAt(0), true); // mark the character as a word since it was the last
                                                               // character
                root.addChild(child); // add the character to the lexicon
                return true;
            }
            // this character does not denote the end of a word, do NOT mark it as a word,
            // just create a character.
            child = new LexiconNode(word.charAt(0), false);
            root.addChild(child); // add character to lexicon
            return addWordHelper(word.substring(1), child);

        }
    }

    /**
     * an iterative function that adds words from a file to the lexicon
     * 
     * @param filename the file containing the words
     * @returns the number of words addded to the lexicon
     */
    public int addWordsFromFile(String filename) {
        Scanner s;
        int numWords = 0;
        try {
            s = new Scanner(new FileInputStream(filename));
            while (s.hasNextLine()) {
                String word = s.nextLine();
                String wordNotCased = caseInsensitize(word);
                addWord(wordNotCased);
                numWords++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("The specified file was not found.");
            System.exit(1);
        }
        return numWords;
    }

    /**
     * a function that removes a word from the lexicon, it removes all the
     * characters from the lexicon.
     * 
     * @param word the word to be removed from the lexicon
     * @returns true if the word was succesfully removed, otherwise false
     */
    public boolean removeWord(String word) {
        String wordNotCased = caseInsensitize(word); // make the word case insensitive
        removeWordsHelper(wordNotCased, root);
        return true;

    }

    /**
     * a recursive helper function for the remove word function
     * 
     * @param word the word to be removed from the lexicon
     * @param root the root of the lexicon trie
     */
    public void removeWordsHelper(String word, LexiconNode root) {
        // have we reached the leaf node of the trie?
        if (word.length() == 0) {
            root.isWord = false; // turn of the isWord marker for the word
        } else {
            LexiconNode child = root.getChild(word.charAt(0));
            removeWordsHelper(word.substring(1), child);
            if (!child.isWord && child.numOfChildren == 0) { // if the child is not a word and has no children?
                root.removeChild(child.letter); // remove the child completely from ther tree
            }
        }

    }

    /**
     * NB: I COULD HAVE KEPT A FIELD TO KEEP TRACK OF THE NUMBER OF WORDS BUT NEEDED
     * TO PRACTICE RECURSION.
     * a function that counts the number of words in the trie,
     */
    public int numWords() {
        return numWordsHelper(root);
    }

    /**
     * a recursive helper function to the numWords function
     * 
     * @param root the root of the lexicon
     * @return the numver of words in the lexicon
     */
    public int numWordsHelper(LexiconNode root) {
        // is the tree an empty tree?
        if (root == null) {
            return 0; // tree is empty, return 0 words
        }
        // instantiate iterator for children of root
        int wordsNumber = 0;
        for (LexiconNode node : root) {
            wordsNumber += numWordsHelper(node); // count all words and add them to the wordsNumber function recursively
        }
        if (root.isWord) { // is the root a word?
            wordsNumber++; // root is a word, increment the words number variable
        }
        return wordsNumber;
    }

    /**
     * a function to check if the lexicon contains the target word
     */
    public boolean containsWord(String word) {
        String wordNotCased = this.caseInsensitize(word);
        return containsWordHelper(wordNotCased, root);
    }

    /**
     * recursive helper function to the contains word function
     * 
     * @param word the target word
     * @param root the root of the lexicon
     * @returns true if the lexicon contains the word, otherwise returns false
     */
    public boolean containsWordHelper(String word, LexiconNode root) {
        LexiconNode child = root.getChild(word.charAt(0));
        if (child == null) { // is the first character of the CURRENT word in the lexicon?
            return false; // first character of CURRENT word isn't in lexicon, let alone the whole word.
                          // return false.
        } else if (word.length() == 1) {
            if (child.isWord) { // is the character a word?
                return true; // we have found the word, we are looking for, return true
            }
            return false; // exhaustive search was done in the lexicon and the word was not found.
        }
        return containsWordHelper(word.substring(1), child);
    }

    /**
     * a function that checks whether the lexicon contains the target prefix or not
     */
    public boolean containsPrefix(String prefix) {
        String prefixNotCased = this.caseInsensitize(prefix);
        return containsPrefixHelper(prefixNotCased, root);
    }

    /**
     * the recursive helper function to the target prefix function
     * 
     * @param prefix the target prefix
     * @param root   the root of the lexicon
     * @return true if the prefix was found, otherwise returns false
     */
    public boolean containsPrefixHelper(String prefix, LexiconNode root) {
        LexiconNode child = root.getChild(prefix.charAt(0));
        if (child == null) { // first character of CURRENT the prefix in lexicon?
            return false;// first character of CURRENT prefix is not in the lexicon, let alone the word,
                         // return false
        } else if (prefix.length() == 1) {
            if (child.letter == prefix.charAt(0)) { // has prefix been found?
                return true;
            } else if (child.letter != prefix.charAt(0)) {
                return false;
            }
        }
        return containsPrefixHelper(prefix.substring(1), child);

    }

    /**
     * the itarator for the lexicon trie
     * 
     * @returns the iterator object
     */
    public Iterator<String> iterator() {
        Vector<String> words = new Vector<>();
        findWords(root, "", words);
        return words.iterator();
    }

    /**
     * a recursive function to find all words in the
     * 
     * @param root        the root of the lexicon
     * @param stringSoFar the string constructed so far
     * @param words       the vector to add all words to.
     */
    public void findWords(LexiconNode root, String stringSoFar, Vector<String> words) {
        for (LexiconNode child : root) {
            if (child.isWord) {
                words.add(stringSoFar + child.letter);
                findWords(child, stringSoFar + child.letter, words);
            } else {
                findWords(child, stringSoFar + child.letter, words);
            }
        }
    }

    /**
     * a function that suggests other corrections for a potentially mispelled word
     */
    public Set<String> suggestCorrections(String target, int maxDistance) {
        Set<String> suggestions = new SetVector<>();
        suggestCorrectionsHelper(target, maxDistance, "", root, suggestions);
        return suggestions;
    }

    /**
     * recursive helper function to the suggestCorrections function
     * 
     * @param target      the potentially mispelled word
     * @param maxDistance the degree of variation between the target and wods to be
     *                    found
     * @param soFar       the string to gather up corrections
     * @param root        the root of the lexicon
     * @param suggestions the set of suggestions
     */
    public void suggestCorrectionsHelper(String target, int maxDistance, String soFar, LexiconNode root,
            Set<String> suggestions) {

        suggestCorrectionsBaseCaseHelper(target, maxDistance, soFar, root, suggestions);
        if (maxDistance > 0) {
            for (LexiconNode child : root) {
                if (target.charAt(0) == child.letter) { // a character that matches target string exactly has been found
                    // recurse downwards without decrementing max distance
                    suggestCorrectionsHelper(target.substring(1), maxDistance, soFar + child.letter, child,
                            suggestions);
                } else {
                    // decrement max distance since there is no character to match the string
                    // exactly
                    suggestCorrectionsHelper(target.substring(1), maxDistance - 1, soFar + child.letter, child,
                            suggestions);
                }
            }
            return;
        }
    }

    /**
     * helper functions for the base case of suggest corrections
     * 
     * @param target      the potentially mispelled word
     * @param maxDistance the degree of variation between the target and words to be
     *                    found
     * @param soFar       the string to gather up corrections
     * @param root        the root of the lexicon
     * @param suggestions the set of suggestions
     */
    public void suggestCorrectionsBaseCaseHelper(String target, int maxDistance, String soFar, LexiconNode root,
            Set<String> suggestions) {
        if (target.length() == 0) { // are there any more more characters in the target string?
            if (root.isWord) { // is current root a word?
                suggestions.add(soFar); // add root to suggestions
                return;
            }
            return;
        }
        if (maxDistance == 0) { // do we have 0 degrees of freedom left?
            if (target.length() == 0) { // are there any more characters left in the target string?
                if (root.isWord) {
                    // there are no more degrees of freedom left and the root is a word, add suggest
                    // words to suggestions
                    suggestions.add(soFar);
                    return;
                }
                return;
            } else if (target.length() > 0) {
                LexiconNode child = root.getChild(target.charAt(0));
                if (child != null) {
                    // a character that matches the target exactly has been found
                    soFar += child.letter;
                    // recurse downwards but dont decrement the max Distance
                    suggestCorrectionsHelper(target.substring(1), maxDistance, soFar, child, suggestions);
                    return;
                }
            }
        }
    }

    /**
     * a function that finds all the words matching the regular expression
     */
    public Set<String> matchRegex(String pattern) {
        Set<String> matches = new SetVector<>();
        matchRegexHelper(pattern, "", root, matches);
        return matches;
    }

    /**
     * a helper function to the match regular expression function
     * 
     * @param pattern the pattern to be matched
     * @param soFar   the string constructed so far
     * @param root    the root of the lexicon
     * @param matches the set containing all the matches
     */
    public void matchRegexHelper(String pattern, String soFar, LexiconNode root, Set<String> matches) {
        if (pattern.equals("")) { // are there no more characters left to match in pattern
            // is the current root a word?
            if (root.isWord) {
                matches.add(soFar);
                return;
            }
            // add soFar to list of words since it matches the regular expression

        } else {

            if (pattern.charAt(0) == '*') {
                // match the empty string with '*'
                matchRegexHelper(pattern.substring(1), soFar, root, matches);
                for (LexiconNode child : root) {
                    // iterate through all the children and match all of them
                    matchRegexHelper(pattern, soFar + child.letter, child, matches);
                }
            }
            if (pattern.charAt(0) == '?') {
                // match the empty string with '?'
                matchRegexHelper(pattern.substring(1), soFar, root, matches);
                for (LexiconNode child : root) {
                    // iterate through all the children and match all of them
                    matchRegexHelper(pattern.substring(1), soFar + child.letter, child, matches);
                }
            }
            if (pattern.charAt(0) != '*' && pattern.charAt(0) != '?') {
                // we have found a character that matches the target exactly
                LexiconNode child = null;
                child = root.getChild(pattern.charAt(0));
                if (child != null) {
                    // continue matching the subsequent pattern recursively
                    matchRegexHelper(pattern.substring(1), soFar + child.letter, child, matches);
                }
            }

        }

    }

}
