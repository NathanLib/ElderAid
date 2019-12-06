package uk.ac.rgu.elderaid;


import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentedTest {
    private EventDao eventDao;
    private ElderaidDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, ElderaidDatabase.class).build();
        eventDao = db.eDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertEvent() throws Exception {
          Event e = new Event("Title1","Description1","2020-1-3","2020-1-1",
                  "RGU","2019-12-06");

          eventDao.insert(e);

          List<Event> returnedList = eventDao.getEventsForDate("2020-1-3");
          assertThat(returnedList.get(0).getTitle(), equalTo(e.getTitle()));



    }

    @Test
    public void deleteEvent(){
        eventDao.deleteAllEvents();

        List<Event> returnedDeletedList = eventDao.getEventsForDate("2020-1-3");
        assertThat(returnedDeletedList.size(), equalTo(0));
    }
}
