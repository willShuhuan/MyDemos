package com.dsh.txlessons.generics.kotlingenerics

/**
 * @author dongshuhuan
 * date 2020/11/14
 * version
 */
class KotlinShop3<in T>{//declare-site variance 声明处
  fun refund(item:T):Float{
    return 1f;
  }
}