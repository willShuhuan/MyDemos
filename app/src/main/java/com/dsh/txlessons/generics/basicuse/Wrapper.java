package com.dsh.txlessons.generics.basicuse;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
public class Wrapper<T> {
    private T instance;

    public T get() {
        return instance;
    }

    public void set(T instance) {
        this.instance = instance;
    }
}
