package com.dsh.txlessons.generics.basicuse.fruit;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
public class Pear implements Fruit{
    float price;

    public Pear(float price) {
        this.price = price;
    }


    @Override
    public float getPrice() {
        return price;
    }
}
