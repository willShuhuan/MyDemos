package com.dsh.mydemos.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Administrator on 2018/2/27.
 */

public abstract class DBaseActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public abstract void initView() ;

    public abstract void setListener() ;

    public abstract void initData() ;

    @Override public abstract void onClick(View arg0);

}
