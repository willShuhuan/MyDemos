package com.dsh.mydemos;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dsh.mydemos.activity.AudioMp3Activity;
import com.dsh.mydemos.activity.IntentServiceActivity;
import com.dsh.mydemos.activity.MyCameraActivity;
import com.dsh.mydemos.activity.QRCodeActivity;
import com.dsh.mydemos.activity.RoundCornerLayoutActivity;
import com.dsh.mydemos.activity.RxJavaActivity;
import com.dsh.mydemos.activity.SearchViewGreenDaoActivity;
import com.dsh.mydemos.base.BaseActivity;
import com.dsh.mydemos.mvp.activity.MVPLoginActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {

    final String[] permissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private RxPermissions rxPermissions;

    @BindView(R.id.rxjava)
    Button mRxjava;
    @BindView(R.id.flowLayout)
    Button mFlowLayout;
    @BindView(R.id.mycamera)
    Button mMycamera;
    @BindView(R.id.intentservice)
    Button mIntentservice;
    @BindView(R.id.mp3)
    Button mMp3;
    @BindView(R.id.qrcode)
    Button qrcode;
    @BindView(R.id.mvp)
    Button mvp;
    @BindView(R.id.rc_layout)
    Button rcLaout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        ButterKnife.bind(this);

        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void setListener() {
        mRxjava.setOnClickListener(this);
        mFlowLayout.setOnClickListener(this);
        mMycamera.setOnClickListener(this);
        mIntentservice.setOnClickListener(this);
        mMp3.setOnClickListener(this);
        qrcode.setOnClickListener(this);
        mvp.setOnClickListener(this);
        rcLaout.setOnClickListener(this);

    }

    @Override
    public void initData() {
        rxPermissions = new RxPermissions(this);
        rxPermissions.request(permissions)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (!aBoolean) {
                            Log.e(MainActivity.class.getName(),
                                    "We highly recommend that you need to grant the " +
                                            "special permissions before initializing the SDK, otherwise some functions will not work");

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        finish();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.rxjava:
                startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
                break;
            case R.id.flowLayout:
                startActivity(new Intent(MainActivity.this, SearchViewGreenDaoActivity.class));
                break;
            case R.id.mycamera:
                startActivity(new Intent(MainActivity.this, MyCameraActivity.class));
                break;
            case R.id.intentservice:
                startActivity(new Intent(MainActivity.this, IntentServiceActivity.class));
                break;
            case R.id.mp3:
                startActivity(new Intent(MainActivity.this, AudioMp3Activity.class));
                break;
            case R.id.qrcode:
                startActivity(new Intent(MainActivity.this, QRCodeActivity.class));
                break;
            case R.id.mvp:
                startActivity(new Intent(MainActivity.this, MVPLoginActivity.class));
                break;
            case R.id.rc_layout:
                startActivity(new Intent(MainActivity.this, RoundCornerLayoutActivity.class));
                break;
            default:
                break;
        }
    }
}
