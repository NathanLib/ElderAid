package uk.ac.rgu.elderaid;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import java.util.Comparator;


@Entity(tableName = "contact")
public class Contact {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int contactId;

    private String name;
    private String phoneNum;
    private String imagePath;

    private Boolean isFavourite;

    //Constructor

    public Contact(String name, String phoneNum, String imagePath, Boolean isFavourite) {
        this.name = name;

        this.phoneNum = phoneNum;
        this.imagePath = imagePath;
        this.isFavourite = isFavourite;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Contact{");
        sb.append("contactId=").append(contactId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", phoneNum='").append(phoneNum).append('\'');
        sb.append(", imagePath='").append(imagePath).append('\'');
        sb.append(", isFavourite=").append(isFavourite);
        sb.append('}');
        return sb.toString();
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

    public Boolean getIsFavourite() {
        return isFavourite;
    }

    //Setters
    public void setContactId(int contactId) {
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

    public void setIsFavourite(Boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
}

