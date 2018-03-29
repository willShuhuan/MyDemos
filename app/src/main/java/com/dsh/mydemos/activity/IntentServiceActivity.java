package com.dsh.mydemos.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.dsh.mydemos.R;
import com.dsh.mydemos.base.BaseActivity;
import com.dsh.mydemos.service.MyIntentService;

/**
 * Created by Adam on 2018/3/29.
 */
public class IntentServiceActivity extends BaseActivity implements MyIntentService.UpdateUI{

    /**
     * 图片地址集合
     */
    private String url[] = {
            "http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg",
            "http://sucai.qqjay.com/qqjayxiaowo/201210/26/1.jpg",
            "http://img02.tooopen.com/images/20140504/sy_60294738471.jpg",
            "http://img2.imgtn.bdimg.com/it/u=819201812,3553302270&fm=214&gp=0.jpg",
            "http://pic55.nipic.com/file/20141208/19462408_171130083000_2.jpg",
            "http://img05.tooopen.com/images/20150531/tooopen_sy_127457023651.jpg",
            "http://pic.58pic.com/58pic/17/41/38/88658PICNuP_1024.jpg"
    };

    private static ImageView imageView;
    private static final Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            imageView.setImageBitmap((Bitmap) msg.obj);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intentservice);

        initView();
        setListener();
        initData();

    }

    @Override
    public void initView() {
        imageView = (ImageView) findViewById(R.id.image);

        Intent intent = new Intent(this, MyIntentService.class);
        for (int i = 0; i < 7; i++) {
            intent.putExtra(MyIntentService.DOWNLOAD_URL, url[i]);
            intent.putExtra(MyIntentService.INDEX_FLAG, i);
            startService(intent);
        }
        MyIntentService.setUpdateUI(this);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View arg0) {

    }

    @Override
    public void updateUI(Message message) {
        mUIHandler.sendMessageDelayed(message, message.what * 1000);
    }
}
