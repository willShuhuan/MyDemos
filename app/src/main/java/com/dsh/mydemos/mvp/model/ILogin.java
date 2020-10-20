package com.dsh.mydemos.mvp.model;

import com.dsh.mydemos.mvp.model.bean.LoginParam;
import com.dsh.mydemos.mvp.model.bean.UserData;

/**
 * Created by dongshuhuan
 * createDate 2018/8/28
 * Email 785434788@qq.com
 * Description
 * Change Log
 */

public interface ILogin {
    void goLogin(LoginParam param, ILoginListener iLoginListener);
    interface ILoginListener {
        void loginSuccess(UserData data);
        void loginFailed(String result);
    }
}
