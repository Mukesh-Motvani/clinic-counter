package com.bma.counter.clinic;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bma.counter.clinic.appSingletone.ModelSiteOption;
import com.bma.counter.clinic.fonts.Fonts;
import com.bma.counter.clinic.preference.ClinicPref;
import com.fujiyuu75.sequent.Animation;
import com.fujiyuu75.sequent.Direction;
import com.fujiyuu75.sequent.Sequent;

public class ActivityScanner extends AppCompatActivity {

    private TextView finalText;
    private RelativeLayout relativeLayout;
    private Button btnHome;
    private boolean appOver = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        relativeLayout = findViewById(R.id.activity_scanner);
        finalText = findViewById(R.id.txtFinal);
        btnHome = (Button) findViewById(R.id.btnHome);

        Fonts.getInstance().setTextViewFont(finalText, Fonts.MUSEO);
        Fonts.getInstance().setButtonFont(btnHome, Fonts.MUSEO);


        if(getIntent().getExtras() != null){
            appOver = getIntent().getBooleanExtra("APP_OVER",false);
        }

        if(!appOver) {

            if (ModelSiteOption.getInstance().getSetterGeneralClass() != null) {
                for (int i = 0; i < ModelSiteOption.getInstance().getSetterGeneralClass().getData().size(); i++) {
                    if (ModelSiteOption.getInstance().getSetterGeneralClass().getData().get(i).getOptionName().equalsIgnoreCase("thank_you_message")) {
                        if (ModelSiteOption.getInstance().getSetterGeneralClass().getData().get(i).getOptionVal() != null) {
                            finalText.setText(ModelSiteOption.getInstance().getSetterGeneralClass().getData().get(i).getOptionVal());
                            Log.d("test", "ThankYouMessage: " + ModelSiteOption.getInstance().getSetterGeneralClass().getData().get(i).getOptionVal());
                        }
                        break;
                    }
                }
            }
        }else{

            if(ModelSiteOption.getInstance().getAppOver() != null){
                finalText.setText(ModelSiteOption.getInstance().getAppOver());
            }else {

                finalText.setText("Your Appointment is Over..");
            }
        }

        Sequent.origin(relativeLayout).offset(300).anim(this, Animation.FADE_IN_UP).flow(Direction.FORWARD).start();
        findViewById(R.id.btnHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityScanner.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
