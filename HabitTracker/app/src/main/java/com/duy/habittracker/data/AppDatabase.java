package com.duy.habittracker.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Topic.class, TrackRecord.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract TopicDao topicDao();
    public abstract RecordDao recordDao();

    public static AppDatabase get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "habit-db"
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}

