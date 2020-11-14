package com.dsh.txlessons.generics.kotlingenerics

/**
 * @author dongshuhuan
 * date 2020/11/14
 * version
 */
class KotlinShop2<out T>{//declare-site variance 声明处
  fun buy():T{
    return null as T;
  }
}