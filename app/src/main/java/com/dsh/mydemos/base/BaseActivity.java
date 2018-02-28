package com.dsh.mydemos.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/27.
 */

public class BaseActivity extends AppCompatActivity implements BaseInterface{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void initView() {

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
}
