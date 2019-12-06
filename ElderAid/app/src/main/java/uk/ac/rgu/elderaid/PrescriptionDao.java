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
public interface PrescriptionDao {

    // Insert singular prescription
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Prescription prescription);

    // Insert multiple prescription
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertPrescriptions(Prescription... prescription);

    //Returns all prescription's
    @Query("SELECT * FROM Prescription ORDER BY name ASC")
    public List<Prescription> getPrescription();

    //Update a singular Prescription
    @Update
    public void update(Prescription prescription);

    //Update multiple Prescription
    @Update
    public void updateContacts(Prescription... prescription);

    //Delete a singular Prescription
    @Delete
    public void delete(Prescription prescription);

    //Delete multiple Prescription
    @Delete
    public void deletePrescriptions(Prescription... prescription);

}
