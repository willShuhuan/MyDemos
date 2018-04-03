package com.dsh.mydemos.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dsh.mydemos.R;
import com.dsh.mydemos.base.BaseActivity;
import com.dsh.mydemos.player.MediaPlayerUtil;
import com.dsh.mydemos.player.UPlayer;
import com.dsh.mydemos.service.AudioMp3RecoderService3;
import com.dsh.mydemos.utils.TimeUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Adam on 2018/4/3.
 */

public class AudioMp3Activity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_recordtime)
    TextView mTvRecordtime;
    @BindView(R.id.vol)
    TextView mVol;
    @BindView(R.id.img_record)
    ImageView mImgRecord;
    @BindView(R.id.tv_record)
    TextView mTvRecord;
    @BindView(R.id.img_play)
    ImageView mImgPlay;
    @BindView(R.id.tv_play)
    TextView mTvPlay;
    @BindView(R.id.tv_playtime)
    TextView mTvPlaytime;
    @BindView(R.id.tv_savepath)
    TextView mTvSavepath;

    private boolean isRecording = false;
    Intent intent;
    private AudioMp3RecoderService3 audioMp3RecordService;
    private int RECORD_FLAG = 1;
    private int PLAY_FLAG = 11;
    private String mFilepath;
    private UPlayer player;
    private Context mContext;
    String[] permiss = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};
    int REQUEST_CODE = 1002;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audiomp3);
        ButterKnife.bind(this);

        if(!checkMyPermission(permiss)){
            ActivityCompat.requestPermissions(this,permiss,REQUEST_CODE);
        }

        bindAudioService();

        initView();
        setListener();
        initData();
    }

    private void bindAudioService() {
        // TODO Auto-generated method stub
        //绑定Service
//			intent = new Intent(TemplateSoundRecordActivity.this,AudioRecoderService.class);
        intent = new Intent(AudioMp3Activity.this, AudioMp3RecoderService3.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void initView() {
        mContext = AudioMp3Activity.this;
        mTvTime.setText(new SimpleDateFormat("yyyy年MM月dd日 ah:mm").format(new Date())); //6：38
    }

    @Override
    public void setListener() {
        mImgPlay.setOnClickListener(this);
        mImgRecord.setOnClickListener(this);
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //返回一个MsgService对象
//        	audioRecordService = ((AudioRecoderService.MsgBinder)service).getService();
            audioMp3RecordService = ((AudioMp3RecoderService3.MsgBinder) service).getService();

            if (audioMp3RecordService.isFlag()) {
                mTvRecord.setText("停止");
                RECORD_FLAG = 2;
                isRecording = true;
            } else {
                mTvRecord.setText("录音");
                isRecording = false;
                RECORD_FLAG = 1;
            }

            audioMp3RecordService.setOnAudioStatusUpdateListener(
                    new AudioMp3RecoderService3.OnAudioStatusUpdateListener() {


                        //录音中....db为声音分贝，time为录音时长
                        public void onUpdate(int db, long time) {
                            mTvRecord.setText("停止");
//                            mVol.setText("音量"+(int) (3000 + 6000 * db / 100));
                            mVol.setText("音量(分贝)"+db);
                            mTvRecordtime.setText(TimeUtils.long2String(time));
                        }

                        //录音结束，filePath为保存路径
                        @Override
                        public void onStop(final String filePath) {
                            Log.d("", "onStop: 20182018");
                            Message msg = new Message();
                            msg.what = 2;
                            msg.obj = filePath;
                            mHandler.sendMessageDelayed(msg, 500);

                        }


                    });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            audioMp3RecordService = null;
        }
    };

    @Override
    public void initData() {

    }

    private boolean checkMyPermission(String[] permiss){
        if(permiss !=null && permiss.length > 0 ){
            for(String per : permiss) {
                if (ContextCompat.checkSelfPermission(this,per) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if (requestCode == REQUEST_CODE) {
            boolean isAllGranted = true;
            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                openAppDetails();
            }
        }
    }
    /**
     * 打开 APP 的详情设置
     */
    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("录音需要访问 “外部存储器”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            // TODO Auto-generated method stub
            int count = 0;
            switch (msg.what) {
                case 1:
                    mTvPlay.setText("播放");
                    mImgPlay.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                            R.mipmap.audio_play));//放播放的图
                    PLAY_FLAG = 11;
                    break;
                case 2:
                    String filepath = msg.obj.toString();
                    mVol.setText("音量0");
                    mTvRecordtime.setText(TimeUtils.long2String(0));
                    mTvRecord.setText("重录");
                    mTvSavepath.setText("文件路径：" + filepath);
                    mFilepath = filepath;
                    break;

                default:
                    break;

            }
            super.dispatchMessage(msg);
        }
    };

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
        		case R.id.img_record:
                    if (RECORD_FLAG == 1) {
                        isRecording = true;
                        startService(intent);
                        try {
                            audioMp3RecordService.startRecord();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        RECORD_FLAG = 2;
                        break;
                    } else if (RECORD_FLAG == 2) {
                        try {
                            audioMp3RecordService.stopRecordingAndConvertFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stopService(intent);
                        isRecording = false;
                        RECORD_FLAG = 1;
                        break;
                    }
        			break;
            case R.id.img_play:
                if (PLAY_FLAG == 11) {
                    player = new UPlayer(mFilepath, mHandler);
                    if (mFilepath==null) {
                        Toast.makeText(mContext, "请先录音才能播放", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    Toast.makeText(this, "开始播放", Toast.LENGTH_SHORT).show();
                    MediaPlayerUtil.getInstance(mContext).initCountDownPlayer(mFilepath,
                            mHandler, null, mTvPlaytime);
                    mTvPlay.setText("停止");
                    mImgPlay.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                            R.mipmap.audio_stop));//放暂停的图
                    PLAY_FLAG = 12;
                    break;
                } else if (PLAY_FLAG == 12) {
                    MediaPlayerUtil.getInstance(mContext).releasePlayer();
                    Toast.makeText(mContext, "停止播放", Toast.LENGTH_SHORT).show();
                    mTvPlay.setText("播放");
                    mImgPlay.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                            R.mipmap.audio_play));//放播放的图
                    PLAY_FLAG = 11;
                    break;
                }
                break;

        		default:
        			break;
        		}
    }


}
