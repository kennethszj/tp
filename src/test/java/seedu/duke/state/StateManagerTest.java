package seedu.duke.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import seedu.duke.commands.Command;
import seedu.duke.commands.CommandResult;
import seedu.duke.commands.HospitalCommand;
import seedu.duke.data.exception.InvalidCommandException;
import seedu.duke.data.exception.UnknownStateFound;
import seedu.duke.data.hospital.Hospital;
import seedu.duke.data.hospital.Hospital.PatientNotFoundException;
import seedu.duke.data.state.State;
import seedu.duke.data.state.StateManager;
import seedu.duke.data.state.StateType;
import seedu.duke.parser.Parser;
import seedu.duke.storage.StorageFile;

public class StateManagerTest {

    @Test
    public void initialState_success() {
        StateManager stateManager = new StateManager();
        assertEquals(StateType.MAIN_STATE, stateManager.getState());
    }

    @Test
    public void changeState_success() {
        StateManager stateManager = new StateManager();
        stateManager.changeState(StateType.TASK_STATE);
        assertEquals(StateType.TASK_STATE, stateManager.getState());
    }

    @Test
    public void setCurrentState_success() {
        StateManager stateManager = new StateManager();
        stateManager.setCurrentState(new State());
        assertEquals(StateType.MAIN_STATE, stateManager.getState());
    }

    @Test
    public void runMainStatePatientSelect_success() throws UnknownStateFound, InvalidCommandException {
        String filePath = "src/test/java/seedu/duke/data/hospital_data_load.json";
        StorageFile storage = new StorageFile(filePath);
        StateManager stateManager = new StateManager();
        Command command;
        String commandInput = "select 1";
        Hospital hospital = storage.load();
        HospitalCommand.setHospital(hospital);

        // Test for main state
        command = new Parser().parseCommand(commandInput, stateManager.getCurrentState());

        CommandResult result = stateManager.runState(commandInput, command, hospital);

        assertEquals("Selected patient: Alice", result.getFeedbackToUser());
    }

    @Test
    public void runMainState_nullOrInvalidCommand_expectException() throws UnknownStateFound {
        StateManager stateManager = new StateManager();
        Command command;
        String commandInput = "lol";
        Hospital hospital = new Hospital();

        // Test for main state
        command = new Parser().parseCommand(commandInput, stateManager.getCurrentState());

        assertThrows(InvalidCommandException.class, () -> stateManager.runMainState(commandInput, command, hospital));
    }

    @Test
    public void runTaskStateNoPatient_success() throws UnknownStateFound, InvalidCommandException {
        String filePath = "src/test/java/seedu/duke/data/hospital_data_load.json";
        StorageFile storage = new StorageFile(filePath);
        StateManager stateManager = new StateManager();
        stateManager.changeState(StateType.TASK_STATE);
        Command command;
        String commandInput = "exit";
        Hospital hospital = storage.load();

        HospitalCommand.setHospital(hospital);

        // Test for main state
        command = new Parser().parseCommand(commandInput, stateManager.getCurrentState());

        CommandResult result = stateManager.runState(commandInput, command, hospital);
        System.out.println(result.getFeedbackToUser());
        assertEquals("No patient selected.", result.getFeedbackToUser());
    }

    @Test
    public void runTaskStateWithPatient_success()
            throws UnknownStateFound, InvalidCommandException, PatientNotFoundException {
        String filePath = "src/test/java/seedu/duke/data/hospital_data_load.json";
        StorageFile storage = new StorageFile(filePath);
        StateManager stateManager = new StateManager();
        stateManager.changeState(StateType.TASK_STATE);
        Command command;
        String commandInput = "exit";
        Hospital hospital = storage.load();

        HospitalCommand.setHospital(hospital);
        hospital.setSelectedPatient(0);

        // Test for main state
        command = new Parser().parseCommand(commandInput, stateManager.getCurrentState());

        CommandResult result = stateManager.runState(commandInput, command, hospital);
        System.out.println(result.getFeedbackToUser());
        assertEquals("Exiting program...", result.getFeedbackToUser());
    }

}
