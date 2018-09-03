package com.bma.counter.clinic;


import android.content.DialogInterface;
import android.content.Intent;

import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bma.counter.clinic.appSingletone.ModelSiteOption;
import com.bma.counter.clinic.fonts.Fonts;
import com.bma.counter.clinic.preference.ClinicPref;
import com.bma.counter.clinic.service.ThreadGetResponsePost;
import com.bma.counter.clinic.setters.MainDetailPojo;
import com.bma.counter.clinic.setters.PatientDetailResposnsePojo;
import com.bma.counter.clinic.setters.SetterAppointmentResponse;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import cn.iwgang.countdownview.CountdownView;



public class ActivityPatientDetails extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private CountdownView mCvCountdownViewTest22;
    private ImageView imgHome, imgRefresh, imgCancel;
    private TextView errortext, txtNote, title, textWait, txtName, txtDocName, txtDocNameValue, txtWardNo, txtWardNoValue, txtYourNo, txtYourNOValue, txtCurrentNo, txtCurrentValue, txtAppId, txtAppIdValueNew, txtDocStatusValue, txtDocStatus;
    private String sJsonPatientData;
    private String sJsonHeader;
    private String jwt;
    private PatientDetailResposnsePojo setterMainPatientDetail;
    private MainDetailPojo setterDetailPatient;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Handler customHandler;
    private LinearLayout scrollLin, errorlayout;
    private boolean isStop = false;
    private static Integer currentCount;
    private static int serviceCount;
    private static long priviousTime;
    private static boolean isAvgTym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_passanger_details);


        loadtoolbar();
        inItVIews();
        getJsonData();
        requestForCount();
        ClinicPref.getInstance(this).setTotalServiceCall(0);
        serviceCount = 0;
        currentCount = 0;
        ClinicPref.getInstance(this).setScreenlog(1);

    }

    private void requestForCount() {
        customHandler = new Handler();
        customHandler.postDelayed(updateTimerThread, 30000);
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            //write here whaterver you want to repeat
            if (!isStop) {
                callServiceAppointment();
                customHandler.postDelayed(this, 10000);
            } else {
                customHandler.removeCallbacks(this);
            }
        }
    };

    private void callServiceAppointment() {
        String currentDateandTime = sdf.format(new Date());

        String mPayload = "appointment=" + ClinicPref.getInstance(ActivityPatientDetails.this).getjwToken() + "&" + "deviceTime=" + currentDateandTime;
        Log.d("testNew", "appointment:" + jwt + "\n" + "deviceTime:" + currentDateandTime);
        new ThreadGetResponsePost(ActivityPatientDetails.this, new HandlerAppointmentDetail(), /*"http://www.malpaniground.com:8080*/ModelSiteOption.getInstance().getDomainPath() + "/api/appointment-stats", mPayload).execute();
        //Log.d("test",new Gson().toJson(payload));
    }

    public void sendnotificationData(){

        String notiPaylod = "deviceId="+ClinicPref.getInstance(this).getNotificationToken();
        Log.d("test",notiPaylod);
        new ThreadGetResponsePost(ActivityPatientDetails.this,new HandlerNoti(),ModelSiteOption.getInstance().getDomainPath() + "/api/notification/send", notiPaylod ).execute();
    }

    private void getJsonData() {
        //if (getIntent().getExtras() != null) {
        //sJsonHeader = getIntent().getStringExtra(ClinicConstants.JWT_HEADER);
        sJsonPatientData = ClinicPref.getInstance(this).getjwtHeader();
        jwt = ClinicPref.getInstance(this).getjwToken();
        Gson gson = new Gson();
        setterMainPatientDetail = gson.fromJson(sJsonPatientData, PatientDetailResposnsePojo.class);
        String patientDetail = setterMainPatientDetail.getData();
        setterDetailPatient = gson.fromJson(patientDetail, MainDetailPojo.class);
        setData();
        // callServiceAppointment();
        //  }
    }

    private void setData() {
        txtName.setText("Welcome " + setterDetailPatient.getPatientName() + ",");
        txtAppIdValueNew.setText(String.valueOf(setterDetailPatient.getAppointmentId()));
        txtDocNameValue.setText(setterDetailPatient.getDoctorName());
        txtWardNoValue.setText(setterDetailPatient.getClinicRoomNumber());
        txtYourNOValue.setText(String.valueOf(setterDetailPatient.getCurrQueueCount()));
        currentCount = setterDetailPatient.getCurrQueueCount();
    }


    private void loadtoolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        imgCancel = (ImageView) findViewById(R.id.toolbarClose);
        imgHome = (ImageView) findViewById(R.id.toolBarHome);
        imgRefresh = (ImageView) findViewById(R.id.toobBarRefresh);

        //toolbar.setTitle("Patient Details");
        //toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(toolbar);
        imgRefresh.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        imgCancel.setOnClickListener(this);
    }

    private void inItVIews() {
        txtAppId = (TextView) findViewById(R.id.txtAppId);
        txtAppIdValueNew = (TextView) findViewById(R.id.appIdTxt);
        txtName = (TextView) findViewById(R.id.name);
        txtDocName = (TextView) findViewById(R.id.txtDocName);
        txtDocNameValue = (TextView) findViewById(R.id.txtDocNameValue);
        txtCurrentNo = (TextView) findViewById(R.id.txtCurrentNo);
        txtCurrentValue = (TextView) findViewById(R.id.txtCurrentNoValue);
        txtWardNo = (TextView) findViewById(R.id.txtWardNo);
        txtWardNoValue = (TextView) findViewById(R.id.txtWardNoValue);
        txtNote = (TextView) findViewById(R.id.txtNote);
        txtYourNo = (TextView) findViewById(R.id.txtYourNo);
        txtYourNOValue = (TextView) findViewById(R.id.txtYourNOValue);
        txtNote = (TextView) findViewById(R.id.txtNote);
        errortext = (TextView) findViewById(R.id.errortext);
        textWait = (TextView) findViewById(R.id.textWait);
        mCvCountdownViewTest22 = (CountdownView) findViewById(R.id.cv_countdownViewTest22);
        title = (TextView) findViewById(R.id.title);
        txtDocStatusValue = (TextView) findViewById(R.id.txtDocStatusValue);
        txtDocStatus = (TextView) findViewById(R.id.txtDocStatus);
        scrollLin = (LinearLayout) findViewById(R.id.scrollLin);
        errorlayout = (LinearLayout) findViewById(R.id.errorlayout);


        Fonts.getInstance().setTextViewFont(txtAppId, Fonts.MUSEO_1);
        Fonts.getInstance().setTextViewFont(txtAppIdValueNew, Fonts.MUSEO);
        Fonts.getInstance().setTextViewFont(txtName, Fonts.MUSEO_1);
        Fonts.getInstance().setTextViewFont(txtCurrentNo, Fonts.MUSEO_1);
        Fonts.getInstance().setTextViewFont(txtCurrentValue, Fonts.MUSEO);
        Fonts.getInstance().setTextViewFont(errortext, Fonts.MUSEO_1);
        Fonts.getInstance().setTextViewFont(txtWardNo, Fonts.MUSEO_1);
        Fonts.getInstance().setTextViewFont(txtWardNoValue, Fonts.MUSEO);
        Fonts.getInstance().setTextViewFont(txtDocName, Fonts.MUSEO_1);
        Fonts.getInstance().setTextViewFont(txtDocNameValue, Fonts.MUSEO);
        Fonts.getInstance().setTextViewFont(txtYourNo, Fonts.MUSEO_1);
        Fonts.getInstance().setTextViewFont(txtYourNOValue, Fonts.MUSEO);
        Fonts.getInstance().setTextViewFont(txtNote, Fonts.MUSEO_1);
        Fonts.getInstance().setTextViewFont(txtDocStatus, Fonts.MUSEO_1);
        Fonts.getInstance().setTextViewFont(txtDocStatusValue, Fonts.MUSEO);
        Fonts.getInstance().setTextViewFont(textWait, Fonts.MUSEO_1);
        Fonts.getInstance().setTextViewFont(title, Fonts.MUSEO_1);

        // Fonts.getInstance().setButtonFont(btnHome,Fonts.MUSEO);

        // long time22 = (long) 60 * 1000;
        //  mCvCountdownViewTest22.start(time22);
    }


    public void open() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage("Do you really want to close this app?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.toobBarRefresh:
                imgRefresh.setRotation((float) +360.0);
                callServiceAppointment();
                break;
            case R.id.toolbarClose:
                open();
                break;
            case R.id.toolBarHome:
                Intent i = new Intent(ActivityPatientDetails.this, MainActivity.class);
                /*i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
                i.putExtra("FROMAPPOINTMENTSTATUS", true);
                startActivity(i);
                //ClinicPref.getInstance(ActivityPatientDetails.this).setIsScanned(false);
                //finish();
                break;
        }
    }


    public class HandlerNoti extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String response = (String) msg.obj;
            Log.d("test",response);
        }

    }

    public class HandlerAppointmentDetail extends Handler {
        long diffIntimenew;
        long totalWaitingTime;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String response = (String) msg.obj;
            Log.d("test", "Response:" + response);
            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    SetterAppointmentResponse setterAppointmentResponse = new Gson().fromJson(jsonObject.toString(), SetterAppointmentResponse.class);
                    if (setterAppointmentResponse.getOkay()) {
                        ClinicPref.getInstance(ActivityPatientDetails.this).setPriviousStatus(setterAppointmentResponse.getData().getDoctorStatus());

                        //  Date remainingTym = sdf.parse(setterAppointmentResponse.getData().getApproxAppointmentTime().toString());
                        Date currentDate = sdf.parse(sdf.format(new Date()));
                        long currentTime = System.currentTimeMillis() % 1000;
                        // As per Logic


                        //long diffBtTime = remainingTym.getTime() - currentDate.getTime();

                        long avgTime = setterAppointmentResponse.getData().getAvgTime() * 60 * 1000;
                        long extraTime = setterAppointmentResponse.getData().getExtraTime() * 60 * 1000;
                        long pauseTime = setterAppointmentResponse.getData().getPauseTime() * 60 * 1000;


                        if (setterAppointmentResponse.getData().getCurrPatientInTime() > setterAppointmentResponse.getData().getAvgTime()) {
                            totalWaitingTime = (setterAppointmentResponse.getData().getAvgTime() + setterAppointmentResponse.getData().getExtraTime() + setterAppointmentResponse.getData().getPauseTime()) * 60 * 1000 + 5;
                        } else {
                            totalWaitingTime = (setterAppointmentResponse.getData().getAvgTime() + setterAppointmentResponse.getData().getExtraTime() + setterAppointmentResponse.getData().getPauseTime()) * 60 * 1000;
                        }

                        long diffBtTime = totalWaitingTime + currentTime;
                        Log.d("test TYM DIFF", "" + diffBtTime);
                        diffIntimenew = (setterDetailPatient.getCurrQueueCount() - setterAppointmentResponse.getData().getCurrQueueCount()) * diffBtTime;

                        long diffInTimeWithoutPause;
                        diffInTimeWithoutPause = (avgTime + extraTime) * (setterDetailPatient.getCurrQueueCount() - setterAppointmentResponse.getData().getCurrQueueCount());

                        if (pauseTime > 0) {
                            diffInTimeWithoutPause = diffInTimeWithoutPause + pauseTime;
                        }
                        if (extraTime > 0) {
                            diffInTimeWithoutPause = diffInTimeWithoutPause + pauseTime;
                        }

                        if (setterAppointmentResponse.getData().getCurrPatientInTime() > setterAppointmentResponse.getData().getAvgTime()) {
                            diffInTimeWithoutPause = diffInTimeWithoutPause + 5;
                        }


                        if (!setterAppointmentResponse.getData().getDoctorStatus().equalsIgnoreCase("Left for Today")) {
                            if (setterAppointmentResponse.getData().getCurrQueueCount() != 0) {
                                mCvCountdownViewTest22.setVisibility(View.VISIBLE);
                                textWait.setText("Please wait, Your Turn Comes in..");
                                //mCvCountdownViewTest22.start(diffBtTime);
                                if (diffIntimenew > 0) {
                                    if (ClinicPref.getInstance(ActivityPatientDetails.this).getTotalServiceCall() == 0) {

                                        mCvCountdownViewTest22.start(diffInTimeWithoutPause);
                                        priviousTime = totalWaitingTime;
                                        ClinicPref.getInstance(ActivityPatientDetails.this).setPriviousWaitingTime(totalWaitingTime);
                                    } else {
                                        if (ClinicPref.getInstance(ActivityPatientDetails.this).getPriviousWaitingTime() != totalWaitingTime || ClinicPref.getInstance(ActivityPatientDetails.this).getLastpatientcount() != setterAppointmentResponse.getData().getCurrQueueCount()) {
                                            mCvCountdownViewTest22.start(diffInTimeWithoutPause);
                                            priviousTime = totalWaitingTime;
                                            ClinicPref.getInstance(ActivityPatientDetails.this).setPriviousWaitingTime(totalWaitingTime);
                                        } else {
                                            priviousTime = totalWaitingTime;
                                            ClinicPref.getInstance(ActivityPatientDetails.this).setPriviousWaitingTime(totalWaitingTime);
                                        }
                                    }

                                    /*if(mCvCountdownViewTest22.getRemainTime()< avgTime){
                                        mCvCountdownViewTest22.start(mCvCountdownViewTest22.getRemainTime()+ (5*60*1000));
                                    }*/

                                    if (ClinicPref.getInstance(ActivityPatientDetails.this).getPreviousWaitingCount() != 0) {
                                        if (mCvCountdownViewTest22.getRemainTime() < (diffInTimeWithoutPause - avgTime) && setterAppointmentResponse.getData().getCurrQueueCount() == ClinicPref.getInstance(ActivityPatientDetails.this).getPreviousWaitingCount()) {
                                            mCvCountdownViewTest22.start(mCvCountdownViewTest22.getRemainTime() + (5 * 60 * 1000));
                                        }
                                    }
                                }

                                //new changes
                               /* if (currentCount == setterAppointmentResponse.getData().getCurrQueueCount()) {
                                    if (mCvCountdownViewTest22.getRemainTime() < 2000) {
                                        mCvCountdownViewTest22.start(diffBtTime);
                                    }
                                } else {
                                    if (currentCount != setterAppointmentResponse.getData().getCurrQueueCount() || isAvgTym != setterAppointmentResponse.getData().getAvgTimeSet() || setterAppointmentResponse.getData().getDoctorStatus().equalsIgnoreCase("on break") || setterAppointmentResponse.getData().getPatientInTimeMoreThanAvgWaitTime()) {
                                        mCvCountdownViewTest22.start(diffBtTime);
                                    }

                                    if (!ClinicPref.getInstance(ActivityPatientDetails.this).getPriviousStatus().equalsIgnoreCase(setterAppointmentResponse.getData().getDoctorStatus())) {
                                        mCvCountdownViewTest22.start(diffBtTime);
                                    }
                                    // }
                                }*/
                            } else {
                                mCvCountdownViewTest22.setVisibility(View.INVISIBLE);
                                textWait.setText("Please Wait...!");
                            }
                        } else {
                            mCvCountdownViewTest22.setVisibility(View.INVISIBLE);
                            textWait.setText("Doctor left for today..!");
                        }
                        serviceCount++;
                        ClinicPref.getInstance(ActivityPatientDetails.this).setTotalServiceCall(1);
                        ClinicPref.getInstance(ActivityPatientDetails.this).setLastpatientcount(setterAppointmentResponse.getData().getCurrQueueCount());
                        //  mCvCountdownViewTest22.start(diffBtTime);
                        txtCurrentValue.setText("" + setterAppointmentResponse.getData().getCurrQueueCount());
                        txtDocStatusValue.setText(setterAppointmentResponse.getData().getDoctorStatus());
                        if (!setterAppointmentResponse.getData().getDoctorStatus().equalsIgnoreCase("Logged Out")) {
                            if (setterDetailPatient.getCurrQueueCount() - setterAppointmentResponse.getData().getCurrQueueCount() == 1) {
                                mCvCountdownViewTest22.setVisibility(View.INVISIBLE);
                                if (setterAppointmentResponse.getData().getCurrQueueCount() != 0) {
                                    if(ClinicPref.getInstance(ActivityPatientDetails.this).getNotificationCount() == 0){
                                        sendnotificationData();
                                        ClinicPref.getInstance(ActivityPatientDetails.this).setNotificationCount(1);
                                    }
                                    textWait.setText("Please, be Ready You are Next...");
                                    textWait.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                    textWait.setTextColor(ContextCompat.getColor(ActivityPatientDetails.this, R.color.colorAccent));
                                } else {
                                    textWait.setText("Please Wait...!!");
                                }
                            }
                        } else {
                            textWait.setText("Doctor left for today..!");
                            mCvCountdownViewTest22.setVisibility(View.INVISIBLE);
                        }
                        if (setterAppointmentResponse.getData().getCurrQueueCount() == setterDetailPatient.getCurrQueueCount()) {
                            Intent intent = new Intent(ActivityPatientDetails.this, ActivityScanner.class);
                            startActivity(intent);
                            ClinicPref.getInstance(ActivityPatientDetails.this).setIsScanned(false);
                            ClinicPref.getInstance(ActivityPatientDetails.this).setTotalServiceCall(0);
                            customHandler.removeCallbacks(updateTimerThread);
                            isStop = true;
                            serviceCount = 0;
                            isAvgTym = false;
                            ClinicPref.getInstance(ActivityPatientDetails.this).setNotificationCount(0);
                            finish();
                        }
                        if (setterAppointmentResponse.getData().getCurrQueueCount() > setterDetailPatient.getCurrQueueCount()) {
                            Log.d("test", "Your appointment is over.");
                            Intent intent = new Intent(ActivityPatientDetails.this, ActivityScanner.class);
                            intent.putExtra("APP_OVER", true);
                            startActivity(intent);
                            customHandler.removeCallbacks(updateTimerThread);
                            ClinicPref.getInstance(ActivityPatientDetails.this).setIsScanned(false);
                            ClinicPref.getInstance(ActivityPatientDetails.this).setTotalServiceCall(0);
                            ClinicPref.getInstance(ActivityPatientDetails.this).setNotificationCount(0);
                            isStop = true;
                            serviceCount = 0;
                            finish();
                        }
                        isAvgTym = setterAppointmentResponse.getData().getAvgTimeSet();
                        ClinicPref.getInstance(ActivityPatientDetails.this).setPreviousWaitingCount(setterAppointmentResponse.getData().getCurrQueueCount());
                        Log.d("test CHECK", setterAppointmentResponse.getMessage());
                    } else {
                        Log.d("test CHECK", setterAppointmentResponse.getMessage());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                isStop = true;
                errorlayout.setVisibility(View.VISIBLE);
                scrollLin.setVisibility(View.GONE);
                Log.d("test", "null Response, Plz Check...");
                Toast.makeText(ActivityPatientDetails.this, "Something Goes Wrong.....!", Toast.LENGTH_SHORT).show();
                //finish();
                ClinicPref.getInstance(ActivityPatientDetails.this).setIsScanned(false);
                customHandler.removeCallbacks(updateTimerThread);
            }
        }
    }
}
