package com.mitcoe.ishanjoshi.depressionanalyser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton VoiceRecordButton = findViewById(R.id.VoiceRecordButton);
        VoiceRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 06-Jan-18 Add Code to start Recording voice message. Also change UI Layout to accomodate this 
            }
        });

    }
}
