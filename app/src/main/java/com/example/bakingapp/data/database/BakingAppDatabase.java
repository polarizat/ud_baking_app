package com.example.bakingapp.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.bakingapp.data.models.Recipe;

@Database(entities = {Recipe.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class BakingAppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "recipes";

    private static final String LOG_TAG = BakingAppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static BakingAppDatabase sInstance;

    public static BakingAppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        BakingAppDatabase.class, BakingAppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }
    public abstract RecipesDao recipesDao();
}
