package com.dsh.txlessons.generics.charapter4;

import com.dsh.txlessons.generics.basicuse.fruit.Apple;
import com.dsh.txlessons.generics.charapter3.Shop;

/**
 * @author dongshuhuan
 * date 2020/11/13
 * version
 */
class ComparableShop implements Shop<Apple> {
    @Override public Apple buy() {
        return null;
    }

    @Override public float refund(Apple item) {
        return 0;
    }

    @Override public <E> E tradeIn(E item, float money) {
        return null;
    }

    @Override public <R> R take() {
        return null;
    }

    /**
     *
     * @param old 被比较的旧对象
     * @return 当前对象大于被比较的旧对象 返回正数
     *          等于                   返回0
     *          小于                   返回负数
     */
    @Override
    public int compareTo(Shop<Apple> old) {
        return this.refund(new Apple(2f))>old.refund(new Apple(3f))?1:-1;
    }
}
