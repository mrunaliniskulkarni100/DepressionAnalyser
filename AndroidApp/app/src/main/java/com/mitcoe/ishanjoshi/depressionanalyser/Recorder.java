package com.mitcoe.ishanjoshi.depressionanalyser;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Ishan Joshi on 11-Jan-18.
 */

public class Recorder {

    private MediaRecorder mediaRecorder;
    private String TAG = "DepressionAnalyzerRecorder";
    private boolean recording = false;

    public void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFile(getFilePath());
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "startRecording: ");
        }
        mediaRecorder.start();
    }

    public void startStopRecording() {

        if (recording) {
            stopRecording();
        } else {
            startRecording();
        }
        recording = (!recording);

    }

    private String getFilePath() {
        String mFileName = "DepressionAnalyser_";
        mFileName += System.currentTimeMillis();

        return mFileName;
    }

    public void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }


}
