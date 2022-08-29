abstract class Command {
    abstract boolean isExit();

    public void execute(TaskList tasks, Ui ui, Storage storage) throws StashyException {
        throw new StashyException("Note to dev: Implement this method in the child class!");
    }
}