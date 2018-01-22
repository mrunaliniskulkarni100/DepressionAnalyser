package com.mitcoe.ishanjoshi.depressionanalyser;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

   private ImageButton buttonStartRecord,buttonStopRecord;
   private ImageButton buttonStartPlay,buttonStopPlay;
   MediaRecorder mRecorder;
   MediaPlayer mPlayer;
   String FilePath;
   SimpleDateFormat s;
   public static final int RequestPermissionCode = 1;
   private RecyclerView recyclerView;
   private RecyclerView.Adapter mAdapter;
   List<FileDataClass> inputFilePathList = new ArrayList<>();
   int pos = 0;
   FileDataClass D;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       final String appFolderName = "DepressionAnalyserApp";
       File f = new File(Environment.getExternalStorageDirectory(),appFolderName);
       if (!f.exists())
           f.mkdirs();

       buttonStartRecord = findViewById(R.id.ImageButtonStartRecording);
       buttonStopRecord = findViewById(R.id.ImageButtonStopRecording);
       buttonStartPlay = findViewById(R.id.ImageButtonStartPlaying);
       buttonStopPlay = findViewById(R.id.ImageButtonStopPlaying);

       buttonStopRecord.setEnabled(false);
       buttonStartPlay.setEnabled(false);
       buttonStopPlay.setEnabled(false);

       if(!checkPermission())
           requestPermission();

       recyclerView = (RecyclerView)findViewById(R.id.recycler);
       LinearLayoutManager llm = new LinearLayoutManager(this);
       llm.setOrientation(LinearLayoutManager.VERTICAL);
       recyclerView.setLayoutManager(llm);
       mAdapter = new AdapterClass(inputFilePathList);
       recyclerView.setAdapter(mAdapter);

       buttonStartRecord.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(checkPermission()){
                   s =new SimpleDateFormat("ddMMyyhhmmss");
                   FilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "DepressionAnalyserApp" + "/" + s.format(new Date()) + ".3gp";

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

                   D = new FileDataClass(FilePath);
                   inputFilePathList.add(D);
                   mAdapter.notifyItemInserted(mAdapter.getItemCount());

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

       recyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(getApplicationContext(), recyclerView, new RecyclerItemTouchListener.RecyclerTouchListener() {
           @Override
           public void onClickItem(View v, int position) {
               FilePath = inputFilePathList.get(position).FilePathData;
               Toast.makeText(MainActivity.this, "File Selected",
                       Toast.LENGTH_LONG).show();
           }

           @Override
           public void onLongClickItem(View v, int position) {
               Toast.makeText(MainActivity.this, "Long Press Detected",
                       Toast.LENGTH_LONG).show();
           }
       }));
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
