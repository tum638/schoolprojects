
// Students, please implement this class
import structure5.*;

/**
 * A Table holds a collection of strings, each of which has an
 * associated FrequencyTable
 */
public class Table<V> {
  protected Hashtable<String, FrequencyTable<V>> table;

  /** Construct an empty table */
  public Table() {
    table = new Hashtable<>();
  }

  /**
   * Updates the table as follows
   * If key already exists in the table, update its FrequencyTable
   * by adding value to it
   * Otherwise, create a FrequencyTable for key and add value to it
   * 
   * @param key   is the desired k-letter sequence
   * @param value is the character to add to the FrequencyTable of key
   */
  public void add(String key, V value) {
    if (!table.containsKey(key)) {
      FrequencyTable<V> freqTable = new FrequencyTable<>();
      table.put(key, freqTable);
    }
    FrequencyTable<V> charFrequencies = table.get(key);
    charFrequencies.add(value);
  }

  /**
   * If key is in the table, return one of the characters from
   * its FrequencyTable with probability equal to its relative frequency
   * Otherwise, determine a reasonable value to return
   * 
   * @param key is the k-letter sequence whose frequencies we want to use
   * @return a character selected from the corresponding FrequencyTable
   */
  public V choose(String key) {
    V character = null;
    if (table.containsKey(key)) {
      FrequencyTable<V> charFrequencies = table.get(key);
      character = charFrequencies.choose();
      return character;
    }
    System.out.println("This key has not been encountered before");
    System.exit(1);
    return character;

  }

  /**
   * a function to check if the key is contained in the table.
   * 
   * @param key the key being searched for
   * @return true if the key is in the table otherwise returns false.
   */
  public Boolean contains(String key) {
    return table.containsKey(key);
  }

  /**
   * Produce a string representation of the Table
   * 
   * @return a String representing this Table
   */
  public String toString() {
    String set = "Table { ";
    for (String sequence : table.keySet()) {
      set += sequence + ":" + table.get(sequence).toString() + "\n";
    }
    set += " }";
    return set;
  }

  /**
   * function to test functionality of Table class
   * 
   * @param args
   */
  public static void main(String[] args) {
    Table<Character> t = new Table<>();
    FrequencyTable<Character> f = new FrequencyTable<>();

    System.out.println(t.toString());
  }

}
