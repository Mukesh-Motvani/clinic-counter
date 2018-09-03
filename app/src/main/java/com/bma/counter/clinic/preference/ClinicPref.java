package com.bma.counter.clinic.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SymbolTable;

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
    private static final String PRIVIOUS_STATUAS = "mainLogo";
    private static final String PREVIOUS_WAITING_TIME = "privious_waitingTime";
    private static final String totalServiceCall = "totalServiceCall";
    private static final String LASTPATIENTCOUNT = "LastpatientCount";
    private static final String PREVIOUSWAITINGCOUNT = "previousCurrentCount";
    private static final String SCREENLOG = "screenLog";
    private static final String  NOTIFICATION_TOKEN = "notification_token";
    private static final String  NOTIFICATION_COUNT = "notification_count";


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


    public void setPriviousStatus(String priviousStatus) {
        editor.putString(PRIVIOUS_STATUAS, priviousStatus);
        editor.commit();
    }


    public String getPriviousStatus() {
        return preferences.getString(PRIVIOUS_STATUAS, "");
    }


    public void setJwtHeader(String jwtHeader) {
        editor.putString(JWT_HEADER, jwtHeader);
        editor.commit();
    }

    public String getjwtHeader() {
        return preferences.getString(JWT_HEADER, "");
    }


    public void setNotificationToken (String token){
        editor.putString(NOTIFICATION_TOKEN,token);
        editor.commit();
    }

    public String getNotificationToken(){
        return preferences.getString(NOTIFICATION_TOKEN,"");
    }


    public void setMainLogo(String mainLogo) {
        editor.putString(MAIN_LOGO, mainLogo);
        editor.commit();
    }

    public String getMainLogo() {
        return preferences.getString(MAIN_LOGO, "");
    }


    public void setPriviousWaitingTime(long priviousCount) {
        editor.putLong(PREVIOUS_WAITING_TIME, priviousCount);
        editor.commit();
    }

    public long getPriviousWaitingTime() {
        return preferences.getLong(PREVIOUS_WAITING_TIME,0);
    }

    public void setNotificationCount(int notificationCount) {
        editor.putLong(NOTIFICATION_COUNT, notificationCount);
        editor.commit();
    }

    public long getNotificationCount() {
        return preferences.getLong(NOTIFICATION_COUNT,0);
    }

    public void setTotalServiceCall(long totalServiceCall) {
        editor.putLong(this.PREVIOUSWAITINGCOUNT, totalServiceCall);
        editor.commit();
    }

    public long getTotalServiceCall() {
        return preferences.getLong(PREVIOUSWAITINGCOUNT,0);
    }

    public void setPreviousWaitingCount(long previouusWaitingCount) {
        editor.putLong(this.totalServiceCall, previouusWaitingCount);
        editor.commit();
    }

    public long getPreviousWaitingCount() {
        return preferences.getLong(totalServiceCall,0);
    }


    public void setLastpatientcount(int lastpatientcount) {
        editor.putInt(LASTPATIENTCOUNT, lastpatientcount);
        editor.commit();
    }

    public long getLastpatientcount() {
        return preferences.getInt(LASTPATIENTCOUNT,0);
    }


    public void setScreenlog(int screenLog){
        editor.putInt(SCREENLOG,screenLog);
        editor.commit();
    }

    public int getScreenLog(){
        return preferences.getInt(SCREENLOG,0);
    }


    public void setIsScanned(boolean isUserLogin) {
        editor.putBoolean(IS_SCANNED, isUserLogin);
        editor.commit();
    }

    public boolean getIsScanned() {
        return preferences.getBoolean(IS_SCANNED, false);
    }

}
