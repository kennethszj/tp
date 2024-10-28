package seedu.duke.parser;

import seedu.duke.commands.Command;
import seedu.duke.commands.ListPatientCommand;
import seedu.duke.commands.ListTaskCommand;
import seedu.duke.data.hospital.Patient;
import seedu.duke.data.state.State;
import seedu.duke.data.state.StateType;
import seedu.duke.data.hospital.Hospital;
import seedu.duke.ui.Ui;

/**
 * Parses and executes the "list" command to display either patients or tasks based on the current application state.
 * Implements the {@link CommandParser} interface.
 */
public class ListParser implements CommandParser{
    private final Hospital hospital;
    private final Ui ui;
  
  
    /**
     * Initializes a new instance of {@code ListParser} with the specified {@link Hospital} and {@link Ui}.
     *
     * @param hospital The hospital context for executing the list command.
     * @param ui       The user interface instance to handle display output.
     */
    public ListParser(Hospital hospital, Ui ui) {
        this.hospital = hospital;
        this.ui = ui;
    }

    /**
     * Executes the "list" command by returning the appropriate command to list patients or tasks
     * based on the current state of the application.
     *
     * @param line  The input string containing the "list" command (not utilized in execution).
     * @param state The current state of the application, used to determine whether to list patients or tasks.
     * @return A {@link ListPatientCommand} if in {@code MAIN_STATE},
     *         a {@link ListTaskCommand} if in {@code TASK_STATE},
     *         or {@code null} if the state is neither.
     */
    @Override
    public Command execute(String line, State state) {
        if (state.getState() == StateType.MAIN_STATE) {
            return new ListPatientCommand(hospital, ui);
        } else if (state.getState() == StateType.TASK_STATE) {
            Patient selectedPatient = hospital.getSelectedPatient();
            return new ListTaskCommand(selectedPatient, ui);
        }

        return null;
    }
}
