package com.mitcoe.ishanjoshi.depressionanalyser;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.ImageButton;

import java.io.IOException;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

   private ImageButton buttonStartRecord,buttonStopRecord;
   private ImageButton buttonStartPlay,buttonStopPlay;
   MediaRecorder mRecorder;
   MediaPlayer mPlayer;
   String FilePath;
   Long timeStampLong;
   String timeStamp;
   public static final int RequestPermissionCode = 1;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       buttonStartRecord = findViewById(R.id.ImageButtonStartRecording);
       buttonStopRecord = findViewById(R.id.ImageButtonStopRecording);
       buttonStartPlay = findViewById(R.id.ImageButtonStartPlaying);
       buttonStopPlay = findViewById(R.id.ImageButtonStopPlaying);

       buttonStopRecord.setEnabled(false);
       buttonStartPlay.setEnabled(false);
       buttonStopPlay.setEnabled(false);

       if(!checkPermission())
           requestPermission();

       buttonStartRecord.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(checkPermission()){

                   timeStampLong = System.currentTimeMillis();
                   timeStamp = timeStampLong.toString();
                   FilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + timeStamp + ".3gp";

                   mRecorder=new MediaRecorder();
                   mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                   mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                   mRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                   mRecorder.setOutputFile(FilePath);

                   try {
                       mRecorder.prepare();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   mRecorder.start();

                   buttonStartRecord.setEnabled(false);
                   buttonStopRecord.setEnabled(true);

                   Toast.makeText(MainActivity.this, "Recording Started",
                           Toast.LENGTH_LONG).show();

               }else{

                   requestPermission();

               }
           }
       });

       buttonStopRecord.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mRecorder.stop();
               buttonStartRecord.setEnabled(true);
               buttonStartPlay.setEnabled(true);
               buttonStopPlay.setEnabled(false);

               Toast.makeText(MainActivity.this, "Recording Completed",
                       Toast.LENGTH_LONG).show();

           }
       });

       buttonStartPlay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) throws IllegalArgumentException,SecurityException,IllegalStateException {

               buttonStartRecord.setEnabled(false);
               buttonStopRecord.setEnabled(false);
               buttonStopPlay.setEnabled(true);

               mPlayer = new MediaPlayer();

               try {
                   mPlayer.setDataSource(FilePath);
               } catch (IOException e) {
                   e.printStackTrace();
               }
               try {
                   mPlayer.prepare();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               mPlayer.start();

               Toast.makeText(MainActivity.this, "Recording Playing",
                       Toast.LENGTH_LONG).show();

           }
       });

       buttonStopPlay.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               buttonStartRecord.setEnabled(true);
               buttonStopRecord.setEnabled(false);
               buttonStartPlay.setEnabled(true);
               buttonStopPlay.setEnabled(false);

               if(mPlayer!=null){
                   mPlayer.stop();
                   mPlayer.release();
                   mRecorder=new MediaRecorder();
                   mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                   mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                   mRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                   mRecorder.setOutputFile(FilePath);
               }

               Toast.makeText(MainActivity.this, "Recording Playing Stopped",
                       Toast.LENGTH_LONG).show();

           }
       });
   }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

}
