package seedu.duke.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import seedu.duke.MediTask;

class UiTest {

    /**
     * Tests reading a valid command from the user input.
     * Ensures that the input is read correctly and matches the expected result.
     */
    @Test
    public void readCommand_validInput_returnsCorrectCommand() {
        String input = "sample command\n"; // stimulate user input
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Ui ui = new Ui();
        String command = ui.readCommand();

        assertEquals("sample command", command); // Verify the input is read correctly
    }

    /**
     * Tests that the showLine method prints the correct horizontal line.
     * Verifies that the printed line matches the expected output.
     */
    @Test
    public void showLine_printsCorrectLine() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent)); // Capture console output

        Ui ui = new Ui();
        ui.showLine();

        String expectedLine = Colors.ANSI_BLACK
                + "────────────────────────────────────────────────────────────" + Colors.ANSI_RESET;

        // Normalize line endings and compare
        String actualOutput = outContent.toString().trim();
        assertEquals(expectedLine.trim(), actualOutput); // check the printed line is correct
    }

    /**
     * Tests that the scanner is closed without throwing any exceptions.
     * Ensures that the closeScanner method works as expected.
     */
    @Test
    public void closeScanner_noExceptions_closesCorrectly() {
        Ui ui = new Ui();
        assertDoesNotThrow(() -> ui.closeScanner()); // check that no exception is thrown when closing the scanner
    }

    /**
     * Tests that the showWelcome method prints the correct welcome message.
     * Verifies that the printed message matches the expected output.
     */
    @Test
    public void showWelcome_displaysWelcomeMessage_correctly() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent)); // Capture console output

        Ui ui = new Ui();
        ui.showWelcome();

        String expectedLogoOutput = """
                      _____
                     [IIIII]
                     )"\"\""\"(
                    /       \\
                   /         \\
                   |`-.....-'|
                   | MediTask|
                 _ |`-.....-'|     _
                (\\)`-.__.__.(I) _(/)
                  (I)  (/)(I)(\\)
                     (I)        Task management for medical professionals
                            """;

        String expectedOutput = Colors.ANSI_BLACK
                + "────────────────────────────────────────────────────────────" + Colors.ANSI_RESET + "\n      "
                + expectedLogoOutput.trim() + "\n" + MediTask.VERSION + "\n\nWelcome to " + Colors.ANSI_YELLOW
                + "MediTask" + Colors.ANSI_RESET
                + "!";

        // Normalize line endings and compare
        String actualOutput = outContent.toString().trim().replace("\r\n", "\n");
        assertEquals(expectedOutput.trim(), actualOutput); // check the welcome message is correct
    }
}
