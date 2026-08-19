package com.example.todonotes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private RadioButton radioButtonLowPriority;
    private RadioButton radioButtonMediumPriority;
    private RadioButton radioButtonHighPriority;
    private Button saveNoteButton;
//    private Database database = Database.getInstance();
    private NoteDatabase noteDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void initViews(){
        editTextNote = findViewById(R.id.editTextNote);
        radioButtonLowPriority = findViewById(R.id.radioButtonLowPriority);
        radioButtonMediumPriority = findViewById(R.id.radioButtonMediumPriority);
        radioButtonHighPriority = findViewById(R.id.radioButtonHighPriority);
        saveNoteButton = findViewById(R.id.saveNoteButton);
        noteDatabase = NoteDatabase.getInstance(getApplication());
    }

    private void saveNote(){
        String noteText = editTextNote.getText().toString().trim();
        int priority = getPriority();
//        int id = database.getNotes().size();
        int id = 0; //when zero id will be generated
        Note note = new Note(noteText, priority); //pass id with additive constructor
//        database.addNote(note);
        noteDatabase.notesDao().add(note);
        finish(); //заканчивается работа активити после нажатия на кнопку
    }

    private int getPriority(){
        int priority;
        if(radioButtonLowPriority.isChecked()){
            priority = 0;
        }
        else if(radioButtonMediumPriority.isChecked()){
            priority = 1;
        }
        else{
            priority = 2;
        }
        return priority;
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, AddNoteActivity.class);
        return intent;
    }
}