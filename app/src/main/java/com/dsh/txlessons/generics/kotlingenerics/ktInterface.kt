package com.dsh.txlessons.generics.kotlingenerics

import com.dsh.txlessons.generics.basicuse.fruit.Apple

/**
 * @author dongshuhuan
 * date 2020/11/15
 * version
 */
interface ktInterface<T> {
  fun buy():T;
}

class ktImpl:ktInterface<Apple>{
  override fun buy(): Apple {
    println("I buy an Apple");
    return Apple(1f);
  }

  fun main(){
    val ktApple: ktImpl = ktImpl();
    ktApple.buy();
  }

}
