package com.bma.counter.clinic.preference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Montu on 4/11/2018.
 */

public class ClinicPref {

    public static ClinicPref pref;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "ClinicCounterPreference";
    private static final String USER_NAME = "username";
    private static final String IS_SCANNED = "isScanned";
    private static final String jwToken = "jwToken";
    private static final String JWT_HEADER = "jwtHeader";
    private static final String MAIN_LOGO = "mainLogo";

    private ClinicPref(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, 0);
        editor = preferences.edit();
    }

    public static ClinicPref getInstance(Context context) {
        if (pref == null) {
            pref = new ClinicPref(context);
        }
        return pref;
    }

    public void setUserName(String userName) {
        editor.putString(USER_NAME, userName);
        editor.commit();
    }


    public String getUserName() {

        return preferences.getString(USER_NAME, "");
    }

    public void setJwToken(String jwtoken) {
        editor.putString(jwToken, jwtoken);
        editor.commit();
    }


    public String getjwToken() {
        return preferences.getString(jwToken, "");
    }


    public void setJwtHeader(String jwtHeader) {
        editor.putString(JWT_HEADER, jwtHeader);
        editor.commit();
    }

    public String getjwtHeader() {
        return preferences.getString(JWT_HEADER, "");
    }

    public void setMainLogo(String mainLogo) {
        editor.putString(MAIN_LOGO, mainLogo);
        editor.commit();
    }

    public String getMainLogo() {
        return preferences.getString(MAIN_LOGO, "");
    }


    public void setIsScanned(boolean isUserLogin) {
        editor.putBoolean(IS_SCANNED, isUserLogin);
        editor.commit();
    }

    public boolean getIsScanned() {
        return preferences.getBoolean(IS_SCANNED, false);
    }

}
