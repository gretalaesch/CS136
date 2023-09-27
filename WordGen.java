import java.util.Hashtable;
import java.util.Scanner;
import structure5.*;

/**
 *
 * This class takes a number and a file name as input, where the number is the
 * level of analysis to preform and the file name is text for the word generator to be trained on.
 * This class uses Hashtable storage to gather information about string patterns and uses
 * this information to generate text. The level of analysis is the length of the character sequence
 * for which the word generator will be trained on.
 *
 */
public class WordGen {

    protected String inputText;
    protected Integer kLevel;
    protected Integer outputLength;

    /**
     * Constructs the class fields using the Scanner class to built the input text from a textfile.
     * The level of analysis, output length, and seed are also constructed in this method.
     *
     * @param kLevel the level of analysis, taken from the command line in the main method
     * @param inputText the body of text for the table to be trained on
     * @param outputLength the number of characters to be generated
     * @pre k level must be greater than zero
     */
    public WordGen(Integer kLevel, String inputText, Integer outputLength) {
        Assert.pre(kLevel > 0, "K level must be greater than 0");
        this.inputText = inputText;
        this.kLevel = kLevel;
        this.outputLength = outputLength;
    }

    /**
     * This method is a naive implementation for level 2 analysis,
     * that builds a table with learned information about the input text.
     *
     * This method does not have a robust way of handling the ends of strings.
     *
     * @return a Table object with character sequencing stored
     */
    public Table trainingLevTwo() {
        Table table = new Table();
        for (int i = 0; i < this.inputText.length() - 2; i++) { // -2 to avoid null pointer
            String prefix = inputText.substring(i, i + 2); // exclusive, so will be two (update for k)
            Character suffix = inputText.charAt(i + 2);
            table.add(prefix, suffix);
        }
        String lastLetter = inputText.substring(inputText.length() - 2, inputText.length() - 1);
        table.add(lastLetter + " ", '.');
        return table;

    }

    /**
     * This method uses the input text field to construct a table that stored
     * learned information about character patterns
     *
     * @return a Table object with character sequencing stored
     */
    public Table trainingLevK() {
        Table kTable = new Table();
        String prefix = "";
        char suff = 'z';
        for (int i = 0; i < inputText.length(); i++) {
            // two cases: not at the end vs. at the end of the text
            // end of text case:
            if (i + kLevel >= inputText.length()) {
                prefix = inputText.substring(i) + inputText.substring(0, i + kLevel - inputText.length());
                suff = inputText.charAt(i + kLevel - inputText.length());
            } else {
                prefix = inputText.substring(i, i + kLevel);
                suff = inputText.charAt(i + kLevel);
            }
            kTable.add(prefix, suff);
        }

        return kTable;
    }


    /**
     * Pulls information from dataTable to generate text using the probabilities
     * of character sequences
     *
     * @param dataTable a Table object with character sequencing stored
     * @param toBuild the current string of k-letters to be built off upon by
     * the generator
     */
    public void generate(Table dataTable, String toBuild) {
        System.out.print(toBuild);

        for (int i = 0; i < outputLength; i++) {
            toBuild = generateHelper(dataTable, toBuild);
        }

    }

    /**
     *
     * @param dataTable Table object with character sequencing stored
     * @param prefix a string that is made of the next k characters to analyze
     * @return an updated string that is made of the next k characters to analyze
     */
    public String generateHelper(Table dataTable, String prefix) {
        Assert.pre(prefix.length() == kLevel, "Can only build on a string with k characters");
        char suffix = dataTable.choose(prefix);
        System.out.print(suffix);

        String next = prefix.substring(1) + suffix;

        return next;
        
    }


    /**
     * Uses both the command line and input redirection to generate the training text
     * and set the k-level, seed, and the output length
     * @param args
     */
    public static void main(String[] args) {
        Assert.pre(args.length == 2,
        "Call this class with the format: \n $ java WordGen <K Level> <Output Length> < pathtofile.txt");
        Integer kLevel = Integer.valueOf(args[0]);
        Integer outputLength = Integer.valueOf(args[1]);
        // Generate one long string with all text
        Scanner in = new Scanner(System.in);
        StringBuffer textBuffer = new StringBuffer();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            textBuffer.append(line);
            textBuffer.append("\n");
        }
        String inputText = textBuffer.toString();

        WordGen wordGen = new WordGen(kLevel, inputText, outputLength);
        Table data = wordGen.trainingLevK();
        String seed = inputText.substring(0, kLevel);
        //System.out.println(data);
        wordGen.generate(data, seed);
        System.out.println("\n");
    }

}
