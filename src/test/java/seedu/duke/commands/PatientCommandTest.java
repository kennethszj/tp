package seedu.duke.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.data.hospital.Hospital;
import seedu.duke.data.state.State;
import seedu.duke.data.state.StateType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatientCommandTest {
    private Hospital hospital;
    private State state;

    @BeforeEach
    void setUp() {
        hospital = new Hospital();
        state = new State(StateType.MAIN_STATE);
    }

    private void addPatients(String[] names, String[] nrics) {
        for (int i = 0; i < names.length; i++) {
            hospital.addPatient(names[i], nrics[i]);
        }
    }

    private CommandResult executeSelectCommand(int index) {
        SelectPatientCommand selectCommand = new SelectPatientCommand(index, state);
        selectCommand.setHospital(hospital);
        return selectCommand.execute();
    }

    @Test
    public void testAddPatientCommand_success_addsPatientSuccessfully() throws Hospital.PatientNotFoundException {
        AddPatientCommand addPatientCommand = new AddPatientCommand("Alice", "S1234567A");
        addPatientCommand.setHospital(hospital);
        CommandResult result = addPatientCommand.execute();

        assertEquals("New patient added: Alice [S1234567A]", result.getFeedbackToUser());
        assertEquals(1, hospital.getSize());
        assertEquals("Alice", hospital.getPatient(0).getName());
    }

    @Test
    public void testAddPatientCommand_duplicatePatient_returnsDuplicateMessage() {
        hospital.addPatient("Alice", "S1234567A");

        AddPatientCommand addDuplicateCommand = new AddPatientCommand("Alice", "S1234567A");
        addDuplicateCommand.setHospital(hospital);
        CommandResult result = addDuplicateCommand.execute();

        assertEquals("This patient already exists in the hospital", result.getFeedbackToUser());
        assertEquals(1, hospital.getSize());
    }

    @Test
    public void testAddPatientCommand_blankName_throwsAssertionError() {
        AddPatientCommand addPatientCommand = new AddPatientCommand("", "S1234567A");
        addPatientCommand.setHospital(hospital);

        assertThrows(AssertionError.class, addPatientCommand::execute, "Patient name should not be null or empty");
        assertEquals(0, hospital.getSize());
    }

    @Test
    public void testFindPatientCommand_withMatch_returnsMatchingPatients() {
        hospital.addPatient("Alice", "S1234567A");
        hospital.addPatient("Bob", "S7654321B");

        FindPatientCommand findPatientCommand = new FindPatientCommand("alice");
        findPatientCommand.setHospital(hospital);
        CommandResult result = findPatientCommand.execute();

        String expectedOutput = "Here are the matching patients in your list: \n1. Alice\n";
        assertEquals(expectedOutput, result.getFeedbackToUser());
    }

    @Test
    public void testFindPatientCommand_noMatch_returnsNoMatchMessage() {
        hospital.addPatient("Alice", "S1234567A");

        FindPatientCommand findPatientCommand = new FindPatientCommand("charlie");
        findPatientCommand.setHospital(hospital);
        CommandResult result = findPatientCommand.execute();

        assertEquals("There are no matching patients in your list.", result.getFeedbackToUser());
    }

    @Test
    public void testFindPatientCommand_caseInsensitive_returnsMatchingPatients() {
        hospital.addPatient("Alice", "S1234567A");

        FindPatientCommand findPatientCommand = new FindPatientCommand("ALICE");
        findPatientCommand.setHospital(hospital);
        CommandResult result = findPatientCommand.execute();

        String expectedOutput = "Here are the matching patients in your list: \n1. Alice\n";
        assertEquals(expectedOutput, result.getFeedbackToUser());
    }

    @Test
    public void testListPatientCommand_withPatients_displaysPatientList() {
        hospital.addPatient("Alice", "S1234567A");
        hospital.addPatient("Bob", "S7654321B");

        ListPatientCommand listPatientCommand = new ListPatientCommand();
        listPatientCommand.setHospital(hospital);
        CommandResult result = listPatientCommand.execute();

        String expectedOutput = "End of your patients list!";
        assertEquals(expectedOutput, result.getFeedbackToUser());
    }

    @Test
    public void testListPatientCommand_emptyList_displaysEmptyMessage() {
        ListPatientCommand listPatientCommand = new ListPatientCommand();
        listPatientCommand.setHospital(hospital);
        CommandResult result = listPatientCommand.execute();

        assertEquals("The patient list is currently empty!", result.getFeedbackToUser());
    }

    @Test
    public void testDeletePatientCommand_success_deletesPatient() {
        hospital.addPatient("Alice", "S1234567A");

        DeletePatientCommand deletePatientCommand = new DeletePatientCommand(1);
        deletePatientCommand.setHospital(hospital);
        CommandResult result = deletePatientCommand.execute();

        assertEquals("Deleted patient: Alice", result.getFeedbackToUser());
        assertEquals(0, hospital.getSize());
    }

    @Test
    public void testDeletePatientCommand_invalidIndex_returnsNotFoundMessage() {
        DeletePatientCommand deletePatientCommand = new DeletePatientCommand(1);
        deletePatientCommand.setHospital(hospital);
        CommandResult result = deletePatientCommand.execute();

        assertEquals("Patient not found in the list!", result.getFeedbackToUser());
    }

    @Test
    public void testDeletePatientCommand_outOfBoundsIndex_returnsNotFoundMessage() {
        hospital.addPatient("Alice", "S1234567A");

        DeletePatientCommand deletePatientCommand = new DeletePatientCommand(2);
        deletePatientCommand.setHospital(hospital);
        CommandResult result = deletePatientCommand.execute();

        assertEquals("Patient not found in the list!", result.getFeedbackToUser());
    }

    @Test
    public void testSelectPatientCommand_success_changesState() throws Hospital.PatientNotFoundException {
        addPatients(new String[]{"Alice", "Bob"}, new String[]{"S1234567A", "S7654321B"});

        CommandResult result = executeSelectCommand(1);

        assertEquals("Selected patient: Alice [S1234567A]", result.getFeedbackToUser());
        assertEquals(StateType.TASK_STATE, state.getState());
    }

    @Test
    public void testSelectPatientCommand_selectLastPatient_changesState() throws Hospital.PatientNotFoundException {
        addPatients(new String[]{"Alice", "Bob", "Charlie"}, new String[]{"S1234567A", "S7654321B", "S1111111C"});

        CommandResult result = executeSelectCommand(3);

        assertEquals("Selected patient: Charlie [S1111111C]", result.getFeedbackToUser());
        assertEquals(StateType.TASK_STATE, state.getState());
    }

    @Test
    public void testSelectPatientCommand_invalidIndexOutOfBounds_returnsNotFoundMessage() {
        addPatients(new String[]{"Alice", "Bob", "Charlie"}, new String[]{"S1234567A", "S7654321B", "S1111111C"});

        CommandResult result = executeSelectCommand(4);

        assertEquals("Patient not found in the list!", result.getFeedbackToUser());
        assertEquals(StateType.MAIN_STATE, state.getState());
    }
 //   @@ NCF3535
    @Test
    public void testSelectPatientCommand_invalidIndexNegative_throwsAssertionError() {
        SelectPatientCommand selectCommand = new SelectPatientCommand(-1, state);
        selectCommand.setHospital(hospital);

        CommandResult result = selectCommand.execute();
        assertEquals("Invalid input: Please enter a valid numeric index.", result.getFeedbackToUser());
    }

    @Test
    public void testSelectPatientCommand_invalidIndexZero_throwsAssertionError() {
        addPatients(new String[]{"Alice"}, new String[]{"S1234567A"});

        AssertionError thrown = assertThrows(AssertionError.class, () -> executeSelectCommand(0),
                "Expected execute() to throw an AssertionError for zero index.");

        assertTrue(thrown.getMessage().contains("Index should be non-negative"));
    }

    @Test
    public void testSelectPatientCommand_emptyHospital_returnsNotFoundMessage() {
        CommandResult result = executeSelectCommand(1);

        assertEquals("Patient not found in the list!", result.getFeedbackToUser());
        assertEquals(StateType.MAIN_STATE, state.getState());
    }

    @Test
    public void testSelectPatientCommand_nullState_throwsAssertionError() {
        addPatients(new String[]{"Alice"}, new String[]{"S1234567A"});

        SelectPatientCommand selectCommand = new SelectPatientCommand(1, null);
        selectCommand.setHospital(hospital);

        AssertionError thrown = assertThrows(AssertionError.class, selectCommand::execute,
                "Expected execute() to throw an AssertionError for null state.");
        assertTrue(thrown.getMessage().contains("State object should not be null"));
    }
}
