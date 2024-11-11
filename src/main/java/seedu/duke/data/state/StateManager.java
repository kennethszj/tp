package seedu.duke.data.state;

import seedu.duke.commands.Command;
import seedu.duke.commands.CommandResult;
import seedu.duke.data.exception.InvalidCommandException;
import seedu.duke.data.exception.UnknownStateFound;
import seedu.duke.data.hospital.Hospital;
import seedu.duke.data.hospital.Hospital.PatientNotFoundException;
import seedu.duke.ui.Ui;

/**
 * Manages and runs the different states of the program.
 */
public class StateManager {
    private State currentState; // current state of the program

    /**
     * Constructor for StageManager.
     */
    public StateManager() {
        this.currentState = new State();
        assert currentState != null : "Current state should not be null.";
    }

    /**
     * Returns the current state of the program {@link State}.
     *
     * @return current state of the program
     */
    public State getCurrentState() {
        return currentState;
    }


    /**
     * Sets the current state of the program {@link State}.
     *
     * @param currentState new state of the program
     */
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    /**
     * Changes the state of the program {@link State}.
     *
     * @param state new state of the program
     */
    public void changeState(StateType state) {
        currentState.setState(state);
    }

    /**
     * Returns the current state of the program {@link StateType}.
     *
     * @return current state of the program
     */
    public StateType getState() {
        return currentState.getState();
    }

    /**
     * Runs the command based on the current state of the program.
     *
     * @param commandInput user input command string
     * @param command command to be executed {@link Command}
     * @param hospital hospital object {@link Hospital}
     * @return result of the command execution {@link CommandResult}
     * @throws UnknownStateFound if the state is not recognized
     */
    public CommandResult runState(String commandInput, Command command, Hospital hospital) throws UnknownStateFound {
        try {
            switch (currentState.getState()) {
            case MAIN_STATE:
                return runMainState(commandInput, command, hospital);
            case TASK_STATE:
                return runTaskState(commandInput, command, hospital);
            default:
                throw new UnknownStateFound("StageManager does not recognize the current state.");
            }
        } catch (InvalidCommandException e) {
            return new CommandResult(e.getMessage());
        }
    }

    /**
     * Runs the command based on the MAIN_STATE of the program.
     * If the command is a SelectPatientCommand, the patient will be selected.
     *
     * @param commandInput user input command string
     * @param command command to be executed {@link Command}
     * @param hospital hospital object {@link Hospital}
     * @return result of the command execution {@link CommandResult}
     * @throws InvalidCommandException if the command is invalid or patient is not selected
     */
    public CommandResult runMainState(String commandInput, Command command, Hospital hospital)
            throws InvalidCommandException {
        if (command == null) {
            throw new InvalidCommandException("Invalid Command");
        }
        // Handle patient selection
        if (command instanceof seedu.duke.commands.SelectPatientCommand) {
            try {
                // Convert user input to index
                int patientIndex = Integer.parseInt(commandInput.split(" ")[1]) - 1;

                // check if patient index fall within the number of patients in the hospital
                assert patientIndex >= 0 && patientIndex < hospital.getSize() : "Invalid patient index.";

                // get selected patient
                hospital.setSelectedPatient(patientIndex);

            } catch (NumberFormatException e) {
                Ui.showToUserException("Invalid patient selection.");
            } catch (PatientNotFoundException e) {
                Ui.showToUserException("Invalid patient selection.");
            }
        }
        return command.execute();
    }

    /**
     * Runs the command based on the TASK_STATE of the program.
     * If patient is not selected, an InvalidCommandException will be thrown.
     *
     * @param commandInput user input command string
     * @param command command to be executed {@link Command}
     * @param hospital hospital object {@link Hospital}
     * @return result of the command execution {@link CommandResult}
     * @throws InvalidCommandException if the command is invalid
     */
    public CommandResult runTaskState(String commandInput, Command command, Hospital hospital)
            throws InvalidCommandException {
        if (command == null) {
            throw new InvalidCommandException("");
        }

        if (hospital.getSelectedPatient() == null) {
            throw new InvalidCommandException("No patient selected.");
        }

        assert hospital.getSelectedPatient() != null : "A patient must be selected in TASK_STATE.";

        command.setData(hospital.getSelectedPatient().getTaskList());
        return command.execute();
    }

}
