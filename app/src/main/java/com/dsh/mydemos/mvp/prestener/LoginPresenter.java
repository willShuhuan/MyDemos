package com.dsh.mydemos.mvp.prestener;

import android.arch.lifecycle.LifecycleOwner;
import android.os.Handler;
import android.util.Log;
import com.dsh.mydemos.mvp.model.ILogin;
import com.dsh.mydemos.mvp.model.bean.UserData;
import com.dsh.mydemos.mvp.model.LoginModel;
import com.dsh.mydemos.mvp.model.bean.LoginParam;
import com.dsh.mydemos.mvp.view.ILoginView;
import java.lang.ref.WeakReference;

/**
 * Created by dongshuhuan
 * createDate 2018/8/28
 * Email 785434788@qq.com
 * Description presenter关联model与view
 */

public class LoginPresenter extends BasePresenter{

    private static final String TAG = "LoginPresenter";

    private LoginModel mModel = new LoginModel();;
    private Handler mHandler = new Handler();


    public void goLogin(final LoginParam param){

        ((ILoginView)mView.get()).showLoading();

        mModel.goLogin(param, new ILogin.ILoginListener() {
            @Override
            public void loginSuccess( final UserData data) {
                //handler交给主线程更新UI
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((ILoginView)mView.get()).hideLoading();
                        ((ILoginView)mView.get()).loginSuccess(data);
                    }
                },2000);

            }

            @Override
            public void loginFailed( final String result) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((ILoginView)mView.get()).hideLoading();
                        ((ILoginView)mView.get()).loginFail(result);

                    }
                },2000);

            }
        });



    }

    @Override
    void oncreate(LifecycleOwner owner) {
        super.oncreate(owner);
        Log.d(TAG, "oncreate: ");
    }

    @Override
    void onDestroy(LifecycleOwner owner) {
        super.onDestroy(owner);
        Log.d(TAG, "onDestroy: ");
    }
}
