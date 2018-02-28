package com.dsh.mydemos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        ButterKnife.bind(this);

        setListener();
    }

    @Override
    public void setListener() {
        mRxjava.setOnClickListener(this);
        mFlowLayout.setOnClickListener(this);
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

        		default:
        			break;
        		}
    }
}
