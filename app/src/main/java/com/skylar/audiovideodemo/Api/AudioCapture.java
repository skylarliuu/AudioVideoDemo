package com.skylar.audiovideodemo.Api;

import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Created by Skylar on 2018/9/10
 * Github : https://github.com/skylarliuu
 */
public class AudioCapture {

    private int minBufferSize;
    private boolean isCapture = false;
    private boolean isLoop = true;
    private AudioRecord mAudioRecord;
    private Thread mThread;
    private AudioCaptureListener mAudioCaptureListener;

    public void startCapture(int sampleRateInHz, int channelConfig, int audioFormat){
        if(isCapture)
            return;

        //计算缓冲区大小，初始化AudioRecorder
        minBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz,channelConfig,audioFormat);
        if(minBufferSize == AudioRecord.ERROR_BAD_VALUE){
            return;
        }

        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,sampleRateInHz,channelConfig,audioFormat,minBufferSize);
        if(mAudioRecord.getState() == AudioRecord.STATE_UNINITIALIZED){
            return;
        }

        mAudioRecord.startRecording();

        //开启线程，不断读取AudioRecorder
        isLoop = true;
        mThread = new Thread(new AudioCaptureRunnable());
        mThread.start();

        isCapture = true;

    }

    public void stopCapture(){
        if(!isCapture)
            return;

        if(mAudioRecord!=null){
            if (mAudioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
            }
        }

        isCapture = false;
        isLoop = false;

        if(mThread!=null){
            try {
                mThread.interrupt();
                mThread.join(1000);
                mThread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        mAudioCaptureListener = null;
    }

    class AudioCaptureRunnable implements Runnable{

        @Override
        public void run() {
            while(isLoop){
                byte[] buffer = new byte[minBufferSize * 2];
                //不断读取AudioRecorder缓冲区的数据
                int ret = mAudioRecord.read(buffer,0,buffer.length);
                if(ret == AudioRecord.ERROR_INVALID_OPERATION){

                }else if(ret == AudioRecord.ERROR_BAD_VALUE){

                }else{
                    //数据传入回调
                    if(mAudioCaptureListener != null){
                        mAudioCaptureListener.captureData(buffer);
                    }
                }
            }
        }
    }

    public void setAudioCaptureListener(AudioCaptureListener audioCaptureListener){
        mAudioCaptureListener = audioCaptureListener;
    }

    public interface AudioCaptureListener{
        void captureData(byte[] data);
    }

}
