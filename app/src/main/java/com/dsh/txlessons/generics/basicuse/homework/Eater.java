package com.dsh.txlessons.generics.basicuse.homework;

/**
 * @author dongshuhuan
 * date 2020/11/12
 * version
 */
interface Eater<T extends Food> {
    void eat(T food);
}

class EaterDemo {
    public static void main(String[] args) {
        //Eater<String> eater = new Eater<String>() {
        //    @Override public void eat(String food) {
        //
        //    }
        //};
        Eater<Food> eater2 = new Eater<Food>() {
            @Override public void eat(Food food) {

            }
        };
    }
}