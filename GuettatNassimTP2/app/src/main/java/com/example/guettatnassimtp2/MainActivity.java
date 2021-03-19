package com.example.guettatnassimtp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent otherActivity = new Intent(MainActivity.this, Authentification.class);
        startActivity(otherActivity);
    }
}