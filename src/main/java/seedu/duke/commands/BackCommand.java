package seedu.duke.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.duke.data.hospital.Hospital;
import seedu.duke.data.state.State;
import seedu.duke.data.state.StateType;

public class BackCommand extends Command {
    public static final String COMMAND_WORD = "back";
    public static final String MESSAGE_SWITCHED_TO_MAIN = "Switched back to Main Screen.";
    public static final String MESSAGE_ALREADY_IN_MAIN = "Already in Main Screen.";

    private static final Logger logger = Logger.getLogger(BackCommand.class.getName());

    private State state;
    private Hospital hospital; //need to clear selected patient after using backCommand

    static {
        logger.setLevel(Level.SEVERE); // Only show warnings and errors
    }

    public BackCommand(State state, Hospital hospital) {
        this.state = state;
        this.hospital = hospital;
    }

    @Override
    public CommandResult execute() {
        assert state != null : "State object should not be null";
        assert hospital != null : "Hospital object should not be null";

        if (state.getState() == StateType.TASK_STATE) {
            state.setState(StateType.MAIN_STATE);
            hospital.clearSelectedPatient();
            // System.out.println(MESSAGE_SWITCHED_TO_MAIN);
            return new CommandResult(MESSAGE_SWITCHED_TO_MAIN);
        } else {
            logger.log(Level.WARNING, "Attempted to switch back, but already in MAIN_STATE.");
            // System.out.println(MESSAGE_ALREADY_IN_MAIN);
            return new CommandResult(MESSAGE_ALREADY_IN_MAIN);
        }
    }
}
