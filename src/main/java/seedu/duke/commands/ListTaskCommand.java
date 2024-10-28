package seedu.duke.commands;

import seedu.duke.data.hospital.Patient;
import seedu.duke.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;


public class ListTaskCommand extends Command {
    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Here are the tasks in your list!\n" + //
                "%1$s";
    public static final String MESSAGE_EMPTY_LIST = "There are no tasks in your list!";
    
    private static final Logger logger = Logger.getLogger(ListPatientCommand.class.getName());

    private final Patient patient;
    private final Ui ui;
    static {
        logger.setLevel(Level.SEVERE);
    }

    public ListTaskCommand(Patient patient, Ui ui) {
        this.patient = patient;
        this.ui = ui;
    }
    @Override
    public CommandResult execute() {
        assert tasks != null : "Task list should not be null";

        if(tasks.getSize() == 0) {
            logger.log(Level.WARNING, "Attempted to list tasks, but the list is empty.");
            return new CommandResult(MESSAGE_EMPTY_LIST);
        } else{
            ui.showTaskList(patient);
            return new CommandResult("");
        }
    }
}
