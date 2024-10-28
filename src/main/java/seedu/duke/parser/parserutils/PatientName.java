package seedu.duke.parser.parserutils;
/**
 * Utility class for extracting a patient's name from an input string.
 * Implements the {@link StringExtraction} interface.
 */
public class PatientName implements StringExtraction{
    /**
     * Extracts the patient's name from the input string, assuming the name follows the "add" command.
     * If the input contains a "/tag" indicator, the name is extracted only from the part before "/tag".
     *
     * @param input The input string containing the "add" command followed by the patient's name and optional tags.
     * @return The extracted patient name as a trimmed string.
     * @throws ArrayIndexOutOfBoundsException if the input format is incorrect and does not contain expected keywords.
     */
    @Override
    public String extract(String input) {
        String[] name;
        if(input.contains("/tag ")){
            name = input.split(" /tag ");
        } else {
            name = new String[]{input};
        }
        String[] result;
        result = name[0].split("add ");
        return result[1].trim();
    }
}
