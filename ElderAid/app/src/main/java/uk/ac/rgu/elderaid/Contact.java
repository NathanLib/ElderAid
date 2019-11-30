package uk.ac.rgu.elderaid;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int contactId;

    private String name;
    private String phoneNum;
    private Bitmap image;

    public Contact(){
        super();
    }

    //Constructor
    public Contact(String name, String phoneNum, Bitmap image){
        this.name = name;
        this.phoneNum = phoneNum;
        this.image = image;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public Bitmap getImage() {
        return image;
    }

    //Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
