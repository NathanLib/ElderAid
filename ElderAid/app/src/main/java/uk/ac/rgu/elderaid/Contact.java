package uk.ac.rgu.elderaid;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity
public class Contact {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int contactId; // auto generate doesn't work, all contacts have the same idea

    private String name;
    private String phoneNum;
    private String imagePath;

    //Constructor
    public Contact(String name, String phoneNum, String imagePath) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Contact{");
        sb.append("contactId=").append(contactId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", phoneNum='").append(phoneNum).append('\'');
        sb.append(", imagePath='").append(imagePath).append('\'');
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


    // Code from https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/
    /*Comparator for sorting the list by Contact Name*/
    public static Comparator<Contact> ContactNameComparator = new Comparator<Contact>() {

        public int compare(Contact c1, Contact c2) {
            String contactName1 = c1.getName().toUpperCase();
            String contactName2 = c1.getName().toUpperCase();

            //ascending order
            return contactName1.compareTo(contactName2);

            //descending order
            //return contactName2.compareTo(contactName1);
        }
    };
}

