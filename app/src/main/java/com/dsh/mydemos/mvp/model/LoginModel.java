package com.dsh.mydemos.mvp.model;

import android.os.Handler;
import com.dsh.mydemos.mvp.bean.LoginParam;
import com.dsh.mydemos.mvp.bean.UserData;

/**
 * Created by dongshuhuan
 * createDate 2018/8/28
 * Email 785434788@qq.com
 * Description
 * Change Log
 */

public class LoginModel implements ILogin {

    @Override
    public void goLogin( final LoginParam param, final ILoginListener iLoginListener) {
        //模拟子线程网络请求
        new Thread(new Runnable() {
            @Override public void run() {
                if ("dsh".equals(param.getUserName()) && "123456".equals(param.getUserPwd())) {
                    UserData data = new UserData("dsh",true);
                    data.setLoginSuccess(true);
                    data.setUserName("dsh");
                    iLoginListener.loginSuccess(data);
                } else {
                    iLoginListener.loginFailed("账号或密码错误");
                }
            }
        }).start();



    }

}
