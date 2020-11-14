package com.dsh.txlessons.generics.basicuse;

import com.dsh.txlessons.generics.basicuse.simcard.ChinaMobile;
import com.dsh.txlessons.generics.basicuse.simcard.ChinaUnicom;
import com.dsh.txlessons.generics.basicuse.simcard.SimCard;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
class MobileSimShop<T,C extends SimCard & Cloneable> implements SimShop<T,C> {
    @Override
    public C getSim(String name, String id) {
        return (C) new ChinaMobile(name,id);
    }

    @Override public T buy() {
        return null;
    }

    @Override public float refund(T item) {
        return 0;
    }

    public static void main(String[] args) {
        MobileSimShop<String, ChinaMobile> mobileSimShop = new MobileSimShop<>();
        ChinaMobile simCard = mobileSimShop.getSim("dsh", "330101xxxxxx");
        System.out.println(simCard);//中国电信电话卡{姓名：'dsh', 身份证号：'330101xxxxxx', 余额：'988935'}
    }
}
