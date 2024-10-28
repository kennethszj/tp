package seedu.duke.parser;

import seedu.duke.commands.AddPatientCommand;
import seedu.duke.commands.Command;
import seedu.duke.data.state.StateType;
import seedu.duke.data.state.State;
import seedu.duke.parser.parserutils.PatientName;
import seedu.duke.parser.parserutils.Tag;
/**
 * Parses and executes the "add" command to add a new patient.
 * Implements the {@link CommandParser} interface.
 */
public class AddParser implements CommandParser{
    /**
     * Executes the "add" command by extracting the patient's name and tag,
     * then creating an {@link AddPatientCommand} if the state is {@code MAIN_STATE}.
     *
     * @param line  The input string containing the "add" command, patient name, and optional tag.
     * @param state The current state of the application, used to ensure the command is valid in the current context.
     * @return An {@link AddPatientCommand} if the patient is successfully parsed in the {@code MAIN_STATE};
     *         {@code null} if the state is not {@code MAIN_STATE}.
     */
    @Override
    public Command execute(String line, State state) {
        if (state.getState() == StateType.MAIN_STATE) {
            String patientName = new PatientName().extract(line);
            String tag = new Tag().extract(line);
            return new AddPatientCommand(patientName, tag);
        }
        return null;
    }
}
