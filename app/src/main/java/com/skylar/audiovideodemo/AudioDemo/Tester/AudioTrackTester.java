package com.skylar.audiovideodemo.AudioDemo.Tester;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.os.Environment;

import com.skylar.audiovideodemo.AudioDemo.Api.AudioPlay;
import com.skylar.audiovideodemo.AudioDemo.Wav.WavFileReader;

import java.io.File;
import java.io.IOException;

/**
 * Created by Skylar on 2018/9/11
 * Github : https://github.com/skylarliuu
 */

/*
* *  1. 开始播放，读取文件的WAV头部信息
 *  2. 计算缓冲区大小，初始化AudioTrack，开始播放
 *  3. 开启线程，不断写入AudioTrack的缓冲区
 *  4. 停止播放，释放资源
*
* */
public class AudioTrackTester implements Tester {

    private static final int DEFAULT_SAMPLE_RATE = 44100;
    private static final int DEFAULT_CHANNEL = AudioFormat.CHANNEL_OUT_MONO;
    private static final int DEFAULT_BIT_PER_SAMPLE = AudioFormat.ENCODING_PCM_16BIT;

    private static final String DETAULT_PATH = Environment.getExternalStorageDirectory()
            + File.separator+"test.wav";

    private WavFileReader mWavFileReader;
    private AudioPlay mAudioPlay;
    private Thread mThread;
    private boolean isLoop = true;


    @Override
    public void startTest() {
        mWavFileReader = new WavFileReader();
        mAudioPlay = new AudioPlay();

        try {
            mWavFileReader.openFile(DETAULT_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mAudioPlay.startPlay(AudioManager.STREAM_MUSIC,DEFAULT_SAMPLE_RATE,DEFAULT_CHANNEL,DEFAULT_BIT_PER_SAMPLE);

        isLoop = true;
        mThread = new Thread(new PlayRunnable());
        mThread.start();
    }

    @Override
    public void stopTest() {
        mAudioPlay.stopPlay();

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
    }

    class PlayRunnable implements Runnable{

        @Override
        public void run() {
            byte[] buffer = new byte[mAudioPlay.getMinBufferSize() * 2];
            while(isLoop && mWavFileReader.readData(buffer,0,buffer.length) >0){
                 mAudioPlay.play(buffer);
            }

            mAudioPlay.stopPlay();
            try {
                mWavFileReader.closeFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
