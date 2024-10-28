package seedu.duke;

import seedu.duke.commands.Command;
import seedu.duke.commands.CommandResult;
import seedu.duke.commands.ExitCommand;
import seedu.duke.commands.HospitalCommand;
import seedu.duke.data.exception.UnknownStateFound;
import seedu.duke.data.hospital.Hospital;
import seedu.duke.data.state.StageManager;
import seedu.duke.data.state.State;
import seedu.duke.parser.Parser;
import seedu.duke.storage.StorageFile;
import seedu.duke.ui.Ui;

/**
 * Entry point of the MediTask program is a task management application for
 * medical professionals.
 * Initializes the application and starts the interaction with the user.
 */
public class MediTask {

    /** Version info of the program. */
    public static final String VERSION = "MediTask - Version 2.0";

    private Ui ui;
    private StorageFile storage;
    private Hospital hospital; // Load data from file
    private StageManager stageManager; // Manages the different states of the program

    /** Runs the program until termination. */
    public void run() {
        start();
        runCommandLoopUntilExitCommand();
        exit(); // Terminates the program gracefully
    }

    /** Prints the Goodbye message and exits. */
    private void exit() {
        // ui.showGoodbyeMessage();
        System.exit(0);
    }

    /**
     * Sets up the required objects, loads up the data from the storage file, and
     * prints the welcome message.
     */
    private void start() {
        ui = new Ui();
        storage = new StorageFile();
        hospital = storage.load(); // Load data from file
        stageManager = new StageManager(); // Initialize the stage manager

        ui.showWelcome();

        HospitalCommand.setHospital(hospital);
    }

    /**
     * Reads the user command and executes it, until the user issues the exit
     * command.
     */
    private void runCommandLoopUntilExitCommand() {
        Command command;
        String commandInput;
        Parser parser = new Parser(hospital);

        do {
            // Show main screen if no patient is selected
            if (hospital.getSelectedPatient() != null) {
                ui.showTaskScreen(hospital.getSelectedPatient());
            } else {
                ui.showMainScreen();
            }

            commandInput = ui.readCommand();

            // parse and execute the command, and also pass in the currentState
            command = new Parser(hospital).parseCommand(commandInput, stageManager.getCurrentState());

            // Execute the command
            CommandResult result;
            try {
                result = stageManager.runState(commandInput, command, hospital);
            } catch (UnknownStateFound e) {
                ui.showToUser(e.getMessage());
                stageManager.setCurrentState(new State()); // Reset the state
                continue;
            }

            // Show the feedback to the user
            ui.showToUser(result.getFeedbackToUser());

            // Save hospital data to file
            storage.save(hospital);

        } while (!ExitCommand.isExit(command));
    }

    /**
     * Main entry-point for the java.duke.MediTask application.
     */
    public static void main(String[] args) {
        new MediTask().run();
    }

}
