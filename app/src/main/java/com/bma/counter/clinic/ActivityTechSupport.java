package com.bma.counter.clinic;

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
        Picasso.get().load(ModelSiteOption.getInstance().getDomainPath()+"/global/images/logo/"+ ModelSiteOption.getInstance().getMainLogo()).into(logoImage);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbarClose) {
            finish();
        }
    }
}
