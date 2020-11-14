package com.dsh.txlessons.generics.charapter7;

import com.dsh.txlessons.generics.basicuse.fruit.Apple;
import com.dsh.txlessons.generics.basicuse.fruit.Fruit;
import com.dsh.txlessons.generics.charapter3.Shop;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dongshuhuan
 * date 2020/11/14
 * version
 */
class Characpter7 {
    public static void main(String[] args) {
        //Gson泛型擦除
        TypeToken<List<String>> listToken = new TypeToken<List<String>>(){};
        //Gson可以返回此类型的原始(非泛型)类型。
        listToken.getRawType();

        Field filed = null;
        filed.getType();
        filed.getGenericType();
        Method method = null;
        method.getGenericReturnType();
        method.getGenericParameterTypes();

        Test test = new Test();
        try {
            Field field1 = test.getClass().getDeclaredField("list");
            System.out.println(field1.getGenericType());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        //泛型不支持协变和逆变，有类型擦除
        //编译错误
        List<Object> objects = new ArrayList<String>();
        //编译通过 通过「 ？extends 」使其支持 covariant 协变
        //【左边List是右边ArrayList的父类，左边泛型Object是右边泛型String的父类，】
        List<? extends Object> objects2 = new ArrayList<String>();

        //编译错误，边界错误
        List<String> objects4 = new ArrayList<Object>();
        //编译通过 通过「 ？super 」使其支持 contravariant 逆变/反变
        // 【左边String是右边Object的子类，右边ArrayList是左边List的子类】
        List<? super String> objects3 = new ArrayList<Object>();

        //数组支持协变，但是没有类型擦除
        //编译错误, 由于数组没有泛型擦除，所以数组声明不能使用泛型
        Shop<Apple>[] appleShop = new Shop<Apple>[10];
        //编译通过
        Shop[] appleShop2 = new Shop[10];


    }
}
