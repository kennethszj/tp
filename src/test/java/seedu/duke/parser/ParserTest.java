package seedu.duke.parser;
import org.junit.jupiter.api.Test;
import seedu.duke.commands.Command;
import seedu.duke.data.hospital.Hospital;
import seedu.duke.data.state.State;
import seedu.duke.data.state.StateType;
import seedu.duke.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParserTest {
    @Test
    public void parseCommandAdd() {
        State mainState = new State(StateType.MAIN_STATE);
        Command returnedCommand = new AddParser().execute("add Tom", mainState);
        assertEquals(true, returnedCommand != null);

        boolean thrown = false;
        try{
            new AddParser().execute("Invalid command", mainState);
        } catch(Exception e){
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void parseCommandTodo() {
        State taskState = new State(StateType.TASK_STATE);
        Command returnedCommand = new AddTodoParser().execute("todo CS2113", taskState);
        assertEquals(true, returnedCommand != null);

        boolean thrown = false;
        try{
            new AddTodoParser().execute("Invalid command", taskState);
        } catch(Exception e){
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void parseCommandDeadline() {
        State taskState = new State(StateType.TASK_STATE);
        Command returnedCommand = new AddDeadlineParser().execute("deadline CS2113 /by 2359", taskState);
        assertEquals(true, returnedCommand != null);

        boolean thrown = false;
        try{
            new AddDeadlineParser().execute("Invalid command", taskState);
        } catch(Exception e){
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void parseCommandRepeat() {
        State taskState = new State(StateType.TASK_STATE);
        Command returnedCommand = new AddRepeatParser().execute("repeat Tom", taskState);
        assertEquals(true, returnedCommand != null);

        boolean thrown = false;
        try{
            new AddDeadlineParser().execute("Invalid command", taskState);
        } catch(Exception e){
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void parseCommandList() {
        Hospital hospital = new Hospital();
        Ui ui = new Ui();
        State mainState = new State(StateType.MAIN_STATE);
        Command returnedCommand = new ListParser(hospital, ui).execute("list", mainState);
        assertNotNull(returnedCommand);

        State taskState = new State(StateType.TASK_STATE);
        Command returnedCommand1 = new ListParser(hospital, ui).execute("list", taskState);
        assertNotNull(returnedCommand1);
    }

    @Test
    public void parseCommandDel() {
        State mainState = new State(StateType.MAIN_STATE);
        Command returnedCommand = new DeleteParser().execute("delete 0", mainState);
        assertNotNull(returnedCommand);

        boolean thrown = false;
        try{
            new DeleteParser().execute("delete Tom", mainState);
        } catch(Exception e){
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void parseCommandSelect() {
        State mainState = new State(StateType.MAIN_STATE);
        Command returnedCommand = new SelectParser().execute("select 0", mainState);
        assertNotNull(returnedCommand);

        boolean thrown = false;
        try{
            new SelectParser().execute("select Tom", mainState);
        } catch(Exception e){
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void parseCommandMark() {
        State taskState = new State(StateType.TASK_STATE);
        Command returnedCommand = new MarkParser().execute("mark 0", taskState);
        assertNotNull(returnedCommand);

        boolean thrown = false;
        try{
            new MarkParser().execute("mark Tom", taskState);
        } catch (Exception e){
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void parseCommandUnmark() {
        State taskState = new State(StateType.TASK_STATE);
        Command returnedCommand = new UnmarkParser().execute("mark 0",taskState);
        assertEquals(true, returnedCommand != null);

        boolean thrown = false;
        try{
            new UnmarkParser().execute("mark Tom",taskState);
        } catch(Exception e){
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void parseCommandBack() {
        State taskState = new State(StateType.TASK_STATE);
        Command returnedCommand = new BackParser(new Hospital()).execute("back",taskState);
        assertEquals(true, returnedCommand != null);
    }

    @Test
    public void parseCommandFind() {
        State taskState = new State(StateType.TASK_STATE);
        Command returnedCommand = new FindParser().execute("find key",taskState);
        assertEquals(true, returnedCommand != null);

        State mainState = new State(StateType.MAIN_STATE);
        Command returnedCommand1 = new FindParser().execute("find key",mainState);
        assertEquals(true, returnedCommand1 != null);

        boolean thrown = false;
        try{
            new FindParser().execute("Invalid command",taskState);
        } catch(Exception e){
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void parseCommandExit() {
        State taskState = new State(StateType.TASK_STATE);
        Command returnedCommand = new ExitParser().execute("exit",taskState);
        assertEquals(true, returnedCommand != null);
    }
}
