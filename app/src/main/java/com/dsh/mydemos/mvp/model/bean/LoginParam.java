package com.dsh.mydemos.mvp.model.bean;

/**
 * Created by dongshuhuan
 * createDate 2018/8/28
 * Email 785434788@qq.com
 * Description
 * Change Log
 */

public class LoginParam {
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public LoginParam(String name,String pwd){
        this.userName = name;
        this.userPwd = pwd;
    }

    private String userName;
    private String userPwd;
}
