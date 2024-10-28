package seedu.duke.parser.parserutils;
/**
 * Utility class for extracting the search keyword from a "find" command input.
 * Implements the {@link StringExtraction} interface.
 */
public class Find implements StringExtraction{
    /**
     * Extracts the keyword from the input string following the "find" command.
     *
     * @param input The input string containing the "find" command and keyword.
     * @return The keyword specified after "find" in the input string, trimmed of leading and trailing spaces.
     * @throws ArrayIndexOutOfBoundsException if "find" is not followed by any keyword.
     */
    @Override
    public String extract(String input) {
        String[] find = input.split("find");
        return find[1].trim();
    }
}
