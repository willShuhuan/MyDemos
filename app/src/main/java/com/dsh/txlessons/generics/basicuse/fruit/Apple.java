package com.dsh.txlessons.generics.basicuse.fruit;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
public class Apple implements Fruit{

    float price;

    public Apple(float price) {
        this.price = price;
    }

    @Override
    public float getPrice() {
        return price;
    }
}
