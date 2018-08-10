package com.dsh.mydemos.player;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;

/**
 * 2014-3-24下午4:19:16
 * 2017-3-27  DSH改了下
 *
 * @author:hlloworld.Mr-zz
 *
 * @todo:MediaPlayer工具类，可以得到一个装载好的MediaPlayer,并且装载完毕后会自动播放一次
 *                                                           1.首先会检查本地是否有指定路径（该路径有项目根目录
 *                                                           +voice+网络路径的文件名组成）
 *                                                           的音频文件 2.如果存在，直接装载播放
 *                                                           3.如果不存在，则异步播放网络音频
 */
public class MediaPlayerUtil {

    private ProgressDialog pd;
    private MediaPlayer player = null;
    private Handler handler = null;
    private static Context context;
    private static MediaPlayerUtil instance = null;
    private int playPosition;
    String ttime;
    TextView ttv;

    private MediaPlayerUtil() {
    }

    public static MediaPlayerUtil getInstance(Context context) {
        if (instance == null) {
            MediaPlayerUtil.context = context;
            instance = new MediaPlayerUtil();
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.listen(new MyPhoneListener(),
                    PhoneStateListener.LISTEN_CALL_STATE);
        }
        return instance;
    }

    public void initCountDownPlayer(final String voiceUrl,final Handler handler, final String time,final TextView tv) {
        // 如果sd卡能用，播放sd卡上的文件
        ttv=tv;
        try {
            player = player == null ? new MediaPlayer() : player;
            player.setOnCompletionListener(new OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                    releasePlayer();

                }
            });
            player.setDataSource(voiceUrl);
            player.prepare();
            player.start();
            int durtime = player.getDuration();
            String dddtime=secToTime(durtime/1000);
            ttime=dddtime;
            System.out.println("zzzz"+durtime+"xxxx"+dddtime);
            UpdateVoiceTimeThread.getInstance(dddtime,tv).start();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        //        //播放网络文件
        //        else {
        //            playNetVoiceWithCountDown(voiceUrl, time,tv);
        //        }
    }

    private void playNetVoiceWithCountDown(final String voiceUrl,final String time,final TextView tv){
        // 如果sd卡不能用，直接播放網絡文件
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Uri uri = Uri.parse(voiceUrl);
                player = player == null ? MediaPlayer.create(context, uri)
                        : player;
                player.setOnCompletionListener(new OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        UpdateVoiceTimeThread.getInstance( time,tv).stop();

                    }
                });
                player.setOnPreparedListener(new OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        player.start();

                        UpdateVoiceTimeThread.getInstance( time,tv).start();
                        Message msg = handler.obtainMessage();
                        msg.obj = player;
                        msg.what = 10;
                        handler.sendMessage(msg);
                    }
                });
            }
        }).start();

    }

    /**
     * 显示无边框透明的progressdialog
     */
    private void showProgress() {
        pd = new ProgressDialog(context);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
    }

    /**
     * 隐藏无边框透明的progressdialog
     */
    private void dissmissProgress() {
        if (pd == null) {
            return;
        }
        pd.dismiss();
    }

    public void releasePlayer() {
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
            player = null;
        }
        //        if (pd != null) {
        //            pd.dismiss();
        //            pd = null;
        //        }
        UpdateVoiceTimeThread.getInstance(ttime, ttv).stop();
        context = null;
        instance = null;
    }

    /**
     * 只有电话来了之后才暂停音乐的播放
     */
    static class MyPhoneListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:// 电话来了
                    if (instance!=null) {
                        instance.callIsComing();
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE: // 通话结束
                    if (instance!=null) {
                        instance.callIsDown();
                    }
                    break;
            }
        }
    }

    /**
     * 来电话了
     */
    public void callIsComing() {
        if (player.isPlaying()) {
            playPosition = player.getCurrentPosition();// 获得当前播放位置
            player.stop();
        }
    }

    /**
     * 通话结束
     */
    public void callIsDown() {
        if (playPosition > 0) {
            player.seekTo(playPosition);
            playPosition = 0;
        }
    }


    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        }
        else {
            retStr = "" + i;
        }
        return retStr;
    }







}
