package seedu.command;

import seedu.ModuleManager;
import seedu.ScheduleManager;
import seedu.Storage;
import seedu.exception.InvalidReplyException;
import seedu.task.*;
import seedu.Ui;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Represents a command for adding different subclass of tasks
 */
public class AddCommand  extends Command {
    private Task task;

    public AddCommand(Task task){
        this.task = task;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Method to add the lesson into the schedule manager.
     * Exceptions settle later, check if method works first.
     * Main function is to add the lesson into the semesterSchedule of the ScheduleManager.
     * Note that everthing in the Lesson Object if it is a Lesson object is still in String form.
     * Convert it to LocalTime if necessary.
     * @param scheduleManager scheduleManager that handles tasks.
     * @param moduleManager moduleManager that handles modules where we need to add task into module.
     * @param ui ui that helps with ui stuff.
     * TODO
     *  - make this work for adding deadlines and events too.
     *  - add the tasks to the ModuleManager as well.
     */
    @Override
    public void execute(ScheduleManager scheduleManager, ModuleManager moduleManager, Ui ui) {
        if (task instanceof Lesson) {
            /*
            if (scheduleManager.checkIfLessonToBeAddedClashesWithCurrentTimetable((Lesson) task)) {
                String verifyIfReallyWantToAdd = ui.readYesOrNo();
                if (verifyIfReallyWantToAdd.equals("No")) {
                    return;
                } else if (!verifyIfReallyWantToAdd.equals("Yes")) {
                    throw new InvalidInputException("You should type in Yes or No");
                }
            }

             */
            scheduleManager.addLesson((Lesson) task, moduleManager, ui); //add the lesson to the schedule manager
            System.out.println("Got it, added lesson to the schedule manager!");
            String moduleCode = task.getModuleCode();
            // if module code exist in the module manager, simply add the task into the module manager
        } else if (task instanceof Event) {
            scheduleManager.addEvent((Event) task,moduleManager);
            // now check if the module code exist or is an empty string
            // look at the validateEvent method in Parser to understand that if the module code is invalid,
            // meaning the user didn't key in a module code for his event, the moduleCode will be an empty string.
            if (!task.getModuleCode().equals("")) {
                System.out.println("Event added to both Schedule manager and Module manager");
            } else {
                System.out.println("Event added to Schedule Manager only");
            }
            //System.out.println(moduleManager.getListOfModuleCodes());
        } else if (task instanceof Deadline) {
            scheduleManager.addDeadline((Deadline) task,moduleManager);
            System.out.println("Got it, added deadline to Schedule Manager and Module Manager");
        } else {
            return;
        }
        Ui.printSeparator();
    }
}

