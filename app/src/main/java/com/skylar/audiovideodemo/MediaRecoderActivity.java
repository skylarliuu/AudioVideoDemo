package com.skylar.audiovideodemo;

import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MediaRecoderActivity extends AppCompatActivity {

    private TextView tvMsg;
    private boolean isRecording = false;
    private MediaRecorder mMediaRecorder;
    private File mRecorderFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_recoder);
        tvMsg = findViewById(R.id.tvMsg);
    }

    public void recoder(View view){
        if(!isRecording){
            doStart();
        }else{
            doStop();
        }
        isRecording = !isRecording;
    }

    /**
     * 启动录音
     *
     * @return
     */

    private boolean doStart() {
        tvMsg.setText("");
        try {
            //创建MediaRecorder
            mMediaRecorder = new MediaRecorder();
            //创建录音文件
            mRecorderFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/recorderdemo/" + System.currentTimeMillis() + ".m4a");
            if (!mRecorderFile.getParentFile().exists()) mRecorderFile.getParentFile().mkdirs();
            mRecorderFile.createNewFile();

            addText("录音文件地址："+mRecorderFile.getAbsolutePath());

            //配置MediaRecorder

            //从麦克风采集
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

            //保存文件为MP4格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

            //所有android系统都支持的适中采样的频率
            mMediaRecorder.setAudioSamplingRate(44100);

            //通用的AAC编码格式
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            //设置音质频率
            mMediaRecorder.setAudioEncodingBitRate(96000);

            //设置文件录音的位置
            mMediaRecorder.setOutputFile(mRecorderFile.getAbsolutePath());


            //开始录音
            mMediaRecorder.prepare();
            mMediaRecorder.start();

            addText("正在录音");


        } catch (Exception e) {
            Toast.makeText(MediaRecoderActivity.this, "录音失败，请重试"+e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 关闭录音
     *
     * @return
     */
    private boolean doStop() {
        try {
            mMediaRecorder.stop();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addText("录制停止");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 释放上一次的录音
     */
    private void releaseRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
            mMediaRecorder = null;
            addText("释放资源");
        }
    }

    private void addText(String str) {
        tvMsg.setText(tvMsg.getText() + "\n" + str);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseRecorder();
    }
}
