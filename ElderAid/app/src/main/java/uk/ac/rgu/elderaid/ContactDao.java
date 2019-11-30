package uk.ac.rgu.elderaid;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//It is possible to create a singular Dao which could have all of the queries in it.
//However a Dao for each table is more organised.
@Dao
public interface ContactDao {


    // Insert singular contact
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Contact contact);

    // Insert multiple contacts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertContacts(Contact... contact);


    //Returns all contacts
    @Query("SELECT * FROM Contact ORDER BY name ASC")
    public List<Contact> getContacts();


    //Update a singular Contact
    @Update
    public void update(Contact contact);

    //Update multiple Contacts
    @Update
    public void updateContacts(Contact... contact);

    //Delete a singular Contact
    @Delete
    public void delete(Contact contact);

    //Delete multiple Contacts
    @Delete
    public void deleteContacts(Contact... contact);
}
