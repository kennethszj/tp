package seedu.duke.parser;

import seedu.duke.commands.AddTaskCommand;
import seedu.duke.commands.Command;
import seedu.duke.data.state.State;
import seedu.duke.data.state.StateType;
import seedu.duke.parser.parserutils.Duration;
import seedu.duke.parser.parserutils.TaskName;
import seedu.duke.parser.parserutils.Tag;
/**
 * Parses and executes the "add repeat" command to create a repeating task.
 * Implements the {@link CommandParser} interface.
 */
public class AddRepeatParser implements CommandParser {
    /**
     * Executes the "add repeat" command by extracting the task name, recurrence interval,
     * and optional tag, then creating an {@link AddTaskCommand} if the state is {@code TASK_STATE}.
     *
     * @param line  The input string containing the "add repeat" command, task name,
     *              recurrence interval, and optional tag.
     * @param state The current state of the application, used to ensure the command is valid in the current context.
     * @return An {@link AddTaskCommand} with task type "repeat" if successfully parsed in the {@code TASK_STATE};
     *         {@code null} if the state is not {@code TASK_STATE}.
     */
    @Override
    public Command execute(String line, State state) {
        if (state.getState() == StateType.TASK_STATE) {
            String taskName = new TaskName().extract(line);
            String recur = new Duration().extract(line);
            String tag = new Tag().extract(line);
            return new AddTaskCommand("repeat", taskName, recur, tag);
        }
        return null;
    }
}
