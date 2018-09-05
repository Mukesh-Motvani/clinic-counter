package com.bma.counter.clinic;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bma.counter.clinic.appSingletone.ModelSiteOption;
import com.bma.counter.clinic.fonts.Fonts;
import com.squareup.picasso.Picasso;

public class ActivityTechSupport extends AppCompatActivity implements View.OnClickListener {


    private TextView textEmail;
    private TextView textContact;
    private TextView title;
    private Toolbar toolbar;
    private ImageView imageHome, imgRefresh, toolbarClose, logoImage;
    public static final int PERMISSION_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_support);
        initView();
    }

    private void initView() {
        textEmail = (TextView) findViewById(R.id.textEmailId);
        textContact = (TextView) findViewById(R.id.textContactDetail);
        title = (TextView) findViewById(R.id.title);
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        imageHome = (ImageView) findViewById(R.id.toolBarHome);
        imgRefresh = (ImageView) findViewById(R.id.toobBarRefresh);
        toolbarClose = (ImageView) findViewById(R.id.toolbarClose);
        logoImage = (ImageView) findViewById(R.id.logoImageCon);
        Fonts.getInstance().setTextViewFont(title, Fonts.MUSEO_1);
        Fonts.getInstance().setTextViewFont(textContact, Fonts.MUSEO);
        Fonts.getInstance().setTextViewFont(textEmail, Fonts.MUSEO);

        imageHome.setVisibility(View.GONE);
        imgRefresh.setVisibility(View.GONE);
        toolbarClose.setOnClickListener(this);
        textContact.setOnClickListener(this);
        textEmail.setOnClickListener(this);
        loadData();
    }

    private void loadData() {

        if (ModelSiteOption.getInstance().getSupportEmail() != null) {
            textEmail.setText(ModelSiteOption.getInstance().getSupportEmail());
        }
        if (ModelSiteOption.getInstance().getSupportNumber() != null) {
            textContact.setText(ModelSiteOption.getInstance().getSupportNumber());
        }
        //  Picasso.get().load("http://www.malpaniground.com:8080/global/images/logo/" + ModelSiteOption.getInstance().getMainLogo()).into(logoImage);
        Picasso.get().load(ModelSiteOption.getInstance().getDomainPath() + "/global/images/logo/" + ModelSiteOption.getInstance().getMainLogo()).into(logoImage);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbarClose) {
            finish();
        } else if (v.getId() == R.id.textEmailId) {
            Intent email_intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", ModelSiteOption.getInstance().getSupportEmail(), null));
            // email_intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject text here");
            // email_intent.putExtra(android.content.Intent.EXTRA_TEXT,"Body text here");
            startActivity(Intent.createChooser(email_intent, "Send email..."));

        } else if (v.getId() == R.id.textContactDetail) {
            makeCall(ModelSiteOption.getInstance().getSupportNumber());
        }
    }

    public void makeCall(String s) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + s));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestForCallPermission();
        } else {
            startActivity(intent);
        }
    }

    public void requestForCallPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CALL_PHONE)) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall(ModelSiteOption.getInstance().getSupportNumber());
                }
                break;
        }
    }
}
