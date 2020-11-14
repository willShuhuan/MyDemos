package com.dsh.txlessons.generics.basicuse;

import com.dsh.txlessons.generics.basicuse.fruit.Pear;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
class PearJuicingShop implements JuicingShop<Pear> {

    @Override
    public void juice(Pear item) {
        System.out.println("梨汁榨好了");
    }

    @Override public Pear buy() {
        return new Pear(2f);
    }

    @Override public float refund(Pear item) {
        return item.getPrice();
    }

    public static void main(String[] args) {
        PearJuicingShop reapairShop = new PearJuicingShop();
        reapairShop.juice(new Pear(2f));
    }

}
