package com.dsh.mydemos.service;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import com.buihha.audiorecorder.Mp3Recorder;
import com.dsh.mydemos.utils.TimeUtils;

import java.io.File;
import java.io.IOException;

/**
 * 录音service，实现了录音边录边转码mp3的功能
 * Created by Adam 2017/4/3
 */
public class AudioMp3RecoderService3 extends Service implements Mp3Recorder.RecorderListener {


    private static final String TAG = AudioMp3RecoderService3.class.getSimpleName();
    Mp3Recorder mp3Recorder;
    private boolean isRecording = false;
    private boolean flag = false;
    private String mp3Floder = Environment.getExternalStorageDirectory() + "/MyDemos/";
    private String mp3Path = null;
    private long startTime;
    private OnAudioStatusUpdateListener audioStatusUpdateListener;

    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 1:
                    int db = (int) msg.obj;
                    updateMicStatus(db);
                    break;
                default:
                    break;

            }
            super.dispatchMessage(msg);
        }
    };

    /**
     * 文件存储默认sdcard/record
     */
    public AudioMp3RecoderService3() {

        //默认保存路径为/sdcard/record/下
        this(Environment.getExternalStorageDirectory() + "/MyDemos/");
    }

    public AudioMp3RecoderService3(String filePath) {

        File path = new File(filePath);
        if (!path.exists()) {
            path.mkdirs();
        }

        this.mp3Floder = filePath;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return new MsgBinder();
    }

    /**
     * 开始录音
     */
    public boolean startRecord() throws IOException {
        // 如果正在录音，则返回
        if (isRecording) {
            return isRecording;
        }

        isRecording = true;

        startTime = System.currentTimeMillis();
        if (mp3Recorder == null) {
            String fileName = TimeUtils.getCurrentTime();
            mp3Path = mp3Floder + fileName + ".mp3";
            mp3Recorder = new Mp3Recorder(mp3Path);
            mp3Recorder.setListener(this);
            mp3Recorder.startRecording();
            Toast.makeText(this, "开始录音!", Toast.LENGTH_SHORT).show();
        } else {
            mp3Recorder.startRecording();
            Toast.makeText(this, "正在录音!", Toast.LENGTH_SHORT).show();
        }

        setFlag(true);
        return isRecording;
    }


    /**
     * 停止录音
     */
    public boolean stopRecordingAndConvertFile() throws IOException {

        if (!isRecording) {
            return isRecording;
        }
        isRecording = false;
        mp3Recorder.stopRecording();

        setFlag(false);
        audioStatusUpdateListener.onStop(mp3Path);
        //通知媒体库更新文件夹
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(mp3Path));
        intent.setData(uri);
        this.sendBroadcast(intent);
        mp3Recorder = null;

        return isRecording;// convertOk==true,return true
    }

    @Override
    public void stop() {
//		Toast.makeText(this,"录音文件保存完毕,文件保存在:" + mp3Path,Toast.LENGTH_SHORT).show();
    }

    //音量监听
    @Override
    public void Volume(final int volume) {

        Message msg = new Message();
        msg.what = 1;
        msg.obj = volume;
        mHandler.sendMessageDelayed(msg, 100);

    }

    public void setOnAudioStatusUpdateListener(OnAudioStatusUpdateListener
            audioStatusUpdateListener) {
        this.audioStatusUpdateListener = audioStatusUpdateListener;
    }

    /**
     * 更新麦克状态
     */
    private void updateMicStatus(int db) {

        if (mp3Recorder != null) {
            if (null != audioStatusUpdateListener) {
                audioStatusUpdateListener.onUpdate(db, System.currentTimeMillis() - startTime);
            }

//            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
        }
    }

    public interface OnAudioStatusUpdateListener {
        /**
         * 录音中...
         *
         * @param db   当前声音分贝
         * @param time 录音时长
         */
        public void onUpdate(int db, long time);

        /**
         * 停止录音
         *
         * @param filePath 保存路径
         */
        public void onStop(String filePath);


    }


    public class MsgBinder extends Binder {
        /**
         * 获取当前Service的实例
         */
        public AudioMp3RecoderService3 getService() {
            return AudioMp3RecoderService3.this;
        }
    }

}
