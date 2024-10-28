package seedu.duke.parser;

import seedu.duke.commands.BackCommand;
import seedu.duke.commands.Command;
import seedu.duke.data.hospital.Hospital;
import seedu.duke.data.state.State;

public class BackParser implements CommandParser {
    private final Hospital hospital;

    public BackParser(Hospital hospital) {
        this.hospital = hospital;
    }
    @Override
    public Command execute(String line, State state) {
        return new BackCommand(state, hospital);
    }
}
