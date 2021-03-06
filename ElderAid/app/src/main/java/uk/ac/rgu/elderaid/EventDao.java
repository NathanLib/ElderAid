package uk.ac.rgu.elderaid;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface EventDao {

    // Insert singular Event
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Event event);

    // Insert multiple events
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertEvents(Event... event);


    //Returns all events
    @Query("SELECT * FROM Event ORDER BY startDate ASC")
    public List<Event> getEvents();

    //Returns the events for a specific date
    @Query("SELECT * FROM Event WHERE startDate = :startDate ORDER BY title ASC")
    public List<Event> getEventsForDate(String startDate);

    //Update a singular event
    @Update
    public void update(Event event);

    //Update multiple events
    @Update
    public void updateEvents(Event... event);

    //Delete a singular event
    @Delete
    public void delete(Event event);

    //Delete multiple events
    @Delete
    public void deleteEvents(Event... event);

    @Query("DELETE FROM Event")
    public void deleteAllEvents();

}
