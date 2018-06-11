package com.bma.counter.clinic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityNoINternet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        TextView txtLableConnection = (TextView) findViewById(R.id.txtConnectionFailedLabel);
        Button btnRetry = (Button) findViewById(R.id.btnTryAgain);
        setTitle("Connection Failed");

        findViewById(R.id.btnTryAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
