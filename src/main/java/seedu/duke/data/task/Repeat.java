package seedu.duke.data.task;

public class Repeat extends Task{
    protected String repeat;

    // Jackson requires a default constructor
    public Repeat() {
        super();
    }

    public Repeat(String description) {
        super(description);
    }

    public Repeat(String description, String repeat) {
        super(description);
        this.repeat = repeat;
    }

    public Repeat(String description, String repeat, boolean isDone) {
        super(description, isDone);
        this.repeat = repeat;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    @Override
    public String toString() {
        return "[R]" + super.toString() + " (repeat: every " + repeat + ")";
    }

}
