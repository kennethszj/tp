package seedu.duke.parser;

import seedu.duke.commands.AddTaskCommand;
import seedu.duke.commands.Command;
import seedu.duke.data.exception.DateParseException;
import seedu.duke.data.state.State;
import seedu.duke.data.state.StateType;
import seedu.duke.parser.parserutils.DateFormat;
import seedu.duke.parser.parserutils.Duration;
import seedu.duke.parser.parserutils.TaskName;
import seedu.duke.parser.parserutils.Tag;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Parses and executes the "add deadline" command to create a task with a deadline.
 * Implements the {@link CommandParser} interface.
 */
public class AddDeadlineParser implements CommandParser {

    private static final Logger LOGGER = Logger.getLogger(AddDeadlineParser.class.getName());
    /**
     * Executes the "add deadline" command by extracting task details,
     * validating the deadline, and creating an {@link AddTaskCommand}.
     *
     * @param line  The input string containing the "add deadline" command, task name, deadline, and optional tag.
     * @param state The current state of the application, used to ensure the command is valid in the current context.
     * @return An {@link AddTaskCommand} if the task is successfully parsed and validated;
     *      {@code null} if the state is incorrect or date parsing fails.
     */
    @Override
    public Command execute(String line, State state) {
        if (state.getState() == StateType.TASK_STATE) {
            String taskName = new TaskName().extract(line);
            String by = new Duration().extract(line);
            String tag = new Tag().extract(line);
            try {
                // Parse, validate, and format the date using DateFormat
                by = DateFormat.validateAndParseToStandardFormat(by);
            } catch (DateParseException e) {
                LOGGER.log(Level.SEVERE, "Invalid date/time format for deadline: {0}", by);
                return null;
            }
            return new AddTaskCommand("deadline", taskName, by, tag);
        }
        return null;
    }
}
