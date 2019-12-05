package uk.ac.rgu.elderaid;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task")
public class Task {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int taskId;

    private String name;
    private String date;
    private String time;
    private String occurrence;
    private String description;

    //Constructor
    public Task(String name, String date, String time, String occurrence, String description) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.occurrence = occurrence;
        this.description = description;
    }

    // Getters
    public int getTaskId(){
        return taskId;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getOccurrence(){ return occurrence;}

    public String getDescription(){ return description;}

    //Setters
    public void setTaskId(int taskId){this.taskId = taskId;}

    public void setTName(String name){this.name = name;}

    public void setTDate(String date){this.date = date;}

    public void setTime(String time){this.time = time;}

    public void setOccurrence(String occurrence){this.occurrence = occurrence; }

    public void setDescription(String description){this.description = description;}
}
