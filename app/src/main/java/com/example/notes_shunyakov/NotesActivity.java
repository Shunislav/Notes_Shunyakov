package com.example.notes_shunyakov;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesActivity extends AppCompatActivity {
EditText title1;
EditText not1;
ImageView saveimg;
Notes notes;
    boolean old = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        saveimg = findViewById(R.id.saveimg);
        title1 = findViewById(R.id.title);
        not1 = findViewById(R.id.not);
        notes = new Notes();
        try {
            notes = (Notes) getIntent().getSerializableExtra("not");
            title1.setText(notes.getTitle());
            not1.setText(notes.getNotes());
            old = true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        saveimg.setOnClickListener (new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String title = title1.getText().toString();
                String not = not1.getText().toString();

                if(not.isEmpty()){
                    Toast.makeText(NotesActivity.this, "Enter notes", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                Date data = new Date();

                if(!old){
                    notes = new Notes();
                }


                notes.setTitle(title);
                notes.setNotes(not);
                notes.setData(format.format((data)));

                Intent intent = new Intent();
                intent.putExtra("notes",notes);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });


    }
}