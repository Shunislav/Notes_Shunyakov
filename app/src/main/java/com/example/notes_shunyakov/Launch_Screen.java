package com.example.notes_shunyakov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Launch_Screen extends AppCompatActivity {
    private static int splash = 2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_screen);

    new Handler().postDelayed(new Runnable(){
        @Override
                public  void run(){
            Intent intent = new Intent(Launch_Screen.this,Main.class);
                startActivity(intent);
                finish();
            }
        }, splash);
    }
}