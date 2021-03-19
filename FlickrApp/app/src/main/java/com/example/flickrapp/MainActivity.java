package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


class GetImageListener implements View.OnClickListener {

    private AppCompatActivity activity;

    public GetImageListener(AppCompatActivity activity){

        this.activity = activity;

    }
@Override
public void onClick(View v) {
        AsyncFlickrJSONData task = new AsyncFlickrJSONData(activity);
        task.execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");


        }

        }

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // We get all the elements of the layout
        Button button = findViewById(R.id.getImageBtn);
        Button list = findViewById(R.id.redirect);
        Intent intent = new Intent(MainActivity.this, ListActivity.class); // Create amain activity intent
        button.setOnClickListener(new GetImageListener(MainActivity.this));
        list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent); //when the user click on the list button, we start the list activity
            }
        });
    }
}