package seedu.duke.parser;

import seedu.duke.commands.Command;
import seedu.duke.commands.FindPatientCommand;
import seedu.duke.commands.FindTaskCommand;
import seedu.duke.data.state.State;
import seedu.duke.data.state.StateType;
import seedu.duke.parser.parserutils.Find;
/**
 * Parses and executes the "find" command to search for a patient or task within the application.
 * Implements the {@link CommandParser} interface.
 */
public class FindParser implements CommandParser{
    /**
     * Executes the "find" command by extracting the search term from the input line and determining
     * whether to search for a patient or a task based on the current state.
     *
     * @param line  The input string containing the "find" command followed by the search term.
     * @param state The current state of the application,
     *              used to determine whether the command relates to a patient or a task.
     * @return A {@link FindPatientCommand} if in {@code MAIN_STATE},
     *      or a {@link FindTaskCommand} if in {@code TASK_STATE}.
     */
    @Override
    public Command execute(String line, State state) {
        if (state.getState() == StateType.MAIN_STATE) {
            String toFind = new Find().extract(line);
            return new FindPatientCommand(toFind);
        } else {
            String toFind = new Find().extract(line);
            return new FindTaskCommand(toFind);
        }
    }
}
