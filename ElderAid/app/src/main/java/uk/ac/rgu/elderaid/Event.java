package uk.ac.rgu.elderaid;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.api.client.util.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "event")
public class Event {
    @NonNull
    @PrimaryKey (autoGenerate = true)
    private int eventID;

    private String title;
    private String desc;
    private String startDate;
    private String endDate;
    private String location;
    private String creation;


    public Event(String title, String desc, String startDate, String endDate, String location, String creation){
        this.title = title;
        this.desc = desc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.creation = creation;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    @NonNull
    public int getEventID() {
        return eventID;
    }

    public void setEventID(@NonNull int eventID) {
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


//    public String generateKey(){
//        //Since the google event IDs provided by the API use a naming convention that
//        // only permits letters from a-v, our keys will start with the z character,
//        // preventing any collisions
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
//        Date date = new Date();
//        String currentDateTime =formatter.format(date);
//
//        // Since the events can only be made one by one when creating them manually,
//        // the final time increment of seconds should be fine.
//        StringBuilder key = new StringBuilder();
//        key.append("z")
//                .append(currentDateTime);
//
//        return key.toString();
//    }
}
