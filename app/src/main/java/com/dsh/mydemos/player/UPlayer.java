package com.dsh.mydemos.player;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class UPlayer implements IVoiceManager {

    private final String TAG = UPlayer.class.getName();
    private String path;
    private static Handler mHandler;

    private MediaPlayer mPlayer;
    public UPlayer(String path,Handler handler){
        this.path = path;
        this.mHandler = handler;
        mPlayer = new MediaPlayer();
    }

    @Override
    public boolean start() {
        try {
            //设置要播放的文件
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(path);
            mPlayer.prepare();
            //播放
            mPlayer.start();

            mPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.d("tag", "播放完毕");
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                }
            });

            //             File file = new File(path);
            // 			FileInputStream fis = new FileInputStream(file);
            // 			mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 			mPlayer.setDataSource(fis.getFD());
            // 			mPlayer.prepare();
            // 			mPlayer.start();

        }catch(Exception e){
            Log.e(TAG, "prepare() failed");
        }

        return false;
    }

    @Override
    public boolean stop() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
        return false;
    }

    @Override
    public boolean pause() {
        mPlayer.pause();
        return false;
    }



} 
