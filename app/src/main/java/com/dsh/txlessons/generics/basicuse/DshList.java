package com.dsh.txlessons.generics.basicuse;

import java.util.Arrays;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
public class DshList<T> {
    private Object[] instances = new Object[0];

    public T get(int index) {
        return (T) instances[index];
    }

    public void set(int index,T instance) {
        this.instances[index] = instance;
    }

    public void add(T newInstance){
        instances = Arrays.copyOf(instances,instances.length+1);
        instances[instances.length-1] = newInstance;
    }

    @Override public String toString() {
        return Arrays.toString(instances);
    }

    public static void main(String[] args) {
        DshList<String> dshList = new DshList<>();
        dshList.add("aaa");
        dshList.add("bbb");
        dshList.add("cccc");
        System.out.println(dshList);//[aaa, bbb, cccc]
        System.out.println(dshList.get(2));//ccc
    }
}
