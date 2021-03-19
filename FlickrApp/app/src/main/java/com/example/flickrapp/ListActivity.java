package com.example.flickrapp;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ListActivity extends AppCompatActivity {

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ListView l = findViewById(R.id.list);
        adapter = new MyAdapter(l.getContext()); // We create the adapter how manage the list
        l.setAdapter(adapter); // We set the adapter on the viewlist
        AsyncFlickrJSONDataForList task = new AsyncFlickrJSONDataForList(adapter); // We instanciate the class before the execution
        task.execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json"); // We send the URI where we can get the images
    }
}