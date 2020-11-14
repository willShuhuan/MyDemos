package com.dsh.txlessons.generics.basicuse;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
interface Shop<T> {
    T buy();
    float refund(T item);
}
