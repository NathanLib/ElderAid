package uk.ac.rgu.elderaid;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact")
public class Contact {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int contactId;

    private String name;
    private String phoneNum;
    private String imagePath;

    //Constructor
    public Contact(String name, String phoneNum, String imagePath){
        this.name = name;
        this.phoneNum = phoneNum;
        this.imagePath = imagePath;
    }

    // Getters
    public int getContactId() {
        return contactId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getImagePath() {
        return imagePath;
    }

    //Setters
    public void setContactId(int contactId){
        this.contactId = contactId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
