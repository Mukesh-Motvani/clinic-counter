package com.bma.counter.clinic.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by Montu on 4/5/2018.
 */

public class UtisClinicCounter {

        public static void decoded(String JWTEncoded) throws Exception {
            try {
                String[] split = JWTEncoded.split("\\.");
                Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
                Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
            } catch (UnsupportedEncodingException e) {
                //Error
            }
        }

        private static String getJson(String strEncoded) throws UnsupportedEncodingException{
            byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
            return new String(decodedBytes, "UTF-8");
        }

        public static boolean isInternet(Context context){
            boolean isAvailible = false;
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                isAvailible = true;
            }
            else{
                isAvailible = false;
            }
            return isAvailible;
        }



}
