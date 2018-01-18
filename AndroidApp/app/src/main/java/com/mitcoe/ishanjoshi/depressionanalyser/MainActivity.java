package com.mitcoe.ishanjoshi.depressionanalyser;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button buttonStartRecord;
    private Button buttonStopRecord;
    String FilePathName = null;
    MediaRecorder mediaRecorder;
    Random random;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        buttonStartRecord = findViewById(R.id.button1);
        buttonStopRecord = findViewById(R.id.button2);
        buttonStopRecord.setEnabled(false);

        random = new Random();

        buttonStartRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilePathName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + RandomFileName(5) + "AudioRecording.3gp";
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                mediaRecorder.setOutputFile(FilePathName);

                try {
                    mediaRecorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaRecorder.start();

                buttonStartRecord.setEnabled(false);
                buttonStopRecord.setEnabled(true);

                Toast.makeText(MainActivity.this, "Recording Started",
                        Toast.LENGTH_LONG).show();
            }
        });

        buttonStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                buttonStartRecord.setEnabled(true);
                buttonStopRecord.setEnabled(false);


                Toast.makeText(MainActivity.this, "Recording Completed",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    public String RandomFileName(int n){
        StringBuilder S;
        S = new StringBuilder(n);
        int i=0;
        while(i<n){
            S.append(RandomAudioFileName.charAt(random.nextInt(RandomAudioFileName.length())));
            i++;
        }
        return S.toString();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }

        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }


}
