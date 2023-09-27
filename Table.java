import java.util.Hashtable;

// Students, please implement this class

/**
 * A Table holds a collection of strings, each of which has an
 * associated FrequencyTable
 */
public class Table {
  protected Hashtable<String, FrequencyTable> table;

  /** Construct an empty table */
  public Table() {
    this.table = new Hashtable<String, FrequencyTable>();
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
  public void add(String key, char value) {
    // If the key (letter sequence) exists:
    if (table.containsKey(key)) {
      FrequencyTable suffixTable = table.get(key); // retrieve suffix table
      suffixTable.add(value); // add or update suffix
    } else if (!table.containsKey(key)) {
      FrequencyTable addSuffix = new FrequencyTable(); // create a new frequency table
      addSuffix.add(value); // add the suffix
      table.put(key, addSuffix); // update this table with the new key, and the new frequency table
    }

  }

  /**
   * If key is in the table, return one of the characters from
   * its FrequencyTable with probability equal to its relative frequency
   * Otherwise, determine a reasonable value to return
   *
   * @param key is the k-letter sequence whose frequencies we want to use
   * @return a character selected from the corresponding FrequencyTable
   */
  public char choose(String key) {
    if (table.containsKey(key)) {
      FrequencyTable suffixes = table.get(key);
      return suffixes.choose();
    }
    return 'a';
  }

  /**
   * Produce a string representation of the Table
   *
   * @return a String representing this Table
   */
  public String toString() {
    return this.table.toString();
  }

  /**
   * Used to test Table class
   * @param args
   */
  public static void main(String[] args) {

  }

}
