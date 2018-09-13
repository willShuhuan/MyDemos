package com.dsh.mydemos.mvp.model;

import com.dsh.mydemos.mvp.model.bean.LoginParam;

/**
 * Created by dongshuhuan
 * createDate 2018/8/28
 * Email 785434788@qq.com
 * Description
 * Change Log
 */

public interface ILogin {
    void goLogin(LoginParam param, ILoginListener iLoginListener);
}
