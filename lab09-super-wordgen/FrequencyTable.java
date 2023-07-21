import java.util.Random;
import structure5.*;
// Students, please implement this class

/**
 * A FrequencyTable stores a set of characters each of which has
 * an associated integer frequency
 */
public class FrequencyTable<T> {
  protected Hashtable<T, Integer> frequencyTable;
  protected Integer entrySum;

  /** Construct an empty FrequencyTable */
  public FrequencyTable() {
    frequencyTable = new Hashtable<>();
    entrySum = 0;
  }

  /**
   * add(char ch)
   * If ch is in the FrequencyTable, increment its associated frequency
   * Otherwise add ch to FrequencyTable with frequency 1
   * 
   * @param ch is the String to add to the FrequencyTable
   */
  public void add(T ch) {
    if (!frequencyTable.containsKey(ch)) {
      frequencyTable.put(ch, 0);
    }
    Integer key = frequencyTable.get(ch);
    frequencyTable.put(ch, key + 1);
    entrySum++;
  }

  /**
   * Selects a character from this FrequencyTable with probabality equal to its
   * relative frequency.
   * 
   * @return a character from the FrequencyTable
   */
  public T choose() {
    Random r = new Random();
    Integer randomNumber = r.nextInt(entrySum);
    Integer value = 0;
    for (T letter : frequencyTable.keySet()) {
      value = value + frequencyTable.get(letter);
      if (randomNumber < value) {
        return letter;
      }
    }
    return null;
  }

  /**
   * Produce a string representation of the FrequencyTable
   * 
   * @return a String representing the FrequencyTable
   */
  public String toString() {
    String set = "{ ";
    for (T ch : frequencyTable.keySet()) {
      set += ch + ":" + frequencyTable.get(ch) + " ";
    }
    set += "}";
    return set;
  }

  /**
   * function to tezt frequency Table class
   * @param args  NOT IN USE
   */
  public static void main(String[] args) {
    FrequencyTable<Character> f = new FrequencyTable<>();
    f.add('a');
    f.add('a');
    f.add('a');
    f.add('a');
    f.add('a');
    f.add('a');
    f.add('b');
    f.add('b');
    f.add('b');
    f.add('c');
    f.add(('d'));
    f.add('d');
    System.out.println(f.entrySum);
    Hashtable<Character, Integer> dist = new Hashtable<>();
    dist.put('a', 0);
    dist.put('b', 0);
    dist.put('c', 0);
    System.out.println(f.toString());
    System.out.println(dist);
  }

}
