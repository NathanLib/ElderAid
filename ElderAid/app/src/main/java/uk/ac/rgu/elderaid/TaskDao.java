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
public interface TaskDao {

    // Insert singular task
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Task task);

    // Insert multiple task
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertTasks(Task... task);

    //Returns all task
    @Query("SELECT * FROM Task ORDER BY name ASC")
    public List<Task> getTask();

    //Update a singular task
    @Update
    public void update(Task task);

    //Update multiple task
    @Update
    public void updateContacts(Task... task);

    //Delete a singular task
    @Delete
    public void delete(Task task);

    //Delete multiple task
    @Delete
    public void deleteTasks(Task... task);
}
