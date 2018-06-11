package com.bma.counter.clinic.service;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by suraj on 4/6/2016.
 */
public class ThreadGetResponsePost extends AsyncTask<Object,Object,String> {

    private Context context;
    private Handler handler;
    private String mUrl;


    private String jsonObject;

    public ThreadGetResponsePost(Context context, Handler handler, String url, String jsonObject){

        this.context = context;
        this.handler = handler;
        this.mUrl     = url;
        this.jsonObject = jsonObject;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Log.d("test", "on pre execute");
        Log.d("test", "url: "+mUrl);
        Log.d("test", "payload: : "+jsonObject);
    }

    @Override
    protected String doInBackground(Object... params) {

        try {

            URL url = new URL(mUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(jsonObject.getBytes().length));
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream ());
            wr.writeBytes(jsonObject);
            wr.flush();
            wr.close();

            Log.d("test", "response code: " + urlConnection.getResponseCode());

            BufferedReader bufferedReader =  new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response         =  new StringBuilder();
            String inputLine;

             while ((inputLine = bufferedReader.readLine())!=null){
                response.append(inputLine).append("\n");
            }

            bufferedReader.close();

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        Log.d("test", "on post execute:");
        Log.d("test", "response: "+response);
        if(response == null){

            ((Activity)context).finish();
            // Intent intent = new Intent(context, ActivityConnectionFailed.class);
            //context.startActivity(intent);
        }

        Message message = new Message();
        message.obj = response;
        handler.sendMessage(message);

    }

}