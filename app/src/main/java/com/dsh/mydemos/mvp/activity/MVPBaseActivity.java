package com.dsh.mydemos.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.dsh.mydemos.R;
import com.dsh.mydemos.mvp.model.bean.LoginParam;
import com.dsh.mydemos.mvp.prestener.BasePresenter;
import com.dsh.mydemos.mvp.prestener.LoginPresenter;
import com.dsh.mydemos.mvp.view.IBaseView;
import android.support.v7.app.AppCompatActivity;

/**
 * @author dongshuhuan
 * date 2020/10/19
 * version
 */
public abstract class MVPBaseActivity<T extends BasePresenter,V extends IBaseView> extends AppCompatActivity {


    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvplogin);
        //选择表示层
        mPresenter = createPresenter();
        mPresenter.attachView((V)this);
        init();
        doOtherThings();
    }

    protected abstract T createPresenter();

    protected abstract void  doOtherThings();

    protected abstract void  init();

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mPresenter.detachView();
    }



}
