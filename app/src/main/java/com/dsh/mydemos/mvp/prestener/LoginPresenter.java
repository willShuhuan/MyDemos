package com.dsh.mydemos.mvp.prestener;

import android.os.Handler;
import com.dsh.mydemos.mvp.bean.UserData;
import com.dsh.mydemos.mvp.model.ILoginListener;
import com.dsh.mydemos.mvp.model.LoginModel;
import com.dsh.mydemos.mvp.bean.LoginParam;
import com.dsh.mydemos.mvp.view.ILoginView;


/**
 * Created by dongshuhuan
 * createDate 2018/8/28
 * Email 785434788@qq.com
 * Description presenter关联model与view
 */

public class LoginPresenter {
    private LoginModel mModel;
    private ILoginView mView;
    private Handler mHandler = new Handler();

    public LoginPresenter(ILoginView loginView) {
        this.mView = loginView;
        this.mModel= new LoginModel();
    }

    public void goLogin(final LoginParam param){

        mView.showLoading();

        mModel.goLogin(param, new ILoginListener() {
            @Override
            public void loginSuccess( final UserData data) {
                //handler交给主线程更新UI
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mView.hideLoading();
                        mView.loginSuccess(data);
                    }
                },2000);

            }

            @Override
            public void loginFailed( final String result) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mView.hideLoading();
                        mView.loginFail(result);

                    }
                },2000);

            }
        });



    }

}
