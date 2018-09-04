package com.bma.counter.clinic;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bma.counter.clinic.appSingletone.ModelSiteOption;
import com.bma.counter.clinic.fonts.Fonts;
import com.bma.counter.clinic.preference.ClinicPref;
import com.bma.counter.clinic.service.ThreadGetRequest;
import com.bma.counter.clinic.setters.SetterGeneralClass;
import com.bma.counter.clinic.setters.SetterOfflineScan;
import com.bma.counter.clinic.setters.SetterScannedTokenPojo;
import com.bma.counter.clinic.utils.ClinicConstants;
import com.bma.counter.clinic.utils.UtisClinicCounter;
import com.fujiyuu75.sequent.Animation;
import com.fujiyuu75.sequent.Direction;
import com.fujiyuu75.sequent.Sequent;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private TextView textScan;
    private Button button, btnNewScan, btnPreviousScan;
    private LottieAnimationView animationView;
    private LinearLayout linearLayout, layoutBtn;
    private ImageView imgLogo;
    private boolean isFromAppointMent = false;
    private boolean doubleBackToExitPressedOnce = false;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!UtisClinicCounter.isInternet(this)) {
            finish();
            startActivity(new Intent(this, ActivityNoINternet.class));
        }

        if (getIntent().getExtras() != null) {
            isFromAppointMent = getIntent().getBooleanExtra("FROMAPPOINTMENTSTATUS", false);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        changeToolbarFont(toolbar, this);

        initViews();

        if(ModelSiteOption.getInstance().getSupportNumber() == null && ModelSiteOption.getInstance().getSupportEmail()== null){
            hideItem();
        }
    }


    private void hideItem()
    {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_technicalSupport).setVisible(false);
    }

    public static void changeToolbarFont(Toolbar toolbar, Activity context) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                if (tv.getText().equals(toolbar.getTitle())) {
                    applyFont(tv, context);
                    break;
                }
            }
        }
    }

    public static void applyFont(TextView tv, Activity context) {
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/MontserratSemiBold.ttf"));
    }

    private void initViews() {

        button = (Button) findViewById(R.id.btnMainScanYourBarcode);
        btnNewScan = (Button) findViewById(R.id.btnMainScanYourBarcodeNew);
        btnPreviousScan = (Button) findViewById(R.id.btnCurrentStack);
        textScan = (TextView) findViewById(R.id.textScan);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);

        linearLayout = (LinearLayout) findViewById(R.id.linMain);
        layoutBtn = (LinearLayout) findViewById(R.id.layButton);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);
        Fonts.getInstance().setButtonFont(button, Fonts.MUSEO_1);

        button.setOnClickListener(this);
        btnNewScan.setOnClickListener(this);
        btnPreviousScan.setOnClickListener(this);

        Fonts.getInstance().setTextViewFont(textScan, Fonts.MUSEO);
        Fonts.getInstance().setButtonFont(btnNewScan, Fonts.MUSEO);
        Fonts.getInstance().setButtonFont(btnPreviousScan, Fonts.MUSEO);

        if (isFromAppointMent) {
            layoutBtn.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
        } else {
            layoutBtn.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
        }

        Picasso.get().load(ModelSiteOption.getInstance().getDomainPath()+"/global/images/logo/"+ ModelSiteOption.getInstance().getMainLogo()).into(imgLogo);
       // Picasso.get().load("http://www.malpaniground.com:8080/global/images/logo/" + ClinicPref.getInstance(this).getMainLogo()).into(imgLogo);
        Sequent.origin(linearLayout).offset(100).anim(this, Animation.FADE_IN_UP).flow(Direction.FORWARD).start();

    }

    private void initnew() {
        new IntentIntegrator(this).setOrientationLocked(false).setCaptureActivity(CustomScannerActivity.class).initiateScan();
    }


    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_offline_scan, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edtCode);
        Button btnSubmit = (Button) dialogView.findViewById(R.id.btnSubmit);
        final AlertDialog b = dialogBuilder.create();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt.getText().toString() != null && !edt.getText().toString().isEmpty()) {
                    b.dismiss();

                    tokenSerciceCall(edt.getText().toString());

                } else {
                    Toast.makeText(MainActivity.this, "Please Enter Appointment Id.", Toast.LENGTH_SHORT).show();
                    b.dismiss();
                }
            }
        });


        b.show();
    }

    private void tokenSerciceCall(String serviceCall){
        String url = /*http://www.malpaniground.com:8080/api*/ModelSiteOption.getInstance().getDomainPath()+"/api/fetch-appointment-token/" + serviceCall;
        new ThreadGetRequest(MainActivity.this, new HandlerOfflineSCan(), url).execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        Log.d("test", "Request code:" + requestCode);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("test", "permissionGranted:");
                    initnew();

                } else {
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.anv_contact) {
            // Handle the camera action
            startActivity(new Intent(this, ActivityContactDetails.class));
        }else if(id == R.id.nav_technicalSupport){
            startActivity(new Intent(this, ActivityTechSupport.class));
        } else {
            showChangeLangDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnMainScanYourBarcode:
            case R.id.btnMainScanYourBarcodeNew:
                cameraPermission();
                break;

            case R.id.btnCurrentStack:
                startActivity(new Intent(MainActivity.this, ActivityPatientDetails.class));
                finishAffinity();
                break;
        }

    }

    private void cameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Log.d("test", "permision granted");
            // init(mydecoderview, getIntent(), null);
            initnew();
        } else {
            Log.d("test", "pop for permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                finish();
            } else {
                try {
                    String text = result.getContents();
                    StringBuilder stringBuilder = new StringBuilder(text);
                    Log.d("test", "Response Received :: " + text);
                    Log.d("Actual JWT", text);

                    SetterScannedTokenPojo setterScannedTokenPojo = new Gson().fromJson(text,SetterScannedTokenPojo.class);

                    tokenSerciceCall(setterScannedTokenPojo.getAppointmentId().toString());

                    /*ClinicPref.getInstance(this).setJwToken(text);
                    decoded(text);
                    ClinicPref.getInstance(this).setIsScanned(true);*/
                } catch (Exception e) {
                    Log.d("test", "exception:" + e.toString());
                    Toast.makeText(MainActivity.this, "No Valid Code  Activity", Toast.LENGTH_SHORT).show();
                    new IntentIntegrator(this).setOrientationLocked(false).setCaptureActivity(CustomScannerActivity.class).initiateScan();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private class HandlerOfflineSCan extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String response = (String) msg.obj;
            if (response != null) {

                 SetterOfflineScan setterOfflineScan = new Gson().fromJson(response,SetterOfflineScan.class);
                if(setterOfflineScan != null){
                    if(setterOfflineScan.getOkay()){
                        ClinicPref.getInstance(MainActivity.this).setJwToken(setterOfflineScan.getData());
                        try {
                            decoded(setterOfflineScan.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ClinicPref.getInstance(MainActivity.this).setIsScanned(true);
                    }else{
                        Toast.makeText(MainActivity.this, setterOfflineScan.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
    }

    public void decoded(String JWTEncoded) throws Exception {
        try {
            String[] split = JWTEncoded.split("\\.");
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
            finish();
            Intent i = new Intent(MainActivity.this, ActivityPatientDetails.class);
            i.putExtra(ClinicConstants.JWT_BODY, "" + getJson(split[1]));
            i.putExtra(ClinicConstants.JWT_HEADER, "" + getJson(split[0]));
            ClinicPref.getInstance(this).setJwtHeader("" + getJson(split[1]));
            i.putExtra("JWT", JWTEncoded);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
        }
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}
