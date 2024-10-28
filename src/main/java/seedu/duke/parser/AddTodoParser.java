package seedu.duke.parser;

import seedu.duke.commands.AddTaskCommand;
import seedu.duke.commands.Command;
import seedu.duke.data.state.State;
import seedu.duke.data.state.StateType;
import seedu.duke.parser.parserutils.TaskName;
import seedu.duke.parser.parserutils.Tag;
/**
 * Parses and executes the "add todo" command to create a new todo task.
 * Implements the {@link CommandParser} interface.
 */
public class AddTodoParser implements CommandParser {
    /**
     * Executes the "add todo" command by extracting the task name and optional tag,
     * then creating an {@link AddTaskCommand} if the state is {@code TASK_STATE}.
     *
     * @param line  The input string containing the "add todo" command, task name, and optional tag.
     * @param state The current state of the application, used to ensure the command is valid in the current context.
     * @return An {@link AddTaskCommand} with task type "todo" if successfully parsed in the {@code TASK_STATE};
     *         {@code null} if the state is not {@code TASK_STATE}.
     */
    @Override
    public Command execute(String line, State state) {
        if (state.getState() == StateType.TASK_STATE) {
            String taskName = new TaskName().extract(line);
            String tag = new Tag().extract(line);
            return new AddTaskCommand("todo", taskName, tag);
        }
        return null;
    }
}
