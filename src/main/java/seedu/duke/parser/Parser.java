package seedu.duke.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.duke.commands.Command;
import seedu.duke.data.hospital.Hospital;
import seedu.duke.data.state.State;
import seedu.duke.ui.Ui;

/**
 * Parses user input commands and executes the corresponding actions in the application.
 * This class handles various commands such as adding tasks, deleting tasks, marking tasks,
 * and managing the application's state.
 */
public class Parser {

    private static final Logger LOGGER = Logger.getLogger("Parser");

    static {
        LOGGER.setLevel(Level.SEVERE);
    }

    private final Hospital hospital;
    private final Ui ui;

    /**
     * Initializes a new instance of the {@code Parser} class with the specified {@link Hospital} and {@link Ui}.
     *
     * @param hospital The hospital context for parsing commands.
     * @param ui       The user interface instance for displaying output.
     */
    public Parser(Hospital hospital, Ui ui) {
        this.hospital = hospital;
        this.ui = ui;
    }

    /**
     * Parses a command line input and executes the corresponding command based on the current state.
     *
     * @param line  The input command line as a string.
     * @param state The current state of the application, which influences command execution.
     * @return The command object representing the executed command, or {@code null} if the command is invalid
     *         or the input is empty.
     */
    public Command parseCommand(String line, State state){
        String[] parts = line.split(" ");

        switch(parts[0]){
        case "todo":
            try{
                return new AddTodoParser().execute(line, state);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("The input cannot be empty");
                LOGGER.log(Level.WARNING, "Todo Command Error: Non-Numerical Error");
            }
            break;

        case "deadline":
            try{
                return new AddDeadlineParser().execute(line, state);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("The input cannot be empty");
                LOGGER.log(Level.WARNING, "Deadline Command Error: Non-Numerical Error");

            }
            break;

        case "repeat":
            try{
                return new AddRepeatParser().execute(line, state);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("The input cannot be empty");
                LOGGER.log(Level.WARNING, "Repeat Command Error: Non-Numerical Error");
            }
            break;

        case "add":
            try{
                return new AddParser().execute(line, state);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("The input cannot be empty");
                LOGGER.log(Level.WARNING, "Add Command Error: Empty field");
            }
            break;

        case "list":
            try{
                return new ListParser(hospital, ui).execute(line, state);
            } catch (NumberFormatException e) {
                System.out.println("Number format exception");
                LOGGER.log(Level.WARNING, "List Command Error: Non-Numerical Error");
            }
            break;

        case "delete":
            try{
                return new DeleteParser().execute(line, state);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("The input cannot be empty");
                LOGGER.log(Level.WARNING, "Delete Command Error: Non-Numerical Error");
            }
            break;

        case "select":
            try{
                return new SelectParser().execute(line, state);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("The input cannot be empty");
                LOGGER.log(Level.WARNING, "Select Command Error: Non-Numerical Error");
            }
            break;

        case "mark":
            try{
                return new MarkParser().execute(line, state);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("The input cannot be empty");
                LOGGER.log(Level.WARNING, "Mark Command Error: Non-Numerical Error");
            }
            break;

        case "unmark":
            try{
                return new UnmarkParser().execute(line, state);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("The input cannot be empty");
                LOGGER.log(Level.WARNING, "Unmark Command Error: Non-Numerical Error");
            }
            break;

        case "back":
            try{
                return new BackParser(hospital).execute(line, state);
            } catch (NumberFormatException e) {
                System.out.println("Number format exception");
                LOGGER.log(Level.WARNING, "Back Command Error: Non-Numerical Error");
            }
            break;

        case "find":
            try{
                return new FindParser().execute(line, state);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("The input cannot be empty");
                LOGGER.log(Level.WARNING, "Find Command Error: Non-Numerical Error");
            }
            break;

        case "exit":
            return new ExitParser().execute(line, state);

        default:
            LOGGER.log(Level.WARNING, "The user did not enter a valid command");
            return null;
        }
        return null;
    }
}
