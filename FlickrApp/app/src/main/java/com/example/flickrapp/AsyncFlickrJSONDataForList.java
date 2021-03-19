package com.example.flickrapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, JSONObject> { // quite the same as AsyncFlickrJSONData

    private final MyAdapter adapter;

    public  AsyncFlickrJSONDataForList(MyAdapter a){
        this.adapter = a;
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        JSONObject s = null;
        try {
            URL url = new URL(urls[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                s = readStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    private JSONObject readStream(InputStream is) { // I comented only the readstream of the AsyncFlickrJSONData class
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            StringBuilder s = new StringBuilder();
            s.append(bo.toString());
            String j = s.substring("jsonFlickrFeed(".length(), s.length() - 1);
            return new JSONObject(j);
        } catch (IOException | JSONException e) {
            return new JSONObject();
        }
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        JSONArray items = null;
        try {
            items = jsonObject.getJSONArray("items");
            for (int i = 0; i < items.length(); i++){
                Log.i("JFL", "Adding to adapter url: " + items.getJSONObject(i).getJSONObject("media").getString("m")); // Display the values in the terminal to check if it's ok
                adapter.add(items.getJSONObject(i).getJSONObject("media").getString("m")); // Add a new Image to the
                adapter.notifyDataSetChanged(); // This informs the adapter that data has changed and that the display needs to be refreshed.
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}