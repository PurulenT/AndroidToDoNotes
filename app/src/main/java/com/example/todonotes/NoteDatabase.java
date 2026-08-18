package com.example.todonotes;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public static NoteDatabase instance = null;
    public static final String DB_NAME = "notes.db";

    public static NoteDatabase getInstance(Application application){
        if (instance == null){
            instance = Room.databaseBuilder(application, NoteDatabase.class, DB_NAME)
                    .allowMainThreadQueries() //temporary allowing to use main thread
                    .build();
        }
        return instance;
    }

    public abstract NotesDao notesDao();
}
