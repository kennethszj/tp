package seedu.duke.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import seedu.duke.commands.AddTaskCommand;
import seedu.duke.commands.MarkTaskCommand;
import seedu.duke.data.task.TaskList;

public class TaskListTest {

    /**
     * Test of getCompletionRate method, of class TaskList.
     */
    @Test
    public void testGetCompletionRate() {
        System.out.println("calCompletionRate");
        TaskList instance = new TaskList();
        AddTaskCommand command = new AddTaskCommand("todo", "Read book");
        command.setData(instance);
        command.execute();
        AddTaskCommand command2 = new AddTaskCommand("todo", "close book");
        command2.setData(instance);
        command2.execute();
        MarkTaskCommand command3 = new MarkTaskCommand(1);
        command3.setData(instance);
        command3.execute();
        String expResult = "50.00%";
        String result = instance.completionRatePercentageToString();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCompletionRate_emptyTaskList() {
        System.out.println("calCompletionRate");
        TaskList instance = new TaskList();
        String expResult = "100.00%";
        String result = instance.completionRatePercentageToString();
        assertEquals(expResult, result);
    }
}
