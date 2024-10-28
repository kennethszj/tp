package seedu.duke.parser;

import seedu.duke.commands.Command;
import seedu.duke.commands.ListPatientCommand;
import seedu.duke.commands.ListTaskCommand;
import seedu.duke.data.hospital.Patient;
import seedu.duke.data.state.State;
import seedu.duke.data.state.StateType;
import seedu.duke.data.hospital.Hospital;
import seedu.duke.ui.Ui;

public class ListParser implements CommandParser{
    private final Hospital hospital;
    private final Ui ui;
    public ListParser(Hospital hospital, Ui ui) {
        this.hospital = hospital;
        this.ui = ui;
    }
    @Override
    public Command execute(String line, State state) {
        if (state.getState() == StateType.MAIN_STATE) {
            return new ListPatientCommand();
        } else if (state.getState() == StateType.TASK_STATE) {
            Patient selectedPatient = hospital.getSelectedPatient();
            return new ListTaskCommand(selectedPatient, ui);
        }

        return null;
    }
}
