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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private RadioButton radioButtonLowPriority;
    private RadioButton radioButtonMediumPriority;
    private RadioButton radioButtonHighPriority;
    private Button saveNoteButton;
    private AddNoteViewModel viewModel;


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
        viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        viewModel.getShouldClose().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if(shouldClose){
                    finish();
                }
            }
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
    }

    private void saveNote(){
        String noteText = editTextNote.getText().toString().trim();
        int priority = getPriority();
        Note note = new Note(noteText, priority); //pass id with additive constructor
        viewModel.saveNote(note);
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