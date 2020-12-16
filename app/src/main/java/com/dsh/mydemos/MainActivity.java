package com.dsh.mydemos;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dsh.mydemos.activity.AudioMp3Activity;
import com.dsh.mydemos.activity.EmptyLayoutActivity;
import com.dsh.mydemos.activity.IntentServiceActivity;
import com.dsh.mydemos.activity.MyCameraActivity;
import com.dsh.mydemos.activity.QRCodeActivity;
import com.dsh.mydemos.activity.RoundCornerLayoutActivity;
import com.dsh.mydemos.activity.RxJavaActivity;
import com.dsh.mydemos.activity.SearchViewGreenDaoActivity;
import com.dsh.mydemos.base.BaseActivity;
import com.dsh.mydemos.mvp.activity.MVPLoginActivity;
import com.dsh.mydemos.mvvm.MvvmMainActivity;
import com.dsh.txlessons.annotaionprocessing.AptActivity;
import com.dsh.txlessons.blockcanary.BlockCanaryActivity;
import com.dsh.txlessons.constrainlayout.ConstrainLayoutActivity;
import com.dsh.txlessons.customview.CustomViewActivity;
import com.dsh.txlessons.gradle.GradleMainActivity;
import com.dsh.txlessons.leakcanary.LeakCanaryActivity;
import com.dsh.txlessons.mvvm.MvvmActivity;
import com.dsh.txlessons.plugin.PluginActivity;
import com.dsh.txlessons.setview.SetViewActivity;
import com.dsh.txlessons.viewanimation.ViewAnimationActivity;
import com.dsh.txlessons.viewbitmap.BitmapDrawableActivity;
import com.dsh.txlessons.viewclip.ViewClipActivity;
import com.dsh.txlessons.viewcustomize.ViewCustomizeActivity;
import com.dsh.txlessons.viewmaterialedittext.MaterialEditTextActivity;
import com.dsh.txlessons.viewtext.TextMeasureActivity;
import com.dsh.txlessons.viewxfer.XferActivity;
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
    @BindView(R.id.rxjava) Button rxjava;
    @BindView(R.id.flowLayout) Button flowLayout;
    @BindView(R.id.mycamera) Button mycamera;
    @BindView(R.id.intentservice) Button intentservice;
    @BindView(R.id.mp3) Button mp3;
    @BindView(R.id.qrcode) Button qrcode;
    @BindView(R.id.mvp) Button mvp;
    @BindView(R.id.rc_layout) Button rcLayout;
    @BindView(R.id.empty_view) Button emptyView;
    @BindView(R.id.mvvm) Button mvvm;
    @BindView(R.id.kt) Button kt;
    @BindView(R.id.apt) Button apt;
    @BindView(R.id.constrainLayout) Button constrainLayout;
    @BindView(R.id.gradle) Button gradle;
    @BindView(R.id.view1) Button view1;

    private RxPermissions rxPermissions;


    @Override public int getContentView() {
        return R.layout.menu;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void setListener() {
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

    @Override public void onClick(View arg0) {

    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.rxjava, R.id.flowLayout, R.id.mycamera, R.id.intentservice, R.id.mp3, R.id.qrcode,
            R.id.mvp, R.id.rc_layout, R.id.empty_view, R.id.mvvm,R.id.kt,R.id.retrofit,R.id.apt,
            R.id.constrainLayout,R.id.gradle,R.id.plugin,R.id.mvvmArchitecture,R.id.leak,R.id.block,
            R.id.view1,R.id.xfer,R.id.viewText,R.id.viewClip,R.id.viewAnimation,R.id.bitmapDrawable,
            R.id.editText,R.id.viewcustomize,R.id.setview
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            case R.id.empty_view:
                startActivity(new Intent(MainActivity.this, EmptyLayoutActivity.class));
                break;
            case R.id.mvvm:
                startActivity(new Intent(MainActivity.this, MvvmMainActivity.class));
                break;
            case R.id.kt:
                startActivity(new Intent(MainActivity.this, com.dsh.kotlinpractice.example.MainActivity.class));
                break;
            case R.id.retrofit:
                startActivity(new Intent(MainActivity.this, com.dsh.txlessons.retrofit.RetrofitActivity.class));
                break;
            case R.id.apt:
                startActivity(new Intent(MainActivity.this, AptActivity.class));
                break;
            case R.id.constrainLayout:
                startActivity(new Intent(MainActivity.this, ConstrainLayoutActivity.class));
                break;
            case R.id.gradle:
                startActivity(new Intent(MainActivity.this, GradleMainActivity.class));
                break;
            case R.id.plugin:
                startActivity(new Intent(MainActivity.this, PluginActivity.class));
                break;
            case R.id.mvvmArchitecture:
                startActivity(new Intent(MainActivity.this, MvvmActivity.class));
                break;
            case R.id.leak:
                startActivity(new Intent(MainActivity.this, LeakCanaryActivity.class));
                break;
            case R.id.block:
                startActivity(new Intent(MainActivity.this, BlockCanaryActivity.class));
                break;
            case R.id.view1:
                startActivity(new Intent(MainActivity.this, CustomViewActivity.class));
                break;
            case R.id.xfer:
                startActivity(new Intent(MainActivity.this, XferActivity.class));
                break;
            case R.id.viewText:
                startActivity(new Intent(MainActivity.this, TextMeasureActivity.class));
                break;
            case R.id.viewClip:
                startActivity(new Intent(MainActivity.this, ViewClipActivity.class));
                break;
            case R.id.viewAnimation:
                startActivity(new Intent(MainActivity.this, ViewAnimationActivity.class));
                break;
            case R.id.bitmapDrawable:
                startActivity(new Intent(MainActivity.this, BitmapDrawableActivity.class));
                break;
            case R.id.editText:
                startActivity(new Intent(MainActivity.this, MaterialEditTextActivity.class));
                break;
            case R.id.viewcustomize:
                startActivity(new Intent(MainActivity.this, ViewCustomizeActivity.class));
                break;
            case R.id.setview:
                startActivity(new Intent(MainActivity.this, SetViewActivity.class));
                break;
            default:
                break;
        }
    }
}
