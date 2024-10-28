package seedu.duke.parser;

import seedu.duke.commands.Command;
import seedu.duke.commands.ExitCommand;
import seedu.duke.data.state.State;

/**
 * Parses and executes the "exit" command to terminate the application.
 * Implements the {@link CommandParser} interface.
 */
public class ExitParser implements CommandParser {
    /**
     * Executes the "exit" command by creating a new {@link ExitCommand}.
     *
     * @param line  The input string containing the "exit" command (not utilized in execution).
     * @param state The current state of the application (not utilized in this implementation).
     * @return A new {@link ExitCommand} that signifies the termination of the application.
     */
    @Override
    public Command execute(String line, State state) {
        return new ExitCommand();
    }
}
