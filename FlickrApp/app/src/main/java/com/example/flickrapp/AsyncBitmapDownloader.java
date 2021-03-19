package com.example.flickrapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {

    @SuppressLint("StaticFieldLeak")
    private final ImageView image;

    public AsyncBitmapDownloader(AppCompatActivity a){
        image = a.findViewById(R.id.image);
    }

    @Override
    protected Bitmap doInBackground(String... strings) { // I comented only the doInBackground of the AsyncFlickrJSONData class
        URL url = null;
        try {
            url = new URL(strings[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = null;
        try {
            assert url != null;
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert connection != null;
        connection.setDoInput(true);
        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream input = null;
        try {
            input = connection.getInputStream(); // Get the image
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(input); // return a bitmapfactory to create the image
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        this.image.setImageBitmap(bitmap); // We put the image in the ListView
    }
}