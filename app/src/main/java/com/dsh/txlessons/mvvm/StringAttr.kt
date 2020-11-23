package com.dsh.txlessons.mvvm

/**
 * @author dongshuhuan
 * date 2020/11/20
 * version
 */
class StringAttr {
  var value:String?=null
  set(value) {
    field = value;
    onChangeListener?.onChange(value)
  }

  var onChangeListener:OnchangeListener?=null;

  interface OnchangeListener{
    fun onChange(newVal:String?);
  }
}