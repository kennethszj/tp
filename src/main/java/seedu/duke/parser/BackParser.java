package seedu.duke.parser;

import seedu.duke.commands.BackCommand;
import seedu.duke.commands.Command;
import seedu.duke.data.hospital.Hospital;
import seedu.duke.data.state.State;
/**
 * Parses and executes the "back" command to return to the previous state in the application.
 * Implements the {@link CommandParser} interface.
 */
public class BackParser implements CommandParser {
    private final Hospital hospital;

    public BackParser(Hospital hospital) {
        this.hospital = hospital;
    }
    /**
     * Executes the "back" command by creating a new {@link BackCommand} with the current state.
     *
     * @param line  The input string containing the "back" command (not utilized in execution).
     * @param state The current state of the application, which is passed to the {@link BackCommand}.
     * @return A new {@link BackCommand} initialized with the current state.
     */
    @Override
    public Command execute(String line, State state) {
        return new BackCommand(state, hospital);
    }
}
