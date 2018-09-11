package com.skylar.audiovideodemo.Tester;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;

import com.skylar.audiovideodemo.Api.AudioCapture;
import com.skylar.audiovideodemo.Wav.WavFileHeader;
import com.skylar.audiovideodemo.Wav.WavFileWriter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLData;

/**
 * Created by Skylar on 2018/9/8
 * Github : https://github.com/skylarliuu
 */

/*
*  1. 开始录音，创建文件，写入WAV头部信息
*  2. 计算缓冲区大小，初始化AudioRecorder
*  3. 开启线程，不断读取AudioRecorder
*  4. 将读取的数据写入文件中
*  5. 停止录音，计算文件大小写入WAV头部，释放资源
*
* */
public class AudioRecordTester implements Tester,AudioCapture.AudioCaptureListener {

    private static final int DEFAULT_SAMPLE_RATE = 44100;
    private static final int DEFAULT_CHANNEL = 1;
    private static final int DEFAULT_BIT_PER_SAMPLE = 16;

    private static final String DETAULT_PATH = Environment.getExternalStorageDirectory()
            + File.separator+"test.wav";

    private WavFileWriter mWavFileWriter;
    private AudioCapture mAudioCapture;

    @Override
    public void startTest() {
        mAudioCapture = new AudioCapture();
        mWavFileWriter = new WavFileWriter();
        try {
            mWavFileWriter.openFile(DETAULT_PATH,DEFAULT_SAMPLE_RATE,DEFAULT_CHANNEL,DEFAULT_BIT_PER_SAMPLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mAudioCapture.setAudioCaptureListener(this);
        mAudioCapture.startCapture(DEFAULT_SAMPLE_RATE,AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
    }

    @Override
    public void stopTest() {
        mAudioCapture.stopCapture();
        try {
            mWavFileWriter.closeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void captureData(byte[] data) {
        mWavFileWriter.writeData(data,0,data.length);
    }
}
