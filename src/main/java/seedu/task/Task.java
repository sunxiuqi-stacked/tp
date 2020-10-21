package seedu.task;

import java.time.LocalDate;

/**
 * Class that needs to be accessed by Schedule object.
 * Task is a parent class for two child classes, Lesson and Deadline
 * Lesson helps to keep track of tasks that have a frequency (weekly)
 * Deadline acts like normal Deadline like in IP.
 */
public class Task {
    protected String description;
    protected boolean isDone; // should remove this since no longer seem to be useful in our tp.
    protected String taskType; // can be E or D for our project
    protected String moduleCode; // Module this task belongs to
    protected String time; //Time of the event in HH:MM format
    protected LocalDate date;
    protected int frequency;
    protected String by;
    protected String notes; // TODO add it in the constructor. ***Low priority, settle later.

    //FIXME add new event and deadlines to this list whenever created

    public Task(String description, String moduleCode) {
        this.description = description;
        this.moduleCode = moduleCode;
    }

    /**
     * Creates new Task object, without frequency.
     * Error message will be printed if the module with the moduleCode doesn't exist.
     * @param description the task description.
     * @param moduleCode the code of the module this task relates to.
     *  - add searching module based on moduleCode(String). Need to search through the ModuleManager.
     *  - add error handling.
     */
    public Task(String description, String moduleCode, int frequency) {
        this.description = description;
        this.moduleCode = moduleCode;
        this.frequency = frequency;
        //this.isDone = false;
        this.date =null;
    }

    public Task(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }

    public String getModuleCode() {
        //if (this.moduleCode.equals("")){
        //    return "unMod";
        //}
        return this.moduleCode;
    }

    public String getFullDescription() {
        return description;
    }

    public String getDescription() {
        return description;
    }


    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public String getTaskType() {
        return taskType;
    }

    public LocalDate getDate(){ return date;}

    public void maskAsDone() {
        this.isDone = true;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setModulecode(String newModulecode){ this.moduleCode = newModulecode; }

    public void setDate(String newDate){
        this.by = newDate;
    }

    public void setTime( String newTime) {
        int time = Integer.parseInt(newTime);
        if(time > 2359| (time/100)>23|(time%100)>59){
            System.out.println("Invalid Time format");
        } else{
            this.time = newTime;
        }
    }

    public void setTasktype(String newTasktype){
        if(newTasktype.equals("D") | newTasktype.equals("E")) {
            this.taskType = newTasktype;
        }
        else {
            System.out.println("Invalid Task type");
        }
    }

    public void setFrequency(int newFrequency) {
        this.frequency = newFrequency;
    }
}
