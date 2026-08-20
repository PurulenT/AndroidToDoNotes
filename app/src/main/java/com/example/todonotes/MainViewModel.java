package com.example.todonotes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    NoteDatabase noteDatabase;
    public MainViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getInstance(application);
    }

    public void removeNote(Note note){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteDatabase.notesDao().remove(note.getId());
            }
        });
        thread.start();
    }

    public LiveData<List<Note>> getNotes(){
        return noteDatabase.notesDao().getNotes();
    }
}
