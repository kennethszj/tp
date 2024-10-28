package seedu.duke.ui;
import java.util.Scanner;

import seedu.duke.common.Messages;
import seedu.duke.data.hospital.Patient;
import seedu.duke.data.task.TaskList;

/**
 * The Ui class handles the user interface for the task management application.
 * It provides methods for displaying messages and interacting with the user.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Constructs a Ui object and initializes the scanner for user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the main screen prompt to the user.
     */
    public void showMainScreen() {
        showLine();
        System.out.print(Messages.MESSAGE_MAIN_PROMPT);
    }

    /**
     * Displays the task screen with the selected patient's information.
     *
     * @param patient The selected patient whose tasks will be managed.
     */
    public void showTaskScreen(Patient patient) {
        showLine();
        System.out.println("Patient: " + patient.getName() + patient.getFormattedTag());
        System.out.print(Messages.MESSAGE_MAIN_PROMPT);
    }

    /**
     * Prints a message to the user.
     *
     * @param message The message to be displayed.
     */
    public void showToUser(String message) {
        System.out.println(message);
    }

    /**
     * Displays the task list for the selected patient, along with the completion rate.
     *
     * @param patient The selected patient whose task list and completion rate will be displayed.
     */
    public void showTaskList(Patient patient) {
        showLine();

        //retrieve and display completion rate
        TaskList taskList = patient.getTaskList();
        String completionPercentage = taskList.completionRatePercentageToString();

        //display info with completion rate
        System.out.printf("Patient: %s %s [Tasks Completed: %s]%n", patient.getName(), patient.getFormattedTag(), completionPercentage);
        System.out.println("Here are the tasks in your list!");
        System.out.println(taskList); //use TaskList's toString method to print the list of tasks
    }


    /**
     * Reads a command inputted by the user.
     *
     * @return the user input as a String
     */

    public String readCommand() {
        assert scanner != null;
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        } else {
            return null;
        }
    }

    /**
     * Displays a horizontal line to the console.
     */
    public void showLine() {
        System.out.println(Messages.MESSAGE_BREAKLINE);
    }

    /**
     * Displays a welcome message to the user.
     */
    public void showWelcome() {
        showLine();
        System.out.println(Messages.LOGO);
        System.out.println(Messages.MESSAGE_WELCOME);
    }

    /**
     * Closes the scanner
     */
    public void closeScanner() {
        scanner.close();
    }

}
