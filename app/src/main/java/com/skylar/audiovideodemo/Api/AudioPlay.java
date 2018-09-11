package com.skylar.audiovideodemo.Api;

import android.media.AudioManager;
import android.media.AudioTrack;

/**
 * Created by Skylar on 2018/9/11
 * Github : https://github.com/skylarliuu
 */
public class AudioPlay {

    private boolean isPlaying = false;
    private int minBufferSize;
    private AudioTrack mAudioTrack;


    public void startPlay(int streamType, int sampleRateInHz, int channelConfig, int audioFormat){
        if(isPlaying)
            return;

        minBufferSize = AudioTrack.getMinBufferSize(sampleRateInHz,channelConfig,audioFormat);
        if(minBufferSize == AudioTrack.ERROR_BAD_VALUE){
            return;
        }

        mAudioTrack = new AudioTrack(streamType,sampleRateInHz,channelConfig,audioFormat,minBufferSize,AudioTrack.MODE_STREAM);
        if(mAudioTrack.getState() == AudioTrack.STATE_UNINITIALIZED){
            return;
        }



        isPlaying = true;

    }

    public int getMinBufferSize(){
        return minBufferSize;
    }


    public void play(byte[] buffer) {
        if(!isPlaying)
            return;
        if(mAudioTrack.getPlayState() != AudioTrack.PLAYSTATE_PLAYING){
            mAudioTrack.play();
        }

        mAudioTrack.write(buffer,0,buffer.length);
    }

    public void stopPlay(){
        if(!isPlaying)
            return;

        isPlaying = false;

        if(mAudioTrack!=null && mAudioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING){
            mAudioTrack.stop();
            mAudioTrack.release();
            mAudioTrack = null;
        }


    }
}
