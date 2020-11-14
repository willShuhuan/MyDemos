package com.dsh.txlessons.generics.basicuse.homework;

import com.dsh.txlessons.generics.basicuse.fruit.Apple;
import java.util.ArrayList;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
class AppleArrayList<T extends Apple> extends ArrayList {
    public static void main(String[] args) {
        AppleArrayList<Apple> appleList = new AppleArrayList();
        //编译错误
        //AppleArrayList<String> list = new AppleArrayList();
    }
}
