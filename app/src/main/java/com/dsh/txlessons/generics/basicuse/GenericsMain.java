package com.dsh.txlessons.generics.basicuse;

import com.dsh.txlessons.generics.basicuse.fruit.Apple;
import com.dsh.txlessons.generics.basicuse.fruit.Fruit;
import com.dsh.txlessons.generics.basicuse.fruit.Pear;
import com.dsh.txlessons.generics.basicuse.simcard.ChinaMobile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
class GenericsMain {
    public static void main(String[] args) {

        // -----------------------------------1. 泛型的简单使用与创建-----------------------------------
        Wrapper<String> stringWrapper = new Wrapper<>();
        stringWrapper.set("zzz");
        System.out.println(stringWrapper.get());//zzz

        DshList<String> dshList = new DshList<>();
        dshList.add("aaa");
        dshList.add("bbb");
        dshList.add("cccc");
        System.out.println(dshList);//[aaa, bbb, cccc]


        SimShop<String, ChinaMobile> simShop;
        //SimShop<AppleShop, Apple> appleSimShop;//编译错误


        //--------------------------------------2. 泛型类型实例化的上界与下界---------------------------------

        //-------------------上界-------------------

        //通过子类对象(小范围)实例化父类对象(大范围),这种属于自动转换，Apple是Fruit的子类
        Fruit fruit = new Apple(2.0f);
        //通过父类对象(大范围)实例化子类对象(小范围),这种属于强制转换
        Apple apple = (Apple) fruit;

        //1
        //在泛型中，父类的泛型声明不能用子类的泛型声明去实例
        //由于实例了一个只能装Apple的List,声明的List要求能装各种水果，所以这里会编译错误
        ArrayList<Fruit> fruits = new ArrayList<Apple>();
        fruits.add(new Pear(2f));//添加了错误的对象
        Apple apple1 = (Apple) fruits.get(0);//GG 向下转型失败

        //2
        //加上通配符 ? 解开限制,? 规定了泛型的上界，在这里是Fruit
        //声明List的时候不报错
        ArrayList<? extends Fruit> fruits2 = new ArrayList<Apple>();
        //但是添加对象的时候依然报错
        // ? extends Fruit , 编译器并不能确定添加的到底是什么，所以即便添加Apple也会编译错误
        fruits2.add(new Apple(2.0f));

        //3
        //数组泛型，数组没有泛型擦除
        Fruit[] fruits1 = new Apple[10];
        fruits1[0] = new Pear(3f);//GG,运行时错误  ArrayStoreException
        System.out.println("Fruit 数组添加元素完成");

        //4
        //强制转换 -> 运行时等价于 ArrayList fruits2 = (ArrayList) new ArrayList();
        //泛型信息被擦除
        ArrayList<Fruit> fruits3 = (ArrayList) new ArrayList<Apple>();
        fruits3.add(new Pear(2f));//没有报错，正常运行
        Apple apple2 = (Apple) fruits3.get(0);//GG 运行时异常 转型错误ClassCastException
        System.out.println("Fruit List 添加元素完成");

        //5
        //? 通配符的场景化使用
        getTotalPrice(fruits3);
        ArrayList<Apple> apples = new ArrayList<Apple>();
        getTotalPrice(apples);//泛型限制,编译错误
        getTotalPriceImprove(apples);//通配符，可以编译


        //-------------------下界-------------------
        //6
        //在泛型中，子类的泛型声明也不能用父类的泛型声明去实例
        List<Apple> apples1 = new ArrayList<Fruit>();//编译错误
        //用 ？通配符+super关键字声明
        List<? super Apple> apples2 = new ArrayList<Fruit>();
        apples2.add(new Apple(5f));
        Apple apple3 = apples2.get(0);//编译错误，apples2不能确定具体类型
        Apple apple4 = (Apple)apples2.get(0);//使用强制转换获取对象

        //7
        //泛型下界接收父类
        Apple apple5 = new Apple(4f);
        List<Fruit> list = new ArrayList<>();
        list.add(apple5);
        addToList(list);//不接收子类，编译错误
        addToListImprove(list);//addToListImprove 允许接收父类

    }


    //不接收父类
    private static void addToList(List<Apple> list) {

    }

    //接收父类
    private static void addToListImprove(List<? super Apple> list) {
        //只能用Object接收，有损失
        Apple apple = list.get(0);
        //如果传入确定的Apple类型，可以使用强转获取对象
        Apple apple2 = (Apple)list.get(0);
        //用Object接收不会编译错误
        Object object = list.get(0);
    }

    //不接收子类
    static float getTotalPrice(List<Fruit> fruits){
        float total = 0;
        for (Fruit fruit:fruits) {
            total += fruit.getPrice();
        }
        return total;
    }

    //接收子类
    static float getTotalPriceImprove(List<? extends Fruit> fruits){
        return getTotalPrice((List<Fruit>) fruits);
    }

}
