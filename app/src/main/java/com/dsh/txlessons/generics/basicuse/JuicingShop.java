package com.dsh.txlessons.generics.basicuse;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
interface JuicingShop<T> extends Shop<T> {
    void juice(T item);
}
