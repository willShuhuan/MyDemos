package com.dsh.mydemos.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dsh.mydemos.R;
import com.dsh.mydemos.mvp.model.bean.LoginParam;
import com.dsh.mydemos.mvp.model.bean.UserData;
import com.dsh.mydemos.mvp.prestener.LoginPresenter;
import com.dsh.mydemos.mvp.view.ILoginView;

/**
 * Created by dongshuhuan
 * createDate 2018/8/28
 * Email 785434788@qq.com
 * Description
 * Change Log
 */

public class MVPLoginActivity extends MVPBaseActivity<LoginPresenter,ILoginView> implements ILoginView {

    @BindView(R.id.tv_username) EditText tvUsername;
    @BindView(R.id.tv_pwd) EditText tvPwd;
    @BindView(R.id.button) Button button;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvplogin);
        ButterKnife.bind(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (TextUtils.isEmpty(tvPwd.getText().toString().trim()) ||
                        TextUtils.isEmpty(tvUsername.getText().toString())) {
                    Toast.makeText(MVPLoginActivity.this, "用户名或密码不能为空",
                            Toast.LENGTH_SHORT).show();
                } else {
                    LoginParam param = new LoginParam(tvUsername.getText().toString().trim(),
                            tvPwd.getText().toString().trim());
                    mPresenter.goLogin(param);
                }
            }
        });
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void init() {
        getLifecycle().addObserver(mPresenter);
    }

    //预留模板方法,用于通用操作,做其他事情
    @Override
    protected void doOtherThings() {

    }

    @Override
    public void loginSuccess(UserData data) {
        Toast.makeText(MVPLoginActivity.this, "登录成功"+data.getUserName(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFail(String result) {
        Toast.makeText(MVPLoginActivity.this, "登录失败：" + result,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (progressBar.isShown()){
            progressBar.setVisibility(View.GONE);
        }
    }


}


