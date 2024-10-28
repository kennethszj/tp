package seedu.duke.parser;

import seedu.duke.commands.Command;
import seedu.duke.data.state.State;
/**
 * Represents a parser for commands in the application.
 * Implementing classes should provide the logic to parse and execute specific commands
 * based on the input line and the current application state.
 */
public interface CommandParser {
    /**
     * Executes the command based on the given input line and the current state of the application.
     *
     * @param line  The input string containing the command to be executed.
     * @param state The current state of the application,
     *              used to determine if the command is valid in the current context.
     * @return A {@link Command} object representing the result of the executed command
     *      or {@code null} if the command could not be executed.
     */
    Command execute(String line, State state);
}
