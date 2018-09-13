package com.dsh.mydemos.mvp.view;

import com.dsh.mydemos.mvp.model.bean.UserData;

/**
 * Created by dongshuhuan
 * createDate 2018/8/28
 * Email 785434788@qq.com
 * Description
 */

public interface ILoginView {
    void loginSuccess(UserData data);
    void loginFail(String result);
    void showLoading();
    void hideLoading();

}
