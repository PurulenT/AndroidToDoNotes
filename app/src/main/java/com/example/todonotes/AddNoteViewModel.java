package com.example.todonotes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AddNoteViewModel extends AndroidViewModel {
    NoteDatabase noteDatabase;
    MutableLiveData<Boolean> shouldClose = new MutableLiveData<>(false);

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getInstance(application);
    }

    public void saveNote(Note note){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteDatabase.notesDao().add(note);
                shouldClose.postValue(true);
            }
        });
        thread.start();
    }

    public LiveData<Boolean> getShouldClose(){
        return shouldClose;
    }
}
