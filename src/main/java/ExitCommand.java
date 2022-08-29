public class ExitCommand extends Command {
    public static final String KEYWORD = "bye";

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws StashyException {
        ui.sayGoodbye();
        storage.writeTaskListToFile(tasks);
    }
}
