package com.dsh.mydemos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dsh.mydemos.activity.IntentServiceActivity;
import com.dsh.mydemos.activity.MyCameraActivity;
import com.dsh.mydemos.activity.RxJavaActivity;
import com.dsh.mydemos.activity.SearchViewGreenDaoActivity;
import com.dsh.mydemos.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rxjava)
    Button mRxjava;
    @BindView(R.id.flowLayout)
    Button mFlowLayout;
    @BindView(R.id.mycamera)
    Button mMycamera;
    @BindView(R.id.intentservice)
    Button mIntentservice;


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
    }

    @Override
    public void initData() {

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

        		default:
        			break;
        		}
    }
}
