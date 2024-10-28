package seedu.duke.data.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Todo.class, name = "todo"),
    @JsonSubTypes.Type(value = Deadline.class, name = "deadline"),
    @JsonSubTypes.Type(value = Repeat.class, name = "repeat")
})
public class Task {
    private String description;
    private boolean isDone;

    public Task() {
        this("");
    }

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Task(String description, boolean done) {
        this.description = description;
        this.isDone = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    /** Change the name of the getter method to match the JSON key */
    @JsonProperty("isDone")
    public boolean getDone() {
        return isDone;
    }

    public boolean isDone() {
        return isDone;
    }

    public void markAsDone() {
        isDone = true;
    }
    public void markAsUndone() {
        isDone = false;
    }
    public static Task createTask(
        String type,
        String... args
    ) throws MissingTaskArgument, EmptyTaskDescription, UnknownTaskType {
        try{
            if(args[0].isEmpty() || args[0].isBlank()) {
                throw new EmptyTaskDescription();
            }
            switch (type.toLowerCase()) {
            case "todo":
                return new Todo(args[0]);
            case "deadline":
                return new Deadline(args[0], args[1]);
            case "repeat":
                return new Repeat(args[0], args[1]);
            default:
                throw new UnknownTaskType(type);
            }
        } catch (ArrayIndexOutOfBoundsException e){
            throw new MissingTaskArgument(type);
        }
    }
    public static class UnknownTaskType extends Exception {
        public UnknownTaskType(String type) {
            super("Unknown task type: " + type);
        }
    }
    public static class MissingTaskArgument extends Exception {
        public MissingTaskArgument(String type) {
            super(createErrorMessage(type));
        }

        private static String createErrorMessage(String type) {
            StringBuilder sb = new StringBuilder();
            sb.append("Missing arguments for task type: ").append(type).append("\n");
            sb.append("The required arguments for task type: ").append(type).append(" are: ");
            switch (type.toLowerCase()) {
            case "todo":
                sb.append("description");
                break;
            case "deadline":
                sb.append("description, due date");
                break;
            case "repeat":
                sb.append("description, repeat interval");
                break;
            default:
                sb.append("unknown");
                break;
            }
            return sb.toString();
        }
    }


    public static class EmptyTaskDescription extends Exception {
        public EmptyTaskDescription() {
            super("Task description cannot be empty");
        }
    }


    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + description;
    }

    /**
     * Returns true if both tasks have the same description.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task other) {
            return description.equals(other.description);
        }
        return false;
    }
}
