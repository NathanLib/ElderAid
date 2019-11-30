package uk.ac.rgu.elderaid;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version= 1)
public abstract class ElderaidDatabase extends RoomDatabase {

    public abstract ContactDao cDao();

    private static ElderaidDatabase INSTANCE;

    public static ElderaidDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (ElderaidDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ElderaidDatabase.class,"elderaid_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
