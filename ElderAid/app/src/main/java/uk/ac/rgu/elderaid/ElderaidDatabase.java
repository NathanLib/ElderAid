package uk.ac.rgu.elderaid;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Contact.class, Event.class, Prescription.class, Task.class}, version= 3, exportSchema = false)

public abstract class ElderaidDatabase extends RoomDatabase {

    public abstract ContactDao cDao();
    public abstract EventDao eDao();
    public abstract PrescriptionDao pDao();
    public abstract TaskDao tDao();

    private static ElderaidDatabase INSTANCE;

    public static ElderaidDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (ElderaidDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ElderaidDatabase.class,"elderaid_database")
                            .fallbackToDestructiveMigration()
                            // ONLY FOR DEVELOPMENT WITHOUT ASYNC!!! >>>>> .allowMainThreadQueries()
                            .build();
                } } }
        return INSTANCE;
    }


}
