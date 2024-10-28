package seedu.duke.parser.parserutils;
/**
 * Utility class for extracting an index from an input string.
 * Implements the {@link StringExtraction} interface.
 */
public class Index implements StringExtraction{
    /**
     * Extracts the index from the input string, assuming the index is the second word in the input.
     *
     * @param input The input string containing a command followed by an index.
     * @return The extracted index as a trimmed string.
     * @throws ArrayIndexOutOfBoundsException if the input does not contain a second word.
     */
    @Override
    public String extract(String input) {
        String[] find = input.split(" ");
        return find[1].trim();
    }
}
