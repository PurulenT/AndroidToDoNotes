package com.example.todonotes;

import java.util.ArrayList;
import java.util.Random;

public class Database {

    private ArrayList<Note> notes = new ArrayList<>();

    private static Database instance = null;

    public static Database getInstance(){
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }

    private Database(){
        Random random = new Random();
        for(int i = 0; i < 20_000; i++){
            notes.add(new Note(notes.size(), "text content" + i, random.nextInt(3)));
        }
    }

    public void addNote(Note note){
        notes.add(note);
    }

    public void removeNotes(int id){
        for(int i = 0; i < notes.size(); i++){
            Note note = notes.get(i);
            if(note.getId() == id){
                notes.remove(note);
            }
        }
    }

    public ArrayList<Note> getNotes(){
        return new ArrayList<>(notes);
    }
}
