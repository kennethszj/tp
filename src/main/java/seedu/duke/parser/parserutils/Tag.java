package seedu.duke.parser.parserutils;
/**
 * Utility class for extracting a tag from an input string.
 * Implements the {@link StringExtraction} interface.
 */
public class Tag implements StringExtraction{
    /**
     * Extracts the tag from the input string, assuming the tag follows the "/tag" indicator.
     * If no tag is found, returns "No tag" by default.
     *
     * @param input The input string potentially containing a "/tag" indicator followed by a tag.
     * @return The extracted tag as a trimmed string, or "No tag" if no tag is found in the input.
     */
    @Override
    public String extract(String input) {
        String[] tag;

        if(input.contains("/tag ")){
            tag = input.split("/tag ");
        } else {
            tag = new String[]{"No tag"};
            return tag[0].trim();
        }
        return tag[1].trim();
    }
}
