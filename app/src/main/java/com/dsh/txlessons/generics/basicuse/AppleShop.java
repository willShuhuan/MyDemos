package com.dsh.txlessons.generics.basicuse;

import com.dsh.txlessons.generics.basicuse.fruit.Apple;
import com.dsh.txlessons.generics.basicuse.fruit.Pear;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
//Shop<Apple>限制接口范围
class AppleShop implements Shop<Apple> {

    @Override public Apple buy() {
        return null;
    }

    @Override public float refund(Apple item) {
        return 0;
    }

    public static void main(String[] args) {
        //编译错误
        //Shop<Pear> appleShop = new AppleShop();
        //编译通过
        Shop<Apple> appleShop2 = new AppleShop();
    }
}
