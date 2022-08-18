import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    private static final int HORIZONTAL_LINE_LENGTH = 50;
    private static final String NAME = "Duke";

    /**
     * Prints multiple lines, each indented by 4 spaces.
     *
     * @param obj Any printable object
     */
    public static void printIndented(Object obj) {
        System.out.println("    " + obj.toString().replace("\n", "\n    "));
    }

    /**
     * Prints a horizontal line, good to separate the input command and the output.
     */
    public static void printHorizontalLine() {
        printIndented("_".repeat(HORIZONTAL_LINE_LENGTH));
    }

    /**
     * Prints the task list in a tidy manner.
     *
     * @param tasks The list of tasks
     */
    public static void printTasks(ArrayList<Task> tasks) {
        printIndented("Here are the tasks in your list:");
        for (int i = 1; i <= tasks.size(); i++) {
            printIndented(i + "." + tasks.get(i - 1));
        }
    }

    /**
     * Prints the end message.
     */
    public static void sayGoodbye() {
        printIndented(NAME + " says goodbye!");
    }

    /**
     * Marks a particular task from the given task list as done.
     *
     * @param tasks     The list of tasks
     * @param taskID    The task ID, starting from 1
     */
    public static void markTaskAsDone(ArrayList<Task> tasks, int taskID) throws DukeException {
        if (1 <= taskID && taskID <= tasks.size()) {
            printIndented("Nice! I've marked this task as done:");
            tasks.get(taskID - 1).markAsDone();
            printIndented("  " + tasks.get(taskID - 1));
        } else {
            throw new DukeException("Invalid task ID: " + taskID);
        }
    }

    /**
     * Unmarks a particular task from the given task list as not done.
     *
     * @param tasks The list of tasks
     * @param taskID The task ID, starting from 1
     */
    public static void unmarkTaskAsNotDone(ArrayList<Task> tasks, int taskID) throws DukeException {
        if (1 <= taskID && taskID <= tasks.size()) {
            printIndented("OK, I've marked this task as not done yet:");
            tasks.get(taskID - 1).unmarkAsNotDone();
            printIndented("  " + tasks.get(taskID - 1));
        } else {
            throw new DukeException("Invalid task ID: " + taskID);
        }
    }

    /**
     * Deletes a particular task from the given task list.
     *
     * @param tasks     The list of tasks
     * @param taskID    The task ID, starting from 1
     */
    public static void deleteTask(ArrayList<Task> tasks, int taskID) throws DukeException {
        if (1 <= taskID && taskID <= tasks.size()) {
            printIndented("Noted. I've removed this task:\n  " + tasks.get(taskID - 1));
            tasks.remove(taskID - 1);
            printIndented("Now you have " + tasks.size() + " tasks in the list.");
        } else {
            throw new DukeException("Invalid task ID: " + taskID);
        }
    }

    /**
     * Creates a Task object based on the input string.
     *
     * @param tasks The list of tasks
     * @param input The input string, consists of the keyword, the task name, and the by/at metadata
     */
    public static void createNewTask(ArrayList<Task> tasks, String input) throws DukeException {
        String taskName;
        String by;
        String at;
        if (input.startsWith("todo")) {
            taskName = input.replace("todo", "").strip();
            if (taskName.isEmpty()) {
                throw new DukeException("The description of a todo cannot be empty.");
            }
            tasks.add(new ToDos(taskName));
        } else if (input.startsWith("deadline")) {
            String[] temp = input.replace("deadline", "").strip().split("/by");
            taskName = temp[0].strip();
            if (taskName.isEmpty()) {
                throw new DukeException("The description of a deadline cannot be empty.");
            } else if (temp.length < 2) {
                throw new DukeException("You need to specify the due date of the deadline.");
            }
            by = temp[1].strip();
            if (by.isEmpty()) {
                throw new DukeException("You need to specify the due date of the deadline.");
            }
            tasks.add(new Deadlines(taskName, by));
        } else if (input.startsWith("event")) {
            String[] temp = input.replace("event", "").strip().split("/at");
            taskName = temp[0].strip();
            if (taskName.isEmpty()) {
                throw new DukeException("The description of an event cannot be empty.");
            } else if (temp.length < 2) {
                throw new DukeException("You need to specify the time of the event.");
            }
            at = temp[1].strip();
            if (at.isEmpty()) {
                throw new DukeException("You need to specify the time of the event.");
            }
            tasks.add(new Events(taskName, at));
        }
        printIndented("Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1)
                + "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * The main function of this chatbot.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<Task>();
        printHorizontalLine();
        printIndented("Hello! I'm " + NAME + "\nWhat can I do for you?");
        printHorizontalLine();
        while (true) {
            String input = sc.nextLine().strip();
            String keyword = input.split(" ")[0];
            printHorizontalLine();
            try {
                switch (keyword) {
                    case "bye":
                        sayGoodbye();
                        printHorizontalLine();
                        return;
                    case "list":
                        printTasks(tasks);
                        break;
                    case "mark":
                        markTaskAsDone(tasks, Integer.parseInt(input.split(" ")[1]));
                        break;
                    case "unmark":
                        unmarkTaskAsNotDone(tasks, Integer.parseInt(input.split(" ")[1]));
                        break;
                    case "delete":
                        deleteTask(tasks, Integer.parseInt(input.split(" ")[1]));
                        break;
                    case "todo": case "deadline": case "event":
                        createNewTask(tasks, input);
                        break;
                    default:
                        throw new DukeException("Sorry, I don't understand :(");
                }
            } catch (DukeException d) {
                printIndented(d.getMessage());
            }
            printHorizontalLine();
        }
    }
}
