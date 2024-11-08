package seedu.duke.parser;

import seedu.duke.commands.Command;
import seedu.duke.commands.SelectPatientCommand;
import seedu.duke.data.state.State;
import seedu.duke.data.state.StateType;
import seedu.duke.parser.parserutils.Index;

import static java.lang.Integer.parseInt;
/**
 * Parses the "select" command input and executes the corresponding selection action
 * in the application. This class is responsible for selecting a patient based on the provided ID.
 */
public class SelectParser implements CommandParser{
    /**
     * Executes the selection of a patient or task based on the current application state.
     *
     * @param line  The input command line as a string.
     * @param state The current state of the application, which influences command execution.
     * @return The command object representing the selection action, or {@code null}
     *         if the command cannot be executed in the current state.
     */
    @Override
    public Command execute(String line, State state) {
        if(state.getState() == StateType.MAIN_STATE){
            try {
                int id = parseInt(new Index().extract(line));
                return new SelectPatientCommand(id, state);
            } catch (NumberFormatException e) {
                return new SelectPatientCommand(-1, state); //-1 indicate is invalid number
            }
        }
        return null;
    }
}
