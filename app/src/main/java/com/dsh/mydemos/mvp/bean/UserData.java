package com.dsh.mydemos.mvp.bean;

/**
 * Created by dongshuhuan
 * createDate 2018/8/28
 * Email 785434788@qq.com
 * Description
 * Change Log
 */

public class UserData {

    private String userName;
    private boolean loginSuccess;

    public UserData(String userName, boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }

}
