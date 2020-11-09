package com.dsh.kotlinpractice;

import com.dsh.kotlinpractice.example.core.BaseApplication;

/**
 * @author dongshuhuan
 * date 2020/10/27
 * version
 */
class JavaUseKotlin {
    public static void main(String[] args) {
        //java调用kotlin静态方法要加Companion
        BaseApplication.Companion.currentApplication();
        //currentApplication加上jvmStatic注解就可以正常调用
        BaseApplication.currentApplication();
    }
}
