package com.dsh.txlessons.generics.kotlingenerics

import com.dsh.txlessons.generics.basicuse.fruit.Apple
import com.dsh.txlessons.generics.basicuse.fruit.Fruit

/**
 * @author dongshuhuan
 * date 2020/11/14
 * version
 */
class KotlinTest {
  fun main(){
    //使用
    val ktShop:KotlinShop<Apple> = KotlinShop<Apple>();
    //out 等价于 java中 ? extends
    val ktShop2:KotlinShop<out Fruit> = KotlinShop<Apple>()
    //in 等价于 java中 ? super
    val ktShop3:KotlinShop<in Apple> = KotlinShop<Fruit>()
    //* 等价于 java中 ?
    val ktShop4:KotlinShop<*> = KotlinShop<Fruit>()


    //返回值为T , 参数没有T, 可以省略out
    val ktShop5:KotlinShop2<Fruit> = KotlinShop2<Apple>()// use-site variant 使用处
    //返回值没有T , 参数有T, 可以省略in
    val ktShop6:KotlinShop3<Apple> = KotlinShop3<Fruit>()// use-site variant 使用处

  }
}