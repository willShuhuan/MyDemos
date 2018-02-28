package com.dsh.mydemos.base;

import android.view.View;
import android.view.View.OnClickListener;

public interface BaseInterface extends OnClickListener {
    void initView();

    void setListener();

    void initData();

    @Override
    public void onClick(View arg0);
}


