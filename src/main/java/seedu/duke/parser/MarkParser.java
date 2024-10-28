package seedu.duke.parser;

import seedu.duke.commands.Command;
import seedu.duke.commands.MarkTaskCommand;
import seedu.duke.data.state.State;
import seedu.duke.data.state.StateType;
import seedu.duke.parser.parserutils.Index;

import static java.lang.Integer.parseInt;
/**
 * Parses and executes the "mark" command to mark a task as completed.
 * Implements the {@link CommandParser} interface.
 */
public class MarkParser implements CommandParser{
    /**
     * Executes the "mark" command by extracting the task index from the input line and creating
     * a command to mark the specified task as completed, only if the current state is {@code TASK_STATE}.
     *
     * @param line  The input string containing the "mark" command followed by the index of the task to be marked.
     * @param state The current state of the application, used to determine if the command can be executed.
     * @return A {@link MarkTaskCommand} if in {@code TASK_STATE}, or {@code null} if the command cannot be executed.
     */
    @Override
    public Command execute(String line, State state) {
        if(state.getState() == StateType.TASK_STATE) {
            int id = parseInt(new Index().extract(line));
            return new MarkTaskCommand(id);
        }
        return null;
    }
}
