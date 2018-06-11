package com.bma.counter.clinic.service;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 27-07-2017.
 */

public class ThreadGetRequest extends AsyncTask<Object,Object,String> {

    Context context;
    Handler handler;
    String murl;




    public ThreadGetRequest(Context context, Handler handler, String murl) {
        this.context = context;
        this.handler = handler;
        this.murl = murl;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute(); Log.d("test", "on pre execute");
        Log.d("test", "url: "+murl);
       }

    @Override
    protected String doInBackground(Object... params) {
        try {
            URL url = new URL(murl);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");


            Log.d("test", "response code: " + urlConnection.getResponseCode());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null) {

                response.append(inputLine).append("\n");
            }

            bufferedReader.close();
            return response.toString();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            Log.d("test", "onPostExecute");
            Log.d("test", "response: "+response);

            Message message = new Message();
            message.obj = response;
            handler.sendMessage(message);
    }
}
