package com.dsh.txlessons.generics.basicuse.homework;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
class ReverseableArrayList<T> extends ArrayList<T> {
    public void reverse(){
        Collections.reverse(this);
    }

    public static void main(String[] args) {
        ReverseableArrayList<Integer> list = new ReverseableArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list);//[1, 2, 3]
        list.reverse();
        System.out.println(list);//[3, 2, 1]
    }
}
