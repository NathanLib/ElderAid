package uk.ac.rgu.elderaid;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Event {
    @NonNull
    @PrimaryKey (autoGenerate = true)
    private String eventID;

    private String title;
    private String desc;
    private String startDate;
    private String endDate;
    private String location;

    //Constructor
    public Event(String title, String desc, String startDate, String endDate, String location){
        this.title = title;
        this.desc = desc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    @NonNull
    public String getEventID() {
        return eventID;
    }

    public void setEventID(@NonNull String eventID) {
        this.eventID = eventID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
