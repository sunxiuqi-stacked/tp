package seedu.command;

import seedu.ModuleManager;
import seedu.ScheduleManager;
import seedu.Storage;
import seedu.Ui;
import seedu.task.TaskList;

import java.time.LocalDate;

/**
 * Commands to execute edit the task's information. This may be implemented by setter functions.
 * TODO add code
 */
public class EditTaskCommand extends Command{
    String description;
    LocalDate date = null;
    LocalDate newDate = null;
    String type;
    String newProperty;
    int[] newFrequency;

    public EditTaskCommand(String description, String type, LocalDate date, String newProperty){
        this.type = type;
        this.date = date;
        this.newProperty = newProperty;
        this.description = description;
    }

    public EditTaskCommand(String description, String type, LocalDate date, LocalDate newDate){
        this.type = type;
        this.date= date;
        this.newDate = newDate;
        this.description = description;
    }

    public EditTaskCommand(String description, String type, LocalDate date, int[] newFrequency){
        this.type = type;
        this.date= date;
        this.newFrequency = newFrequency;
        this.description = description;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
    }

    @Override
    public void execute(ScheduleManager scheduleManager, ModuleManager moduleManager, Ui ui) {
        switch (type){
            case "type":
                //
            case "description":
                //
            case "modulecode":
                //
            case "module code":
                //
            case "time":
                scheduleManager.editTask(description,date, type, newProperty);
                break;
            case "frequency":
                scheduleManager.editTask(description,date, type, newFrequency);
                break;
            case "date":
                scheduleManager.editTask(description,date, type, newDate);
                break;
            default:
                System.out.println("Invalid type");
        }
    }
}