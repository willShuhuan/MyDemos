package com.dsh.txlessons.generics.charapter3;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
public interface Shop<T> extends Comparable<Shop<T>>{

    T buy();
    float refund(T item);
    //加钱换新
    <E> E tradeIn(E item,float money);
    //取货 <R> 返回类型 R 返回值
    <R> R take();
}
