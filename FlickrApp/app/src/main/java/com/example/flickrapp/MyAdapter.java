package com.example.flickrapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.Vector;


public class MyAdapter extends BaseAdapter {

    private final Vector<String> v = new Vector<String>();
    private final Context c;

    public MyAdapter(Context c){
        this.c = c;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.i("JFL", "TODO");
        // Load the convert View of the new activity to put the image in the List
        if (convertView == null) {
            convertView = LayoutInflater.from(c)
                    .inflate(R.layout.bitmaplayout, parent, false);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        RequestQueue queue = MySingleton.getInstance(image.getContext()).getRequestQueue(); // MySingleton manage the usage of the requestqueue
        // Request the image and download it before adding to the queue
        ImageRequest request = new ImageRequest(getItem(position).toString(),
                (Response.Listener<Bitmap>) image::setImageBitmap, 200, 200, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("JFL", "Image Load Error: ");
            }
        });
        // Add the image to the queue
        queue.add(request);
        return convertView;
    }

    @Override
    public int getCount() {
        return v.size();
    }

    public void add(String url){
        this.v.add(url);
    }

    @Override
    public Object getItem(int position) {
        return v.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}