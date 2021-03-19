package com.example.guettatnassimtp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Authentification extends AppCompatActivity {


    private Button authenticate;
    private EditText userTf;
    private EditText passwordTf;
    private TextView resultTv;
    private JSONObject jsonResult;

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);


        authenticate = (Button) findViewById(R.id.authenticate);
        userTf = (EditText) findViewById(R.id.userTf);
        passwordTf = (EditText) findViewById(R.id.passwordTf);
        resultTv = (TextView) findViewById(R.id.resultTv);



        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            URL url = null;
                            try {
                                url = new URL("https://httpbin.org/basic-auth/bob/sympa");

                                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                String basicAuth = "Basic " + Base64.encodeToString( (userTf.getText()+ ":" + passwordTf.getText()).getBytes(),
                                        Base64.NO_WRAP);
                                urlConnection.setRequestProperty ("Authorization", basicAuth);
                                try {
                                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                                    String s = readStream(in);
                                    jsonResult = new JSONObject(s);
                                    Log.i("JFL", s);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {


                                            try {
                                                JSONArray array = jsonResult.toJSONArray(jsonResult.names());
                                                resultTv.setText("Authentiticated : " +array.getString(0) + " user : " + array.getString(1));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });



                                } finally {
                                    urlConnection.disconnect();
                                }
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();


            }
        });


    }


}