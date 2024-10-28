package seedu.duke.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.data.hospital.Patient;
import seedu.duke.data.task.TaskList;
import seedu.duke.ui.Ui;

public class TaskCommandTest {

    private TaskList tasks;
    private Patient patient;
    private Ui ui;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        patient = new Patient("Test Patient");
        patient.setTaskList(tasks); //link tasklist to patient
        ui = new Ui();
    }

    @Test
    void testAddTodo() {
        AddTaskCommand addTaskCommand = new AddTaskCommand("todo", "Read book");
        TaskList tasks = new TaskList();
        addTaskCommand.setData(tasks);
        CommandResult commandResult = addTaskCommand.execute();
        
        assertEquals("New task added: [T][ ] Read book", commandResult.getFeedbackToUser());
    }

    @Test
    void testAddRepeat(){
        AddTaskCommand addTaskCommand = new AddTaskCommand("repeat", "Read book", "2 days");
        TaskList tasks = new TaskList();
        addTaskCommand.setData(tasks);
        CommandResult commandResult = addTaskCommand.execute();
        assertEquals("New task added: [R][ ] Read book (repeat: every 2 days)", commandResult.getFeedbackToUser());
    }

    @Test
    void testAddTask_noDescription(){
        AddTaskCommand addTaskCommand = new AddTaskCommand("deadline", "");
        TaskList tasks = new TaskList();
        addTaskCommand.setData(tasks);
        CommandResult commandResult = addTaskCommand.execute();
        assertEquals("Task description cannot be empty", commandResult.getFeedbackToUser());
    }

    @Test
    void testAddTask_missingArguments(){
        AddTaskCommand addTaskCommand = new AddTaskCommand("deadline");
        TaskList tasks = new TaskList();
        addTaskCommand.setData(tasks);
        CommandResult commandResult = addTaskCommand.execute();
        assertEquals("Missing arguments for task type: deadline\n" + 
            "The required arguments for task type: deadline are: description, due date", 
            commandResult.getFeedbackToUser());
    }

    @Test
    void testAddTask_unknownTaskType(){
        AddTaskCommand addTaskCommand = new AddTaskCommand("random", "Read book");
        TaskList tasks = new TaskList();
        addTaskCommand.setData(tasks);
        CommandResult commandResult = addTaskCommand.execute();
        assertEquals("Unknown task type: random", commandResult.getFeedbackToUser());
    }

    @Test
    void testDeleteTask_taskNotExist(){
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(1);
        TaskList tasks = new TaskList();
        deleteTaskCommand.setData(tasks);
        CommandResult commandResult = deleteTaskCommand.execute();
        assertEquals(DeleteTaskCommand.MESSAGE_TASK_NOT_FOUND, commandResult.getFeedbackToUser());
    }

    @Test
    void testMarkTask() {
        TaskList tasks = new TaskList();
        AddTaskCommand command = new AddTaskCommand("todo", "Read book");

        command.setData(tasks);
        command.execute();

        MarkTaskCommand command2 = new MarkTaskCommand(1);
        command2.setData(tasks);
        CommandResult commandResult = command2.execute();
        assertEquals("Task marked successfully: [T][X] Read book", commandResult.getFeedbackToUser());
    }

    @Test
    void testPrintTask(){
        TaskList tasks = new TaskList();
        AddTaskCommand command = new AddTaskCommand("todo", "Read book");
        command.setData(tasks);
        command.execute();

        ListTaskCommand listTaskCommand = new ListTaskCommand(patient, ui);
        listTaskCommand.setData(tasks);
        CommandResult commandResult = listTaskCommand.execute();
        assertEquals("Here are the tasks in your list!\n1. [T][ ] Read book\n", 
            commandResult.getFeedbackToUser());
    }

    @Test
    void testDuplicateTask(){
        TaskList tasks = new TaskList();
        AddTaskCommand command = new AddTaskCommand("deadline", "Read book", "2pm");
        command.setData(tasks);
        command.execute();
        AddTaskCommand command2 = new AddTaskCommand("todo","Read book");
        command2.setData(tasks);
        CommandResult commandResult = command2.execute();
        assertEquals(AddTaskCommand.MESSAGE_DUPLICATE_TASK, commandResult.getFeedbackToUser());
    }
    
    @Test
    void testFindTask(){
        TaskList tasks = new TaskList();
        AddTaskCommand command = new AddTaskCommand("deadline", "Read book", "2pm");
        command.setData(tasks);
        command.execute();
        
        AddTaskCommand command2 = new AddTaskCommand("todo", "Meomeo");
        command2.setData(tasks);
        command2.execute();

        AddTaskCommand command3 = new AddTaskCommand("repeat", "Meomeomeomeo", "2 days");
        command3.setData(tasks);
        command3.execute();

        FindTaskCommand findTaskCommand = new FindTaskCommand("meo");
        findTaskCommand.setData(tasks);
        CommandResult commandResult = findTaskCommand.execute();
        assertEquals("Here are the matching tasks in your list: " + 
            "\n1. [T][ ] Meomeo\n2. [R][ ] Meomeomeomeo (repeat: every 2 days)\n", 
            commandResult.getFeedbackToUser());
    }
}
