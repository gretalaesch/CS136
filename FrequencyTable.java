import java.util.Hashtable;
import java.util.Random;

/**
 * A FrequencyTable stores a set of characters each of which has
 * an associated integer frequency
 */
public class FrequencyTable {
  protected Hashtable<Character, Integer> freqTable;

  /** Construct an empty FrequencyTable */
  public FrequencyTable() {
    this.freqTable = new Hashtable<Character, Integer>();
  }

  /**
   * add(char ch)
   * If ch is in the FrequencyTable, increment its associated frequency
   * Otherwise add ch to FrequencyTable with frequency 1
   *
   * @param ch is the String to add to the FrequencyTable
   */
  public void add(char ch) { // two cases
    // Increment associated frequency
    if (freqTable.containsKey(ch)) {

      Integer oldCount = freqTable.get(ch);

      freqTable.put(ch, ++oldCount);

      // Put overwrites instead of adding a new key (keys are unique)
    } else if (!freqTable.containsKey(ch)) {
      freqTable.put(ch, 1);
      // Add ch to freqTable with frequency 1
    }
  }

  /**
   * Selects a character from this FrequencyTable with probabality equal to its
   * relative frequency.
   *
   * @return a character from the FrequencyTable
   */
  public char choose() {
    Random rand = new Random();
    Integer sum = 0;
    // sum values
    for (Integer suf : freqTable.values()) {
      sum += suf;
    }

    Integer choice = rand.nextInt(sum); // get a random number
    Integer letterInst = 0;

    for (Character key : freqTable.keySet()) {
      Integer frequency = freqTable.get(key);
      letterInst += frequency;
      if (letterInst > choice) {
        return key;
      }
    }
    return 'X';
  }

  /**
   * Produce a string representation of the FrequencyTable
   *
   * @return a String representing the FrequencyTable
   */
  public String toString() {
    return this.freqTable.toString();
  }

  /**
   * Used to test FrequencyTable class
   * @param args
   */
  public static void main(String[] args) {

  }

}
