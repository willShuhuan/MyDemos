package com.dsh.txlessons.generics.charapter4;

import com.dsh.txlessons.generics.basicuse.fruit.Apple;
import com.dsh.txlessons.generics.basicuse.fruit.Pear;
import com.dsh.txlessons.generics.charapter3.Shop;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.util.List;

/**
 * 泛型的本质，什么时候使用泛型
 * @author dongshuhuan
 * date 2020/11/13
 * version
 */
class Generics4 {
    public static void main(String[] args) {

        //1. 泛型商店
        Shop<Apple> appleShop = null;
        //使用泛型，不需要转型
        Apple apple = appleShop.buy();
        appleShop.refund(apple);
        //编译错误，泛型可以检查类型
        appleShop.refund(new Pear(2f));


        //2. 非泛型商店
        NonGenericShop nonGenericShop = null;
        //不使用泛型，需要转型
        Apple apple2 = (Apple) nonGenericShop.buy();
        nonGenericShop.refund(apple2);
        //编译通过，不会检查类型
        nonGenericShop.refund(new Pear(3f));

        //3. 固定类型的商店
        //也可以达到类似泛型的效果
        AppleShop appleShop1 = null;
        Apple apple1 = appleShop1.buy();
        appleShop1.refund(apple1);
        appleShop1.refund(new Pear(4f));


        //4.假设我们想要创建一个香蕉Banana商店
        //4.1 使用泛型，可以直接使用Shop接口
        //4.2 不使用泛型，则需要新创建一个Banana的接口

        //5. 多重类型约束
        //编译错误，类型约束
        SerializableAppleShop<Apple> serializableAppleShop= null;
        //编译通过
        SerializableAppleShop<SerializableApple> serializableAppleShop1 = null;


    }

    interface AppleShop{
        Apple buy();
        float refund(Apple object);
    }

    //多重限制
    //5.1 多重类型约束
    interface SerializableAppleShop<T extends Apple & Serializable>{
        //5.2. 约束两个参数为同一类型
        <P> void exchange (P p1,P p2);
    }

}
