package com.bma.counter.clinic;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

import com.bma.counter.clinic.appSingletone.ModelSiteOption;
import com.bma.counter.clinic.preference.ClinicPref;
import com.bma.counter.clinic.service.ThreadGetRequest;
import com.bma.counter.clinic.setters.SetterGeneralClass;
import com.bma.counter.clinic.utils.UtisClinicCounter;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivitySplash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    private ImageView logoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logoImage = (ImageView) findViewById(R.id.logoImage);

        new ThreadGetRequest(this, new HandlerDataGet(), "http://www.malpaniground.com:8080/api/getSiteOptions").execute();

        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (!ClinicPref.getInstance(ActivitySplash.this).getIsScanned()) {
                    Intent i = new Intent(ActivitySplash.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(ActivitySplash.this, ActivityPatientDetails.class);
                    startActivity(i);
                }
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    private void siteDataSorting(SetterGeneralClass setterGeneralClass) {
        for (int i = 0; i < setterGeneralClass.getData().size(); i++) {

            switch (setterGeneralClass.getData().get(i).getOptionName()) {

                case "site_logo":

                    break;

                case "site_domain":
                    ModelSiteOption.getInstance().setDomainPath(setterGeneralClass.getData().get(i).getOptionVal());
                    break;
                case "default_logo_path":
                    ModelSiteOption.getInstance().setDomainPath(setterGeneralClass.getData().get(i).getOptionVal());
                    break;

                case "thank_you_message":
                    ModelSiteOption.getInstance().setThanksNote(setterGeneralClass.getData().get(i).getOptionVal());
                    break;

                case "contact_email":
                    ModelSiteOption.getInstance().setEmailId(setterGeneralClass.getData().get(i).getOptionVal());
                    break;

                case "contact_phone":
                    ModelSiteOption.getInstance().setMobileNumber(setterGeneralClass.getData().get(i).getOptionVal());
                    break;

                case "app_over":
                    ModelSiteOption.getInstance().setAppOver(setterGeneralClass.getData().get(i).getOptionVal());
                    break;

            }

            if (setterGeneralClass.getData().get(i).getOptionName().equalsIgnoreCase("site_logo")) {
                Log.d("image URL =", setterGeneralClass.getData().get(i).getOptionVal());
            }

        }


    }

    private class HandlerDataGet extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String response = (String) msg.obj;
            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    SetterGeneralClass setterGeneralClass = new Gson().fromJson(jsonObject.toString(), SetterGeneralClass.class);
                    ModelSiteOption.getInstance().setSetterGeneralClass(setterGeneralClass);
                    siteDataSorting(setterGeneralClass);

                    for (int i = 0; i < setterGeneralClass.getData().size(); i++) {
                        if (setterGeneralClass.getData().get(i).getOptionName().equalsIgnoreCase("site_logo")) {
                            Log.d("image URL =", setterGeneralClass.getData().get(i).getOptionVal());
                            ClinicPref.getInstance(ActivitySplash.this).setMainLogo(setterGeneralClass.getData().get(i).getOptionVal());
                            Picasso.get().load("http://www.malpaniground.com:8080/global/images/logo/" + setterGeneralClass.getData().get(i).getOptionVal()).into(logoImage);
                            Log.d("test", "http://www.malpaniground.com:8080/global/images/logo/" + setterGeneralClass.getData().get(i).getOptionVal());
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
