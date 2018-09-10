package com.skylar.audiovideodemo.Tester;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Skylar on 2018/9/8
 * Github : https://github.com/skylarliuu
 */
public class MediaRecorderTester implements Tester {

    private boolean isRecordering = false;
    private MediaRecorder mMediaRecorder;

    @Override
    public void startTest() {
        if(isRecordering)
            return;

        try {
            //创建录音文件
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    File.separator + "AudioVideoDemo",System.currentTimeMillis() + ".mp4");
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            file.createNewFile();

            mMediaRecorder = new MediaRecorder();
            //配置参数
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mMediaRecorder.setAudioEncodingBitRate(96000);
            mMediaRecorder.setOutputFile(file.getAbsolutePath());

            //开始录音
            mMediaRecorder.prepare();
            mMediaRecorder.start();

            isRecordering = true;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stopTest() {
        if(isRecordering){
            isRecordering = false;

            if(mMediaRecorder != null){
                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        }

    }
}
