package seedu.duke.parser.parserutils;
/**
 * Utility class for extracting a task name from an input string.
 * Implements the {@link StringExtraction} interface.
 */
public class TaskName implements StringExtraction{
    /**
     * Extracts the task name from the input string, based on the type of task command.
     * Handles commands prefixed with "deadline", "repeat", or "todo" and trims any additional parameters
     * following keywords such as "/by", "/every", or "/tag".
     *
     * @param input The input string containing a task command with the task name and optional parameters.
     * @return The extracted task name as a trimmed string.
     * @throws ArrayIndexOutOfBoundsException if the input format is incorrect or does not contain a valid task name.
     */
    @Override
    public String extract(String input) {
        String[] name;
        if(input.contains("/by ")){
            name = input.split("/by");
        } else if(input.contains("/every ")){
            name = input.split("/every");
        } else if(input.contains("/tag ")){
            name = input.split("/tag");
        } else {
            name = new String[]{input};
        }
        String[] result;
        if(input.contains("deadline ")){
            result = name[0].split("deadline ");
        } else if (input.contains("repeat ")){
            result = name[0].split("repeat ");
        } else if (input.contains("todo ")){
            result = name[0].split("todo ");
        } else {
            result = new String[]{input};
        }
        return result[1].trim();
    }
}
