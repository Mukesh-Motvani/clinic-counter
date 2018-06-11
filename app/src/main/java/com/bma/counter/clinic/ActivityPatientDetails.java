package com.bma.counter.clinic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bma.counter.clinic.fonts.Fonts;
import com.bma.counter.clinic.preference.ClinicPref;
import com.bma.counter.clinic.service.ThreadGetResponsePost;
import com.bma.counter.clinic.setters.MainDetailPojo;
import com.bma.counter.clinic.setters.PatientDetailResposnsePojo;
import com.bma.counter.clinic.setters.Payload;
import com.bma.counter.clinic.setters.SetterAppointmentResponse;
import com.bma.counter.clinic.utils.ClinicConstants;
import com.bma.counter.clinic.utils.UtisClinicCounter;
import com.fujiyuu75.sequent.Animation;
import com.fujiyuu75.sequent.Direction;
import com.fujiyuu75.sequent.Sequent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cn.iwgang.countdownview.CountdownView;

import static java.util.concurrent.TimeUnit.SECONDS;

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
    private static boolean isAvgTym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_passanger_details);


        loadtoolbar();
        inItVIews();
        getJsonData();
        requestForCount();
        serviceCount = 0;
        currentCount = 0;

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
        new ThreadGetResponsePost(ActivityPatientDetails.this, new HandlerAppointmentDetail(), "http://www.malpaniground.com:8080/api/appointment-stats", mPayload).execute();
        //Log.d("test",new Gson().toJson(payload));
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
        callServiceAppointment();
        //  }
    }

    private void setData() {
        txtName.setText("Welcome " + setterDetailPatient.getPatientName() + ",");
        txtAppIdValueNew.setText(String.valueOf(setterDetailPatient.getAppointmentId()));
        txtDocNameValue.setText(setterDetailPatient.getDoctorName());
        txtWardNoValue.setText(setterDetailPatient.getClinicRoomNumber());
        txtYourNOValue.setText(String.valueOf(setterDetailPatient.getCurrQueueCount()));
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


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.toobBarRefresh:
                imgRefresh.setRotation((float)+360.0);
                callServiceAppointment();
                break;
            case R.id.toolbarClose:
                finish();
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


    public class HandlerAppointmentDetail extends Handler {

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

                        Date remainingTym = sdf.parse(setterAppointmentResponse.getData().getApproxAppointmentTime().toString());
                        Date currentDate = sdf.parse(sdf.format(new Date()));
                        long diffBtTime = remainingTym.getTime() - currentDate.getTime();
                        Log.d("test TYM DIFF", "" + diffBtTime);

                        if (setterAppointmentResponse.getData().getCurrQueueCount() != 0) {
                            if (serviceCount == 0) {
                                mCvCountdownViewTest22.start(diffBtTime);
                            } else {
                                if (currentCount == setterAppointmentResponse.getData().getCurrQueueCount()) {
                                    if (mCvCountdownViewTest22.getRemainTime() < 2000) {
                                        mCvCountdownViewTest22.start(diffBtTime);
                                    }
                                } else {
                                    if (currentCount != setterAppointmentResponse.getData().getCurrQueueCount() || isAvgTym != setterAppointmentResponse.getData().getAvgTimeSet()) {
                                        mCvCountdownViewTest22.start(diffBtTime);
                                    }
                                }
                            }
                        } else {
                            mCvCountdownViewTest22.setVisibility(View.INVISIBLE);
                            textWait.setText("Please Wait...!");
                        }
                        serviceCount++;
                        //  mCvCountdownViewTest22.start(diffBtTime);
                        txtCurrentValue.setText("" + setterAppointmentResponse.getData().getCurrQueueCount());
                        txtDocStatusValue.setText(setterAppointmentResponse.getData().getDoctorStatus());
                        if (setterDetailPatient.getCurrQueueCount() - setterAppointmentResponse.getData().getCurrQueueCount() == 1) {
                            mCvCountdownViewTest22.setVisibility(View.INVISIBLE);
                            if (setterAppointmentResponse.getData().getCurrQueueCount() != 0) {
                                textWait.setText("Please, be Ready You are Next...");
                                textWait.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                textWait.setTextColor(ContextCompat.getColor(ActivityPatientDetails.this, R.color.colorAccent));
                            } else {
                                textWait.setText("Please Wait...!!");
                            }
                        }
                        if (setterAppointmentResponse.getData().getCurrQueueCount() == setterDetailPatient.getCurrQueueCount()) {
                            Intent intent = new Intent(ActivityPatientDetails.this, ActivityScanner.class);
                            startActivity(intent);
                            ClinicPref.getInstance(ActivityPatientDetails.this).setIsScanned(false);
                            isStop = true;
                            serviceCount = 0;
                            isAvgTym = false;
                            finish();
                        }
                        if (setterAppointmentResponse.getData().getCurrQueueCount() > setterDetailPatient.getCurrQueueCount()) {
                            Log.d("test", "Your appointment is over.");
                            Intent intent = new Intent(ActivityPatientDetails.this, ActivityScanner.class);
                            intent.putExtra("APP_OVER", true);
                            startActivity(intent);
                            ClinicPref.getInstance(ActivityPatientDetails.this).setIsScanned(false);
                            isStop = true;
                            serviceCount = 0;
                            finish();
                        }

                        currentCount = setterAppointmentResponse.getData().getCurrQueueCount();
                        isAvgTym = setterAppointmentResponse.getData().getAvgTimeSet();

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
                errorlayout.setVisibility(View.VISIBLE);
                scrollLin.setVisibility(View.GONE);
                Log.d("test", "null Response, Plz Check...");
                ClinicPref.getInstance(ActivityPatientDetails.this).setIsScanned(false);
            }
        }
    }
}
