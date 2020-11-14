package com.dsh.txlessons.generics.basicuse;

import com.dsh.txlessons.generics.basicuse.simcard.SimCard;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
//不管是实现还是继承都用extends，并且可以extends多个，
// 如果是多个并且有类，那么类必须写在第一个位置，并且只能继承一个类（java单继承特性）
public interface SimShop<T,C extends SimCard & Cloneable > extends Shop<T> {
    C getSim(String name,String id);
}
