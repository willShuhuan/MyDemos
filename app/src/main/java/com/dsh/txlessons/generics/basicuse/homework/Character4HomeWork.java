package com.dsh.txlessons.generics.basicuse.homework;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dongshuhuan
 * date 2020/11/13
 * version
 */

public class Character4HomeWork{

    public static void main(String[] args) {
        String s = "Character4HomeWork";
        List<String> list = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        merge(s,list);//类型相同，编译通过
        merge(s,list2);//类型不同，编译错误
    }

    public static  <P> void merge(P item, List<P> list){
        list.add(item);
    }
}

interface Character4Interface {
   <T extends Runnable & Serializable> void method(T param);
}
