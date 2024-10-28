package seedu.duke.parser;

import seedu.duke.commands.Command;
import seedu.duke.commands.UnmarkTaskCommand;
import seedu.duke.data.state.State;
import seedu.duke.data.state.StateType;
import seedu.duke.parser.parserutils.Index;

import static java.lang.Integer.parseInt;
/**
 * Parses the "unmark" command input and executes the corresponding action
 * in the application. This class is responsible for unmarking a task based on the provided ID.
 */
public class UnmarkParser implements CommandParser{
    /**
     * Executes the unmarking of a task based on the current application state.
     *
     * @param line  The input command line as a string.
     * @param state The current state of the application, which influences command execution.
     * @return The command object representing the unmarking action, or {@code null}
     *         if the command cannot be executed in the current state.
     */
    @Override
    public Command execute(String line, State state) {
        if(state.getState() == StateType.TASK_STATE){
            int id = parseInt(new Index().extract(line));
            return new UnmarkTaskCommand(id);
        }
        return null;
    }
}
