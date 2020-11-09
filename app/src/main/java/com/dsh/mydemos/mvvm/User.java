package com.dsh.mydemos.mvvm;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.dsh.mydemos.BR;

/**
 * @author dongshuhuan
 * date 2020/10/20
 * ViewModel
 */
public class User extends BaseObservable {

    //user作为一个被观察者, frameWork作为观察者, user可以被观察

    private String name;
    private String pwd;

    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        notifyPropertyChanged(BR.pwd);
    }
}
