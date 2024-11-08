package seedu.duke.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.duke.data.hospital.Hospital;
import seedu.duke.data.hospital.Patient;
import seedu.duke.data.state.State;
import seedu.duke.data.state.StateType;

/**
 * Represents a command to select a specific patient from the hospital system.
 * This command updates the global state to indicate the selected patient and switches to TASK_STATE.
 */
public class SelectPatientCommand extends HospitalCommand {
    public static final String COMMAND_WORD = "select";
    public static final String MESSAGE_SUCCESS = "Selected patient: %1$s";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "Patient not found in the list!";

    private static final Logger logger = Logger.getLogger(SelectPatientCommand.class.getName());

    private State state; // To hold reference to the global state object
    private int index;

    static {
        logger.setLevel(Level.SEVERE);
    }

    /**
     * Constructs a {@code SelectPatientCommand} with the specified index and global state.
     *
     * @param index the one-based index of the patient to select.
     * @param state the global state object to update with the selected patient.
     */
    public SelectPatientCommand(int index, State state) {
        this.index = index - 1;
        this.state = state;
    }

    /**
     * Executes the command to select a patient by index, updating the system state to TASK_STATE.
     * If the patient is found, their name and tag are displayed; if not, an error is logged.
     *
     * @return the result of the command, indicating success or failure due to an invalid index.
     */
    @Override
    public CommandResult execute() {
        //check for invalid input. -2 is result from non integer input
        if (index == -2) {
            return new CommandResult("Invalid input: Please enter a valid numeric index.");
        }
        assert index >= 0 : "Index should be non-negative";
        assert state != null : "State object should not be null";
        assert hospital != null : "Hospital object should not be null";

        try {
            String patientName = hospital.getPatient(index).getName();
            String patientTag = hospital.getPatient(index).getTag();
            Patient p = hospital.getPatient(index);
            String resultMessage = String.format(MESSAGE_SUCCESS, p.getName() + p.getFormattedTag());
            state.setState(StateType.TASK_STATE);
            logger.log(Level.INFO, "System is now in TASK_STATE for patient: {0}", patientName);
            // System.out.println(resultMessage);
            return new CommandResult(resultMessage);
        } catch (Hospital.PatientNotFoundException e) {
            logger.log(Level.SEVERE, "Attempted to select a patient at an invalid index: {0}", index);
            // System.out.println(MESSAGE_PATIENT_NOT_FOUND);
            return new CommandResult(MESSAGE_PATIENT_NOT_FOUND);
        }
    }
}
