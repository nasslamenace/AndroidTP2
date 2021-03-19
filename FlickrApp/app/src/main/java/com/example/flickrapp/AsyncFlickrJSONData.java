package com.example.flickrapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject>{

    @SuppressLint("StaticFieldLeak")
    private AppCompatActivity a;

    public AsyncFlickrJSONData(AppCompatActivity a){
        this.a = a;
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        JSONObject s = null;
        try {
            URL url = new URL(urls[0]); // We get the URI in parameter
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection(); // Create the connection with the specifie URI
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Get all the data and put it in a buffer
                s = readStream(in); // this function read all the data and convert in a JSONObject
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect(); // Disconnect because we finish the task
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    private JSONObject readStream(InputStream is) { // this function read all the data and convert in a JSONObject
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i); // Write in the array
                i = is.read();
            }
            StringBuilder s = new StringBuilder(); // this object allow the manipulation with the string
            s.append(bo.toString());
            String j = s.substring("jsonFlickrFeed(".length(), s.length() - 1); // Removes text that prevents conversion to JSONObject
            return new JSONObject(j); // Return the JSONObject
        } catch (IOException | JSONException e) {
            return new JSONObject();
        }
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        JSONArray items = null;
        try {
            items = jsonObject.getJSONArray("items"); // We retrieve what interests us in the object
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i< Objects.requireNonNull(items).length(); i++)
        {
            JSONObject flickr_entry = null;
            try {
                flickr_entry = items.getJSONObject(i); // Create a clean object
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String urlmedia = null;
            try {
                assert flickr_entry != null;
                urlmedia = flickr_entry.getJSONObject("media").getString("m"); // get the URL of the picture we want to display
                if (i==0){
                    AsyncBitmapDownloader task = new AsyncBitmapDownloader(this.a); // Load the image in the app
                    task.execute(urlmedia); // Execute the task to put the image
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("JFL", "URL media: " + urlmedia); // Juste write the URI in the terminal to see if it's ok
        }
    }
}