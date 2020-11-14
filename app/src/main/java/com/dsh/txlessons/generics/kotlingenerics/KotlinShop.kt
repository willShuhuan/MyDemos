package com.dsh.txlessons.generics.kotlingenerics

/**
 * @author dongshuhuan
 * date 2020/11/14
 * version
 */
class KotlinShop<T>{
  fun buy():T{
    return null as T;
  }
  fun refund(item:T):Float{
    return 1f;
  }
}