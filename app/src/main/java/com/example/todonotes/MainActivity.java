package com.example.todonotes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private RecyclerView notesRecyclerView;
    private FloatingActionButton btnAddNote;
    private NoteDatabase noteDatabase;
    private NotesAdapter notesAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        notesAdapter = new NotesAdapter();
        notesRecyclerView.setAdapter(notesAdapter);
        notesAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
            }
        });
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddNoteActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = notesAdapter.getNotes().get(position);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        noteDatabase.notesDao().remove(note.getId());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showNotes();
                            }
                        });
                    }
                });
                thread.start();
            }
        });
        itemTouchHelper.attachToRecyclerView(notesRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }

    private void initViews(){
        btnAddNote = findViewById(R.id.btnAddNote);
        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        noteDatabase = NoteDatabase.getInstance(getApplication());
    }

    private void showNotes(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Note> notes = noteDatabase.notesDao().getNotes();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notesAdapter.setNotes(noteDatabase.notesDao().getNotes());
                    }
                });
            }
        });
        thread.start();
    }
}