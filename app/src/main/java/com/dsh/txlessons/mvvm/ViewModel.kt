package com.dsh.txlessons.mvvm

import android.widget.EditText

/**
 * @author dongshuhuan
 * date 2020/11/20
 * version
 */
class ViewModel (textView: EditText){

  var data:StringAttr = StringAttr()

  init {
    ViewBinder.bind(textView,data);
  }

  fun init(){
    val data = DataCenter.getData();
  }

}