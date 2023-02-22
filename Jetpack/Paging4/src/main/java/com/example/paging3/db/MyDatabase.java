package com.example.paging3.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.paging3.model.Movie;

@Database(entities = {Movie.class}, version = 1,exportSchema = true)
public abstract class MyDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "my_db.db";

    private static MyDatabase myInstance;

    public static synchronized MyDatabase getInstance(Context context){
        if(myInstance == null){
            myInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MyDatabase.class,
                    DATABASE_NAME)
                    .build();
        }

        return myInstance;
    }

    public abstract MovieDao getMovieDao();
}
