package stashy.commands;

import stashy.data.exception.StashyException;
import stashy.data.task.TaskList;
import stashy.storage.Storage;
import stashy.ui.Ui;

/**
 * A Command specifically designed to mark a particular task
 * in the task list as done.
 */
public class MarkCommand extends Command {
    public static final String KEYWORD = "mark";
    private int taskId;

    /**
     * Constructor method.
     *
     * @param taskId Task ID from the task list
     */
    public MarkCommand(Integer taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Marks a particular task from the given task list as done.
     *
     * @param tasks The list of tasks
     * @param ui The UI of this application
     * @param storage The storage used for this application
     * @throws StashyException If any exception is caught
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws StashyException {
        if (1 <= this.taskId && this.taskId <= tasks.size()) {
            ui.showIndented("LFG, marking this task as done!");
            tasks.get(this.taskId - 1).markAsDone();
            ui.showIndented("  " + tasks.get(this.taskId - 1));
        } else {
            throw new StashyException("Invalid task ID: " + this.taskId);
        }
    }

    /**
     * Adds a Deadline task class to the task list and outputs the UI string.
     *
     * @param tasks The list of tasks
     * @param ui The UI of this application
     * @param storage The storage used for this application
     * @return The stringtified UI output
     * @throws StashyException If any exception is caught
     */
    @Override
    public String executeString(TaskList tasks, Ui ui, Storage storage) throws StashyException {
        if (1 <= this.taskId && this.taskId <= tasks.size()) {
            String returnString = "";
            returnString += "LFG, marking this task as done!";
            tasks.get(this.taskId - 1).markAsDone();
            returnString += "\n  " + tasks.get(this.taskId - 1);
            return returnString;
        } else {
            throw new StashyException("Invalid task ID: " + this.taskId);
        }
    }
}
