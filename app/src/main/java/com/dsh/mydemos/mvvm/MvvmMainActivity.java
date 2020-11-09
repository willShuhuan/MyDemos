package com.dsh.mydemos.mvvm;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dsh.mydemos.R;
import com.dsh.mydemos.databinding.ActivityMvvmMainBinding;

public class MvvmMainActivity extends AppCompatActivity {

    private User user;
    ActivityMvvmMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_mvvm_main);
        user = new User("abc","123456");
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_mvvm_main);
        mBinding.setUser(user);
        //mBinding.setVariable(BR.user,user);

        new Thread(){
            @Override public void run() {
                super.run();
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    user.setName(user.getName()+i);
                }
            }
        }.start();

        //databinding可以使用xml中data及布局中的标签, 如tvName这是布局中的文本标签
        //mBinding.tvName.setText("");
    }
}